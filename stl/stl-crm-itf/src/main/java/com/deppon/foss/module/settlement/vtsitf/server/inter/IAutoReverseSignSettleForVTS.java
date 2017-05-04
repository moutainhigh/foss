package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 08:34:20
 * vts自动反签收反结清restful接口
 *
 */
public interface IAutoReverseSignSettleForVTS {
	
	//反签收的校验
	@POST
	@Path("/checkReverseSignSettle")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String checkReverseSignSettle(String checkReverseSignSettle);
	
}
