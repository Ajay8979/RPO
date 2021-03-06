package com.ojas.rpo.security.dao.bdmreqdtls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.SortOrder;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.RequirementListTranfer;
import com.ojas.rpo.security.util.CandidateStatusCounts;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaBdmReqDao extends JpaDao<BdmReq, Long> implements BdmReqDao {
	public JpaBdmReqDao() {
		super(BdmReq.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BdmReq> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<BdmReq> criteriaQuery = builder.createQuery(BdmReq.class);

		Root<BdmReq> root = criteriaQuery.from(BdmReq.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<BdmReq> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Response findreqByClientId(Long id, String status, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {
        Response response = null;
        Integer totalRecords = 0;
		StringBuilder sql = new StringBuilder(
				"SELECT br.id AS reqId, ac.contact_Name,ac.email, ac.mobile, br.client_id, br.date, br.endDate, br.famount, br.jobDes, br.maxBudget, "
						+ "br.maxBudgetRate, br.minBudget, br.minBudgetRate, br.nameOfRequirement, "
						+ "br.newtype, br.noticePeriod, br.numberOfPositions, br.relavantExperience, br.requirementDescription, "
						+ "br.requirementEndDate, br.requirementStartdate, br.requirementStatus, br.requirementType, br.startDate, "
						+ "br.status, br.typeOfHiring "
						+ "FROM `bdmreq` br LEFT JOIN `client` c on br.client_id=c.id INNER JOIN `addcontact` ac on br.addContact_id=ac.id WHERE c.id=" + id
						+ " AND br.status='"+status+"'");

		StringBuilder countSql = new StringBuilder(
				" SELECT count(*) FROM `bdmreq` br left outer join `client` c on br.client_id=c.id WHERE c.id=" + id
						+ " and br.status='open'");
		/*
		 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		 * CriteriaQuery<BdmReq> cq = cb.createQuery(BdmReq.class); Root<BdmReq> e =
		 * cq.from(BdmReq.class); Join<BdmReq, BdmReq> r = e.join("client",
		 * JoinType.LEFT); Predicate predicates = cb.and(cb.equal(r.get("id"), id),
		 * cb.equal(e.get("status"), status)); cq.where(predicates); TypedQuery<BdmReq>
		 * tq = getEntityManager().createQuery(cq); return tq.getResultList();
		 */
		
		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("ASC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}
		
		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if(sortingField.equalsIgnoreCase("nameOfRequirement")) {
				sortingField = "br.nameOfRequirement";
			}
			if(sortingField.equalsIgnoreCase("spocName") || sortingField.equalsIgnoreCase("contactName")) {
				sortingField = "ac.contact_Name";
			}
			if(sortingField.equalsIgnoreCase("requirementStatus")) {
				sortingField = "br.requirementStatus";
			}
			if(sortingField.equalsIgnoreCase("email")) {
				sortingField = "ac.email,";
			}
			if(sortingField.equalsIgnoreCase("requirementStartdate")) {
				sortingField = "br.requirementStartdate";
			}
			if(sortingField.equalsIgnoreCase("requirementEndDate")) {
				sortingField = "br.requirementEndDate";
			}
		} else {
			sortingField = "br.nameOfRequirement";
		}
		
		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {
			if(searchField.equalsIgnoreCase("nameOfRequirement")) {
				searchField = "br.nameOfRequirement";
			}
			if(searchField.equalsIgnoreCase("spocName") || searchField.equalsIgnoreCase("contactName")) {
				searchField = "ac.contact_Name";
			}
			if(searchField.equalsIgnoreCase("requirementStatus")) {
				searchField = "br.requirementStatus";
			}
			if(searchField.equalsIgnoreCase("email")) {
				searchField = "ac.email,";
			}
		}
		
		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		
		if (null != searchField && null != searchText && !searchField.equals("undefined")
				&& !searchText.equals("undefined") && !searchText.isEmpty()) {
			sql.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortingField + " "
					+ sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		} else {
			sql.append(" ORDER BY " + sortingField + " " + sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		}
		
		Query query = this.getEntityManager().createNativeQuery(sql.toString());
		Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
		System.out.println("Query = " + sql.toString());
		
		try {
			List<Object[]> results = query.getResultList();
			List<RequirementListTranfer> result = new ArrayList<RequirementListTranfer>();
			if(results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Requirements Found");
			}else {
				for(Object[] data : results) {
					RequirementListTranfer req = new RequirementListTranfer();
					
					req.setId(Long.valueOf(data[0].toString()));
					req.setContact_Name(data[1].toString());
					req.setEmail(data[2].toString());
					req.setMobile(data[3].toString());
					if(null != data[4]) {
						req.setClientId(Long.valueOf(data[4].toString()));
					}
					req.setNameOfRequirement(data[13].toString());
					req.setStatus(data[24].toString());
					req.setStartDate((Date)data[20]);
					req.setEndDate((Date)data[19]);
					result.add(req);
				}
				
				response = new Response(ExceptionMessage.OK, result);
			}
			
			totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
			response.setTotalRecords(totalRecords);

			if (totalRecords == 0) {
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable Retrieve Requirements due to : "+e.getMessage());
		}
		
		return response;
	}

	@Override
	public Map<String, Integer> getReqStatusListByCount() {

		Query q = getEntityManager().createNativeQuery("select status ,count(*) from BdmReq group by status");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BdmReq> findreqByStatus(String status) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BdmReq> cq = cb.createQuery(BdmReq.class);
		Root<BdmReq> e = cq.from(BdmReq.class);
		Join<BdmReq, BdmReq> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.equal(r.get("status"), status);
		cq.where(p);
		TypedQuery<BdmReq> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	public List<BdmReq> getBdmReqByRole(Long id, String role) {

		Query query = null;
		List<BdmReq> requirementsList = null;

		if (role.equalsIgnoreCase("BDM")) {
			query = getEntityManager().createQuery(
					"from BdmReq req where req.client.primaryContact.id=? or req.client.secondaryContact.id=? order by id desc");
			query.setParameter(1, id);
			query.setParameter(2, id);
			requirementsList = query.getResultList();
		} else {
			query = getEntityManager()
					.createQuery("from BdmReq req where req.client.accountManger.id=? order by id desc");
			query.setParameter(1, id);
			requirementsList = query.getResultList();
		}

		return requirementsList;
	}

	@Override
	public Response getBdmReqirementsByRole(Long id, String role, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {
		Response response = null;
		List<RequirementListTranfer> reqList = new ArrayList<RequirementListTranfer>();
		List<Object[]> requirementsList = null;
		Integer totalRecords = 0;
		String sortOrder = "DESC";
		String sortField = "req.id";
		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined")) {
			if (sortingField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(sortingField)) {
				sortField = "req.nameOfRequirement";
			} else if (sortingField.equalsIgnoreCase("clientName") || "clientName".contains(sortingField)) {
				sortField = "c.clientName";
			} else if (sortingField.equalsIgnoreCase("startDate") || "startDate".contains(sortingField)) {
				sortField = "req.startDate";
			} else if (sortingField.equalsIgnoreCase("EndDate") || "EndDate".contains(sortingField)) {
				sortField = "req.EndDate";
			} else if ((sortingField.equalsIgnoreCase("contact_Name") || sortingField.equalsIgnoreCase("spocName"))
					|| ("spocname".contains(sortingField) || "contact_Name".contains(sortingField))) {
				sortField = "cont.contact_Name";
			} else if (sortingField.equalsIgnoreCase("mobile") || "mobile".contains(sortingField)) {
				sortField = "cont.mobile";
			} else if (sortingField.equalsIgnoreCase("status") || "status".contains(sortingField)) {
				sortField = "req.status";
			}

		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(searchField)) {
				searchField = "req.nameOfRequirement";
			}
			if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
				searchField = "c.clientName";
			}
			if (searchField.equalsIgnoreCase("startDate") || "startDate".contains(searchField)) {
				searchField = "req.startDate";
			}
			if (searchField.equalsIgnoreCase("endDate") || "endDate".contains(searchField)) {
				searchField = "req.EndDate";
			}
			if (searchField.equalsIgnoreCase("contact_Name") || "contact_Name".contains(searchField)) {
				searchField = "cont.contact_Name";
			}
			if (searchField.equalsIgnoreCase("mobile") || "mobile".contains(searchField)) {
				searchField = "cont.mobile";
			}
			if (searchField.equalsIgnoreCase("status") || "status".contains(searchField)) {
				searchField = "req.status";
			}

		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		Query query = null;
		try {
			if (role.equalsIgnoreCase("BDM")) {
				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					query = getEntityManager().createNativeQuery(
							"SELECT req.id,req.nameOfRequirement,c.clientName,c.id as clientId,req.requirementEndDate ,req.requirementStartdate,cont.contact_Name,cont.mobile,req.status,l.locationName FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND (c.primaryContact_id=? OR c.secondaryContact_id=?) AND "
									+ searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
									+ sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					query = getEntityManager().createNativeQuery(
							"SELECT req.id,req.nameOfRequirement,c.clientName,c.id as clientId,req.requirementEndDate ,req.requirementStartdate,cont.contact_Name,cont.mobile,req.status,l.locationName FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND (c.primaryContact_id=? OR c.secondaryContact_id=?) ORDER BY "
									+ sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				query.setParameter(1, id);
				query.setParameter(2, id);
				requirementsList = query.getResultList();

				String countSql = "SELECT COUNT(*) FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND (c.primaryContact_id=? OR c.secondaryContact_id=?)";
				Query countQuery = this.getEntityManager().createNativeQuery(countSql);
				countQuery.setParameter(1, id);
				countQuery.setParameter(2, id);
				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());

			} else {
				if (null != searchField && null != searchText && !searchField.equalsIgnoreCase("undefined")
						&& !searchText.equalsIgnoreCase("undefined")) {
					query = getEntityManager().createNativeQuery(
							"SELECT req.id,req.nameOfRequirement,c.clientName, c.id as clientId,req.requirementEndDate ,req.requirementStartdate,cont.contact_Name,cont.mobile,req.status,l.locationName FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND c.accountManger_id = ? AND "
									+ searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
									+ sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					query = getEntityManager().createNativeQuery(
							"SELECT req.id,req.nameOfRequirement,c.clientName, c.id as clientId,req.requirementEndDate ,req.requirementStartdate,cont.contact_Name,cont.mobile,req.status,l.locationName FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND c.accountManger_id = ? ORDER BY "
									+ sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				query.setParameter(1, id);
				requirementsList = query.getResultList();

				String countSql = "SELECT COUNT(*) FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id = c.id AND c.accountManger_id = ?";
				Query countQuery = this.getEntityManager().createNativeQuery(countSql);
				countQuery.setParameter(1, id);
				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());
			}

			if (requirementsList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : requirementsList) {
					RequirementListTranfer transferObj = new RequirementListTranfer();
					transferObj.setId(Long.valueOf(req[0] + ""));
					transferObj.setNameOfRequirement((String) req[1]);
					transferObj.setClientName((String) req[2]);
					transferObj.setClientId(Long.valueOf(req[3] + ""));
					transferObj.setEndDate((Date) req[4]);
					transferObj.setStartDate((Date) req[5]);

					transferObj.setContact_Name((String) req[6]);
					transferObj.setMobile((String) req[7]);
					transferObj.setStatus((String) req[8]);
					transferObj.setLocationName((String) req[9]);

					reqList.add(transferObj);
				}
				response = new Response(ExceptionMessage.OK, reqList);
			}

			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

			return response;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Requirements. " + "  " + pe.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Retrieve Candidates " + e.getLocalizedMessage());
		}

	}

	@Override
	public Response searchRequirements(String role, Long id, String searchInput, String searchField, Integer pageNo,
			Integer pageSize) {
		Response response = null;
		Integer totalRecords = 0;
		List<RequirementListTranfer> reqList = new ArrayList<RequirementListTranfer>();
		List<Object[]> requirementsList = null;
		Query query = null;
		String sql = "SELECT req.id,req.nameOfRequirement,c.clientName,req.requirementEndDate ,req.requirementStartdate,cont.contact_Name,cont.mobile,req.status,l.locationName FROM `bdmreq` req CROSS JOIN `client` c  INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN `location` l ON req.location_id = l.id WHERE req.client_id=c.id ";
		StringBuilder sqlBuilder = new StringBuilder(sql);
		// String countQuery = " SELECT count(*) FROM `bdmreq` req CROSS JOIN `client` c
		// INNER JOIN `addcontact` cont ON req.addContact_id = cont.id INNER JOIN
		// `location` l ON req.location_id = l.id WHERE req.client_id=c.id ";
		if (searchField.equalsIgnoreCase("nameOfRequirement") || "nameOfRequirement".contains(searchField)) {
			searchField = "req.nameOfRequirement";
		}
		if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
			searchField = "c.clientName";
		}
		if (searchField.equalsIgnoreCase("startDate") || "startDate".contains(searchField)) {
			searchField = "req.startDate";
		}
		if (searchField.equalsIgnoreCase("endDate") || "endDate".contains(searchField)) {
			searchField = "req.EndDate";
		}
		if (searchField.equalsIgnoreCase("contact_Name") || "contact_Name".contains(searchField)) {
			searchField = "cont.contact_Name";
		}
		if (searchField.equalsIgnoreCase("mobile") || "mobile".contains(searchField)) {
			searchField = "cont.mobile";
		}
		if (searchField.equalsIgnoreCase("status") || "status".contains(searchField)) {
			searchField = "req.status";
		}
		if (searchField.equalsIgnoreCase("locationName") || "locationName".contains(searchField)) {
			searchField = "l.locationName";
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (null != id) {
				if (role.equalsIgnoreCase("BDM")) {
					sqlBuilder.append(
							"AND (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField + " LIKE '%"
									+ searchInput + "%' ORDER BY req.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);
					query.setParameter(2, id);
					// countQuery.concat(" AND (c.primaryContact_id=? OR c.secondaryContact_id=?)
					// ");
				} else {
					sqlBuilder.append(" AND c.accountManger_id = ? AND " + searchField + " LIKE '%" + searchInput
							+ "%' ORDER BY req.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);

					// countQuery.concat(" AND c.accountManger_id = ? ");
				}
			} else {
				sqlBuilder.append(" AND " + searchField + " LIKE '%" + searchInput + "%' ORDER BY req.id DESC LIMIT "
						+ startingRow + "," + pageSize);
				query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
			}

			requirementsList = query.getResultList();
			totalRecords = requirementsList.size();
			if (requirementsList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : requirementsList) {
					RequirementListTranfer transferObj = new RequirementListTranfer();
					transferObj.setId(Long.valueOf(req[0] + ""));
					transferObj.setNameOfRequirement((String) req[1]);
					transferObj.setClientName((String) req[2]);
					transferObj.setEndDate((Date) req[3]);
					transferObj.setStartDate((Date) req[4]);
					transferObj.setContact_Name((String) req[5]);
					transferObj.setMobile((String) req[6]);
					transferObj.setStatus((String) req[7]);
					transferObj.setLocationName((String) req[8]);
					reqList.add(transferObj);
				}
				response = new Response(ExceptionMessage.OK, reqList);
			}

			// totalRecords =
			// Integer.valueOf(this.getEntityManager().createNativeQuery(countQuery).getSingleResult().toString());

			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			}

			if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
				response.setTotalPages(1);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

			return response;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unknown Column '" + searchField + "' .  Enter Correct Column Name. " + pe.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Search Requirements " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<CandidateStatusCounts> getBdmReqCountAndStatus() {

		Map<String, Integer> map = new HashMap<String, Integer>();
		Query q = getEntityManager().createQuery("select count(*)," + " br.status" + ",br.nameOfRequirement,"
				+ " br.client.primaryContact.firstName , br.client.primaryContact.lastName ,br.client.primaryContact.id from BdmReq br group by  br.status ,br.nameOfRequirement ,"
				+ "br.client.primaryContact.firstName , br.client.primaryContact.lastName ,br.client.primaryContact.id"
				+ "");

		@SuppressWarnings("unchecked")
		List<Object[]> results = q.getResultList();
		List<CandidateStatusCounts> list = new ArrayList<CandidateStatusCounts>();
		if (results != null) {
			for (Object obj[] : results) {
				CandidateStatusCounts countAndstatus = new CandidateStatusCounts();
				countAndstatus.setStatuscount(obj[0].toString());
				countAndstatus.setBdmReqStatus(obj[1].toString());
				countAndstatus.setNameOfRequirement(obj[2].toString());
				countAndstatus.setRecruitername(obj[3].toString() + obj[4].toString());
				countAndstatus.setId(obj[5].toString());
				list.add(countAndstatus);
			}
		} else {
			return list;
		}

		return list;
	}

	@Override
	public List<BdmReq> BdmReqBasedOnPrimryBDM(Long id, String status) {
		Query q = getEntityManager().createQuery(" from BdmReq bd where bd.client.primaryContact.id=? and status=?");
		q.setParameter(1, id);
		q.setParameter(2, status);
		List<BdmReq> results = q.getResultList();

		return results;
	}

	/*
	 * @Override public List<Candidate> findCandidateByReqId() { CriteriaBuilder cb
	 * = getEntityManager().getCriteriaBuilder(); CriteriaQuery<Candidate> cq =
	 * cb.createQuery(Candidate.class); Root<Candidate> e =
	 * cq.from(Candidate.class); Join<Candidate, Candidate> r = e.join("",
	 * JoinType.LEFT); Predicate p = cb.equal(r.get("id"), id); cq.where(p);
	 * TypedQuery<BdmReq> tq = getEntityManager().createQuery(cq); return
	 * tq.getResultList();
	 * 
	 * return null; }
	 */

}
