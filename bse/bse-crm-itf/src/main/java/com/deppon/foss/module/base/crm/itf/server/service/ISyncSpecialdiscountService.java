package com.deppon.foss.module.base.crm.itf.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ISyncSpecialdiscountService {
	@POST
	@Path("/addSpecialdiscount")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String addSpecialdiscount(String specialdiscount);
}
