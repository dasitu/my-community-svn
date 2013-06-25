package com.xiaoquyi.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBconnector {

	public static Connection getConnection() throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource dataSource =(DataSource) context.lookup("jdbc/__mysql");
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
}
