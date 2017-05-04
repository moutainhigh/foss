package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *查询POS刷卡记录
 *
 * @author 309603--yangqiang
 * @date 2016-05-12 上午10:19:22
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IQueryPosCardForVTS {
	
	@POST
	@Path( "/query")
	public String queryPosCardForVTS(String entity);

}
