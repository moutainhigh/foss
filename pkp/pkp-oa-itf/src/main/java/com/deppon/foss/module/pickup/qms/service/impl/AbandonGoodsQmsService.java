package com.deppon.foss.module.pickup.qms.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.qms.service.IAbandonGoodsQmsService;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IAbandonGoodsApplicationQmsService;

/**
 * FOSS对接QMS，弃货服务
 * @author 231434-FOSS-bieyexiong
 * @date 2015-5-25 上午09:34:51
 */
public class AbandonGoodsQmsService implements IAbandonGoodsQmsService{
	
	private final static Logger LOGGER = 
			LoggerFactory.getLogger(AbandonGoodsQmsService.class);
	
	 /**
	  * FOSS对接QMS，弃货应用服务
	  */
	 private IAbandonGoodsApplicationQmsService abandonGoodsApplicationQmsService;
	 
	 @Context
	 HttpServletResponse response;
	 
	 /**
	  * 返回值（弃货签收失败：qms数据回滚，弃货工作流可重新点击审核）
	  */
	 private final static String FAILURE_BACK = "2";
	
	 /**
	  * 返回值（弃货签收失败：提示无法弃货签收）
	  */
	 private final static String FAILURE_REFUSED = "3";
	
	 /**
	  * QMS系统对接FOSS弃货签收
	  * @author 231434-FOSS-bieyexiong
 	  * @date 2015-5-18 上午09:26:51
	  */
	 @Override
	public String signAbandonGoods(String requestJson){
		response.setHeader("ESB-ResultCode","1");
		try{
			return abandonGoodsApplicationQmsService.signAbandonGoods(requestJson);
		}catch(BusinessException e){
			Map<String,Object> resMap = new HashMap<String,Object>();
			//设置返回信息
			resMap.put("result",FAILURE_REFUSED);
			resMap.put("message","签收失败：" + e.getErrorCode() != null ? e.getErrorCode() : e.getMessage());
			//将返回信息转成Json字符串
			String response = JSONObject.toJSONString(resMap);
			return response;
		}catch(Exception e){
			LOGGER.info("弃货异常信息:" + e.getMessage(),  e);
			Map<String,Object> resMap = new HashMap<String,Object>();
			//设置返回信息
			resMap.put("result",FAILURE_BACK);
			resMap.put("message","签收失败：系统异常，请重新操作！" + e.getMessage());
			//将返回信息转成Json字符串
			String response = JSONObject.toJSONString(resMap);
			return response;
		}
	}

	/**
	 * QMS对接FOSS，根据运单号，获取异常货运单信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-22 下午15:02:15
	 */
	 @Override
	public String queryUnnormalWaybill(String waybillNo){
		response.setHeader("ESB-ResultCode","1");
		return abandonGoodsApplicationQmsService.queryUnnormalWaybill(waybillNo);
	}
	
	public void setAbandonGoodsApplicationQmsService(
			IAbandonGoodsApplicationQmsService abandonGoodsApplicationQmsService) {
		this.abandonGoodsApplicationQmsService = abandonGoodsApplicationQmsService;
	}
	
}
