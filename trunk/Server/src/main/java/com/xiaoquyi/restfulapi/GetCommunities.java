package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Community;
import com.xiaoquyi.utilities.*;


@Path("/get_communities")
public class GetCommunities extends AbstractAPI {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Community> getLatest10Notices() throws IOException, NamingException, ParseException {
		allowCORS();
		Logger.info(getSelfInfo());
		List<Community> list = null;
		if (!accessTokenValidation()) {
			Logger.info("access token expired!");
//			list.add(new Status(10000,-1,"access token expired!",10000));
			return list;
		}
		try {
			
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_COMMUNITIES);

			if (rs == null) {
				return list;
			}
			
			list = LoadElements.loadCommunities(rs);
			rs.close();
			conn.close();
			return list;

		}
		catch (Exception e) {
			return null;
		}
		
	}
}
