package com.xiaoquyi.utilities;

import java.sql.*;
import java.util.Date;

public class Miscellaneous {
	
	public static final String ROOT_PATH = "tomcat/webapps/";
	public static final String IMAGE_FOLDER = "xqyimages/";
	public static final String IMAGE_REPOSITORY = ROOT_PATH + IMAGE_FOLDER;
	public final static String LOG_FILE_NAME = "./tomcat/logs/API_access.log";
	
	// this SESSION_EXPIRED_TIME is in millisecond, 2592000000 means 30 days
	public final static long SESSION_EXPIRED_TIME = 2592000000L;
	
	
	public static String getCurrentTimestamp() {
		return new Timestamp(new Date().getTime()).toString();
	}
	
	public static long getCurrentTimeInSeconds() {
		return new Date().getTime();
	}

}
