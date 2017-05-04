package com.deppon.foss.module.pickup.qms.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.qms.service.ICubcQueryAmountService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.request.CubcQueryTotalAmountRequest;

/**
 * CUBC查询(预付+到付-代收货款)总额
 * CubcQueryAmountService
 * @author 198771-zhangwei
 * 2017-1-6 下午3:35:57
 */
public class CubcQueryAmountService implements ICubcQueryAmountService{
	 
	 private final static Logger LOGGER = 
			LoggerFactory.getLogger(CubcQueryAmountService.class);
	 
	 @Context
	 HttpServletResponse response;
	 
	 private IWaybillService waybillService;
	
	 /**
	 * @param waybillService the waybillService to set
	 */
	 public void setWaybillService(IWaybillService waybillService) {
		 this.waybillService = waybillService;
	 }



	/**
	  * 
	  * CUBC查询(预付+到付-代收货款)总额
	  * @author 198771-zhangwei
	  * 2017-1-6 下午2:33:02
	  */
	 @Override
	 public String queryTotalAmount(String requestJson){
		response.setHeader("ESB-ResultCode","1");
		Map<String,Object> resMap = new HashMap<String,Object>();
		try{
			CubcQueryTotalAmountRequest requestParam = JSON.parseObject(requestJson, CubcQueryTotalAmountRequest.class);
			String totalAmount =  waybillService.queryTotalAmount(requestParam);
			resMap.put("result", true);
			resMap.put("totalAmount", totalAmount);
		}catch(BusinessException e){
			LOGGER.info("CUBC查询(预付+到付-代收货款)总金额异常信息:" + e.getMessage(),  e);
			//设置返回信息
			resMap.put("result",false);
			resMap.put("totalAmount",null);
		}catch(Exception e){
			LOGGER.info("CUBC查询(预付+到付-代收货款)总金额异常信息:" + e.getMessage(),  e);
			resMap.put("result",false);
			resMap.put("totalAmount",null);
		}
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	 }
}
