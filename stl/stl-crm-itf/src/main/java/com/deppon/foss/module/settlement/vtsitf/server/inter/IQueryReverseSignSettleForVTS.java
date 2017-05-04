package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author 218392 张永雪
 * @date 2016-06-17 06:27:00
 * VTS查询反结清
 */
public interface IQueryReverseSignSettleForVTS {

	//查询反结清：查询到达付款信息
	@POST
	@Path("/queryReverseSettle")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	String queryReverseSettle(String checkReverseSignSettle);
	
}
