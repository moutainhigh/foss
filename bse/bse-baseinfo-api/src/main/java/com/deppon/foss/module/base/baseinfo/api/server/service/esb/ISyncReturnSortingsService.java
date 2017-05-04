package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ISyncReturnSortingsService {

	
	@POST
	@Path("/querySortingsByDeptCode")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String querySortings(String deptCode);
	
	@POST
	@Path("/queryDataDictionarys")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String queryDataDictionarys();
}
