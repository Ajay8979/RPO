package com.ojas.rpo.security.dao.candidateReqMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ClientsListTransfer;
import com.ojas.rpo.security.util.CandidateStatusCounts;

public class JpaCandidateReqMappingDao extends JpaDao<CandidateMapping, Long> implements CandidateReqMappingDao {

	public JpaCandidateReqMappingDao() {
		super(CandidateMapping.class);
	}

	@Override
	public List<CandidateMapping> getCandiateByRecurtierId(Long recutierId, String status) {
		// TODO Auto-generated method stub

		/*
		 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		 * CriteriaQuery<CandidateMapping> cq = cb.createQuery(CandidateMapping.class);
		 * Root<CandidateMapping> e = cq.from(CandidateMapping.class);
		 * Join<CandidateMapping, CandidateMapping> can = e.join("candidate",
		 * JoinType.LEFT); can.alias("candita"); Join<CandidateMapping,
		 * CandidateMapping> r = e.join("candita.user", JoinType.LEFT); Predicate p =
		 * cb.and(cb.equal(r.get("id"), recutierId),cb.equal(can.get("status"),
		 * status)); cq.where(p); TypedQuery<CandidateMapping> tq =
		 * getEntityManager().createQuery(cq);
		 */
		String hql = " FROM CandidateMapping e left join e.candidate can left join can.user user left join e.bdmReq where user.id="
				+ recutierId + " and can.status = '" + status + "'";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();

	}

	@Override
	public List<CandidateMapping> getCandiateMapCandedateId(Long candidateId) {
		// TODO Auto-generated method stub

		/*
		 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		 * CriteriaQuery<CandidateMapping> cq = cb.createQuery(CandidateMapping.class);
		 * Root<CandidateMapping> e = cq.from(CandidateMapping.class);
		 * Join<CandidateMapping, CandidateMapping> can = e.join("candidate",
		 * JoinType.LEFT); can.alias("candita"); Join<CandidateMapping,
		 * CandidateMapping> r = e.join("candita.user", JoinType.LEFT); Predicate p =
		 * cb.and(cb.equal(r.get("id"), recutierId),cb.equal(can.get("status"),
		 * status)); cq.where(p); TypedQuery<CandidateMapping> tq =
		 * getEntityManager().createQuery(cq);
		 */
		String hql = " FROM CandidateMapping e left join e.candidate can left join can.user user left join e.bdmReq where can.id="
				+ candidateId;
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();

	}

	@Override
	public List<CandidateMapping> getCandiateByRequirementId(Long requimentId, String candidateStatus,
			String reqStatus) {
		// TODO Auto-generated method stub

		String hql = " FROM CandidateMapping e left join e.candidate can left join can.user user left join e.bdmReq req where req.id="
				+ requimentId + " and can.status = '" + candidateStatus + "' and req.status='" + reqStatus + "'";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();

	}

	@Override
	public List<CandidateStatusCounts> getCandiateStatusByRequirementId(Long requimentId) {
		// TODO Auto-generated method stub

		String hql = "select count(*),can.candidateStatus,user.name,req.id,req.nameOfRequirement,req.client.clientName FROM CandidateMapping e left join e.candidate can left join can.user user left join e.bdmReq req where req.id="
				+ requimentId + " group by can.candidateStatus,user.name,req.id,req.client.clientName";
		Query query = getEntityManager().createQuery(hql);
		List<Object[]> results = query.getResultList();

		List<CandidateStatusCounts> candidateStatusCountsList = new ArrayList<CandidateStatusCounts>();
		for (Object obj[] : results) {

			CandidateStatusCounts candidateStatusCounts = new CandidateStatusCounts();
			candidateStatusCounts.setStatuscount(obj[0].toString());
			candidateStatusCounts.setCandidateStatus(obj[1].toString());
			candidateStatusCounts.setRecruitername(obj[2].toString());
			candidateStatusCounts.setId(obj[3].toString());
			candidateStatusCounts.setNameOfRequirement(obj[4].toString());
			candidateStatusCounts.setClientname(obj[5].toString());
			candidateStatusCountsList.add(candidateStatusCounts);
		}
		return candidateStatusCountsList;

	}

