package com.deppon.foss.service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillGXGService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillGXGEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResponseGXG;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * rest风格接口(供Dop GXG客户调用)
 * 
 * @author dop-266396
 */
// 请求数据格式为json
@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
// 响应数据格式为json
@Produces("application/json;charset=UTF-8")
public class RestDopGXGOrderService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RestDopGXGOrderService.class);
	private IWaybillGXGService waybillGXGService;

	@Context
	private HttpServletResponse response;

	@POST
	@Path("/gxgOrder")
	public String gxgOrder(String gxgOrderInfo) {
		LOGGER.info("GXG订单信息：" + gxgOrderInfo);
		// ESB必须字段
		ResponseGXG responseDto = new ResponseGXG();
		String responseStr = "";
		WaybillGXGEntity waybillGXGEntity = new WaybillGXGEntity();
		response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		try{
			waybillGXGEntity = JSON.parseObject(gxgOrderInfo,
					WaybillGXGEntity.class);
			responseDto = waybillGXGService
					.addWaybillExpressEntity(waybillGXGEntity);
			responseStr = JSON.toJSONString(responseDto);
			LOGGER.info("GXG订单信息入库成功.");
		}catch(JSONException e){
			responseDto.setResult("false");
			responseDto.setResultCode("3001");
			responseDto.setDesc("参数转化失败");
			responseStr = JSON.toJSONString(responseDto);
		}catch(Exception e){
			responseDto.setResult("false");
			responseDto.setResultCode("3001");
			responseDto.setDesc("信息入库失败");
			responseStr = JSON.toJSONString(responseDto);
		}
		return responseStr;
	}

	public IWaybillGXGService getWaybillGXGService() {
		return waybillGXGService;
	}

	public void setWaybillGXGService(IWaybillGXGService waybillGXGService) {
		this.waybillGXGService = waybillGXGService;
	}

}
