package com.ojas.rpo.security.dao.DashBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.ReportSubmissions;
import com.ojas.rpo.security.entity.Response;

public class JpaSubmissionsImpl extends JpaDao<ReportSubmissions, Long> implements DashBoardInterface {

	public JpaSubmissionsImpl() {
		super(ReportSubmissions.class);
	}

	/*
	 * EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
	 * "Eclipselink_JPA" ); EntityManager entitymanager =
	 * emfactory.createEntityManager();
	 */
	Response response = null;
	Query query = null;
	/*
	 * @Override public List getData1(Long requirementID) { Query q =
	 * getEntityManager().
	 * createNativeQuery(" SELECT  COUNT(*)  noofprofilessummited," +
	 * "bdmreq.id requirementId," + "assign.target," + "bdmreq.nameOfRequirement," +
	 * "candidate.candidateStatus, " + "USER.name, " + " clients.clientName " +
	 * " FROM   bdmreq  bdmreq, " + " candidate  candidate, " + " assign assign , "
	 * + " user USER, " + " recuritermap recuritermap, " + " CLIENT clients " +
	 * " WHERE bdmreq.id=candidate.bdmReq_id " + "  AND  bdmreq.id=?" +
	 * " AND assign.bdmReq_id=bdmreq.id " +
	 * " AND recuritermap.recuriter_ID=USER.id " +
	 * " AND assign.id=recuritermap.Assign_ID " +
	 * "AND candidate.user_id=recuritermap.recuriter_ID" +
	 * " AND clients.id = bdmreq.client_id GROUP BY " + "bdmreq.id");
	 * 
	 * q.setParameter(1, requirementID); List<Object[]> list = q.getResultList();
	 * ReportSubmissions submissions = null; Iterator<Object[]> it =
	 * list.iterator(); List<ReportSubmissions> listSubmissions = new
	 * ArrayList<ReportSubmissions>(); while (it.hasNext()) { Object[] result =
	 * (Object[]) it.next(); // Iterating through array object submissions = new
	 * ReportSubmissions();
	 * submissions.setNoofProfileSumitted(Long.parseLong(result[0].toString()));
	 * submissions.setRequirementId(Long.parseLong(result[1].toString()));
	 * submissions.setTarget(result[2].toString());
	 * submissions.setNameofRequirenment(result[3].toString());
	 * submissions.setCandidateStatus(result[4].toString());
	 * submissions.setRecuriterName(result[5].toString());
	 * submissions.setClientName(result[6].toString());
	 * 
	 * listSubmissions.add(submissions);
	 * 
	 * }
	 * 
	 * return listSubmissions;
	 * 
	 * }
	 * 
	 * public List getData(Long requriterID) { Query q = getEntityManager().
	 * createNativeQuery(" SELECT  COUNT(*)  noofprofilessummited," +
	 * "bdmreq.id requirementId," + "assign.target," + "bdmreq.nameOfRequirement," +
	 * "candidate.candidateStatus, " + "USER.name, " + " clients.clientName " +
	 * " FROM   bdmreq  bdmreq, " + " candidate  candidate, " + " assign assign , "
	 * + " USER USER, " + " recuritermap recuritermap, " + " CLIENT clients " +
	 * " WHERE bdmreq.id=candidate.bdmReq_id " + "  AND   USER.id=?" +
	 * " AND assign.bdmReq_id=bdmreq.id " +
	 * " AND recuritermap.recuriter_ID=USER.id " +
	 * " AND assign.id=recuritermap.Assign_ID " +
	 * "AND candidate.user_id=recuritermap.recuriter_ID" +
	 * " AND clients.id = bdmreq.client_id GROUP BY " + " bdmreq.id ");
	 * 
	 * q.setParameter(1, requriterID);
	 * 
	 * List<Object[]> list = q.getResultList(); ReportSubmissions submissions =
	 * null; Iterator<Object[]> it = list.iterator(); List<ReportSubmissions>
	 * listSubmissions = new ArrayList<ReportSubmissions>(); while (it.hasNext()) {
	 * Object[] result = (Object[]) it.next(); // Iterating through array object
	 * submissions = new ReportSubmissions();
	 * submissions.setNoofProfileSumitted(Long.parseLong(result[0].toString()));
	 * submissions.setRequirementId(Long.parseLong(result[1].toString()));
	 * 
	 * submissions.setNameofRequirenment(result[2].toString());
	 * submissions.setCandidateStatus(result[3].toString());
	 * submissions.setRecuriterName(result[4].toString());
	 * submissions.setClientName(result[5].toString());
	 * 
	 * 
	 * if (result[2] == null) { submissions.setTarget(0 + ""); } else {
	 * submissions.setTarget(result[2].toString()); }
	 * submissions.setNameofRequirenment(result[3].toString());
	 * submissions.setCandidateStatus(result[4].toString());
	 * submissions.setRecuriterName(result[5].toString());
	 * submissions.setClientName(result[6].toString());
	 * 
	 * listSubmissions.add(submissions);
	 * 
	 * } return listSubmissions;
	 * 
	 * }
	 */

