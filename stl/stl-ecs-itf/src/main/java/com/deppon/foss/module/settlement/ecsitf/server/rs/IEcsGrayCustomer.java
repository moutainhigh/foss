package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 月结客户（灰名单）校验接口
 * @author foss-231434-bieyexiong
 * @date 2016-12-15 15:07
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsGrayCustomer {
	
	@POST
	@Path("/check")
	String checkGrayCustomer(String jsonReq);

}
