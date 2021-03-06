/* this is done by Bhanuprakash  */
package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.addClientContact.AddContactDao;
import com.ojas.rpo.security.dao.client.ClientDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Services;

@Component
@Path("/client")
public class ClientResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClientDao clientDao;

	@Autowired
	private AddContactDao addContactDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getdata() throws IOException {

		List<Client> add = this.clientDao.findAll();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		}

		else

		if (add.size() > 0) {

			Date date = new Date();
			ListIterator litr = add.listIterator();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

			while (litr.hasNext()) {
				Client client1 = (Client) litr.next();
				client1.setDate1(formatter.format(client1.getDate() != null ? client1.getDate() : date));
			}

			return new Response(ExceptionMessage.StatusSuccess, add);

		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		Client client = this.clientDao.find(id);
		if (client == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, client);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Client client) {
		System.out.println("==========inside post method====RPO");
		client.setId(id);
		Response response = null;
		if (this.clientDao.save(client) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			if (client.getContactId() != null) {
				AddContact con = addContactDao.find(client.getContactId());
				con.setEmail(client.getEmail());
				con.setMobile(client.getPhone() + "");
				con.setContact_Name(client.getSpocName());
				con.setStatus("Active");
				con.setClient(client);
				con = addContactDao.save(con);
			} else {
				AddContact con = new AddContact();
				con.setEmail(client.getEmail());
				con.setMobile(client.getPhone() + "");
				con.setContact_Name(client.getSpocName());
				con.setStatus("Active");
				con.setClient(client);
				con = addContactDao.save(con);
			}
			response = new Response(ExceptionMessage.StatusSuccess, client);
			response.setRes("Customer Details Updated Successfully ");
		}

		return response;
	}

	@POST

	@Produces(MediaType.APPLICATION_JSON)

	@Consumes(MediaType.APPLICATION_JSON)

	public @ResponseBody Response addClient(Client client) {
		Response response = null;

		List<Services> list = new ArrayList<Services>();
		System.out.println("==========inside post method====RPO" + client.toString());

		if (client.getServices() != null) {
			for (Services ser : client.getServices()) {
				// Services sernew = new Services();
				ser.setId(1L);
				list.add(ser);
			}
			client.setServices(list);
		}

		try {
			boolean b = this.clientDao.chekduplicate(client.getClientName(), client.getEmail(), client.getPhone());
			if (b) {

				response = new Response(ExceptionMessage.DuplicateRecord, client);
				response.setRes("Customer alredy exist !! ");
			} else {
				client = this.clientDao.save(client);

				AddContact contact = new AddContact();
				contact.setEmail(client.getEmail());
				contact.setMobile(client.getPhone() + "");
				contact.setContact_Name(client.getSpocName());
				contact.setClient(client);
				contact.setStatus("Active");
				contact = addContactDao.save(contact);
				client.setContactId(contact.getid());

				client = this.clientDao.save(client);
				response = new Response(ExceptionMessage.StatusSuccess, client);
				response.setRes("Customer Details Created Successfully ");

			}
		} catch (Exception e) {
			response.setRes("Customer alredy exist !! ");
		}
		return response;

	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public @ResponseBody Response
	 * addClient(Client client) { Response response = null; if (client == null)
	 * {
	 * 
	 * response = new Response(ExceptionMessage.DataIsEmpty); response.setRes(
	 * "Invalid Customer Details"); return response; } List<Services> list = new
	 * ArrayList<Services>(); System.out.println(
	 * "==========inside post method====RPO" + client.toString()); if
	 * (client.getServices() != null || !client.getServices().isEmpty()) { for
	 * (Services ser : client.getServices()) { // Services sernew = new
	 * Services(); ser.setId(1L); // list.add(ser); } client.setServices(list);
	 * }
	 * 
	 * client = this.clientDao.save(client); response = new
	 * Response(ExceptionMessage.StatusSuccess, client); response.setRes(
	 * "Customer Details Created Successfully");
	 * 
	 * if (client == null) {
	 * 
	 * response = new Response(ExceptionMessage.DataIsEmpty); response.setRes(
	 * "Invalid Customer Details"); }
	 * 
	 * } return response; }
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findResourcesForClient/{id}")
	public @ResponseBody Response findContactByClientId(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		List<BdmReq> requirementlist = this.clientDao.findResourcesForClient(id);

		if (requirementlist == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}

		if (requirementlist.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, requirementlist);
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
	}

	// GetClients Based on ROleName

	// GetClients Based on ROleName

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getClientsByRole/{id}/{role}")
	public @ResponseBody Response getClientsByRole(@PathParam("id") Long id, @PathParam("role") String role) {

		this.logger.info("read(id)");

		List<Client> clientslist = this.clientDao.getClientsByRole(id, role);

		if (clientslist == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}

		if (clientslist.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, clientslist);
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllClientsByRole/{id}/{role}/{pageNo}/{pageSize}")
	public Response getAllClientsByRole(@PathParam("id") Long id, @PathParam("role") String role,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@QueryParam("sortingOrder") String sortingOrder, @QueryParam("sortingField") String sortingField,
			@QueryParam("searchText") String searchText, @QueryParam("searchField") String searchField) {

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}

		return this.clientDao.getAllClientsByRole(id, role, pageNo, pageSize, sortingOrder, sortingField,searchText,searchField);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchClients/{role}/{id}/{searchInput}/{searchField}/{pageNo}/{pageSize}")
	public Response searchRequirements(@PathParam("role") String role, @PathParam("id") Long id,
			@PathParam("searchInput") String searchInput, @PathParam("searchField") String searchField,
			@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize) {

		if (null == role || role.isEmpty()) {
			return new Response(ExceptionMessage.valueOf("value role 'role' not specified"));
		}

		if (null == id) {
			return new Response(ExceptionMessage.valueOf("value of 'id' not specified"));
		}
		
		if (null == pageNo) {
			return new Response(ExceptionMessage.valueOf("value of 'pageNo' not specified"));
		}

		if (null == pageSize) {
			return new Response(ExceptionMessage.valueOf("value of 'pageSize' not specified"));
		}
		
			return this.clientDao.searchClients(role, id, searchInput, searchField, pageNo, pageSize);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateContactStatus/{id}/{status}")
	public @ResponseBody Response getUpdateContactStatusById(@PathParam("id") Long id,
			@PathParam("status") String status) throws IOException {
		this.logger.info("list()");

		int i = this.addContactDao.updatingStatus(id, status);

		if (i == 0) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else
			return new Response(ExceptionMessage.StatusSuccess);

	}

}