	public Response getSubmissionsAndRejections() {

		try {
			List<Object[]> submissionList = new ArrayList<Object[]>();

			query = getEntityManager().createNativeQuery(
					"(SELECT  COUNT(*)  noofprofilessummited ,IF(candidateStatus  is not null ,'Profiles Submmited', candidateStatus) as candidateStatus "
							+ " from candidatemapping cm , candidate can where can.id=cm.candidate_id) " + " "
							+ "UNION (select  Count(*) noofprofilesrejected  , "
							+ "					 IF(candidateStatus is null ,'rejected', candidateStatus) as candidateStatus from "
							+ "					 candidate where candidateStatus = 'rejected') UNION (select  Count(*) noofprofilesrejected  ,"
							+ "					 IF(candidateStatus is null ,'Rejected By Lead', candidateStatus) as candidateStatus from "
							+ "					 candidate where candidateStatus = 'Rejected By Lead') UNION (select  Count(*) noofprofilesrejected  ,"
							+ "					 IF(candidateStatus is null ,'Customer Rejected', candidateStatus) as candidateStatus from "
							+ "					 candidate where candidateStatus = 'Customer Rejected') UNION (select  Count(*) noofprofilesrejected  ,"
							+ "					 IF(candidateStatus is null ,'Interview Schedule Rejected By Candidate', candidateStatus) as candidateStatus from "
							+ "					 candidate where candidateStatus = 'Interview Schedule Rejected By Candidate') ");
			submissionList = query.getResultList();
			if (submissionList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				Map<String, Integer> submissionMapValues = new HashMap<String, Integer>();
				for (Object[] data : submissionList) {
					submissionMapValues.put(data[1].toString(), (Integer.valueOf(data[0].toString())));
					// submissionList.add(data);
				}
				response = new Response(ExceptionMessage.OK, submissionMapValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrive SubmissionsAndRejections List", e.getLocalizedMessage());

		}
		return response;
	}

	@Override
	public Response getSubmissionsAndClosures() {
		List<Object[]> closuresList = new ArrayList<Object[]>();
		try {
			query = getEntityManager().createNativeQuery(
					"(SELECT  COUNT(*)  noofprofilessummited ,IF(candidateStatus  is not null ,'Profiles Submmited', candidateStatus) as candidateStatus  \r\n"
							+ " from candidatemapping cm , candidate can where can.id=cm.candidate_id) \r\n"
							+ " UNION (select  Count(*) noofCandidatesOfferReleased ,  IF(candidateStatus is null ,'offerReleased', candidateStatus) as candidateStatus from \r\n"
							+ "					 candidate where candidateStatus = 'offerReleased')");

			closuresList = query.getResultList();

			if (closuresList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				Map<String, Integer> closureMapValues = new HashMap<String, Integer>();
				for (Object[] closureData : closuresList) {
					closureMapValues.put(closureData[1].toString(), (Integer.valueOf(closureData[0].toString())));

				}

				response = new Response(ExceptionMessage.OK, closureMapValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to Retrive SubmissionsAndClosures List",
					e.getLocalizedMessage());

		}
		return response;
	}

	@Override
	public Response getClosuresAndJoining() {
		List<Object[]> joiningsList = new ArrayList<Object[]>();
		try {
			query = getEntityManager().createNativeQuery(
					"(select count(*) noofCandidatesOfferReleased, if(candidateStatus is null, 'offerReleased', candidateStatus) as candidateStatus from candidate where candidateStatus = 'offerReleased' ) \r\n"
							+ "   Union ( select count(*) noofCandidatesOnBoarded, if(onBoardedStatus is null, 'On Boarded', onBoardedStatus) as onBoardedStatus from candidate where onBoardedStatus='On Boarded')");

			joiningsList = query.getResultList();
			if (joiningsList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				Map<String, Integer> joiningMapValues = new HashMap<String, Integer>();
				for (Object[] joiningData : joiningsList) {
					joiningMapValues.put(joiningData[1].toString(), (Integer.valueOf(joiningData[0].toString())));
				}
				response = new Response(ExceptionMessage.OK, joiningMapValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to Retrive ClosuresAndJoining List",
					e.getLocalizedMessage());

		}
		return response;
	}

	@Override
	public Response getRequirementAndSubmissions() {
		List<Object[]> requirementList = new ArrayList<Object[]>();
		try {
			query = getEntityManager().createNativeQuery(
					"(select count(*) noofRequirements, if(status is not null,'openRequiRements',status) as status from bdmreq where status='open')\r\n"
							+ "Union (SELECT  COUNT(*)  noofprofilessummited ,IF(candidateStatus  is not null ,'Profiles Submmited', candidateStatus) as candidateStatus  \r\n"
							+ " from candidatemapping cm , candidate can where can.id=cm.candidate_id)");

			requirementList = query.getResultList();
			if (requirementList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is ");
			} else {
				Map<String, Integer> requirementMapValues = new HashMap<String, Integer>();
				for (Object[] requirementData : requirementList)
					requirementMapValues.put(requirementData[1].toString(),
							Integer.valueOf(requirementData[0].toString()));
				response = new Response(ExceptionMessage.OK, requirementMapValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable To Retrive RequirementAndSubmissions values", e.getLocalizedMessage());

		}

		return response;
	}

	@Override
	public Response getRequirementsAndBdm(Long bdm_id) {

		Integer bdmAndReqListCount = null;
		try {
			query = getEntityManager().createNativeQuery(
					"select count(primaryContact_id) as primaryContact_id  from client c Inner join bdmreq br on c.id= br.client_id where primaryContact_id=?");
			query.setParameter(1, bdm_id);

			bdmAndReqListCount = Integer.valueOf(query.getSingleResult().toString());

			if (bdmAndReqListCount == null) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {

				response = new Response(ExceptionMessage.OK, bdmAndReqListCount);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable To Retrive RequirementAndSubmissions values", e.getLocalizedMessage());

		}

		return response;
	}

	@Override
	public Response getSubmissionsByRecruiterId(Long recruiter_Id) {

		Integer RecruiteSubmissionCount = null;
		try {
			query = getEntityManager().createNativeQuery(
					"select count(users_id) as users_id from assign a Inner join candidatemapping cm on cm.bdmReq_id= a.bdmReq_id where users_id=?");
			query.setParameter(1, recruiter_Id);
			RecruiteSubmissionCount = Integer.valueOf(query.getSingleResult().toString());
			if (RecruiteSubmissionCount == null) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				response = new Response(ExceptionMessage.OK, RecruiteSubmissionCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to retrive the RecruiteSubmissionList",
					e.getLocalizedMessage());
		}

		return response;

	}

	@Override
	public Response getClosureByRecruiterId(Long recruiter_Id) {
		Integer closuresByRecruiterCount = null;
		try {
			query = getEntityManager().createNativeQuery(
					"select count(offereStatus) as offereStatus from candidate where offereStatus='Offer Released' and user_id=?");
			query.setParameter(1, recruiter_Id);
			closuresByRecruiterCount = Integer.valueOf(query.getSingleResult().toString());
			if (closuresByRecruiterCount == null) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				response = new Response(ExceptionMessage.OK, closuresByRecruiterCount);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to fetch the closuresByRecruiterCount",
					e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public Response getJoiningByRecruiterId(Long recruiter_Id) {
		Integer joiningsByRecruiterCount = null;
		try {
			query = getEntityManager().createNativeQuery(
					"select count(onBoardedStatus) as onBoardedStatus from candidate where onBoardedStatus='On Boarded' and user_id=?");
			query.setParameter(1, recruiter_Id);
			joiningsByRecruiterCount = Integer.valueOf(query.getSingleResult().toString());
			if (joiningsByRecruiterCount == null) {
				response = new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
			} else {
				response = new Response(ExceptionMessage.OK, joiningsByRecruiterCount);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to fetch the JoiningsByRecruiterCount",
					e.getLocalizedMessage());
		}

		return response;
	}

}
