package com.xiaoquyi.restfulapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.ws.rs.core.*;

import javax.servlet.*; 
import javax.servlet.http.*;

import com.xiaoquyi.utilities.Logger;

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
	
	public void allowCORS() throws IOException {
		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "origin,x-requested-with,Content-Type");
		httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	}
	
}
