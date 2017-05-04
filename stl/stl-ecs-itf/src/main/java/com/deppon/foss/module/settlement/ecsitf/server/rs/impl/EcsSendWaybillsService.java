package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.WayBillRequest;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * 同步运单信息接口
 * @author 326181
 *
 */
@Controller
@RequestMapping("/v1/foss/stl/ecsSendWaybill")
public class EcsSendWaybillsService{

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	IEcsSendWaybillService ecsSendWaybillService;
	
	@Autowired
	private ILogEcsFossService logEcsFossService;
	
	/**
	 * 成功：1
	 */
	private int SUCCESS = 1;
	
	/**
	 * 失败：0
	 */
	private int FAILURE = 0;

	/**
	 * 同步运单信息接口
	 * @param jsonreq 同步运单数据串
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/synchroWaybill", method = RequestMethod.POST)
	public @ResponseBody String synchroWaybill(@RequestBody String jsonreq, HttpServletResponse res) {
		logger.warn("==============================调用synchroWaybill接口 begin=====================================");
		Date date = new Date();
		res.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		//生成返回实体
		String response = "", waybillNo="";
		//是否同步运单成功
		boolean isSuccess = false;
		if(StringUtils.isBlank(jsonreq)){
			// 记录日志
			logger.info("生成运单失败：请求参数为空！");
			response = this.getResponseJson(FAILURE, "生成运单失败：请求参数为空！", null);
		}
		//响应为空 说明校验通过
		if (StringUtils.isBlank(response)) {
			try{
				logger.info("请求参数: *********************" + jsonreq);
				WayBillRequest billRequest = JSONObject.parseObject(jsonreq, WayBillRequest.class);
				
				waybillNo = billRequest.getWaybillEntity() != null 
						? billRequest.getWaybillEntity().getWaybillNo() : 
							billRequest.getWaybillRfcEntity() !=null ? billRequest.getWaybillRfcEntity().getWaybillNo() : "null";
				
				ecsSendWaybillService.doSynchroWaybill(billRequest, true);
				//设置返回成功 1
				response = this.getResponseJson(SUCCESS,"生成运单成功", waybillNo);	
				isSuccess = true;
			} catch(BusinessException e){
				response = this.getResponseJson(FAILURE, "同步运单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()), waybillNo);
			} catch (Exception e){
				//设置返回失败0,声明返回异常信息
				response = this.getResponseJson(FAILURE, "同步运单系统异常：" + e, waybillNo);
			}
		}
		try{
			//保存日志
			logEcsFossService.setLog(jsonreq, response, 
					SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_WAYBILL, waybillNo, isSuccess, date);
		}catch(Exception e1){
			logger.info("记录同步运单接口异常日志插入失败："+e1.toString());
		}
		return response;
	}
	
	/**
	 * 获取返回信息
 	 * @author 231434-FOSS-326181
 	 * @date 2015-7-14 上午10:30:35
 	 */
	public String getResponseJson(int result,String message, String waybillNo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", result);
		resMap.put("message", message);
		resMap.put("waybillNo", waybillNo);
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	}

}

