package com.xiaoquyi.utilities;

import java.sql.Timestamp;

public class Miscellaneous {
	
	public static String getCurrentTimestamp() {
		return new Timestamp((new java.util.Date()).getTime()).toString();
	}

}
