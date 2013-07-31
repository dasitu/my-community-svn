package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Elements;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.DBconnector;
import com.xiaoquyi.utilities.LoadElements;
import com.xiaoquyi.utilities.Logger;
import com.xiaoquyi.utilities.SQLStatements;


@Path("/get_users")
public class GetUsers extends AbstractAPI {

	/**This interface accept the http GET request to get registered user's information. API address: The \b /get_users 
	 * 
	 *@return One list of \ref jsonelements.User have a number of user information which in the form of json list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Elements getUsers() throws IOException, SQLException, NamingException, ParseException {
		allowCORS(); 
		Logger.info(getSelfInfo());
		
		Elements users = new Elements();
		
		if (!accessTokenValidation()) {
			Logger.info("access token error or expired!");
			users.setStatus(new Status(10000, -1,"access token error or expired!", 10000));
			return users;
		}
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_GET_USERS);

			users.setElements(LoadElements.loadElements(conn, rs, new LoadElements.LoadUser()));
			
			rs.close();
			conn.close();
			users.setStatus(new Status());
			return users;

		}
		catch (SQLException e) {
			Logger.warning(e.fillInStackTrace().toString());
			users.setStatus(new Status(10000, -1, e.getMessage(), 10000));
			return users;
		}
	}
}
