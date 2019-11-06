package com.ojas.rpo.security.dao.interviewfeedback;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewFeedback;

public class JpaInterviewFeedbackDao extends JpaDao<InterviewFeedback, Long> implements InterviewFeedbackDao {
	public JpaInterviewFeedbackDao() {
		super(InterviewFeedback.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<InterviewFeedback> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewFeedback> criteriaQuery = builder.createQuery(InterviewFeedback.class);

		Root<InterviewFeedback> root = criteriaQuery.from(InterviewFeedback.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<InterviewFeedback> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateCandiate(Long candiateId, String status) {
		// boolean result=false;
		Query q = getEntityManager().createNativeQuery("update Candidate set candidateStatus=? where id =?");
		q.setParameter(1, status);
		q.setParameter(2, candiateId);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}

	@Override
	@Transactional
	public void updateInterviewDetails(Long candiateId, String status, String nameOftheRound) {
		// boolean result=false;
		Query q = getEntityManager()
				.createNativeQuery("update interviewdetails set status='In Progress' , nameOfRound='" + nameOftheRound
						+ "' where candidate_id =" + candiateId);
				int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}

	@Override
	@Transactional(readOnly = true)
	public InterviewFeedback findById(Long candidateId) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewFeedback> criteriaQuery = builder.createQuery(this.entityClass);

		Root<InterviewFeedback> root = criteriaQuery.from(this.entityClass);
		Path<Long> namePath = root.get("candidateid");
		criteriaQuery.where(builder.equal(namePath, candidateId));
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<InterviewFeedback> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<InterviewFeedback> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
		// return users.listIterator().previous();
	}
}