	@Override
	public List<CandidateStatusCounts> getCandiateStatusByRequirements() {
		// TODO Auto-generated method stub

		String hql = "select count(*),can.candidateStatus,user.name,req.id,req.nameOfRequirement,req.client.clientName FROM CandidateMapping e left join e.candidate can left join can.user user left join e.bdmReq req  group by can.candidateStatus,user.name,req.id ,req.client.clientName";
		Query query = getEntityManager().createQuery(hql);
		List<Object[]> results = query.getResultList();

		List<CandidateStatusCounts> candidateStatusCountsList = new ArrayList<CandidateStatusCounts>();
		for (Object obj[] : results) {

			CandidateStatusCounts candidateStatusCounts = new CandidateStatusCounts();
			candidateStatusCounts.setStatuscount(obj[0].toString());
			candidateStatusCounts.setCandidateStatus(obj[1].toString());
			candidateStatusCounts.setRecruitername(obj[2].toString());
			candidateStatusCounts.setId(obj[3].toString());
			candidateStatusCounts.setNameOfRequirement(obj[4].toString());
			candidateStatusCounts.setClientname(obj[5].toString());
			candidateStatusCountsList.add(candidateStatusCounts);
		}
		return candidateStatusCountsList;

	}

	@Override
	public List<CandidateStatusCounts> getCandiatesStatusCountByBdmReqId() {
		String sql = "select cn.bdmReq.id ,cn.bdmReq.nameOfRequirement, count(*),candidateStatus from CandidateMapping cn  group by cn.candidateStatus ,cn.bdmReq.id ,cn.bdmReq.nameOfRequirement";
		Query query = getEntityManager().createNativeQuery(sql);
		List<CandidateStatusCounts> list = new ArrayList<CandidateStatusCounts>();
		List<Object[]> results = query.getResultList();
		for (Object Obj[] : results) {
			CandidateStatusCounts candidateStatusCounts = new CandidateStatusCounts();
			candidateStatusCounts.setStatuscount(Obj[0] + "");
			candidateStatusCounts.setCandidateStatus(Obj[1] + "");
			list.add(candidateStatusCounts);
		}
		return list;
	}

	@Override
	public void getRemoveMapping(Long id) {
		String sql = " DELETE FROM candidatemapping  WHERE candidate_id =" + id;
		Query query = getEntityManager().createNativeQuery(sql);
		query.executeUpdate();

	}

