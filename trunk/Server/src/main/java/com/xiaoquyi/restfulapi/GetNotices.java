package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Element;
import com.xiaoquyi.jsonelements.Notice;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;


@Path("/get_notices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notice> getLatest10Notices() throws IOException, NamingException, SQLException, ParseException {
		allowCORS(); 
		List<Notice> list = new LinkedList<Notice>();
		if (!accessTokenValidation()) {
			Logger.info("access token expired!");
//			list.add(new Status(10000,-1,"access token expired!",10000));
			return list;
		}
		Logger.info(getSelfInfo());
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_LATSED_10_INFO);

			while (rs.next()) {
				String content = rs.getString("info_text");
				String title = rs.getString("info_title");
				String poster = rs.getString("user_name");
				Timestamp publishTime = rs.getTimestamp("info_last_update");
				Logger.debug(content+ " " + title + " " + publishTime.toString());
				Notice item = new Notice(title,content,poster,publishTime.toString());
				String sqlGetImages = String.format(SQLStatements.S_INFO_IMAGES, rs.getInt("info_id"));
				ResultSet images = DBconnector.DBQuery(conn,sqlGetImages);
				while(images.next()) {
					Logger.debug(images.getString("imag_url"));
					item.addImage(images.getString("imag_url"));
				}
				images.close();
				list.add(item);			
			}
			rs.close();
			conn.close();
			return list;

		}
		catch (SQLException e) {
			Logger.error(e.getMessage());
			return null;
		}
		
	}
}
