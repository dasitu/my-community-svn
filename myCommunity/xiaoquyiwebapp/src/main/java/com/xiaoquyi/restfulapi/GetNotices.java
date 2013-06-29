package com.xiaoquyi.restfulapi;

import java.io.IOException;

import javax.ws.rs.*;

import com.xiaoquyi.jsonelements.Notice;
import com.xiaoquyi.utilities.*;


@Path("/getnotices")
public class GetNotices extends AbstractAPI{

	@GET
	@Produces("application/json")
	public Notice getLatest10Notices() throws IOException {
		Logger.infoWritting(getSelfInfo());
		Notice notice = new Notice();
		notice.content = "this is a phony notice for testing!";
		notice.title = "test";
		notice.images = "http://www.163.com/a.jpg";
		
		return notice;
		
	}
}
