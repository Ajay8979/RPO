package com.ojas.rpo.security.dao.InterviewDetails;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewDetails;

public class JpaInterviewDetailsDao extends JpaDao<InterviewDetails, Long> implements InterviewDetailsDao{
	 

	public JpaInterviewDetailsDao() {
		super(InterviewDetails.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override public InterviewDetails getCandiateByCandidateIdAndStatus(Long
	 * candidateId) {
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	 * CriteriaQuery<InterviewDetails> cq = cb.createQuery(InterviewDetails.class);
	 * Root<InterviewDetails> e = cq.from(InterviewDetails.class);
	 * Join<InterviewDetails, InterviewDetails> r = e.join("candidate",
	 * JoinType.LEFT); Predicate predicates = cb.and(cb.equal(r.get("id"),
	 * candidateId)); cq.where(predicates); TypedQuery<InterviewDetails> tq =
	 * getEntityManager().createQuery(cq); InterviewDetails resultList =
	 * tq.getSingleResult(); System.out.println("Result"+resultList.getAddress());
	 * return resultList;
	 * 
	 * }
	 */
	
	@Override
	public List<InterviewDetails> getInterviewDetailsByCandidateIdAndStatus(Long candidateId, String status) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> cq = getCriteriaQuery();
		Root<InterviewDetails> e = cq.from(InterviewDetails.class);
		Join<InterviewDetails, InterviewDetails> r = e.join("candidate", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), candidateId), cb.equal(e.get("status"), status));

		cq.where(predicates);
		TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
		List<InterviewDetails> resultList = tq.getResultList();
		return resultList;

	}
	
	public InterviewDetails getInterviewDetailsByCandidateId(Long id){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> criteriaQuery = getCriteriaQuery();
		Root<InterviewDetails> root = criteriaQuery.from(InterviewDetails.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("candidate"), id));
		Query query = getEntityManager().createQuery(criteriaQuery);
		List<InterviewDetails> result = query.getResultList();
		if(result.isEmpty()){
			return null;
		}
		
		return result.get(0);
	}

	private CriteriaBuilder getCriteriaBuilder(){
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		return criteriaBuilder;
	}
	
	private CriteriaQuery<InterviewDetails> getCriteriaQuery(){
		CriteriaQuery<InterviewDetails> criteriaQuery = getCriteriaBuilder().createQuery(InterviewDetails.class);
		return criteriaQuery;
	}

	@Transactional
	public InterviewDetails updateInterviewStatus(Long candidateId, String activestatus) {
		InterviewDetails data = getInterviewDetailsByCandidateId(candidateId);
	    //data.setStatus("Waiting for Interview Feedback");
	    data.setStatus(activestatus);
	    InterviewDetails updateData=save(data);
			return updateData;
	}
	
}
