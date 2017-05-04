package com.deppon.foss.module.settlement.consumer.api.server.service;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ISynHhInvoiceService {
	@POST
	@Path("/updateInvoiceState")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String updateHhInvoiceService(String invoiceState);
}
