package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Community;
import com.xiaoquyi.jsonelements.Elements;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;


@Path("/get_communities")
public class GetCommunities extends AbstractAPI {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Elements getLatest10Notices() throws IOException, NamingException, ParseException {
		allowCORS();
		Logger.info(getSelfInfo());
		Elements communities = new Elements();
		if (!accessTokenValidation()) {
			Logger.info("access token expired!");
			communities.setStatus(new Status(10000,-1,"access token expired!",10000));
			return communities;
		}
		try {
			
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_COMMUNITIES);

			if (rs == null) {
				communities.setStatus(new Status(10000,-1,"access token expired!",10000));
				return communities;
			}
			
			communities.setElements(LoadElements.loadElements(rs, new LoadElements.LoadCommunity()));
			rs.close();
			conn.close();
			return communities;

		}
		catch (Exception e) {
			return null;
		}
		
	}
}
