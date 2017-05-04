package com.deppon.foss.module.settlement.pay.api.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**
 * 
 * @author 218392 张永雪
 * @date 2015-12-08 09:14:10
 * 盘点以及未收银确认时在盘点时提示 
 */
public interface ISynCashCheckConfirm {
	@POST
	@Path("/queryCashCheckConfirm")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String queryCashCheckConfirm(String cashCheckConfirmRequest);
}
