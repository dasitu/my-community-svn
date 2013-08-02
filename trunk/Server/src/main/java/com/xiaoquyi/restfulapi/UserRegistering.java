package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;

@Path("/user_register")
public class UserRegistering extends AbstractAPI {
	
	@OPTIONS
	public Status optionsResponse() throws IOException {
		allowCORS();
		return new Status();
	}
	

	/** @brief The interface is used to accept a user registering request and do the follow registering steps,
	 * insert user information into DB; The user information come from the http POST request, The API
	 * address is \b /user_register
	 * 
	 * @param userName The user name of the registering account, mandatory.
	 * @param password The password of the newly registering account, mandatory.
	 * @param weibo The user sina weibo user name, optional, default to null.
	 * @param email The email address,optional, default to null.
	 * @param qq The QQ number,optional, default to null.
	 * @param communityID The id of the community the registering user living,optional, default to -1.
	 * @param visible This parameter control the user if is visible to others,,optional, default to 1,means visible.
	 * @return One instance of \ref jsonelements.Status which give you the result, successful or failed
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Status doRegister(@FormParam("username") String userName,
			@FormParam("password") String passwd,
			@DefaultValue("null") @FormParam("weibo") String weibo,
			@DefaultValue("null") @FormParam("email") String email,
			@DefaultValue("null") @FormParam("qq") String qq,
			@DefaultValue("-1") @FormParam("communityID") String communityID,
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
		if (re == -1) {
			conn.close();
			return new Status(1001,-1,"Insert to DB user info error!",1000);
		}
		
		//@TODO:Need validation for communityID
		if (communityID != "-1") {
			String insertMapping = String.format(SQLStatements.I_MAPPING_ITEM, re, communityID);
			if(DBconnector.DBUpdate(conn,insertMapping) == -1) {
				conn.close();
				return new Status(1001,-1,"Insert to DB mapping table error!",1000);
			}
			
		}
		conn.close();
		return new Status();
	}
}
