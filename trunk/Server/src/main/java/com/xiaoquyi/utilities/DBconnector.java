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


	public static ResultSet DBQuery(Connection conn,String sqlStatement) throws SQLException, IOException{
		if (conn == null) {
			return null;
		}
		try {
			Logger.debug(sqlStatement);
		}
		catch (IOException e) {
			//Do nothing
			Logger.warning(e.getMessage());
		}
		Statement st = conn.createStatement();
		return st.executeQuery(sqlStatement); 

	}


	public static int DBUpdate(Connection conn,String sqlStatement) throws SQLException, IOException{
		Logger.debug(sqlStatement);
		if (conn == null)
			return -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try{
            ps = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);  
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()){
                id = rs.getInt(1);
            }
            return id;
        }catch(Exception e){
           Logger.error(e.getMessage());
        }
        return -1;

	}

}
