package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;

import com.xiaoquyi.jsonelements.Notice;
import com.xiaoquyi.utilities.*;


@Path("/getnotices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces("application/json")
	public List<Notice> getLatest10Notices() throws IOException, NamingException, SQLException {
		Logger.infoWritting(getSelfInfo());
		
		String sqlStatement = SQLStatements.S_LATSED_10_INFO;

		Object re = DBconnector.executeSqlStatement(sqlStatement);
		
		
		try {
			if ((Integer)re == -1)
				return null;
		}
		catch(ClassCastException cce) {
			Logger.warningWritting(cce.getMessage());
		}
		List<Notice> list = new LinkedList<Notice>();
		ResultSet rs = (ResultSet)re;
		while (rs.next()) {
			String content = rs.getString("info_text");
			String title = rs.getString("info_title");
			Timestamp publishTime = rs.getTimestamp("info_last_update");
			Logger.debugWritting(content+ " " + title + " " + publishTime.toString());
			Notice item = new Notice(title,content,publishTime.toString(),"no images");
			list.add(item);			
		}
		rs.close();
		return list;
		
	}
}
