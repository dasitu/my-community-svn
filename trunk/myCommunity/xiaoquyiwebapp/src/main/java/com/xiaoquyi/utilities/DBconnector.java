package com.xiaoquyi.utilities;

import java.io.IOException;
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBconnector {

	public static Connection getConnection() throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource dataSource =(DataSource) context.lookup("java:/comp/env/jdbc/__mysql");
		Connection conn = dataSource.getConnection();
		return conn;
	}

//	public static Object executeSqlStatement(String sqlStatement) throws NamingException, IOException {
//		Logger.debug(sqlStatement);
//		Object re = null;
//		try {
//			Connection conn = getConnection();
//
//
//			Statement st = conn.createStatement();
//			if (sqlStatement.startsWith("select")) {
//				re =  st.executeQuery(sqlStatement); //ResultSet type returned
//			}
//			else {
//				re = st.executeUpdate(sqlStatement);// int type returned
//			}
//			//			conn.close();
//			return re;
//		} 
//		catch(SQLException sqle) {
//			Logger.info(sqle.getMessage());
//			return -1;
//		}	
//
//
//
//	}

	public static ResultSet DBQuery(Connection conn,String sqlStatement) throws SQLException{

		if (conn == null)
			return null;
		try {
			Logger.debug(sqlStatement);
		}
		catch (IOException e) {
			//Do nothing
		}
		Statement st = conn.createStatement();
		return st.executeQuery(sqlStatement); 

	}


	public static int DBUpdate(Connection conn,String sqlStatement) throws SQLException{
		try {
			Logger.debug(sqlStatement);
		}
		catch (IOException e) {
			//Do nothing
		}
		Statement st = conn.createStatement();
		return st.executeUpdate(sqlStatement);

	}

}
