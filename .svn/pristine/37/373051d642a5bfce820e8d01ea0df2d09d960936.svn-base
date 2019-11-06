package com.ojas.rpo.security.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Table(name="candidatemapping")
@javax.persistence.Entity
public class CandidateMapping implements Entity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private BdmReq bdmReq;
	
	@ManyToOne
	private Candidate candidate;
	
	private String status;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BdmReq getBdmReq() {
		return bdmReq;
	}

	public void setBdmReq(BdmReq bdmReq) {
		this.bdmReq = bdmReq;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	

}
