package com.ojas.rpo.security.rest.resources;

import java.util.Date;

public class ONBoardBean {
	
	private Long candidateId;
	
	private Date onBoardingDate ;
	
	private String ctc;
	
	private String status;

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Date getOnBoardingDate() {
		return onBoardingDate;
	}

	public void setOnBoardingDate(Date onBoardingDate) {
		this.onBoardingDate = onBoardingDate;
	}

	public String getCtc() {
		return ctc;
	}

	public void setCtc(String ctc) {
		this.ctc = ctc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
