package com.xiaoquyi.utilities;

import java.sql.*;
import java.util.Date;

public class Miscellaneous {
	
	public static final String IMAGE_REPOSITORY = "../docroot/resources/images/";
	public final static String LOG_FILE_NAME = "../logs/API_access.log";
	
	public static String getCurrentTimestamp() {
		return new Timestamp((new Date()).getTime()).toString();
	}

}
