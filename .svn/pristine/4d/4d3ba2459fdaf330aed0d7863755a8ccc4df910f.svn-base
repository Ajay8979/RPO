package com.ojas.rpo.security.dao.Qualification;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddQualification;
public class JpaQualificationDao extends JpaDao<AddQualification, Long>implements QualificationDao {
	public JpaQualificationDao() {
		super(AddQualification.class);
	}
		@Override
		@Transactional(readOnly = true)
		public List<AddQualification> findAll() {
			final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<AddQualification> criteriaQuery = builder.createQuery(AddQualification.class);

			Root<AddQualification> root = criteriaQuery.from(AddQualification.class);
			criteriaQuery.orderBy(builder.desc(root.get("date")));

			TypedQuery<AddQualification> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
			return typedQuery.getResultList();
		}
	
}
