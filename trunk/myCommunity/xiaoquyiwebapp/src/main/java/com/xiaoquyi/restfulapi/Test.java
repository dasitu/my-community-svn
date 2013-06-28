package com.xiaoquyi.restfulapi;


import java.io.IOException;

import javax.ws.rs.*;

import com.sun.jersey.spi.resource.Singleton;
import com.xiaoquyi.utilities.*;



@Path("/test")
@Singleton
public class Test extends AbstractAPI {

	@GET
	public String printHello() throws IOException {
		Logger loger = new Logger();
		Logger.infoWritting(getSelfInfo());
		return "hello,world!";
	}
}
