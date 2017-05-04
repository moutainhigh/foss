package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/** 
 * 整车结清货款itf接口
 * @author foss结算-310970 caoepng
 * @date 2016-5-18 
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IVtsEffectTl2Service {
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/VtsEffectTl2")
	public String EffectTl2ByVtsToFossTailDto(String vtsToFossTailDto);
	
	
}
