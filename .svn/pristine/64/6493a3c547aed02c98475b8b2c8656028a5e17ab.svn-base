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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewFeedback;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/interviewfeedback")
public class InterviewFeedbackResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(InterviewFeedback feedback) {
		System.out.println("***********************" + feedback.getRecommendedProcess());
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + feedback);
		InterviewFeedback am = null;
		String feedbacks = null;
		if (feedback.getTypeofprocess().equalsIgnoreCase("Customer feedBack")) {
			feedback.setTypeofprocess("Feedback from Customer");
			am = this.interviewfeedbackDao.save(feedback);
			if (feedback.getInterviewStatus() != null) {
				if (feedback.getInterviewStatus().equalsIgnoreCase("rejected")
						|| feedback.getInterviewStatus().equalsIgnoreCase("hold")) {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedback.getInterviewStatus());
				}

				else {
					feedbacks = feedback.getRecommendedProcess();
					interviewfeedbackDao.updateInterviewDetails(feedback.getCandidateid(), "In Progress",
							feedback.getRecommendedNextRound());
				}
			}
		}

		if (am != null)

		{
			if (feedback.getRecommendedProcess() != null && !feedback.getRecommendedProcess().isEmpty()) {
				feedbacks = feedback.getRecommendedProcess();
			} else {
				feedbacks = feedback.getTypeofprocess();
			}
			if (feedback.getInterviewStatus() != null) {
				if (feedback.getInterviewStatus().equalsIgnoreCase("rejected")
						|| feedback.getInterviewStatus().equalsIgnoreCase("hold")) {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedback.getInterviewStatus());
				}else {
					this.interviewfeedbackDao.updateCandiate(feedback.getCandidateid(), feedbacks);
				}
			}
			return new Response(ExceptionMessage.StatusSuccess, feedbacks);
		} else {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<InterviewFeedback> add = this.interviewfeedbackDao.findAll();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response process(@PathParam("id") Long id) {
		InterviewFeedback process = null;
		process.setId(id);
		this.logger.info("update(): " + process);
		process = this.interviewfeedbackDao.save(process);

		if (process == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		InterviewFeedback process = this.interviewfeedbackDao.find(id);
		if (process == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		return new Response(ExceptionMessage.StatusSuccess, process);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/basedid/{id}")
	public @ResponseBody Response readId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		InterviewFeedback amquery = this.interviewfeedbackDao.findById(id);
		if (amquery == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		return new Response(ExceptionMessage.StatusSuccess, amquery);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/mail/{id}")
	public @ResponseBody Response updatestatus(@PathParam("id") Long id) {

		InterviewFeedback feedback = this.interviewfeedbackDao.findById(id);

		// feedback.setTypeofprocess("IN progress");

		if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("shortlisted")) {

			feedback.setTypeofprocess("Submitted to Customer");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		} else if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("selected")) {

			feedback.setTypeofprocess("waiting for Offer release");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		} else if (feedback != null && feedback.getInterviewStatus().equalsIgnoreCase("rejected")) {

			feedback.setTypeofprocess("Candidate profile Rejeceted");
			this.interviewfeedbackDao.updateCandiate(id, feedback.getTypeofprocess());

			// new MailGenaration().sendSpecificMail(feedback);
			return new Response(ExceptionMessage.StatusSuccess, feedback);
		}

		else
			return new Response(ExceptionMessage.DataIsNotSaved);

	}

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

}
