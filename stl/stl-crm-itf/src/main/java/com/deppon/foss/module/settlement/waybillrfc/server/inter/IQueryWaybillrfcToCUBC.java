package com.deppon.foss.module.settlement.waybillrfc.server.inter;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * 
 * @author 378375
 * @date 2017年3月6日 23:09:39
 * 查询foss更改单数据传给CUBC
 *
 */
public interface IQueryWaybillrfcToCUBC {
	
	@POST
	@Path("/searchWaybillrfc")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	Response selectWaybillrfc(String param);

}
