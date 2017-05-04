package com.deppon.foss.module.settlement.closing.api.server.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**
 * @author 218392 张永雪
 * @date 2015-09-06 08:47:50
 * 代收货款清单列表查询接口(供CRM系统开发组调用)
 */
public interface ISynCollectingPaymentList {
	@POST
	@Path("/queryCollectingPayment")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String queryCollectingPaymentList(String collectingPaymentList);
}
