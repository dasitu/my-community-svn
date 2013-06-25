package com.xiaoquyi.utilities;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBconnector {

	public static Connection getConnection() {
		InitialContext context = new InitialContext();
		DataSource dataSource =(DataSource) context.lookup("jdbc/__mysql");
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
}
