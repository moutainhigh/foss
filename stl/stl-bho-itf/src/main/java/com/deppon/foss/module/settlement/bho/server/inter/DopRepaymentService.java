package com.deppon.foss.module.settlement.bho.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
public interface DopRepaymentService {
	
	/**
	 * 根据传入的一或多个运单单号，获取一或多条应收单
     * @author 邓大伟
     * @date 2014-12-12
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("/queryReceivableBill")
	String queryReceivableBill(String receivableBill);
	
	/**
	 * 根据传入的一或多个运单单号，核销一或多条应收单
     * @author 邓大伟
     * @date 2014-12-12
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("/repaymentReceivableBill")
	String repaymentReceivableBill(String receivableBill);
}
