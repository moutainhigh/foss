package com.deppon.foss.module.pickup.tps.server.rs;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 同步TPS反签收信息
 * @author 243921 zhangtingting
 * @date 2017-03-02 13:48:55
 * 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IReverseSignSettleForTPS {
	

	/**
	 * 同步运单反签收、反结清数据
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/reverseSignSettle")
	public @ResponseBody String reverseSignSettle(@RequestBody String jsonStr);
	
}
