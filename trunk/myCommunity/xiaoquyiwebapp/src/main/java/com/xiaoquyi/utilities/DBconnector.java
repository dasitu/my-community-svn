package com.xiaoquyi.utilities;

import java.io.IOException;
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBconnector {

	private static Connection getConnection() throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource dataSource =(DataSource) context.lookup("jdbc/__mysql");
		Connection conn = dataSource.getConnection();
		return conn;
	}
	
	public static Object executeSqlStatement(String sqlStatement) throws NamingException, IOException {
		
		try {
			Connection conn = getConnection();
			
			
			Statement st = conn.createStatement();
			if (sqlStatement.startsWith("select"))
				return st.executeQuery(sqlStatement);
			else
				return st.executeUpdate(sqlStatement);
		} 
		catch(SQLException sqle) {
			Logger.infoWritting(sqle.getMessage());
			return -1;
		}	
		
		
	}
}
