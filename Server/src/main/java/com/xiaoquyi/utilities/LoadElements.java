package com.xiaoquyi.utilities;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.xiaoquyi.jsonelements.Community;
import com.xiaoquyi.jsonelements.Element;

public class LoadElements {
	
public static  Community loadCommunityFromDb(final ResultSet rs) throws SQLException, IOException {
		
		String province = rs.getString("prov_name");
		String city = rs.getString("city_name");
		String area = rs.getString("area_name");
		String name = rs.getString("comm_name");
		String address = rs.getString("comm_address");
		int active = rs.getInt("comm_active");
		Timestamp lastAccess = rs.getTimestamp("comm_last_update");
		Logger.debug(province+ " " + city + " " + area + " " + name + " " + address
				+ " " + lastAccess.toString());
		return new Community(province,city,area,address,name,active,lastAccess.toString());
		
	}

	public static List<Element> loadCommunities(final ResultSet rs) throws SQLException, IOException {
		List<Element> list = new LinkedList<Element>();
		while (rs.next()) {
			Community item = loadCommunityFromDb(rs);
			list.add(item);			
		}
		return list;
	}
	

}


