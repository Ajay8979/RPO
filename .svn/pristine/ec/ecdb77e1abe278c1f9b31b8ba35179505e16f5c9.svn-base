package com.ojas.rpo.security.certificatenames;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Certificate;

public class JpaCertificateTypeDao extends JpaDao<Certificate,Long> implements CertificateTypeDao {

	public JpaCertificateTypeDao() {
		super(Certificate.class);
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
    @Transactional
    public Certificate save(Certificate entity)
    {
        return this.getEntityManager().merge(entity);
    }
}
