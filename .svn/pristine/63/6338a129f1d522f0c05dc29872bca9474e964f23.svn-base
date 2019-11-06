
package com.ojas.rpo.security.rest.resources;

import java.io.BufferedOutputStream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateStatus;
import com.ojas.rpo.security.entity.CtcDetails;
import com.ojas.rpo.security.entity.Deduction;
import com.ojas.rpo.security.entity.Document_All;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.EmployeeEarning;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.OfferDetails;
import com.ojas.rpo.security.entity.OfferedRealse;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Resume;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.service.EmployeeService;
import com.ojas.rpo.security.util.DateParsing;
import com.ojas.rpo.security.util.MailGenaration;
import com.ojas.rpo.security.util.WordToPdf;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/addCandidate")
public class CandidateResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CandidatelistDao candidateDao;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private Resume resume;
	@Autowired
	private Document_All document_All;

	@Autowired
	private EmployeeService employeeService;

	@Value("${fileUploadPath}")
	private String documentsfolder;

	@Value("${resumeUploadPath}")
	private String resumesfolder;

	@Value("${offerletterUploadPath}")
	private String offerletterfolder;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/list/{role}/{status}/{id}")
	public @ResponseBody Response getdata(@PathParam("role") String role, @PathParam("status") String status,
			@PathParam("id") Long id) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Candidate> candidate = null;
		if (role.equalsIgnoreCase("AM")) {
			candidate = this.candidateDao.findAll(status);
		}
		if (role.equalsIgnoreCase("recruiter")) {
			candidate = this.candidateDao.getCandiateByRecurtierId(id);
		}

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			ListIterator litr = candidate.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				Candidate req = (Candidate) litr.next();
				if (req.getSubmittionDate() != null) {
					req.setProfileDate(formatter.format(req.getSubmittionDate()).toString());
				}
			}

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);
		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/display")
	public @ResponseBody Response display() {
		System.out.println("*******display***************************");
		return new Response(ExceptionMessage.StatusSuccess);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getResume/{id}")
	public @ResponseBody Response getResume(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);

		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			if (candidate.getResume() != null) {
				resume.setResume(candidate.getResume());
				return new Response(ExceptionMessage.StatusSuccess, resume);
			} else {
				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
			}
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/PostResume/{id}")
	public @ResponseBody Response postResume(@PathParam("id") Long id, Resume resume) {
		System.out.println("==========inside post method====RPO");
		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);
		candidate.setResume(resume.getResume());
		candidate.setSubmittionDate(new Date());
		this.logger.info("update(): " + candidate);

		if (this.candidateDao.save(candidate) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/requirement/{id}")
	public @ResponseBody Response readRequitrementDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		List<Candidate> candidatedetails = this.candidateDao.getCandiateByRequirementId(id);
		if (candidatedetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidatedetails);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Candidate candidate) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + candidate);

		candidate.setCandidateStatus("Created");

		boolean b = this.candidateDao.chekduplicate(candidate.getMobile(), candidate.getEmail(),
				candidate.getPancardNumber());
		candidate.setSubmittionDate(new Date());
		if (b) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}

		else {
			String firstname = DateParsing.textConvertor(candidate.getFirstName());
			candidate.setFirstName(firstname);
			String lastname = DateParsing.textConvertor(candidate.getLastName());
			candidate.setLastName(lastname);

			Candidate c = this.candidateDao.save(candidate);
			Response r = new Response(ExceptionMessage.StatusSuccess, c);
			r.setRes("Candidate added successfully");

			return r;
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updating/{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Candidate candidate) {
		candidate.setId(id);
		this.logger.info("update(): " + candidate);
		candidate.setSubmittionDate(new Date());
		// Blob blob=candidate.getResume();
		if (candidate.getCandidateStatus().equalsIgnoreCase("Rejected")) {
			candidate.setCandidateStatus("pending Review");
			this.candidateDao.save(candidate);
		}
		if (this.candidateDao.save(candidate) == null) {

			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingstatus/test/{id}")
	public @ResponseBody void changeStatus(@PathParam("id") Long id) {

		String s = "Offer Pending";
		this.candidateDao.updateCandiate(id, s);

		new Response(ExceptionMessage.StatusSuccess);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update/{id}")
	public @ResponseBody Response updatestatus(@PathParam("id") Long id, Candidate candidate) {
		System.out.println("==========inside post method====RPO");
		candidate.setId(id);
		this.logger.info("update(): " + candidate);

		if (candidate.getCandidateStatus().equalsIgnoreCase("Submit to Lead")) {
			candidate.setCandidateStatus("pending Review");
			this.candidateDao.updateCandiate(id, candidate.getCandidateStatus());
		}

		if (this.candidateDao.updateCandiate(id, candidate.getCandidateStatus()) == false) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {

			new MailGenaration().sendSpecificMail(candidate);

			return new Response(ExceptionMessage.StatusSuccess, candidate);

		}
	}
	// offere status

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("accepteddata/{id}")
	public @ResponseBody Response offerstatus(@PathParam("id") Long id, OfferedRealse release) {
		System.out.println("==========inside post method====RPO");
		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);

		candidate.setOffereStatus(release.getOffereStatus());

		if (candidate.getOffereStatus().equalsIgnoreCase("accepted")) {
			candidate.setCandidateStatus("Candidate OfferAccepted");
			candidate.setDoj(release.getDoj());

			this.candidateDao.save(candidate);
		} else if (candidate.getOffereStatus().equalsIgnoreCase("rejected")) {
			candidate.setCandidateStatus("Candidate OfferRejected");
			candidate.setOffRejectedReasion(release.getOffRejectedReasion());
			this.candidateDao.save(candidate);
		}
		if (this.candidateDao.save(candidate) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {

			new MailGenaration().sendSpecificMail(candidate);

			return new Response(ExceptionMessage.StatusSuccess, candidate);

		}
	}

	// offered letter releasing

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/offereletter/{id}")
	public @ResponseBody Response offereData(@PathParam("id") Long id, OfferedRealse release) {

		Candidate candidate = this.candidateDao.find(id);

		candidate.setId(id);
		candidate.setDoj(release.getDoj());
		candidate.setOffereLetter(release.getOffereLetter());
		candidate.setSubmittionDate(new Date());
		candidate.setCandidateStatus("Offer Released");

		this.logger.info("update(): " + candidate);

		if (this.candidateDao.save(candidate) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			this.candidateDao.save(candidate);
			new MailGenaration().sendSpecificMail(candidate);

			return new Response(ExceptionMessage.StatusSuccess);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOfferDetails/{id}")
	public @ResponseBody Response getOfferDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Candidate candidate = this.candidateDao.find(id);

		if (candidate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			// if (candidate.getResume() != null) {
			// SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM
			// yyyy");

			OfferDetails od = new OfferDetails();
			String firstname = DateParsing.textConvertor(candidate.getFirstName());
			od.setFirstName(firstname);
			String lastname = DateParsing.textConvertor(candidate.getLastName());
			od.setLastName(lastname);
			// od.setLastName(candidate.getLastName());

			if (candidate.getDoj() != null) {
				System.out.println("D" + candidate.getDoj());
				od.setDoj1(DateParsing.dateParsing(candidate.getDoj()));
				// od.setDoj1(formatter.format(candidate.getDoj()).toString());

			} else {
				od.setDoj1("");
			}

			od.setExpectedPackage(candidate.getExpectedCTC());
			od.setHrFirstName(candidate.getUser().getFirstName());
			od.setHrLastName(candidate.getUser().getLastName());

			return new Response(ExceptionMessage.StatusSuccess, od);

		}

	}

	@POST
	@Path("/uploadOfferLetter/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadOfferLetter(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("id") long id) {

		String SAVE_FOLDER = offerletterfolder + id + fileInputDetails.getFileName();
		String fileLocation = SAVE_FOLDER + fileInputDetails.getFileName();
		String status = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		OutputStream out = null;
		long file_size = 0;
		// Save the file
		try {
			out = new FileOutputStream(new File(fileLocation));
			byte[] buffer = new byte[1024];
			int bytes = 0;

			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}
			out.flush();
			out.close();

			logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
					fileInputDetails.getFileName(), myFormat.format(file_size)));
			status = "SUCCESS";

		} catch (IOException ex) {
			logger.error("Unable to save file: " + fileLocation);
			return new Response(status, HttpStatus.CONFLICT, "notuploaded");
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Response(status, HttpStatus.OK,
				"File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes");
	}

	@POST
	@Path("/uploadResume/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadResumes(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("id") long id) {

		String SAVE_FOLDER = resumesfolder + id + "." + fileInputDetails.getFileName();
		String fileLocation = SAVE_FOLDER;
		String status = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		System.out.println("Inside Method2");
		myFormat.setGroupingUsed(true);
		OutputStream out = null;
		long file_size = 0;

		// Save the file
		try {

			out = new FileOutputStream(new File(fileLocation));
			byte[] buffer = new byte[1024];
			int bytes = 0;

			while ((bytes = fileInputString.read(buffer)) != -1) {
				out.write(buffer, 0, bytes);
				file_size += bytes;
			}

			out.flush();
			out.close();

			logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
					fileInputDetails.getFileName(), myFormat.format(file_size)));
			status = "SUCCESS";

			WordToPdf.convertWordtoPdf(SAVE_FOLDER, resumesfolder + id + ".pdf");

		} catch (IOException ex) {
			logger.error("Unable to save file: " + fileLocation);
			return new Response(status, HttpStatus.CONFLICT, "notuploaded");
		} catch (Exception e) {
			logger.error("Unable to convert file to docx: " + fileLocation);
			return new Response(status, HttpStatus.CONFLICT, " file not converted");
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Response(status, HttpStatus.OK,
				"File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes");
	}

	@POST
	@Path("/upload/{type}/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response uploadFile(@FormDataParam("file") InputStream fileInputString,
			@FormDataParam("file") FormDataContentDisposition fileInputDetails, @PathParam("type") String type,
			@PathParam("id") long id) {

		System.out.println("Inside method");
		String SAVE_FOLDER = null;

		String name = fileInputDetails.getFileName();
		if (name.endsWith("pdf")) {
			SAVE_FOLDER = documentsfolder + type + "_" + id + ".pdf";
		} else if (name.endsWith("jpg")) {
			SAVE_FOLDER = documentsfolder + type + "_" + id + ".jpg";
		} else {
			SAVE_FOLDER = "notmatched";
		}
		if (!SAVE_FOLDER.equalsIgnoreCase("notmatched")) {
			System.out.println(SAVE_FOLDER);
			String fileLocation = SAVE_FOLDER;
			String status = null;
			NumberFormat myFormat = NumberFormat.getInstance();
			myFormat.setGroupingUsed(true);
			OutputStream out = null;
			long file_size = 0;
			// Save the file
			try {
				out = new FileOutputStream(new File(fileLocation));
				byte[] buffer = new byte[1024];
				int bytes = 0;

				while ((bytes = fileInputString.read(buffer)) != -1) {
					out.write(buffer, 0, bytes);
					file_size += bytes;
				}
				out.flush();
				out.close();

				logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
						fileInputDetails.getFileName(), myFormat.format(file_size)));
				status = "SUCCESS";

			} catch (IOException ex) {
				logger.error("Unable to save file: " + fileLocation);
				return new Response(status, HttpStatus.CONFLICT, "notuploaded");
			} finally {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new Response(status, HttpStatus.OK,
					"File has been uploaded to:" + fileLocation + ", size: " + myFormat.format(file_size) + " bytes");
		} else {
			return new Response("FAILED", HttpStatus.CONFLICT, "notuploaded");
		}
	}

	@GET
	@Path("/download/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadFilebyPath(@PathParam("filename") String fileName) {
		return download(fileName);
	}

	private javax.ws.rs.core.Response download(String fileName) {

		String FILE_FOLDER = documentsfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder;
		// folder.mkdir();
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			long file_size = file_folder.length();
			logger.info(String.format("Inside downloadFile==> fileName: %s, fileSize: %s bytes", fileName,
					myFormat.format(file_size)));
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Path("/downloadResumes/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadResumePath(@PathParam("filename") String fileName) {
		return downloadResume(fileName);
	}

	private javax.ws.rs.core.Response downloadResume(String fileName) {

		String FILE_FOLDER = resumesfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder;
		// folder.mkdir();
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			long file_size = file_folder.length();
			logger.info(String.format("Inside downloadFile==> fileName: %s, fileSize: %s bytes", fileName,
					myFormat.format(file_size)));
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Path("/downloadOfferLetter/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public javax.ws.rs.core.Response downloadOfferLetterPath(@PathParam("filename") String fileName) {
		return downloadOfferLetter(fileName);
	}

	private javax.ws.rs.core.Response downloadOfferLetter(String fileName) {

		String FILE_FOLDER = offerletterfolder;
		File folder = new File(FILE_FOLDER);
		File fileLocation = folder;
		// folder.mkdir();
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String file_name = null;
		File[] files = fileLocation.listFiles();
		File file_folder = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			file_name = file.getName();
			if (file_name.contains(fileName)) {
				System.err.println("file_name" + file_name);
				file_folder = new File(FILE_FOLDER + file_name);
			}
		}

		// Retrieve the file

		javax.ws.rs.core.Response response;
		if (file_folder.exists()) {
			ResponseBuilder builder = javax.ws.rs.core.Response.ok(file_folder);
			builder.header("Content-Disposition", "attachment; filename=" + fileName);
			response = builder.build();

			long file_size = file_folder.length();
			logger.info(String.format("Inside downloadFile==> fileName: %s, fileSize: %s bytes", fileName,
					myFormat.format(file_size)));
		} else {
			logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));
			response = Response().entity("FILE NOT FOUND: " + file_folder).type("text/plain").build();
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/candidateStatuslistByRecruiter/{status}/{id}")
	public @ResponseBody Response candidateStatuslistByRecruiter(@PathParam("status") String status,
			@PathParam("id") Long recutierId) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<Candidate> candidate = this.candidateDao.getCandiateByRecurtierIdByStatus(recutierId, status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			ListIterator litr = candidate.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				Candidate req = (Candidate) litr.next();
				req.setProfileDate(formatter.format(req.getSubmittionDate()).toString());
			}

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateListByRequirementId/{id}/{pageNo}/{pageSize}")
	public @ResponseBody Response getCandiateListByRequirementId(@PathParam("id") Long requiremnetId,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField)
			throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		/*List<Candidate> candidate = this.candidateDao.getCandiateByRequirementId(requiremnetId,pageNo,pageSize,sortOrder,sortField,searchText,searchField);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			ListIterator litr = candidate.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				Candidate req = (Candidate) litr.next();
				if (req.getSubmittionDate() != null) {
					req.setProfileDate(formatter.format(req.getSubmittionDate()).toString());
				}

			}

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
		*/
		Response response  = this.candidateDao.getCandiateByRequirementId(requiremnetId,pageNo,pageSize,sortOrder,sortField,searchText,searchField);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getRequiremntListByCandidateId/{id}")
	public @ResponseBody Response getRequiremntListByCandidateId(@PathParam("id") Long candidateId) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<BdmReq> candidate = this.candidateDao.getRequiremenByCandiateId(candidateId);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			ListIterator litr = candidate.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				BdmReq req = (BdmReq) litr.next();
				// req.setProfileDate(formatter.format(req.get).toString());
			}

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandiateListByRecurtierId/{id}/{status}")
	public @ResponseBody Response getCandiateListByRecurtierId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<Candidate> candidate = this.candidateDao.getCandiateByRecurtierId(recutierId);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (candidate.size() > 0) {

			ListIterator litr = candidate.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				Candidate req = (Candidate) litr.next();
				if (req.getSubmittionDate() != null) {
					req.setProfileDate(formatter.format(req.getSubmittionDate()).toString());
				}
			}

			return new Response(ExceptionMessage.StatusSuccess, candidate);
		}

		else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatuCount/{status}")
	public @ResponseBody Response getCandidateStatuCount(@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatuCount(status);

		if (candidate == null || candidate.isEmpty()) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatusCountByRecruiter/{status}")
	public @ResponseBody Response getCandidateStatusCountByRecruiter(@PathParam("status") String status)
			throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatusCountByRecruiter(status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCandidateStatusCountByRecruiterId/{id}/{status}")
	public @ResponseBody Response getCandidateStatusCountByRecruiterId(@PathParam("id") Long recutierId,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		Map<String, Integer> candidate = this.candidateDao.getCandidateStatusCountByRecruiterId(recutierId, status);

		if (candidate == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidate);

	}

	@GET
	@Path("/convertFileContentToBlob/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response convertFileContentToBlob(@PathParam("id") Long id) throws IOException {
		CandidateData candidateData = new CandidateData();
		byte[] fileContent = null;
		// initialize string buffer to hold contents of file
		StringBuffer fileContentStr = new StringBuffer("");
		BufferedReader reader = null;
		try {
			// initialize buffered reader
			reader = new BufferedReader(new FileReader(resumesfolder + id + ".docx"));
			String line = null;
			// read lines of file
			while ((line = reader.readLine()) != null) {
				// append line to string buffer
				fileContentStr.append(line).append("\n");
			}
			// convert string to byte array
			fileContent = fileContentStr.toString().trim().getBytes();
			candidateData.setFile(fileContent);
			return new Response(ExceptionMessage.StatusSuccess, candidateData);

		} catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	// pending

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/candidateListBySkillName/{skillName}")
	public @ResponseBody Response candidateListBySkillName(@PathParam("skillName") String skillName) {
		this.logger.info("read(skillName)");
		List<Candidate> candidateList = this.candidateDao.getCandiateBySkillName(skillName);
		if (candidateList == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidateList);
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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingstatusbylead/{id}/{status}")
	public @ResponseBody Response changeStatus(@PathParam("id") Long id, @PathParam("status") String status) {

		this.candidateDao.updateCandiate(id, status);
		return new Response(ExceptionMessage.StatusSuccess, "200", "Success");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingOfferStatus/{id}/{offerStatus}")
	public @ResponseBody Response updatingStatus(@PathParam("id") Long id,
			@PathParam("offerStatus") String offerStatus) {
		String status = null;
		if (offerStatus.equalsIgnoreCase("Offer Released")) {
			status = "On Board";
		} else {
			status = offerStatus;
		}

		int i = this.candidateDao.updatingStatus(id, status, offerStatus);
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updatingOnBoardStatus")
	public @ResponseBody Response updatingOnBoardStatus(@RequestBody ONBoardBean onBoardBean) {
		String status = null;
		if (onBoardBean.getStatus().equalsIgnoreCase("On Boarded")) {
			status = "Raise Invoice";
		} else {
			status = onBoardBean.getStatus();
		}

		int i = this.candidateDao.updatingOnBoardStatus(onBoardBean.getCandidateId(), status, onBoardBean.getStatus(),
				onBoardBean.getOnBoardingDate(), onBoardBean.getCtc());
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/confirmBoardStatus")
	public @ResponseBody Response confirmBoardStatus(@RequestBody ONBoardBean onBoardBean) {

		int i = this.candidateDao.confirmBoardStatus(onBoardBean.getCandidateId(), onBoardBean.getStatus(),
				onBoardBean.getOnBoardingDate());
		if (i > 0) {
			return new Response(ExceptionMessage.StatusSuccess, "200", "Success");
		}
		return new Response(ExceptionMessage.Not_Found, "200", "Failed");

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCtcDetails")
	public @ResponseBody Response getCtcDetails(@RequestBody Employee employee) {
		CtcDetails ctcDetails = null;
		try {
			EmployeeEarning employeeEarning = employeeService.getEmployeeEarnings(employee);
			Deduction deduction = employeeService.getEmployeeDeductions(employee, employeeEarning);
			ctcDetails = new CtcDetails();
			ctcDetails.setDeduction(deduction);
			ctcDetails.setEmployee(employee);
			ctcDetails.setEmployeeEarning(employeeEarning);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ctcDetails != null) {
			return new Response(ExceptionMessage.StatusSuccess, ctcDetails);
		}

		return new Response(ExceptionMessage.Not_Found, "500", "Failed");

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getAllCandidatesByAddedPerson/{logId}/{pageNo}/{pageSize}")
	public @ResponseBody Response getAllCandidatesByAddedPerson(@PathParam("logId") Long logId,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortOrder, @QueryParam("sortingField") String sortField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		return this.candidateDao.getAllCandidatesByAddedPerson(logId, pageNo, pageSize, sortOrder, sortField,
				searchText, searchField);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/candidateStausList/{status}")
	public @ResponseBody Response candidateListByStatus(@PathParam("status") String status) {

		List<CandidateStatus> list = new ArrayList<CandidateStatus>();
		if (status.equalsIgnoreCase("Accepted By Lead")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Submitted to Customer");
			list.add(candidateStatus);
			return new Response(ExceptionMessage.StatusSuccess, list);
		}

		if (status.equalsIgnoreCase("Submitted to Customer")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Customer Shortlisted");
			list.add(candidateStatus);

			CandidateStatus candidateStatus1 = new CandidateStatus();
			candidateStatus1.setStatus("Customer Rejected");
			list.add(candidateStatus1);
		}

		if (status.equalsIgnoreCase("Customer Shortlisted")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Process for Interview");
			list.add(candidateStatus);
		}
		if (status.equalsIgnoreCase("Process for Interview")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Waiting for Interview Feedback");
			list.add(candidateStatus);
		}

		if (status.equalsIgnoreCase("Waiting for Interview Feedback")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Customer feedback");
			list.add(candidateStatus);

		}

		if (status.equalsIgnoreCase("Customer feedback")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Shortlisted by Customer");
			list.add(candidateStatus);

		}
		if (status.equalsIgnoreCase("InterviewSchedule")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Process for Interview");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}

		if (status.equalsIgnoreCase("Offer Release")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("Offer Release");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}
		if (status.equalsIgnoreCase("On Board")) {
			CandidateStatus candidateStatus = new CandidateStatus();
			candidateStatus.setStatus("On Board");
			list.add(candidateStatus);

			/*
			 * CandidateStatus candidateStatus1 = new CandidateStatus();
			 * candidateStatus.setStatus("Shortlisted by Customer");
			 * list.add(candidateStatus1);
			 */
		}

		return new Response(ExceptionMessage.StatusSuccess, list);

	}

	@GET
	@Path("/downloadResume/{candidateId}")
	public javax.ws.rs.core.Response downloadApkFile(@PathParam("candidateId") Long id) {
		final Long cid = id;
		StreamingOutput fileStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
				try {
					java.nio.file.Path path = Paths.get(resumesfolder + cid + ".pdf");
					byte[] data = Files.readAllBytes(path);
					output.write(data);
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException();
				}
			}
		};
		return javax.ws.rs.core.Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename = " + cid + ".pdf").build();
	}

	@Path("/uploadZip/{candidateId}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadZipOrRar(@PathParam("candidateId") Long candidateId,
			@FormDataParam("zipfile") InputStream fileData,
			@FormDataParam("zipfile") FormDataContentDisposition fileInfo) throws IOException {
		InputStream is = new ByteArrayInputStream(StreamUtils.copyToByteArray(fileData));
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry readEntry;

		FileOutputStream fout = new FileOutputStream(documentsfolder + candidateId + ".zip");
		ZipOutputStream zout = new ZipOutputStream(fout);

		Response response = null;

		try {
			while ((readEntry = zis.getNextEntry()) != null) {

				int size;
				byte[] buffer = new byte[2048];

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos, buffer.length);

				while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
					bos.write(buffer, 0, size);
				}
				bos.flush();
				bos.close();

				ZipEntry writeEntry = new ZipEntry(readEntry.getName());
				zout.putNextEntry(writeEntry);
				zout.write(baos.toByteArray());

				response = new Response(ExceptionMessage.OK);

			}
		} catch (Exception e) {
			response = new Response(ExceptionMessage.valueOf("Zip File Uploading Failed " + e.getLocalizedMessage()));
			e.printStackTrace();
		}

		zis.close();
		zout.close();
		is.close();
		return response;
	}

}
