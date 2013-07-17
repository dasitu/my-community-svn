package com.xiaoquyi.restfulapi;

import java.io.*;
import java.util.List;
//import java.util.logging.FileHandler;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.xiaoquyi.utilities.*;

@Path("/post_notice")
public class PostNotice extends AbstractAPI{

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("title") String title,
			@FormDataParam("content") String content,
			@FormDataParam("file") List<FormDataBodyPart> dataBodies) throws IOException {

		for(FormDataBodyPart dataBody:dataBodies) {

			String fileName = dataBody.getContentDisposition().getFileName();
			Logger.info(fileName);
			String baseName = fileName.substring(0, fileName.lastIndexOf(".")-1);
			String extend = fileName.substring(fileName.lastIndexOf("."));
			String gid =  baseName 
					+ "_" 
					+ Long.toString(Miscellaneous.getCurrentTimeInSeconds())  
					+ extend;
			String uploadedFileLocation = Miscellaneous.IMAGE_REPOSITORY + gid;
			InputStream is = dataBody.getValueAs(InputStream.class);

			Logger.info(is.toString());
			// save it
			writeToFile(is, uploadedFileLocation);
			Logger.info("file URL is = " + "http://localhost:8080/"+Miscellaneous.IMAGE_FOLDER+gid);
			
		}
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
