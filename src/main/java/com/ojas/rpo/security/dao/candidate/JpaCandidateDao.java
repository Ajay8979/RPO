package com.ojas.rpo.security.dao.candidate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.CandidateListTransfer;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaCandidateDao extends JpaDao<Candidate, Long> implements CandidatelistDao {
	public JpaCandidateDao() {
		super(Candidate.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional
	public boolean updateCandiate(Long candiateId, String status) {
		boolean result = false;
		Query q = getEntityManager().createNativeQuery("update Candidate set candidateStatus=? where id =?");
		q.setParameter(1, status);
		q.setParameter(2, candiateId);
		int i = q.executeUpdate();
		if (i > 0) {
			return true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean chekduplicate(String mobile, String email, String pancardNumber) {
		// boolean result = false;

		Query query = getEntityManager()
				.createQuery("SELECT mobile,email,pancardNumber FROM Candidate  WHERE mobile=? OR email=?");

		query.setParameter(1, mobile);
		query.setParameter(2, email);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		if (results.isEmpty()) {
			return false;
		} else {
			return true;

		}
	}

	public Candidate getCandidateId(String pancardNumber) {

		Candidate cand = null;
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(this.entityClass);

		Root<Candidate> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("pancardNumber");
		criteriaQuery.where(builder.equal(namePath, pancardNumber));

		TypedQuery<Candidate> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Candidate> users = typedQuery.getResultList();

		if (users.size() > 0) {
			cand = (Candidate) users.get(0);
		}
		return cand;
	}

	public Response getAllCandidatesByAddedPerson(Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {

		List<CandidateListTransfer> result = new ArrayList<CandidateListTransfer>();
		Response response = null;
		Integer totalRecords = 0;
		Query query = null;
		StringBuilder sql = new StringBuilder(
				"select concat(can.firstName,' ',can.lastName) AS candidateName,can.email,can.mobile,can.skypeId,can.state,can.totalExperience,can.id,can.submittionDate from candidate can "
						+ "  where can.candidateStatus='Created' and can.user_id = ? ");

		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("ASC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("candidateName") || "candidateName".contains(sortingField)) {
				sortingField = "concat(can.firstName,' ',can.lastName)";
			} else if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField)) {
				sortingField = "can.email";
			} else if ((sortingField.equalsIgnoreCase("submissionDate")
					|| sortingField.equalsIgnoreCase("submissionDate"))) {
				sortingField = "can.submittionDate";
			} else if (sortingField.equalsIgnoreCase("totalExperience") || "totalExperience".contains(sortingField)) {
				sortingField = "can.totalExperience";
			}

		} else {
			sortingField = "concat(can.firstName,' ',can.lastName)";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("candidateName") || "candidateName".contains(searchField)) {
				searchField = "concat(can.firstName,' ',can.lastName)";
			} else if (searchField.equalsIgnoreCase("email") || "email".contains(searchField)) {
				searchField = "can.email";
			} else if (searchField.equalsIgnoreCase("totalExperience") || "totalExperience".contains(searchField)) {
				searchField = "can.totalExperience";
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

		try {
			query = this.getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, id);
			List<Object[]> results = query.getResultList();

			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Candidates Found");
			} else {
				for (Object[] can : results) {
					CandidateListTransfer candidate = new CandidateListTransfer();
					candidate.setCandidateName((String) can[0]);
					candidate.setEmail((String) can[1]);
					candidate.setMobile((String) can[2]);
					candidate.setSkypeId((String) can[3]);
					candidate.setState((String) can[4]);
					candidate.setTotalExperience((can[5].toString()));
					candidate.setCandidateId(Long.valueOf(can[6].toString()));
					candidate.setSubmissionDate(can[7].toString());
					result.add(candidate);
				}

				response = new Response(result, ExceptionMessage.OK);
				totalRecords = results.size();
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
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Requirements due to following Error : " + e.getMessage());
		}

		return response;

	}

	@Override
	public List<Candidate> getCandiateByRecurtierIdByStatus(Long recutierId, String status) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
		Root<Candidate> e = cq.from(Candidate.class);
		Join<Candidate, Candidate> r = e.join("user", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), recutierId), cb.equal(e.get("status"), status));

		cq.where(predicates);
		TypedQuery<Candidate> tq = getEntityManager().createQuery(cq);
		List<Candidate> resultList = tq.getResultList();
		return resultList;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Candidate> findAll(String status) {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(Candidate.class);

		Root<Candidate> root = criteriaQuery.from(Candidate.class);
		Predicate p = builder.notEqual(root.get("candidateStatus"), "Created");
		criteriaQuery.where(p);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Candidate> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<Candidate> getCandiateByRequirementId(Long requiremnetId) {
		Query query = null;
		List<Candidate> requirementsList = null;

		query = getEntityManager().createQuery(
				"select req.candidate from CandidateMapping req where req.bdmReq.id = ? order by req.id desc");
		query.setParameter(1, requiremnetId);
		requirementsList = query.getResultList();

		return requirementsList;

	}

	@Override
	public Response getCandiateByRequirementId(Long requiremnetId, Integer pageNo, Integer pageSize,
			String sortingOrder, String sortingField, String searchText, String searchField) {
		Response response = null;
		List<Candidate> requirementsList = null;
		Integer totalRecords = 0;
		/*
		 * query = getEntityManager().createQuery(
		 * "select req.candidate from CandidateMapping req where req.bdmReq.id = ? order by req.id desc"
		 * ); query.setParameter(1, requiremnetId); requirementsList =
		 * query.getResultList();
		 */

		StringBuilder sql = new StringBuilder(
				"SELECT c.id,concat(c.firstName,' ',c.lastName), c.submittionDate, c.candidateStatus, c.email, c.mobile FROM `candidatemapping` cm INNER JOIN `candidate` c ON cm.candidate_id=c.id WHERE cm.bdmReq_id = ? ");
		StringBuilder countSql = new StringBuilder(
				"SELECT count(*) FROM `candidatemapping` cm INNER JOIN `candidate` c ON cm.candidate_id=c.id WHERE cm.bdmReq_id = ? ");

		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("ASC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("candidateName")) {
				sortingField = "concat(c.firstName,' ',c.lastName)";
			}
			if (sortingField.equalsIgnoreCase("candidateStatus")) {
				sortingField = "c.candidateStatus";
			}
			if (sortingField.equalsIgnoreCase("c.submissionDate")) {
				sortingField = "c.submittionDate";
			}
			if (sortingField.equalsIgnoreCase("c.email")) {
				sortingField = "email";
			}
		} else {
			sortingField = "c.id";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("candidateName")) {
				searchField = "concat(c.firstName,' ',c.lastName)";
			}
			if (searchField.equalsIgnoreCase("candidateStatus")) {
				searchField = "c.candidateStatus";
			}
			if (searchField.equalsIgnoreCase("c.email")) {
				searchField = "email";
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

		try {
			Query query = this.getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, requiremnetId);
			Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
			countQuery.setParameter(1, requiremnetId);

			List<Object[]> results = query.getResultList();
			List<CandidateListTransfer> result = new ArrayList<CandidateListTransfer>();
			if(results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Requirements Found");
			}else {
				for(Object[] data: results) {
					CandidateListTransfer candidate = new CandidateListTransfer();
					
					if(null != data[0]){candidate.setCandidateId(Long.valueOf(data[0].toString()));}
					if(null != data[1]){candidate.setCandidateName(data[1].toString());}
					if(null != data[2]){candidate.setSubmissionDate(data[2].toString());}
					if(null != data[3]){candidate.setCandidateStatus(data[3].toString());}
					if(null != data[4]){candidate.setEmail(data[4].toString());}
					if(null != data[5]){candidate.setMobile(data[5].toString());}
					
					result.add(candidate);
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
			response = new Response(ExceptionMessage.Exception, "500", "Unable Retrieve Candidates due to : "+e.getMessage());
		}
		
		return response;

	}

	@Override
	public List<BdmReq> getRequiremenByCandiateId(Long candidateId) {
		// TODO Auto-generated method stub
		Query query = null;
		List<BdmReq> requirementsList = null;

		query = getEntityManager().createQuery(
				"select req.bdmReq from CandidateMapping req where req.candidate.id = ? order by req.id desc");
		query.setParameter(1, candidateId);
		requirementsList = query.getResultList();

		return requirementsList;

	}

	@Override
	public List<Candidate> getCandiateByRecurtierId(Long recutierId) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
		Root<Candidate> e = cq.from(Candidate.class);
		Join<Candidate, Candidate> r = e.join("user", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), recutierId);
		cq.where(p);
		TypedQuery<Candidate> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();

	}

	@Override
	public List<Candidate> getCandiateBySkillName(String skillName) {
		// TODO Auto-generated method stub

		Query query = getEntityManager()
				.createNativeQuery("select *  from " + " candidate c , skill s , skillcandidate sc where s.skillName="
						+ " ? and c.id = sc.candidate_ID " + " and c.id = sc.SKILL_ID ");
		query.setParameter(1, skillName);
		List<Object[]> results = query.getResultList();
		return null;

	}

	@Override
	public Map<String, Integer> getCandidateStatuCount(String status) {

		Query q = getEntityManager().createNativeQuery("select candidateStatus ,count(*) from Candidate where status= '"
				+ status + "' group by candidateStatus");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	public Map<String, Integer> getCandidateStatusCountByRecruiter(String status) {

		Query q = getEntityManager().createNativeQuery("SELECT " + "    candidateStatus, COUNT(*), us.name " + "FROM "
				+ "    Candidate can , User us where can.user_id=us.id "
				+ "GROUP BY candidateStatus ,us.name and can.status='" + status + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	public Map<String, Integer> getCandidateStatusCountByRecruiterId(Long id, String status) {

		Query q = getEntityManager().createNativeQuery("SELECT " + "    candidateStatus, COUNT(*), us.name " + "FROM "
				+ "    Candidate can , User us where can.user_id=" + id
				+ " GROUP BY candidateStatus ,us.name and can.status='" + status + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	@Transactional
	public int updatingStatus(Long id, String status, String offerStatus) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager()
					.createNativeQuery("update Candidate set candidateStatus=? , offereStatus =? where id =?");
			q.setParameter(1, status);
			q.setParameter(2, offerStatus);
			q.setParameter(3, id);
			i = q.executeUpdate();
		} else {
			Query q = getEntityManager().createNativeQuery("update Candidate set  offereStatus =? where id =?");
			q.setParameter(1, offerStatus);
			q.setParameter(2, id);
			i = q.executeUpdate();
		}

		return i;
	}

	@Override
	@Transactional
	public int updatingOnBoardStatus(Long id, String status, String onBoardStatus, Date onboardeddate, String ctc) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery(
					"update Candidate set candidateStatus=? , onBoardedDate =? ,onBoardedStatus=? , offeredCtc=? where id =?");
			q.setParameter(1, status);
			q.setParameter(2, onboardeddate);
			q.setParameter(3, onBoardStatus);
			q.setParameter(4, ctc);
			q.setParameter(5, id);
			i = q.executeUpdate();
		} else {
			Query q = getEntityManager().createNativeQuery("update Candidate set  onBoardedStatus =? where id =?");
			q.setParameter(1, onBoardStatus);
			q.setParameter(2, id);
			i = q.executeUpdate();
		}
		return i;
	}

	@Override
	@Transactional
	public int confirmBoardStatus(Long id, String onBoardStatus, Date abscondeddate) {
		int i = 0;

		Query q = getEntityManager().createNativeQuery(
				"update Candidate set candidateStatus=? , absconded_date =? ,onBoardedStatus=?  where id =?");
		q.setParameter(1, onBoardStatus);
		q.setParameter(2, abscondeddate);
		q.setParameter(3, onBoardStatus);
		q.setParameter(4, id);
		i = q.executeUpdate();
		
		return i;
	}

	@Override
	public double getInsurance(Employee employee, Double age) {
		double insuranceCoverage = 0.0;
		double insuranceCover = 0.0;
		Query q = null;

		if (employee.getEmployeeType().equalsIgnoreCase("Spl")) {
			insuranceCoverage = 100000.00;
		} else if (employee.getTotalCtc() < 500000.00) {
			insuranceCoverage = 300000.00;

		} else if (employee.getTotalCtc() >= 500000.00 && employee.getTotalCtc() < 1000000.00) {
			insuranceCoverage = 500000.00;

		} else if (employee.getTotalCtc() >= 1000000.00) {
			insuranceCoverage = 700000.00;
		}
		if (age < 35.00) {
			String sql = "select 35Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 35.00 && age < 45.00) {
			String sql = "select 45Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 45.00 && age < 55.00) {
			String sql = "select 55Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 55.00 && age < 65.00) {
			String sql = "select 65Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 65.00 && age < 70.00) {
			String sql = "select 70Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 70.00 && age < 75.00) {
			String sql = "select 75Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 75.00 && age < 80.00) {
			String sql = "select 80Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		}
		List<String> results = q.getResultList();
		if (results != null && !results.isEmpty()) {
			insuranceCover = Double.parseDouble(results.get(0));
		}

		return insuranceCover;
	}

}
