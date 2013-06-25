package com.xiaoquyi.restfulapi;

import javax.ws.rs.Path;
import javax.ws.rs.GET;


@Path("/test")
public class Test implements AbstractAPI {

	@GET
	public String printHello() {
		return "hello,world!";
	}
}
