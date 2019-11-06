package com.ojas.rpo.security.dao.incentive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IncentiveNew;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.transfer.IncentivesData;

public class JpaIncentiveDao extends JpaDao<IncentiveNew, Long> implements IncentiveDao {
	// StoredProcedureQuery query=null;
	public JpaIncentiveDao() {
		super(IncentiveNew.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional
	public IncentiveNew save(IncentiveNew entity) {
		return this.getEntityManager().merge(entity);
	}

	public Response getRecruiterIncentive(Long userId) {
		StoredProcedureQuery query = null;
		try {

			query = getEntityManager().createNamedStoredProcedureQuery("calIncentivesProcedure").setParameter("userId",
					userId);

			query.execute();
		} catch (Exception e) {
		}
		try {
			query = getEntityManager().createNamedStoredProcedureQuery("calIncentivesUnProcessed")
					.setParameter("userId", userId);

			query.execute();
		} catch (Exception e) {
		}
		try {
			query = getEntityManager().createNamedStoredProcedureQuery("calIncentiveForDebit").setParameter("userId",
					userId);

			query.execute();
		} catch (Exception e) {
		}

		return new Response(ExceptionMessage.StatusSuccess, "200", "Successfuly incentives generated");
	}

	/**
	 * 
	 */
	@Transactional
	public Response getAllRecruitersIncentives(List<User> users) {
		StoredProcedureQuery query = null;

		for (User user : users) {
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("calIncentivesProcedure")
						.setParameter("userId", user.getId());

				query.execute();
			} catch (Exception e) {
			}
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("calIncentivesUnProcessed")
						.setParameter("userId", user.getId());

				query.execute();
			} catch (Exception e) {
			}
			try {
				query = getEntityManager().createNamedStoredProcedureQuery("calIncentiveForDebit")
						.setParameter("userId", user.getId());
				query.execute();
			} catch (Exception e) {

			}

		}
		return new Response(ExceptionMessage.StatusSuccess, "200", "Successfuly incentives generated");

	}

	@Override
	public Response getIncentiveList(String role, Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {

		String sortOrder = "DESC";
		String sortField = "incen.id";
		Integer totalRecords = 0;
		Response response = null;
		Query countQuery = null;
		Query query = null;
		List<IncentivesData> list = new ArrayList<IncentivesData>();

		if ((null != sortingOrder) && (sortingOrder.startsWith("asc") || sortingOrder.equalsIgnoreCase("asc"))) {
			sortOrder = "ASC";
		}

		if (null != sortingField && !searchField.equals("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("recId") || "recId".contains(sortingField)) {
				sortField = "incen.recId";
			} else if (sortingField.equalsIgnoreCase("date") || "date".contains(sortingField)) {
				sortField = "incen.date";
			}

		}

		if (null != searchField && !searchField.equals("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("recId") || "recId".contains(searchField)) {
				searchField = "incen.recId";
			} else if (searchField.equalsIgnoreCase("date") || "date".contains(searchField)) {
				searchField = "incen.date";
			}

		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		String sql = "select incen.id as incenId,incen.date as calDate,incen.recId as "
				+ " recid,incen.canId as canId,can.firstName as cfname, can.lastName as clname,br.id as reqId, br.nameOfRequirement,cr_Amount,dr_Amount ,"
				+ " users.email ,users.firstName as ufanme,users.lastName as ulname, users.role from incentivenew incen ,user users ,candidate can ,bdmreq br,"
				+ " candidatemapping map  where  users.id=incen.recId and can.id =incen.canId  and can.id=map.candidate_id and br.id=map.bdmReq_id ";

		String countSql = " SELECT count(*) from incentivenew incen ,user users ,candidate can ,bdmreq br,"
				+ " candidatemapping map  where  users.id=incen.recId and can.id =incen.canId "
				+ " and can.id=map.candidate_id and br.id=map.bdmReq_id ";
		StringBuilder sqlbuilder = new StringBuilder(sql);
		if (role.equalsIgnoreCase("BDM") || role.equalsIgnoreCase("recruiter")) {

			sqlbuilder.append(" and incen.recId=" + id);

		}

		if (role.equalsIgnoreCase("Lead")) {

			sqlbuilder.append(" and ( incen.recId=" + id + " or users.reportingId=" + id + ")");

		}

		if ((null != searchText) && (null != searchField) && !searchField.equalsIgnoreCase("undefined")) {
			sqlbuilder.append(" AND " + searchField + "=" + searchText + " ORDER BY " + sortField + " " + sortOrder
					+ " LIMIT " + startingRow + "," + pageSize);
		} else {
			sqlbuilder.append(" and incen.date>=(CURDATE()-INTERVAL 1 MONTH) ORDER BY " + sortField + " " + sortOrder
					+ " LIMIT " + startingRow + "," + pageSize);
		}
		System.out.println(sqlbuilder.toString());
		query = this.getEntityManager().createNativeQuery(sqlbuilder.toString());

		countQuery = this.getEntityManager().createNativeQuery(countSql.toString());

		List<Object[]> searchList = query.getResultList();
		if (searchList.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
		} else {
			for (Object[] req : searchList) {
				IncentivesData transferObj = new IncentivesData();
				transferObj.setId(Long.parseLong(req[0].toString()));
				transferObj.setDate((Date) req[1]);
				transferObj.setRecId(Long.parseLong(req[2].toString()));
				transferObj.setCanId(Long.parseLong(req[3].toString()));
				transferObj.setCandidateName((String) req[4] + (String) req[5]);
				transferObj.setReqId(Long.parseLong(req[6].toString()));
				transferObj.setRequirementName((String) req[7]);
				if (req[8] != null) {
					transferObj.setCr_Amount(Double.parseDouble(req[8].toString()));
				}
				if (req[9] != null) {
					transferObj.setDr_Amount(Double.parseDouble(req[9].toString()));
				}
				transferObj.setEmailId((String) req[10]);
				transferObj.setRecruiterName((String) req[11] + (String) req[12]);
				transferObj.setRole((String) req[13]);
				list.add(transferObj);
			}
			response = new Response(ExceptionMessage.StatusSuccess, list);
		}

		System.out.println(countQuery.getSingleResult());
		totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
		response.setTotalRecords(totalRecords);
		if (totalRecords == 0) {
			response.setTotalRecords(totalRecords);
			response.setTotalPages(0);
		}

		Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
		totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
		response.setTotalPages(totalPages);

		return response;

	}
}
