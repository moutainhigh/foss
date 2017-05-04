package com.deppon.foss.module.settlement.crmitf.server.rs.impl;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.ICRMPayableBillService;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateRes;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateResponse;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.crmitf.server.rs.IGeneratePayableBill;

/**
 * 生成理赔、服务补救、退运费应付单.
 *
 * @author 231434-foss-bieyexiong
 * @date 2015-12-20 上午10:19:22
 */
public class GeneratePayableBill implements IGeneratePayableBill{
	
	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	/** 
	 * CRM应付单服务. 
	 **/
	private ICRMPayableBillService crmPayableBillService;
	
	/**
	 * 成功：1--生成理赔应付单成功，若为在线理赔，付款也成功
	 */
	private int SUCCESS = 1;
	
	/**
	 * 失败：0--生成理赔应付单失败
	 */
	private int FAILURE = 0;
	
	/**
	 * 付款失败：2--在线理赔应付单付款失败 ：2 
	 */
	private int PAY_FAILURE = 2;

	@Context
	HttpServletResponse res;

	@Override
	public @ResponseBody String claimsPayBillGenerate(@RequestBody String jsonStr) {
		System.out.println(jsonStr);
		
		res.setHeader("ESB-ResultCode" , "1");
		//生成返回实体
		String response = "";
		
		//记录应付单是否生成成功
		boolean flag = false;
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("生成理赔应付单失败：请求参数为空！");
			response = this.getResponse(FAILURE,"生成理赔应付单失败：请求参数为空！");
			return response;
		}
		try{
			ClaimsPayBillGenerateRes payload = JSONObject.
						parseObject(jsonStr, ClaimsPayBillGenerateRes.class);
			//获取crm用户
			CurrentInfo currentInfo = SettlementUtil.getCRMCurrentInfo();

			// 记录日志
			logger.info("生成理赔应付单开始:" + payload.getBillNo());

			// 新增应付单
			crmPayableBillService.addCRMBillPayable(payload, currentInfo);
			// 记录日志
			logger.info("生成理赔应付单结束:" + payload.getBillNo());
			
			//设置返回成功 1
			response = this.getResponse(SUCCESS,"生成理赔应付单成功，若为在线理赔，付款成功");
			
			//应付单生成成功
			flag = true;

		} catch (BusinessException e){
			// 记录日志
			logger.info("生成理赔应付单失败：" + e.getErrorCode());
			// 日志处理
			logger.error(e.getMessage(), e);

			//设置返回失败0,声明返回异常信息
			response = this.getResponse(FAILURE , e.getErrorCode());
		} catch (Exception e){
			// 记录日志
			logger.info("生成理赔应付单失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			//设置返回失败0,声明返回异常信息
			response = this.getResponse(FAILURE , "系统异常：" + e.getMessage());
		} //add by 269044
		try {
			ClaimsPayBillGenerateRes payload = JSONObject.
					parseObject(jsonStr, ClaimsPayBillGenerateRes.class);
		    //获取crm用户
		    CurrentInfo currentInfo = SettlementUtil.getCRMCurrentInfo();
		 	
		 	//判断为在线支付并且是电汇的付款方式，并且应付单生成成功的情况下
	        if((SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(payload.getClaimWay())) 
	        		&& (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER
	        		.equals(payload.getPaymentCategory())) && flag == true) {
	        	 // 记录日志
			 	logger.info("在线理赔付款开始:" + payload.getBillNo());
			 	
	        	//理赔方式为在线理赔的，需直接付款，对接到报账平台
				crmPayableBillService.crmBillPayableToFSSC(payload, currentInfo);
				
				//设置返回成功 1
				response = this.getResponse(SUCCESS,"生成理赔应付单成功，若为在线理赔，付款成功");
				
				// 记录日志
			 	logger.info("在线理赔付款成功:" + payload.getBillNo());
	        }	        	       
			
		} catch (BusinessException e){
			// 记录日志
			logger.info("在线理赔应付单生成成功，付款失败：" + e.getErrorCode());
			// 日志处理
			logger.error(e.getMessage(), e);

			//设置返回失败2,声明返回异常信息
			response = this.getResponse(PAY_FAILURE , e.getErrorCode());
		} catch (Exception e){
			// 记录日志
			logger.info("在线理赔应付单生成成功，付款失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			//设置返回失败2,声明返回异常信息
			response = this.getResponse(PAY_FAILURE , "系统异常：" + e.getMessage());
		}
		return response;
	}
	
	/**
	 * 根据参数生成返回值信息
	 * @param result
	 * @param reason
	 * @return
	 */
	private String getResponse(int result , String reason){
		ClaimsPayBillGenerateResponse response = new ClaimsPayBillGenerateResponse();
		response.setResult(result);
		response.setReason(reason);
		return JSONObject.toJSONString(response);
	}

	public void setCrmPayableBillService(
			ICRMPayableBillService crmPayableBillService) {
		this.crmPayableBillService = crmPayableBillService;
	}

}
