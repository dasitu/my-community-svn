package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;

import javax.naming.NamingException;
import javax.ws.rs.*;

import com.xiaoquyi.utilities.*;

@Path("/user_register")
public class UserRegistering extends AbstractAPI {

	@POST
	public String doRegister(@FormParam("username") String userName,
			@FormParam("password") String passwd,
			@FormParam("weibo") String weibo,
			@FormParam("email") String email,
			@FormParam("qq") String qq,
			@FormParam("visible") String visible) throws SQLException, NamingException, IOException {

		String sqlStatement = String.format(SQLStatements.I_USER_REGISTERING,
				userName,
				passwd,
				weibo,
				email,
				qq,
				visible,
				Miscellaneous.getCurrentTimestamp());


		Logger.infoWritting(getSelfInfo());
		Logger.debugWritting(sqlStatement);

		try {
			Connection conn = DBconnector.getConnection();

			Statement st = conn.createStatement();
			st.executeUpdate(sqlStatement);
			return "0";
		} 
		catch(SQLException sqle) {
			Logger.infoWritting(sqle.getMessage());
			return "-1";
		}
	}
}
