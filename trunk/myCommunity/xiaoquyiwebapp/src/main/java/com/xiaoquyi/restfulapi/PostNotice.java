package com.xiaoquyi.restfulapi;

import java.io.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xiaoquyi.utilities.*;

@Path("/post_notice")
public class PostNotice extends AbstractAPI{
	
	@POST
	@Consumes("multipart/form-data")
	public String postNotice(@FormParam("title") String title,
			@FormParam("content") String content,
			@FormParam("publisher") String publisher,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fcdsFile) throws IOException {
		Logger.infoWritting(getSelfInfo());
		
		String fileLocation = Miscellaneous.IMAGE_REPOSITORY + fcdsFile.getFileName();
	    File destFile = new File(fileLocation);
	    Logger.infoWritting(fileLocation);
	    // your code here to copy file to destFile

		return "failed!";
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
		Logger.infoWritting(getHttpSession().getAttribute("username").toString());
		Logger.infoWritting(getSelfInfo());
			String uploadedFileLocation = Miscellaneous.IMAGE_REPOSITORY + fileDetail.getFileName();
			Logger.infoWritting(uploadedFileLocation);
			// save it
			writeToFile(uploadedInputStream, uploadedFileLocation);
	 
			String output = "File uploaded to : " + "localhost:8080/"+uploadedFileLocation;
	 
			return Response.status(200).entity(output).build();
	 
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
