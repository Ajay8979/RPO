package com.ojas.rpo.security.dao.typeofprocess;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Processtype;

public class JpaProcessDao extends JpaDao<Processtype, Long> implements ProcessDao {
	public JpaProcessDao() {
		super(Processtype.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Processtype> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Processtype> criteriaQuery = builder.createQuery(Processtype.class);

		Root<Processtype> root = criteriaQuery.from(Processtype.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Processtype> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
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
	@Transactional(readOnly = true)
	public Processtype findById(Long candidateId) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Processtype> criteriaQuery = builder.createQuery(this.entityClass);
		Root<Processtype> root = criteriaQuery.from(this.entityClass);
		Path<Long> namePath = root.get("candidateid");
		criteriaQuery.where(builder.equal(namePath, candidateId));
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Processtype> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Processtype> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
		// return users.listIterator().previous();
	}
}
