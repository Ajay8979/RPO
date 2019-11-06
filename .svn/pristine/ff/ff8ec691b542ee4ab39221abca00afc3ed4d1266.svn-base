package com.ojas.rpo.security.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="interviewdetails")
@javax.persistence.Entity
public class InterviewDetails implements Entity{

	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @ManyToOne
	 private InterviewType interviewType;
	 
	 @ManyToOne
	 private BdmReq requirement;
	 
	 @ManyToOne
	 private Candidate candidate;
	 
	 private String  spocName;
	 
	 private String nameOfRound;
	 
	 private String interviewLocation;
	 
	 private String address;
	 
	 private Date interviewDate;
	 
	 private String status;
	
	 private String interviewTime;

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
	public InterviewType getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(InterviewType interviewType) {
		this.interviewType = interviewType;
	}
	public BdmReq getRequirement() {
		return requirement;
	}
	public void setRequirement(BdmReq requirement) {
		this.requirement = requirement;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getSpocName() {
		return spocName;
	}
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	public String getNameOfRound() {
		return nameOfRound;
	}
	public void setNameOfRound(String nameOfRound) {
		this.nameOfRound = nameOfRound;
	}
	public String getInterviewLocation() {
		return interviewLocation;
	}
	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewTime() {
		return interviewTime;
	}
	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

}
