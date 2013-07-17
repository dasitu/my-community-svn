package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.User;
import com.xiaoquyi.utilities.DBconnector;
import com.xiaoquyi.utilities.Logger;
import com.xiaoquyi.utilities.SQLStatements;


@Path("/get_users")
public class GetUsers extends AbstractAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() throws IOException, SQLException, NamingException {
		Logger.info(getSelfInfo());
		
//		Object re = DBconnector.executeSqlStatement(SQLStatements.S_GET_USERS);
//
//		try {
//			if ((Integer)re == -1)
//				return null;
//		}
//		catch(ClassCastException cce) {
//			Logger.warning(cce.getMessage());
//		}
//		List<User> list = new LinkedList<User>();
//		ResultSet rs = (ResultSet)re;
//		while (rs.next()) {
//			String name = rs.getString("user_name");
//			Logger.warning(name);
//			String weibo = rs.getString("user_weibo");
//			String qq = rs.getString("user_qq");
//			String email = rs.getString("user_email");
//			Timestamp lastAccess = rs.getTimestamp("user_last_update");
//			Logger.debug(name+ " " + weibo + " " + qq + " " +  email + " " +  lastAccess.toString());
//			User item = new User(name,weibo,qq,email,lastAccess.toString());
//			list.add(item);			
//		}
//		rs.close();
//		return list;
		
		
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_USERS);

			if (rs == null)
				return null;

			List<User> list = new LinkedList<User>();
			while (rs.next()) {
				String name = rs.getString("user_name");
				Logger.warning(name);
				String weibo = rs.getString("user_weibo");
				String qq = rs.getString("user_qq");
				String email = rs.getString("user_email");
				Timestamp lastAccess = rs.getTimestamp("user_last_update");
				Logger.debug(name+ " " + weibo + " " + qq + " " +  email + " " +  lastAccess.toString());
				User item = new User(name,weibo,qq,email,lastAccess.toString());
				list.add(item);			
			}
			rs.close();
			conn.close();
			return list;

		}
		catch (SQLException e) {
			return null;
		}
	}
}
