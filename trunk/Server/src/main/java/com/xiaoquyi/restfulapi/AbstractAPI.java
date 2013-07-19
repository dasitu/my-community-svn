package com.xiaoquyi.restfulapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;

import javax.ws.rs.core.*;

import javax.naming.NamingException;
import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import com.xiaoquyi.utilities.DBconnector;
import com.xiaoquyi.utilities.Logger;
import com.xiaoquyi.utilities.Miscellaneous;
import com.xiaoquyi.utilities.SQLStatements;

@SuppressWarnings("unused")
public class AbstractAPI {

	
	@Context
	Request req;
	@Context
	UriInfo  ui;
	@Context
	HttpHeaders header;
	@Context
	SecurityContext sc;
	
	@Context
	ServletConfig servletConfig;
	@Context
	ServletContext servletContext;
	@Context
	HttpServletRequest httpServletRequest;
	@Context
	HttpServletResponse httpServletResponse;
	
	
	public String getSelfInfo() {
		StackTraceElement currentElement = (new Throwable()).getStackTrace()[1];
		String selfInfo = String.format("\tApiURL:\t%s\n", getInvokedAPI());
		selfInfo += String.format("\tClassName:\t%s\n", currentElement.getClassName());
		selfInfo += String.format("\tMethodName:\t%s\n",  currentElement.getMethodName());
		selfInfo += String.format("\tClientIP:\t%s\n",  getClientIP()); 
		selfInfo += this.toString();
		return selfInfo;
	}
	
	public String getClientIP() {
		return httpServletRequest.getRemoteAddr();
	}
	
	public String getInvokedAPI() {
		return  ui.getAbsolutePath().toString();
	}
	
	public HttpSession getHttpSession() {
		return httpServletRequest.getSession();
	}
	
	protected boolean accessTokenValidation() throws ParseException, NamingException, IOException {
//		String at = httpServletRequest.getParameter("accesstoken");
		Cookie[] cookie = httpServletRequest.getCookies();
		String accessTokenInCookie = null;
		String uid = null;
		String accessToken = null;
		Timestamp lastAccess = null;
		for (int i=0;i<cookie.length;i++) {
			if (cookie[i].getName().equals("accesstoken"))
				accessTokenInCookie = cookie[i].getValue();
			if (cookie[i].getName().equals("uid"))
				uid = cookie[i].getValue();
		}
		
		Logger.debug("accessTokenInCookie = " + accessTokenInCookie);
		Logger.debug("uid = " + uid);
		if (accessTokenInCookie == null || uid == null)
			return false;
		
		String getAccessToken = String.format(SQLStatements.S_GET_ACCESSTOKEN_BY_UID,uid);
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,getAccessToken);

			if (rs == null)
				return false;
			
			if (rs.next()) {
				accessToken = rs.getString("sess_access_token");
				lastAccess = rs.getTimestamp("sess_last_updated");
			}
			rs.close();
			Logger.debug(String.format("sql [%s] executed and get the result: %s",getAccessToken, accessToken));

		}
		catch (SQLException se) {
			Logger.warning(se.getMessage());
		}
		
		if (accessToken == null)
			return false;
		
		long currentTime = new Date().getTime();
		
		
		Date la = DateFormat.getDateTimeInstance().parse(lastAccess.toString());
		long laInSecond = la.getTime();
		
		Logger.debug("current time compare to last access" + (Long)(currentTime-laInSecond));
		
		if (accessToken.equals(accessTokenInCookie) && currentTime-laInSecond<Miscellaneous.SESSION_EXPIRED_TIME) {
			
			return true;
		}
		return false;
			
	}
	
	protected void allowCORS() throws IOException {
		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "origin,x-requested-with,Content-Type");
		httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	}
	
	protected void injectCookies(String name, String value) {
		httpServletResponse.addCookie(new Cookie(name,value));
	}
	
}
