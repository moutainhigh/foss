package com.deppon.foss.module.pickup.oms.server.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.crm.AsynOmsGoodsBillReceiveRequest;
import com.deppon.esb.inteface.domain.crm.AsynOmsGoodsBillReceiveResponse;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
/**
 * oms调foss异步改造同步
 * @author 327090
 *
 */
@Controller
@RequestMapping("/omsOrderFossService")
public class OmsOrderFossServiceImpl {

	private static final Logger logger = LogManager.getLogger(OmsOrderFossServiceImpl.class);
	
	private static final String SERVERCODE = "FOSS_ESB2FOSS_ABOUT_CAR";
	
	/**
	 * oms订单处理service
	 */
	@Resource
	private IOrderTaskHandleService OrderTaskHandleService;	
	
	/**
	 * 
	 * 327090-2016-09-20
	 * @param str
	 * @param res
	 * @return bl
	 */
	@RequestMapping(value = "/omsOrderFoss", method = RequestMethod.POST)	
	public @ResponseBody AsynOmsGoodsBillReceiveResponse omsOrderFoss(@RequestBody String str,HttpServletResponse res){
		logger.info("--------------oms同步调用foss开始----------------");
		res.setHeader("ESB-ResultCode" , "1");
		res.setCharacterEncoding("utf-8");
		AsynOmsGoodsBillReceiveResponse response = new AsynOmsGoodsBillReceiveResponse();
		AsynOmsGoodsBillReceiveRequest asynOmsGoodsBillReceiveRequest = null;
		try{
			asynOmsGoodsBillReceiveRequest = JSONObject.parseObject(str, AsynOmsGoodsBillReceiveRequest.class);
		} catch(Exception t){
			logger.info("异常："+t.getMessage());
			response.setServerCode(SERVERCODE);
			response.setResult(false);
			response.setReason("传过来的参数不匹配！");
			return response;
		}
		
		try{			
			if(asynOmsGoodsBillReceiveRequest==null||asynOmsGoodsBillReceiveRequest.getOrderNo()==null){
				response.setServerCode(SERVERCODE);
				response.setResult(false);
				response.setReason("约车订单或单号为空！");
			} else{
				OrderTaskHandleService.addOmsPickupOrder(asynOmsGoodsBillReceiveRequest);
				// 成功标志
				response.setServerCode(SERVERCODE);
				response.setOrderNumber(asynOmsGoodsBillReceiveRequest.getOrderNo());
				response.setResult(true);
			}
			
		} catch(Exception e){
			logger.info("异常："+e.getMessage());	
			response.setServerCode(SERVERCODE);
			response.setOrderNumber(asynOmsGoodsBillReceiveRequest.getOrderNo());
			response.setResult(false);
			response.setReason(e.getMessage());
		}
		logger.info("--------------oms同步调用foss结束----------------");
		return response;
	}
}
