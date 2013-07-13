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
		Logger.infoWritting(getSelfInfo());
		
		Object re = DBconnector.executeSqlStatement(SQLStatements.S_LATSED_10_INFO);
		
		
		try {
			if ((Integer)re == -1)
				return null;
		}
		catch(ClassCastException cce) {
			Logger.warningWritting(cce.getMessage());
		}
		List<Notice> list = new LinkedList<Notice>();
		ResultSet rs = (ResultSet)re;
		Logger.warningWritting(rs.toString());
		while (rs.next()) {
			String content = rs.getString("info_text");
			Logger.warningWritting(content);
			String title = rs.getString("info_title");
			Timestamp publishTime = rs.getTimestamp("info_last_update");
			Logger.debugWritting(content+ " " + title + " " + publishTime.toString());
			Notice item = new Notice(title,content,publishTime.toString(),"no images");
			//TODO: add image URL to DB
			list.add(item);			
		}
		rs.close();
		return list;
		
	}
}
