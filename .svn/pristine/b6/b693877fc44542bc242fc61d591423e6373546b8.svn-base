package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;

import com.ojas.rpo.security.entity.Skill;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaSkillDao extends JpaDao<Skill, Long>implements SkillDao {
	public JpaSkillDao() {
		super(Skill.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Skill> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Skill> criteriaQuery = builder.createQuery(Skill.class);

		Root<Skill> root = criteriaQuery.from(Skill.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Skill> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
}
