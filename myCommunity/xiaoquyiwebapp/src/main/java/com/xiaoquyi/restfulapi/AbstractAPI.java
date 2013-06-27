package com.xiaoquyi.restfulapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.ws.rs.core.*;

import javax.servlet.*; 
import javax.servlet.http.*;

@SuppressWarnings("unused")
public class AbstractAPI {

	public final String LOG_FILE_NAME = "API_access.log";
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
	
	public void writtingLogs(String logBeWritten){
		
//		Enumeration<String>  enum1 = servletContext.getInitParameterNames();
		
		
		
		Date dt = new Date();
		String callerInfo = String.format("[%s]\n",dt.toString());
		StackTraceElement currentElement = (new Throwable()).getStackTrace()[1];

		callerInfo += logBeWritten + "\n";
		callerInfo += String.format("\tClassName:\t%s\n", currentElement.getClassName());
		callerInfo += String.format("\tMethodName:\t%s\n",  currentElement.getMethodName());
		callerInfo += String.format("\tFileName:\t%s\n",  currentElement.getFileName());
		callerInfo += String.format("\tLineNumber:\t%s",  currentElement.getLineNumber());
		
		File logFile = new File(LOG_FILE_NAME);
		try {
			if (!logFile.exists())
				logFile.createNewFile();
			FileWriter fw = new FileWriter(logFile,true);
			fw.write(callerInfo+"\n\n");
			fw.close();
		}catch(IOException ie) {
			System.out.println(ie.getMessage());
		}
		
		
		
	}
}
