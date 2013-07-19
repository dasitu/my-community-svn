package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.*;
import com.xiaoquyi.utilities.*;


@Path("/get_communities")
public class GetCommunities extends AbstractAPI {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Element> getLatest10Notices() throws IOException, NamingException {
		allowCORS();
		Logger.info(getSelfInfo());
		try {
			List<Element> list = new LinkedList<Element>();
			if (!accessTokenValidation()) {
				Logger.info("access token expired!");
				list.add(new Status(10000,-1,"access token expired!",10000));
				return list;
			}
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_COMMUNITIES);

			if (rs == null) {
				return list;
			}

//			List<Element> list = new LinkedList<Element>();
			while (rs.next()) {
				String province = rs.getString("prov_name");
				Logger.warning(province);
				String city = rs.getString("city_name");

				String area = rs.getString("area_name");
				Logger.warning(province);
				String name = rs.getString("comm_name");

				String address = rs.getString("comm_address");

				int active = rs.getInt("comm_active");


				Timestamp lastAccess = rs.getTimestamp("comm_last_update");
				Logger.debug(province+ " " + city + " " + area + " " + name + " " + address
						+ " " + lastAccess.toString());
				Community item = new Community(province,city,area,address,name,active,lastAccess.toString());
				list.add(item);			
			}
			rs.close();
			conn.close();
			return list;

		}
		catch (Exception e) {
			return null;
		}
		
	}
}
