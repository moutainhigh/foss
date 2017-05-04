package com.deppon.foss.module.settlement.cubcitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * cubc同步财务单据完结信息到foss
 * 
 * @author 269044-zhurongrong
 * @date 2017-04-21
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces("application/json;charset=UTF-8")
public interface ISyncPodEntityService {
	
	@POST
	@Path("/syncPodEntity")
	public String syncPodEntity(String jsonStr);

}
