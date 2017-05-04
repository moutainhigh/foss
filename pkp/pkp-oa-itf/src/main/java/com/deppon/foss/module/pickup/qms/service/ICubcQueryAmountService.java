package com.deppon.foss.module.pickup.qms.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface ICubcQueryAmountService {
	
	/**
	 * CUBC查询(预付+到付-代收货款)总额
	 * String
	 * @author 198771-zhangwei
	 * 2017-1-6 下午2:25:44
	 */
	@POST
	@Path("/queryTotalAmount")
	public @ResponseBody String queryTotalAmount(@RequestBody String requestJson);

}
