package com.xiaoquyi.utilities;

public class SQLStatements {
	public static final String S_LATSED_10_INFO = "select * from infor_table order by info_id desc limit 10;";
	
	
	
	
	
	// The column order is user_id,user_name,user_password,user_weibo,user_email,visialbe and timestamp.
	public static final String I_USER_REGISTERING = "INSERT INTO user_table VALUES (0,'%s','%s','%s','%s','%s',%s,'%s');";
	
}
