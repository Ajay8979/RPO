package com.ojas.rpo.security.dao.InterviewDetails;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewDetails;


public interface InterviewDetailsDao extends Dao<InterviewDetails,Long>{
	
	public List<InterviewDetails>  getInterviewDetailsByCandidateIdAndStatus(Long candidateId, String status);
	public InterviewDetails getInterviewDetailsByCandidateId(Long id);
	public InterviewDetails updateInterviewStatus(Long candidateId, String activestatus);
	
}
