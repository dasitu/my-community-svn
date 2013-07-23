package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Users;
import com.xiaoquyi.utilities.DBconnector;
import com.xiaoquyi.utilities.Logger;
import com.xiaoquyi.utilities.SQLStatements;


@Path("/get_users")
public class GetUsers extends AbstractAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Users getUsers() throws IOException, SQLException, NamingException, ParseException {
		allowCORS(); 
		Logger.info(getSelfInfo());
		
		Users users = new Users();
		
		if (!accessTokenValidation()) {
			Logger.info("access token expired!");
			users.setStatus(10000, -1, "access token expired!", 10000);
			return users;
		}
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_USERS);

			if (rs == null)
				return null;

			
			while (rs.next()) {
				String name = rs.getString("user_name");
				String weibo = rs.getString("user_weibo");
				String qq = rs.getString("user_qq");
				String email = rs.getString("user_email");
				Timestamp lastAccess = rs.getTimestamp("user_last_update");
				Logger.debug(name+ " " + weibo + " " + qq + " " +  email + " " +  lastAccess.toString());
				Users.User user = new Users.User(name,weibo,qq,email,lastAccess.toString());
				String sqlGetCommunities = String.format(SQLStatements.S_GET_COMMUNITIES_BY_UID,rs.getString("user_id"));
				ResultSet communities = DBconnector.DBQuery(conn,sqlGetCommunities);
				while(communities.next()) {
					Logger.debug(communities.getString("comm_name"));
					user.addCommunity(communities.getString("comm_name"));
				}
				communities.close();
				users.addUser(user);			
			}
			rs.close();
			conn.close();
			return users;

		}
		catch (SQLException e) {
			Logger.warning(e.getMessage());
			users.setStatus(10000, -1, e.getMessage(), 10000);
			return users;
		}
	}
}
