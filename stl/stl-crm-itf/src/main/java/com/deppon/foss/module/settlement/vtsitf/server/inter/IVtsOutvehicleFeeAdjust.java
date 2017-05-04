package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-05-27 19:10:20
 * VTS外请车费用调整同意之后调用结算重生成整车尾款应付单restful接口
 *
 */
public interface IVtsOutvehicleFeeAdjust {
	
	@POST
	@Path("/adjustOutvehicleFee")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String modifyTL2PayableAmount(String requestAdjustFee);
	
}
