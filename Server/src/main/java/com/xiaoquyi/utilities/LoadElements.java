package com.xiaoquyi.utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.xiaoquyi.jsonelements.Community;
import com.xiaoquyi.jsonelements.Element;
import com.xiaoquyi.jsonelements.User;



interface LoadElement {
	public Element loadElementFromDb(final Connection conn, final ResultSet rs) throws SQLException, IOException;
}


public class LoadElements {
	
	
	public static class LoadCommunity implements LoadElement{
		public  Element loadElementFromDb(final Connection conn,final ResultSet rs) throws SQLException, IOException {
			
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
	}

	public static class LoadUser implements LoadElement{
		public  Element loadElementFromDb(final Connection conn,final ResultSet rs) throws SQLException, IOException {
			
			String name = rs.getString("user_name");
			String weibo = rs.getString("user_weibo");
			String qq = rs.getString("user_qq");
			String email = rs.getString("user_email");
			Timestamp lastAccess = rs.getTimestamp("user_last_update");
			Logger.debug(name+ " " + weibo + " " + qq + " " +  email + " " +  lastAccess.toString());
			User user = new User(name,weibo,qq,email,lastAccess.toString());
			String sqlGetCommunities = String.format(SQLStatements.S_GET_COMMUNITIES_BY_UID,rs.getString("user_id"));
			
			ResultSet communities = DBconnector.DBQuery(conn,sqlGetCommunities);
			user.setCommunities(LoadElements.loadElements(conn,communities, new LoadCommunity()));
			communities.close();
			return user;
		}
	}


	public static List<Element> loadElements(final Connection conn,final ResultSet rs,LoadElement loader) throws SQLException, IOException {
		List<Element> list = new LinkedList<Element>();
		while (rs.next()) {
			Element item = loader.loadElementFromDb(conn,rs);
			list.add(item);			
		}
		return list;
	}
	

}


