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
public interface IAbandonGoodsQmsService {
	
	/**
	  * QMS系统对接FOSS弃货签收
	  * @author 231434-FOSS-bieyexiong
	  * @date 2015-5-18 上午09:26:51
	  */
	@POST
	@Path("/signAbandonGoods")
	public @ResponseBody String signAbandonGoods(@RequestBody String requestJson);
	
	/**
	 * QMS对接FOSS，根据运单号，获取异常货运单信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-22 下午15:02:15
	 */
	@POST
	@Path("/queryUnnormalWaybill")
	public @ResponseBody String queryUnnormalWaybill(@RequestBody String waybillNo);

}
