package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Community;
import com.xiaoquyi.utilities.DBconnector;
import com.xiaoquyi.utilities.Logger;
import com.xiaoquyi.utilities.SQLStatements;


@Path("/get_communities")
public class GetCommunities extends AbstractAPI {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Community> getLatest10Notices() throws IOException, NamingException, SQLException {
		Logger.infoWritting(getSelfInfo());
		
		Object re = DBconnector.executeSqlStatement(SQLStatements.S_GET_COMMUNITIES);
		
		
		try {
			if ((Integer)re == -1)
				return null;
		}
		catch(ClassCastException cce) {
			Logger.warningWritting(cce.getMessage());
		}
		List<Community> list = new LinkedList<Community>();
		ResultSet rs = (ResultSet)re;
		Logger.warningWritting(rs.toString());
		while (rs.next()) {
			String province = rs.getString("prov_name");
			Logger.warningWritting(province);
			String city = rs.getString("city_name");
			
			String area = rs.getString("area_name");
			Logger.warningWritting(province);
			String name = rs.getString("comm_name");
			
			String address = rs.getString("comm_address");
			
			int active = rs.getInt("comm_active");
			
			
			Timestamp lastAccess = rs.getTimestamp("comm_last_update");
			Logger.debugWritting(province+ " " + city + " " + area + " " + name + " " + address
					 + " " + lastAccess.toString());
			Community item = new Community(province,city,area,address,name,active,lastAccess.toString());
			//TODO: add image URL to DB
			list.add(item);			
		}
		rs.close();
		return list;
	}
}
