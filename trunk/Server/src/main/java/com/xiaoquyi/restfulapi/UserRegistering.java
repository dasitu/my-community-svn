package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.xiaoquyi.utilities.*;

@Path("/user_register")
public class UserRegistering extends AbstractAPI {
	
	@OPTIONS
	public Response optionsResponse() throws IOException {
		allowCORS();
		return Response.status(200).entity("asafdsa").build();
	}
	

	@POST
	public Response doRegister(@FormParam("username") String userName,
			@FormParam("password") String passwd,
			@DefaultValue("null") @FormParam("weibo") String weibo,
			@DefaultValue("null") @FormParam("email") String email,
			@DefaultValue("null") @FormParam("qq") String qq,
			@DefaultValue("1") @FormParam("visible") String visible) throws SQLException, NamingException, IOException {
		allowCORS();
		String sqlStatement = String.format(SQLStatements.I_USER_REGISTERING,
				userName,
				passwd,
				weibo,
				email,
				qq,
				visible);


		Logger.info(getSelfInfo());
		Connection conn = DBconnector.getConnection();
		int re = DBconnector.DBUpdate(conn,sqlStatement);
		conn.close();
		if (re == -1) {
			return Response.status(200).entity("{\"success\":\"true\"}").build();
		}
		return Response.status(200).entity("{\"success\":\"false\"}").build();
	}
}
