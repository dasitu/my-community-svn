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
			@DefaultValue("") @FormParam("weibo") String weibo,
			@DefaultValue("") @FormParam("email") String email,
			@DefaultValue("") @FormParam("qq") String qq,
			@DefaultValue("1") @FormParam("visible") String visible) throws SQLException, NamingException, IOException {

		String sqlStatement = String.format(SQLStatements.I_USER_REGISTERING,
				userName,
				passwd,
				weibo,
				email,
				qq,
				visible,
				Miscellaneous.getCurrentTimestamp());


		Logger.infoWritting(getSelfInfo());

		if ((Integer)DBconnector.executeSqlStatement(sqlStatement) != -1)
			return "0";
		return "-1";
	}
}
