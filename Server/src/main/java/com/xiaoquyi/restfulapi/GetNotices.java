package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Elements;
import com.xiaoquyi.jsonelements.Notice;
import com.xiaoquyi.jsonelements.Notices;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;


@Path("/get_notices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**@brief This interface accept the http GET request to get latest 10 notices. API address: The \b /get_notices 
	 * 
	 *@return One instance of \ref jsonelements.Notices have 10 latest notices which in the form of json list
	 */
	
	public Elements getLatest10Notices() throws IOException, NamingException, SQLException, ParseException {
		allowCORS(); 
		Elements notices = new Elements();
		if (!accessTokenValidation()) {
			Logger.info("Access token error or expired!");
			notices.setStatus(new Status(10000, -1, "Access token error or expired!", 10000));
			return notices;
		}
		Logger.info(getSelfInfo());
		
		try {
			Connection conn = DBconnector.getConnection();
			ResultSet rs = DBconnector.DBQuery(conn,SQLStatements.S_LATSED_10_INFO);

			while (rs.next()) {
				String content = rs.getString("info_text");
				String title = rs.getString("info_title");
				String poster = rs.getString("user_name"); // the poster
				Timestamp publishTime = rs.getTimestamp("info_last_update");
				Logger.debug(content+ " " + title + " " + publishTime.toString());
				Notice item = new Notice(title,content,poster,publishTime.toString());
				String sqlGetImages = String.format(SQLStatements.S_INFO_IMAGES, rs.getInt("info_id"));
				ResultSet images = DBconnector.DBQuery(conn,sqlGetImages);
				while(images.next()) {
					Logger.debug("xxxxxxxxxxxxxxxxxxxxxxxx");
					Logger.debug(images.getString("imag_url"));
					item.addImage(images.getString("imag_url"));
				}
				images.close();
				notices.addElement(item);	
			}
			rs.close();
			conn.close();
			notices.setStatus(new Status());
			return notices;

		}
		catch (SQLException e) {
			Logger.error(e.getMessage());
			notices.setStatus(new Status(10000, -1, e.getMessage(), 10000));
			return notices;
		}
		
	}
}
