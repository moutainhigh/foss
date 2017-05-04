package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ITRFWKLoadService {
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/sysnLoad")
	// http://localhost:8080/foss/services/wkLoad/sysnLoad
	public Object sysnLoad(String reqMsg);
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/sysnUpdateLoad")
	// http://localhost:8080/foss/services/wkLoad/sysnUpdateLoad
	public Object sysnUpdateLoad(String reqMsg);
	
	
	

}
