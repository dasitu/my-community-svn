package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;


import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


import com.sun.jersey.multipart.FormDataParam;
import com.xiaoquyi.jsonelements.Elements;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;


@Path("/get_communities")
public class GetCommunities extends AbstractAPI {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Elements getCommunities() throws IOException, NamingException, ParseException {
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
			
			communities.setElements(LoadElements.loadElements(conn, rs, new LoadElements.LoadCommunity()));
			rs.close();
			conn.close();
			communities.setStatus(new Status());
			return communities;

		}
		catch (Exception e) {
			return null;
		}
		
	}
}
