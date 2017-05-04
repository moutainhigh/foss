package com.deppon.foss.module.settlement.positf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * 
 * @author 378375
 * @date 2017年1月20日 17:28:20
 * 把CUBC的pos机刷卡记录保存到foss的付款记录表里面
 */
public interface IPosSettleForCUBC {
	
	@POST
	@Path("/posSettle")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	Response insertPosSettleRecord(String repaymentEntity);

}
