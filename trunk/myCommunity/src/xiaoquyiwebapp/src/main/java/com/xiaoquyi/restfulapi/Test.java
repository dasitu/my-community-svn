package com.xiaoquyi.restfulapi;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;

@Path("/test")
public class Test implements AbstractAPI {

	@GET
	public String printHello() {
		return "hello,world!";
	}
}
