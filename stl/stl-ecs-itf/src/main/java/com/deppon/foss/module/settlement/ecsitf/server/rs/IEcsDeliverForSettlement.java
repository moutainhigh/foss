package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 快递派送业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-06-06 上午8:43:01
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsDeliverForSettlement {
	
	/**
	 * 获取网上支付未完成的运单
	 * @param jsonStr
	 */
	@POST
	@Path("/queryUnpaidOnlinePay")
	public String queryUnpaidOnlinePay(String jsonStr);

}