	@Override
	@Transactional(readOnly = true)
	public List<CandidateData> findAllCanditaes(String role, Long loginId) {

		String hql = null;
		Query query = null;
		List<CandidateData> candidateStatusCountsList = null;
		if (role.equalsIgnoreCase("BDM") || role.equalsIgnoreCase("AM")) {

			hql = "select can.candidate.id,can.bdmReq.id ,can.bdmReq.client.clientName,can.candidate.submittionDate, can.candidate.candidateStatus,can.candidate.firstName,can.candidate.lastName ,can.bdmReq.nameOfRequirement ,can.candidate.user.email "
					+ " ,can.candidate.offereStatus FROM CandidateMapping can where can.bdmReq.client.primaryContact.id=? "
					+ " or can.bdmReq.client.secondaryContact.id=?";

			if (role.equalsIgnoreCase("AM")) {
				hql = "select can.candidate.id,can.bdmReq.id ,can.bdmReq.client.clientName,can.candidate.submittionDate, can.candidate.candidateStatus,can.candidate.firstName,can.candidate.lastName ,can.bdmReq.nameOfRequirement,can.candidate.user.email"
						+ ",can.candidate.offereStatus FROM CandidateMapping can where can.bdmReq.client.accountManger.id=? ";

			}
			query = getEntityManager().createQuery(hql);
			if (role.equalsIgnoreCase("BDM")) {

				query.setParameter(1, loginId);
				query.setParameter(2, loginId);
			} else {
				query.setParameter(1, loginId);
			}
			List<Object[]> results = query.getResultList();

			candidateStatusCountsList = new ArrayList<CandidateData>();
			for (Object obj[] : results) {
				CandidateData candidateData = new CandidateData();
				candidateData.setCandidateid(obj[0].toString());
				candidateData.setBdmReqId(obj[1].toString());
				candidateData.setClientName(obj[2].toString());
				candidateData.setCandidateSubmitionDate(obj[3].toString());
				candidateData.setCandidateStatus(obj[4] + "");
				candidateData.setCandidateName(obj[5].toString() + " " + obj[6].toString());
				candidateData.setNameOfTheReq(obj[7].toString());
				candidateData.setNameOftheRecruiter(obj[8].toString());
				candidateData.setOfferStatus(obj[9] + "");
				candidateStatusCountsList.add(candidateData);
			}
			return candidateStatusCountsList;
		} else {
			if (role.equalsIgnoreCase("recruiter")) {
				hql = "select can.candidate.id,can.bdmReq.id ,can.bdmReq.client.clientName,can.candidate.submittionDate, can.candidate.candidateStatus,can.candidate.firstName,can.candidate.lastName"
						+ " , can.bdmReq.nameOfRequirement,can.candidate.user.email ,can.candidate.offereStatus FROM CandidateMapping can where can.candidate.user.id="
						+ loginId + " and can.candidate.candidateStatus!='Created'";
			}
			if (role.equalsIgnoreCase("Lead")) {
				hql = "select can.candidate.id,can.bdmReq.id ,can.bdmReq.client.clientName,can.candidate.submittionDate, can.candidate.candidateStatus,can.candidate.firstName,can.candidate.lastName"
						+ " , can.bdmReq.nameOfRequirement,can.candidate.user.email ,can.candidate.offereStatus FROM CandidateMapping can where   can.candidate.candidateStatus!='Created' and (can.candidate.user.id="
						+ loginId + " or can.candidate.user.reportingId=" + loginId + ")";

			}
			query = getEntityManager().createQuery(hql);
			List<Object[]> results = query.getResultList();

			candidateStatusCountsList = new ArrayList<CandidateData>();

			for (Object obj[] : results) {
				CandidateData candidateData = new CandidateData();
				candidateData.setCandidateid(obj[0].toString());
				candidateData.setBdmReqId(obj[1].toString());
				candidateData.setClientName(obj[2].toString());
				candidateData.setCandidateSubmitionDate(obj[3].toString());
				candidateData.setCandidateStatus(obj[4].toString());
				candidateData.setCandidateName(obj[5].toString() + " " + obj[6].toString());
				candidateData.setNameOfTheReq(obj[7].toString());
				candidateData.setNameOftheRecruiter(obj[8].toString());
				candidateData.setOfferStatus(obj[9] + "");
				candidateStatusCountsList.add(candidateData);
			}
			return candidateStatusCountsList;
		}

	}

