package com.xiaoquyi.restfulapi;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
//import java.util.logging.FileHandler;





import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


import com.sun.jersey.multipart.*;
import com.xiaoquyi.utilities.*;

@Path("/post_notice")
public class PostNotice extends AbstractAPI{
	
	
	@OPTIONS
	public Response optionsResponse() throws IOException {
		
		
		allowCORS();
		
		return Response.status(200).entity("asafdsa").build();

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("title") String title,
			@FormDataParam("content") String content,
			@FormDataParam("uid") String uid, //TODO:get this value from cookie
			@FormDataParam("communityID") String communityID,
			@FormDataParam("visiable") String visiable,
			@FormDataParam("file") List<FormDataBodyPart> dataBodies) throws IOException, SQLException, NamingException {
		allowCORS();
		// The column order is info_id,user_id,comminity_id,info_text,info_visible,info_last_update and info_title.
		
		String newNotice = String.format(SQLStatements.I_POST_NOTICE, uid, communityID,content,visiable,title);
		
		Connection conn = DBconnector.getConnection();
		int noticeId = DBconnector.DBUpdate(conn, newNotice);
		
		
		Logger.info(newNotice);
		
		
		String addImage = null;
		if (dataBodies == null)
			return Response.status(200).entity("dfsfds").build();
		for(FormDataBodyPart dataBody:dataBodies) {

			String fileName = dataBody.getContentDisposition().getFileName();
			String baseName = fileName.substring(0, fileName.lastIndexOf(".")-1);
			String extend = fileName.substring(fileName.lastIndexOf("."));
			String uniqueName =  baseName 
					+ "_" 
					+ Long.toString(Miscellaneous.getCurrentTimeInSeconds())  
					+ extend;
			String uploadedFileLocation = Miscellaneous.IMAGE_REPOSITORY + uniqueName;
			InputStream is = dataBody.getValueAs(InputStream.class);

			
			// save it
			writeToFile(is, uploadedFileLocation);
			String url = "http://localhost:8080/"+Miscellaneous.IMAGE_FOLDER+uniqueName;
			Logger.info("file URL is = " + url);
			
			addImage = String.format(SQLStatements.I_NOTICE_IMAGE, noticeId, url);
			DBconnector.DBUpdate(conn, addImage);
			Logger.info(addImage);
			
		}
		conn.close();
		return Response.status(200).entity("dfsfds").build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			uploadedInputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
