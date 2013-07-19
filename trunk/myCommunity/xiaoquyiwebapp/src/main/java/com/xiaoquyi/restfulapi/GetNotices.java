package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Notice;
import com.xiaoquyi.utilities.*;


@Path("/get_notices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notice> getLatest10Notices() throws IOException, NamingException, SQLException {
		allowCORS(); 
		Logger.info(getSelfInfo());
		
//		Object re = DBconnector.executeSqlStatement(SQLStatements.S_LATSED_10_INFO);
//		
//		
//		try {
//			if ((Integer)re == -1)
//				return null;
//		}
//		catch(ClassCastException cce) {
//			Logger.warning(cce.getMessage());
//		}
//		List<Notice> list = new LinkedList<Notice>();
//		ResultSet rs = (ResultSet)re;
//		Logger.warning(rs.toString());
//		while (rs.next()) {
//			String content = rs.getString("info_text");
//			Logger.warning(content);
//			String title = rs.getString("info_title");
//			Timestamp publishTime = rs.getTimestamp("info_last_update");
//			Logger.debug(content+ " " + title + " " + publishTime.toString());
//			Notice item = new Notice(title,content,publishTime.toString(),"no images");
//			list.add(item);			
//		}
//		rs.close();
//		return list;
		
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_LATSED_10_INFO);

			if (rs == null)
				return null;

			List<Notice> list = new LinkedList<Notice>();
			while (rs.next()) {
				String content = rs.getString("info_text");
				Logger.warning(content);
				String title = rs.getString("info_title");
				Timestamp publishTime = rs.getTimestamp("info_last_update");
				Logger.debug(content+ " " + title + " " + publishTime.toString());
				Notice item = new Notice(title,content,publishTime.toString(),"no images");
				list.add(item);			
			}
			rs.close();
			conn.close();
			return list;

		}
		catch (SQLException e) {
			return null;
		}
		
	}
}
