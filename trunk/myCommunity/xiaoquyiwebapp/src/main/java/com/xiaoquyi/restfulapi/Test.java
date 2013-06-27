package com.xiaoquyi.restfulapi;


import javax.ws.rs.Path;
import javax.ws.rs.GET;

import com.sun.jersey.spi.resource.Singleton;



@Path("/test")
@Singleton
public class Test extends AbstractAPI {

	@GET
	public String printHello() {
		writtingLogs(ui.getRequestUri().toString());
		return "hello,world!";
	}
}