	@Override
	public Response getCandidatesList(String role, Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {
		Response response = null;
		List<CandidateData> reqList = new ArrayList<CandidateData>();
		List<Object[]> searchList = null;
		String sortOrder = "DESC";
		String sortField = "candidateid";
		Integer totalRecords = 0;

		Query countQuery = null;
		Query query = null;

		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		}

		if (null != sortingField && !searchField.equals("undefined")) {
			if (sortingField.equalsIgnoreCase("bdmReqId") || "bdmReqId".contains(sortingField)) {
				sortField = "bdmReqId";
			} else if (sortingField.equalsIgnoreCase("clientName") || "clientName".contains(sortingField)) {
				sortField = "c.clientName";
			} else if (sortingField.equalsIgnoreCase("candidateSubmitionDate")
					|| "candidateSubmitionDate".contains(sortingField)) {
				sortField = "candidateSubmitionDate";
			} else if (sortingField.equalsIgnoreCase("candidateStatus") || "candidateStatus".contains(sortingField)) {
				sortField = "can.candidateStatus";
			} else if (sortingField.equalsIgnoreCase("candidateName") || "candidateName".contains(sortingField)) {
				sortField = "CONCAT(can.firstName," + "' '" + ",can.lastName)";
			} else if (sortingField.equalsIgnoreCase("nameOfRequirement")
					|| "nameOfRequirement".contains(sortingField)) {
				sortField = "req.nameOfRequirement";
			} else if (sortingField.equalsIgnoreCase("nameOftheRecruiter")
					|| "nameOftheRecruiter".contains(sortingField)) {
				sortField = "u.name";
			} else if (sortingField.equalsIgnoreCase("offerStatus") || "offerStatus".contains(sortingField)) {
				sortField = "can.offereStatus";
			}

		}

		if (null != searchField && !searchField.equals("undefined")) {
			if (searchField.equalsIgnoreCase("bdmReqId") || "bdmReqId".contains(searchField)) {
				searchField = "candmap.bdmReq_id";
			} else if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
				searchField = "c.clientName";
			} else if (searchField.equalsIgnoreCase("candidateSubmitionDate")
					|| "candidateSubmitionDate".contains(searchField)) {
				searchField = "can.submittionDate";
			} else if (searchField.equalsIgnoreCase("candidateStatus") || "candidateStatus".contains(searchField)) {
				searchField = "can.candidateStatus";
			} else if (searchField.equalsIgnoreCase("candidateName") || "candidateName".contains(searchField)) {
				searchField = "CONCAT(can.firstName,' ',can.lastName)";
			} else if (searchField.equalsIgnoreCase("nameOfTheReq") || "nameOfRequirement".contains(searchField)) {
				searchField = "req.nameOfRequirement";
			} else if (searchField.equalsIgnoreCase("nameOftheRecruiter")
					|| "nameOftheRecruiter".contains(searchField)) {
				searchField = "u.name";
			} else if (searchField.equalsIgnoreCase("offerStatus") || "offerStatus".contains(searchField)) {
				searchField = "can.offereStatus";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		String sql = "SELECT candmap.candidate_id AS candidateid, candmap.bdmReq_id AS bdmReqId, c.clientName AS clientName, can.submittionDate AS candidateSubmitionDate,"
				+ "can.candidateStatus AS candidateStatus, CONCAT(can.firstName," + "' '"
				+ ",can.lastName) AS candidateName, req.nameOfRequirement AS nameOfTheReq,"
				+ "u.name AS nameOftheRecruiter, can.offereStatus AS offerStatus "
				+ "FROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u "
				+ "WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id  AND u.id=can.user_id";
		StringBuilder sqlbuilder = new StringBuilder(sql);

		String countSql = " SELECT count(*) FROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u "
				+ "  WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id AND can.user_id=u.id ";
        StringBuilder countSqlBuilder = new StringBuilder(countSql);
		try {
			if (role.equalsIgnoreCase("BDM")) {

				System.out.println("Entered in bdm condition");

				if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
					sqlbuilder.append(" AND (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField
							+ " LIKE '%" + searchText + "%' ORDER BY " + sortField + " " + sortOrder + " LIMIT "
							+ startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(" AND (c.primaryContact_id=? OR c.secondaryContact_id=?) ORDER BY " + sortField
							+ " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				System.out.println(sqlbuilder.toString());
				query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
				query.setParameter(2, id);

				countQuery = this.getEntityManager().createNativeQuery(countSqlBuilder.append(" AND (c.primaryContact_id=? OR c.secondaryContact_id=?) ").toString());
				countQuery.setParameter(1, id);
				countQuery.setParameter(2, id);
			} else if (role.equalsIgnoreCase("RECRUITER")) {
				if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
					sqlbuilder.append(" AND can.user_id=?  AND " + searchField + " LIKE '%" + searchText
							+ "%'  ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(" AND can.user_id=? ORDER BY " + sortField + " " + sortOrder + " LIMIT "
							+ startingRow + "," + pageSize);
				}
				System.out.println(sqlbuilder.toString());
				query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);

				countSql = countSql + " AND can.user_id=? ";
				countQuery = this.getEntityManager().createNativeQuery(countSqlBuilder.append(" AND can.user_id=?  ").toString());
				countQuery.setParameter(1, id);

			} else if (role.equalsIgnoreCase("AM")) {
				if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
					sqlbuilder.append(" AND c.accountManger_id=?  AND " + searchField + " LIKE '%" + searchText
							+ "%'ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(" AND c.accountManger_id=? ORDER BY " + sortField + " " + sortOrder + " LIMIT "
							+ startingRow + "," + pageSize);
				}
				System.out.println(sqlbuilder.toString());
				query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);

				countQuery = this.getEntityManager().createNativeQuery(countSqlBuilder.append(" AND c.accountManger_id=? ").toString());
				countQuery.setParameter(1, id);
			} else if (role.equalsIgnoreCase("LEAD")) {
				if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%'ORDER BY " + sortField + " "
							+ sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}
				System.out.println(sqlbuilder.toString());
				query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());

