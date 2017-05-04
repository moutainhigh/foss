package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 运单信用额度接口
 * @author 268217
 * @date 2016-03-22 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface IcanDebitIsBeBebt {
	
	@POST
	@Path("/canDebitIsBeBebt")
	public String canDebitIsBeBebt(String jsonStr);
}
