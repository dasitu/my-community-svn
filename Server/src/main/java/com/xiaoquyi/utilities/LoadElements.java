package com.xiaoquyi.utilities;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.xiaoquyi.jsonelements.Community;

public class LoadElements {

	public static List<Community> loadCommunities(final ResultSet rs) throws SQLException, IOException {
		List<Community> list = new LinkedList<Community>();
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
		return list;
	}
}
