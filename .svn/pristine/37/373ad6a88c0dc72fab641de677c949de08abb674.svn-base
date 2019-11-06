package com.ojas.rpo.security.dao.candidate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.Response;

public interface CandidatelistDao extends Dao<Candidate, Long> {

	public List<Candidate> findAll(String status);

	public Response getCandiateByRequirementId(Long requiremnetId, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	public List<Candidate> getCandiateByRequirementId(Long requiremnetId);

	
	boolean updateCandiate(Long candiateId, String status);

	boolean chekduplicate(String mobile, String email, String pancardNumber);

	List<Candidate> getCandiateByRecurtierId(Long recutierId);
	// List<Candidate> getCandiateByAmId(Long amId);
	// public List<User> getAmEmail();

	public Candidate getCandidateId(String pancardNumber);

	List<Candidate> getCandiateByRecurtierIdByStatus(Long recutierId, String status);

	public List<Candidate> getCandiateBySkillName(String skillName);

	Map<String, Integer> getCandidateStatuCount(String status);

	Map<String, Integer> getCandidateStatusCountByRecruiter(String status);

	Map<String, Integer> getCandidateStatusCountByRecruiterId(Long id, String status);

	List<BdmReq> getRequiremenByCandiateId(Long requiremnetId);

	public Response getAllCandidatesByAddedPerson(Long id, Integer pageNo, Integer pageSize, String sortOrder, String sortField, String searchText, String searchField);

	public int updatingStatus(Long id, String status, String offerStatus);

	public int updatingOnBoardStatus(Long id, String status, String onBoardStatus, Date onboardeddate, String ctc);

	public double getInsurance(Employee employee, Double age);

	int confirmBoardStatus(Long id, String onBoardStatus, Date abscondeddate);

}
