package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Notices;
import com.xiaoquyi.utilities.*;


@Path("/get_notices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Notices getLatest10Notices() throws IOException, NamingException, SQLException, ParseException {
		allowCORS(); 
		Notices data = new Notices();
		if (!accessTokenValidation()) {
			Logger.info("access token expired!");
			data.setStatus(10000, -1, "access token expired!", 10000);
			return data;
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
				Notices.Notice item = new Notices.Notice(title,content,poster,publishTime.toString());
				String sqlGetImages = String.format(SQLStatements.S_INFO_IMAGES, rs.getInt("info_id"));
				ResultSet images = DBconnector.DBQuery(conn,sqlGetImages);
				while(images.next()) {
					Logger.debug(images.getString("imag_url"));
					item.addImage(images.getString("imag_url"));
				}
				images.close();
				data.addNotice(item);		
			}
			rs.close();
			conn.close();
			return data;

		}
		catch (SQLException e) {
			Logger.error(e.getMessage());
			data.setStatus(10000, -1, e.getMessage(), 10000);
			return null;
		}
		
	}
}
