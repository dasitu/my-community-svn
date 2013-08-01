package com.xiaoquyi.restfulapi;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.xiaoquyi.jsonelements.Elements;
import com.xiaoquyi.jsonelements.Status;
import com.xiaoquyi.utilities.*;


@Path("/get_notices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**@brief This interface accept the http GET request to get notices based on the query confition. API address: The \b /get_notices 
	 * 
	 *@Param pageflag This parameter indicate the the request page direction, 0 for first page, 1 for latest pages and 2 for previous pages
	 *@Param pagetime This parameter specify the the begin time of the requesting page;  set it to the time of first item of last request when pageflag = 1
	 *or set to the last item time of last requested items when  pageflag = 2, ingore it when pageflag = 0
	 *@Param pageItems The amount of the requesting items of a page, default to 10
	 *@return One list of \ref jsonelements.Notice have specified by \ref pageItems amount notices which in the form of json list
	 */
	
	public Elements getNotices(
			@QueryParam("pageflag") String pageflag,
			@QueryParam("pagetime") String pagetime,
			@DefaultValue("10") @QueryParam("pageItems") String pageItems) throws IOException, NamingException, SQLException, ParseException {
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
			String sqlClause = null;
			String timeScope = null;
			if (pageflag.equals("0")) {
				timeScope = "< CURRENT_TIMESTAMP";
			} else if (pageflag.equals("1")) {
				timeScope = " > " + "'" + pagetime + "'" ;
			} else if (pageflag.equals("2")) {
				timeScope = " < " +  "'" + pagetime + "'" ;
			}
			
			sqlClause = String.format(SQLStatements.S_GET_INFO, timeScope, pageItems);
			ResultSet rs = DBconnector.DBQuery(conn,sqlClause);

			notices.setElements(LoadElements.loadElements(conn, rs, new LoadElements.LoadNotice()));
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
