package com.deppon.foss.module.settlement.bho.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by youkun on 2016/5/6.
 * 支付宝网关支付
 */
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
public interface GatewayPaymentService {

    @POST
    @Path("/addGatewayPayment")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    String addGatewayPayment(String receivableBill);


}
