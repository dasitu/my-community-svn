package com.xiaoquyi.utilities;

public class SQLStatements {
	public static final String S_LATSED_10_INFO = "select * from infor_table order by info_id desc limit 10;";
	
	public static final String S_GET_PASSWD_BY_NAME = "select user_pass from user_table where user_name='%s';";
	public static final String S_GET_USER_ID_BY_NAME = "select user_id from user_table where user_name='%s';";
	
	public static final String S_GET_USERS = "select * from user_table;";
	
	public static final String S_GET_COMMUNITIES = "select * from community_view;";
	
	
	// The column order is user_id,user_name,user_password,user_weibo,user_email,visialbe and timestamp.
	public static final String I_USER_REGISTERING = "INSERT INTO user_table VALUES (0,'%s','%s','%s','%s','%s',%s,CURRENT_TIMESTAMP);";
	
	// The column order is info_id,user_id,comminity_id,info_text,info_visible, info_title, info_last_update.
	public static final String I_POST_NOTICE = "INSERT INTO infor_table VALUES (0, %s, %s, '%s',%s,'%s',CURRENT_TIMESTAMP);";
	
	// The column order is imag_id,info_id,imag_url and timestamp.
	public static final String I_NOTICE_IMAGE = "INSERT INTO image_table VALUES (0, %s,'%s',CURRENT_TIMESTAMP);";
	
	public static final String I_SESSION_RECORD = "INSERT INTO session_table VALUES (0, %s, '%s','%s');";
	
}
