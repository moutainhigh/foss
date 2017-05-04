package com.deppon.foss.module.settlement.costcontrolitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
public interface PaymentTransferService {

	/**
	 * 更新付款转报销工作流
     * @author 邓大伟
     * @date 2015-08-17
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("/updatePaymentTransfer")
	String updatePaymentTransfer(String paymentTransfer);
}
