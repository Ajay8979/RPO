package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.typeofprocess.ProcessDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/interviewDetails")
public class InterviewDetailsResource {

	@Autowired
	private InterviewDetailsDao interviewDetailsDao;

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	private ProcessDao processDao;

	static final String activeStatus = "Active";

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public @ResponseBody Response create(InterviewDetails newDetails) {

		Long candidateId = newDetails.getCandidate().getId();

		try {
			InterviewDetails oldDetails = interviewDetailsDao.getInterviewDetailsByCandidateId(candidateId);
			if (oldDetails != null) {
				oldDetails.setAddress(newDetails.getAddress());
				oldDetails.setInterviewDate(newDetails.getInterviewDate());
				oldDetails.setInterviewTime(newDetails.getInterviewTime());
				oldDetails.setInterviewLocation(newDetails.getInterviewLocation());
				oldDetails.setInterviewType(newDetails.getInterviewType());
				oldDetails.setNameOfRound(newDetails.getNameOfRound());
				oldDetails.setSpocName(newDetails.getSpocName());
				oldDetails.setStatus("Active");
				interviewDetailsDao.save(oldDetails);
				this.interviewfeedbackDao.updateCandiate(candidateId,
						"Shared " + oldDetails.getNameOfRound() + " Round Interview Details with Recruiter");
			} else {

			/*
			 * newDetails.setStatus("In progress"); interviewDetailsDao.save(newDetails);
			 */
				
			
				newDetails.setStatus("In progress");
				interviewDetailsDao.save(newDetails);
				this.interviewfeedbackDao.updateCandiate(candidateId,
						"Shared " + newDetails.getNameOfRound() + " Round Interview Details with Recruiter");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		// candidateDao.updateCandiate(details.getCandidate().getId(), "In
		// progress");

		return new Response(ExceptionMessage.StatusSuccess, newDetails);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getInterviewDetailsById/{id}")
	public @ResponseBody Response getInterviewDetailsById(@PathParam("id") Long id) {

		// InterviewDetails interviewDetails =
		// this.interviewDetailsDao.find(id);
		InterviewDetails interviewDetails = this.interviewDetailsDao.getInterviewDetailsByCandidateId(id);

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllInterviewDetails() {

		List<InterviewDetails> interviewDetails = this.interviewDetailsDao.findAll();

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/interviewDetailslistByCandidateId/{id}/{status}")
	public @ResponseBody Response interviewDetailslistByCandidateId(@PathParam("id") Long candidateId,
			@PathParam("status") String status) throws IOException {
		List<InterviewDetails> interviewDetls = this.interviewDetailsDao
				.getInterviewDetailsByCandidateIdAndStatus(candidateId, status);

		if (interviewDetls == null) { // throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, interviewDetls);
		}
	}

	@POST
	@Path("/updateInterviewStatusDetails/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInterviewStatus(@PathParam("id") Long candidateId) {
		InterviewDetails interviewDetails = interviewDetailsDao.updateInterviewStatus(candidateId, activeStatus);
		processDao.updateCandiate(candidateId, "Waiting for Interview Feedback");
		if (interviewDetails == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{id}")
	public Response update(@PathParam("id") Long id, InterviewDetails interviewDetails) {

		Response response = new Response(ExceptionMessage.DataIsNotSaved);
		if (null != interviewDetails) {
			interviewDetails.setId(id);
			this.interviewDetailsDao.save(interviewDetails);
			response = new Response(ExceptionMessage.StatusSuccess, interviewDetails);
		}
		return response;
	}
}
