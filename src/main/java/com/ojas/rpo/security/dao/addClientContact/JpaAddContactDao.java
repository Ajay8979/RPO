package com.ojas.rpo.security.dao.addClientContact;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ContactListTransfer;

public class JpaAddContactDao extends JpaDao<AddContact, Long> implements AddContactDao {

	public JpaAddContactDao() {
		super(AddContact.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional
	public AddContact save(AddContact entity) {
		// TODO Auto-generated method stub
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findContactByClientId(Long id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), id);
		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findActiveContactByClientId(Long id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"));
		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Response findContactByBdmId(Long id, String role, String sortField, String sortOrder,
			String searchField, String searchText, Integer pageNo, Integer pageSize) {
        Response response = null;
		Query query = null;
		String sql = "SELECT a.contact_Name,a.mobile,a.email,c.clientName,a.id FROM `addcontact` a INNER JOIN `client` c on a.client_id=c.id ";
		StringBuilder sqlbuilder = new StringBuilder(sql);

		if (null != sortField && !sortField.isEmpty() && !sortField.equalsIgnoreCase("undefined")) {
             if(sortField.equalsIgnoreCase("contactName")) {
            	 sortField = "a.contact_Name";
             }
             if(sortField.equalsIgnoreCase("clientName")) {
            	 sortField = "c.clientName";
             }
             if(sortField.equalsIgnoreCase("email")) {
            	 sortField = "a.email";
             }
             if(sortField.equalsIgnoreCase("mobile")) {
            	 sortField = "a.mobile";
             }
		} else {
			sortField = "a.contact_Name";
		}
		
		if(sortOrder.equalsIgnoreCase("ASC")) {
			sortOrder = "ASC";
		}else {
			sortOrder = "DESC";
		}

		if (null != searchField && !searchField.isEmpty() && !searchField.equalsIgnoreCase("undefined")) {
			 if(searchField.equalsIgnoreCase("contactName")) {
				 searchField = "a.contact_Name";
             }
             if(searchField.equalsIgnoreCase("clientName")) {
            	 searchField = "c.clientName";
             }
             if(searchField.equalsIgnoreCase("email")) {
            	 searchField = "a.email";
             }
             if(searchField.equalsIgnoreCase("mobile")) {
            	 searchField = "a.mobile";
             }
		} 
		
		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (role.equalsIgnoreCase("BDM")) {
				// query = getEntityManager().createQuery("from AddContact req where
				// req.client.primaryContact.id=? or req.client.secondaryContact.id=? order by
				// id desc");
				sqlbuilder.append(" WHERE (c.primaryContact_id=? OR c.secondaryContact_id=?) ");

				System.out.println("search test = "+searchText);
				System.out.println("search field = "+searchField);
				
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " LIMIT "+startingRow+","+pageSize);
				} else {
					sqlbuilder.append(" ORDER BY " + sortField + " " + sortOrder+ " LIMIT "+startingRow+","+pageSize);
				}

				System.out.println("query = "+sqlbuilder.toString());
				
				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
				query.setParameter(2, id);
			} else {
				//query = getEntityManager().createQuery("from AddContact req  where req.client.accountManger.id=?  order by id desc");
				
				sqlbuilder.append(" WHERE c.accountManger_id=? ");
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " "+ " LIMIT "+startingRow+","+pageSize);
				} else {
					sqlbuilder.append(" ORDER BY " + sortField + " " + sortOrder+ " LIMIT "+startingRow+","+pageSize);
				}
				
				System.out.println("query = "+sqlbuilder.toString());
				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
			}
 
			List<Object[]> results = query.getResultList();
			List<ContactListTransfer> contactList = new ArrayList<ContactListTransfer>();
			if(!results.isEmpty()) {
				for(Object[] data : results){
					ContactListTransfer contact = new ContactListTransfer();
					
					contact.setContactName((String)data[0]);
					contact.setMobile((String)data[1]);
					contact.setEmail((String)data[2]);
					contact.setClientName((String)data[3]);
					contact.setId(data[4].toString());
					contactList.add(contact);
				}
				
				response = new Response(ExceptionMessage.OK, contactList);
			}else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Contacts Found");
			}
			response.setTotalRecords(results.size());
			int totalRecords = results.size();
			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			}

			if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
				response.setTotalPages(1);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", " Unable Retrieve Contact List Due to following Exception : "+e.getMessage());
		}
		
		return response;

	}

	@Override
	@Transactional
	public int updatingStatus(Long id, String status) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery("update addcontact set status=?  where id =?");
			q.setParameter(1, status);
			q.setParameter(2, id);
			i = q.executeUpdate();

		}

		return i;

	}

}
