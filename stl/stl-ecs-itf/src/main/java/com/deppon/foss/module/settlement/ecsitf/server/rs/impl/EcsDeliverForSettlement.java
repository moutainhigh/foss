package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.shared.dto.*;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsDeliverForSettlement;
/**
 * 快递派送业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-06-06 上午08:48:01
 */
public class EcsDeliverForSettlement implements IEcsDeliverForSettlement {
	
	//日志
	private final Logger logger = LogManager.getLogger(EcsDeliverForSettlement.class);
	
	//结算 收入确认、反确认 服务接口
	private ITakingService takingService;

	@Context
	HttpServletResponse res;

	/**
	 * 获取网上支付完成的运单
	 * @param jsonStr
	 */
	@SuppressWarnings("unchecked")
	@Override
	public @ResponseBody String queryUnpaidOnlinePay(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//获取的网上未支付完成的运单
		EcsResponseDto resDto = new EcsResponseDto();
		//返回的集合，推送给快递
		List<String> list = new ArrayList<String>();
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("获取网上支付未完成的运单失败：请求参数为空！");
			
			resDto.setResult(SettlementConstants.RETURN_FAILURE);
			resDto.setMessage("获取网上支付未完成的运单失败：请求参数为空！");
			resDto.setWaybillNos(list);
			response = JSONObject.toJSONString(resDto);
			
			return response;
		}
		try{
			List<String> waybillNOs = JSONArray.fromObject(jsonStr);

			//开单网上支付，但是尚未支付的单据
			List<String> notPayWaybillNo=takingService.unpaidOnlinePay(waybillNOs);

			// 记录日志
			logger.info("获取网上支付未完成的运单成功！");

			resDto.setResult(SettlementConstants.RETURN_SUCESS);
			resDto.setMessage("获取网上支付未完成的运单成功！");
			resDto.setWaybillNos(notPayWaybillNo);
			response = JSONObject.toJSONString(resDto);

			logger.info(response);
			return response;
		}catch(BusinessException e){
			return this.getResponse(SettlementConstants.RETURN_FAILURE, "结算获取网上支付未完成的运单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(SettlementConstants.RETURN_FAILURE, "结算获取网上支付未完成的运单失败：系统异常，请重新操作！："+e);
		}
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-9-12 18:17
	 */
	private String getResponse(int result,String message){
		//获取的网上未支付完成的运单
		EcsResponseDto resDto = new EcsResponseDto();
		
		resDto.setResult(result);
		resDto.setMessage(message);
		String response = JSONObject.toJSONString(resDto);
		
		logger.info(response);
		return response;
	}
	
	public void setTakingService(ITakingService takingService) {
		this.takingService = takingService;
	}
}
