package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

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
import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.CandidateStatusCounts;

@Component
@Path("/candidateReqMapping")
public class CandidateReqMappingResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CandidateReqMappingDao candidateReqMappingDao;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private CandidatelistDao candidateDao;
	@Autowired
	private InterviewDetailsDao interviewDetailsDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateMapping candidateMapping) {
		this.logger.info("save()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		if (candidateMapping != null) {
			CandidateMapping candidateData = candidateReqMappingDao.save(candidateMapping);
			candidateDao.updateCandiate(candidateData.getCandidate().getId(),"Submitted for Review");
			Response response = new Response(ExceptionMessage.StatusSuccess, candidateData);
			response.setRes("Candidate Mapping done successfully");
			return response;

		}
		Response response = new Response(ExceptionMessage.DataIsEmpty);
		response.setRes("Invalid Customer Details");
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateByRecurtierId/{id}/{status}")
	public @ResponseBody Response getCandiateByRecurtierId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateMapping = this.candidateReqMappingDao.getCandiateByRecurtierId(recutierId,
				status);

		if (candidateMapping == null || candidateMapping.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateByRequirementId/{id}/{candidateStatus}/{reqStatus}")
	public @ResponseBody Response getCandiateByRequirementId(@PathParam("id") Long requimentId,
			@PathParam("candidateStatus") String candidateStatus, @PathParam("reqStatus") String reqStatus)
			throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateMapping = this.candidateReqMappingDao.getCandiateByRequirementId(requimentId,
				candidateStatus, reqStatus);

		if (candidateMapping.isEmpty() || candidateMapping == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateStatusByRequirementId/{id}")
	public @ResponseBody Response getCandiateStatusByRequirementId(@PathParam("id") Long requimentId)
			throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateStatusByRequirementId(requimentId);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateMapCandedateId/{id}")
	public @ResponseBody Response getCandiateMapCandedateId(@PathParam("id") Long candidateId) throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateMapping> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateMapCandedateId(candidateId);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateStatusByRequirements")
	public @ResponseBody Response getCandiateStatusByRequirements() throws IOException {

		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateStatusCounts = this.candidateReqMappingDao
				.getCandiateStatusByRequirements();

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateCountByStatus")
	public @ResponseBody Response getCandiateCountByStatus() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<CandidateStatusCounts> candidateMapping = this.candidateReqMappingDao.getCandiatesStatusCountByBdmReqId();

		if (candidateMapping == null || candidateMapping.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

			return new Response(ExceptionMessage.StatusSuccess, candidateMapping);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllCandidatesDetail/{role}/{loginid}")
	public @ResponseBody Response getAllCandidatesDetails(@PathParam("role") String role,
			@PathParam("loginid") Long loginid) throws IOException {

		this.logger.info("list()");
		List<CandidateData> candidateStatusCounts = this.candidateReqMappingDao.findAllCanditaes(role, loginid);

		if (candidateStatusCounts == null || candidateStatusCounts.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			Long id = null;
			for (CandidateData candidateData : candidateStatusCounts) {
				id = Long.parseLong(candidateData.getCandidateid());
				InterviewDetails details = interviewDetailsDao.getInterviewDetailsByCandidateId(id);
				if (null != details) {
					candidateData.setNameOfRound(details.getNameOfRound());
				}

			}

			return new Response(ExceptionMessage.StatusSuccess, candidateStatusCounts);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidatesList/{role}/{loginid}/{pageNo}/{pageSize}")
	public @ResponseBody Response getCandidatesList(@PathParam("role") String role, @PathParam("loginid") Long id,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField)
			throws IOException {

		this.logger.info("list()");
		
		if(null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}
		
		if(null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		
		
		Response response = this.candidateReqMappingDao.getCandidatesList(role,id,pageNo,pageSize, sortingOrder, sortingField,searchText, searchField);

		try {
			if (response.getStatus().equals(ExceptionMessage.DataIsEmpty) || response.getStatus().equals(ExceptionMessage.Exception)) {
				return response;
			} else {
				List<CandidateData> candidateslist = (List<CandidateData>)response.getResult();
				for (CandidateData candidateData : candidateslist) {
					id = Long.parseLong(candidateData.getCandidateid());
					InterviewDetails details = interviewDetailsDao.getInterviewDetailsByCandidateId(id);
					if (null != details) {
						candidateData.setNameOfRound(details.getNameOfRound());
					}

				}
				response.setResult(candidateslist);
				response.setStatus(ExceptionMessage.OK);
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500", "Unable to Retrieve Mapped Candidates");
		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchCandidatesList/{role}/{id}/{searchInput}/{searchField}/{pageNo}/{pageSize}")
	public Response searchCandidatesList(@PathParam("role") String role, @PathParam("id") Long id,
			@PathParam("searchInput") String searchInput, @PathParam("searchField") String searchField,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize) {
		
		if(null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}
		
		if(null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		
		if (null == pageNo) {
			return new Response(ExceptionMessage.valueOf("value of 'pageNo' not specified"));
		}

		if (null == pageSize) {
			return new Response(ExceptionMessage.valueOf("value of 'pageSize' not specified"));
		}
		    
			return this.candidateReqMappingDao.searchCandidatesList(role, id, searchInput, searchField, pageNo, pageSize);
	}

	private ResponseBuilder Response() {
		return javax.ws.rs.core.Response.ok();
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