				countQuery = this.getEntityManager().createNativeQuery(countSqlBuilder.toString());

			} else {
				sqlbuilder
						.append(" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				System.out.println(sqlbuilder.toString());
				query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());

				countQuery = this.getEntityManager().createNativeQuery(countSqlBuilder.toString());
			}

			searchList = query.getResultList();
			if (searchList.isEmpty()) {
				return new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : searchList) {
					CandidateData transferObj = new CandidateData();
					transferObj.setCandidateid(req[0].toString());
					transferObj.setBdmReqId(req[1].toString());
					transferObj.setClientName((String) req[2]);
					transferObj.setCandidateSubmitionDate(req[3].toString());
					transferObj.setCandidateStatus((String) req[4]);
					transferObj.setCandidateName((String) req[5]);
					transferObj.setNameOfTheReq((String) req[6]);
					transferObj.setNameOftheRecruiter((String) req[7]);
					transferObj.setOfferStatus((String) req[8]);
					reqList.add(transferObj);
				}
				response = new Response(ExceptionMessage.Created, reqList);
			}

			System.out.println(countQuery.getSingleResult());
            totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			}

			Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
			totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
			response.setTotalPages(totalPages);

			return response;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Mapped Candidates List. " + " " + pe.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Retrieve Mapped Candidates " + " " + e.getLocalizedMessage());
		}

	}

	public Response searchCandidatesList(String role, Long id, String searchInput, String searchField, Integer pageNo,
			Integer pageSize) {

		List<CandidateData> reqList = new ArrayList<CandidateData>();
		List<Object[]> candidatesList = null;
		Query query = null;
		Integer totalRecords = 0;
		Response response = null;
		String sql = "SELECT candmap.candidate_id AS candidateid, candmap.bdmReq_id AS bdmReqId, c.clientName AS clientName, can.submittionDate AS candidateSubmitionDate,"
				+ "can.candidateStatus AS candidateStatus, CONCAT(can.firstName,' ',"
				+ "can.lastName) AS candidateName, req.nameOfRequirement AS nameOfTheReq,"
				+ "u.name AS nameOftheRecruiter, can.offereStatus AS offerStatus "
				+ "FROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u "
				+ "WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id AND u.id=can.user_id AND ";
		StringBuilder sqlBuilder = new StringBuilder(sql);

		String countQuery = "SELECT COUNT(*) FROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u "
				+ "WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id AND u.id=can.user_id AND ";

		if (searchField.equalsIgnoreCase("bdmReqId") || "bdmReqId".contains(searchField)) {
			searchField = "candmap.bdmReq_id";
		} else if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
			searchField = "c.clientName";
		} else if (searchField.equalsIgnoreCase("candidateSubmitionDate")
				|| "candidateSubmitionDate".contains(searchField)) {
			searchField = "can.submittionDate";
		} else if (searchField.equalsIgnoreCase("candidateStatus") || "candidateStatus".contains(searchField)) {
			searchField = "can.candidateStatus";
		} else if (searchField.equalsIgnoreCase("candidateName") || "candidateName".contains(searchField)) {
			searchField = "CONCAT(can.firstName,' ',can.lastName)";
		} else if (searchField.equalsIgnoreCase("nameOfTheReq") || "nameOfRequirement".contains(searchField)) {
			searchField = "req.nameOfRequirement";
		} else if (searchField.equalsIgnoreCase("nameOftheRecruiter") || "nameOftheRecruiter".contains(searchField)) {
			searchField = "u.name";
		} else if (searchField.equalsIgnoreCase("offerStatus") || "offerStatus".contains(searchField)) {
			searchField = "can.offereStatus";
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (null != id && null != role) {
				if (role.equalsIgnoreCase("BDM")) {

					sqlBuilder.append(" (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField
							+ " LIKE '%" + searchInput + "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);
					query.setParameter(2, id);

				} else if (role.equalsIgnoreCase("AM")) {
					sqlBuilder.append(" c.accountManger_id = ? AND " + searchField + " LIKE '%" + searchInput
							+ "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);

					countQuery = countQuery + " AND c.accountManger_id = ?";
					Query countQ = this.getEntityManager().createNativeQuery(countQuery);
					countQ.setParameter(1, id);

				} else if (role.equalsIgnoreCase("RECRUITER")) {
					sqlBuilder.append(" can.user_id=? AND " + searchField + " LIKE '%" + searchInput
							+ "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
					query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
					query.setParameter(1, id);

					countQuery = countQuery + " AND can.user_id = ?";
					Query countQ = this.getEntityManager().createNativeQuery(countQuery);
					countQ.setParameter(1, id);
				} else if (role.equalsIgnoreCase("lead")) {
					Query countQ = this.getEntityManager().createNativeQuery(countQuery);
				}

			} else {
				sqlBuilder.append(searchField + " LIKE '%" + searchInput + "%' ORDER BY req.id DESC LIMIT "
						+ startingRow + "," + pageSize);
				query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
			}

			candidatesList = query.getResultList();
			// totalRecords = candidatesList.size();
			if (candidatesList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
			} else {
				for (Object[] req : candidatesList) {
					CandidateData transferObj = new CandidateData();
					transferObj.setCandidateid(req[0].toString());
					transferObj.setBdmReqId(req[1].toString());
					transferObj.setClientName((String) req[2]);
					transferObj.setCandidateSubmitionDate(req[3].toString());
					transferObj.setCandidateStatus((String) req[4]);
					transferObj.setCandidateName((String) req[5]);
					transferObj.setNameOfTheReq((String) req[6]);
					transferObj.setNameOftheRecruiter((String) req[7]);
					transferObj.setOfferStatus((String) req[8]);
					reqList.add(transferObj);
				}
				response = new Response(ExceptionMessage.OK, reqList);
			}

			totalRecords = Integer
					.valueOf(this.getEntityManager().createNativeQuery(countQuery).getSingleResult().toString());
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
					"Unknown Column '" + searchField + "' .  Enter Correct Column Name. " + pe.getLocalizedMessage());

		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					" Unable to Search Candidates " + e.getLocalizedMessage());
		}
	}
}
