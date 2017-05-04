package com.deppon.foss.module.settlement.bho.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 * @caopeng310970 
 */

public interface ISynGreenHandWrapList {
	@POST
	@Path("/addFinanceBill")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String addGreenHandWrapList(String greenHandWrapList);

}
