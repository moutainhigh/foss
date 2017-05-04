package com.deppon.foss.module.settlement.vtsitf.server.ws;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.LineOfcreditRequest;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineOfcreditResponseDto;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IcanDebitIsBeBebt;

/**
 * 运单信用额度接口
 * @author 268217
 * @date 2016-03-22 
 */
public class CanDebitIsBeBebt implements IcanDebitIsBeBebt {
	
	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	/**
	 * 
	 */
	//private IWaybillPickupService WaybillPickupService;
	/**
	 * 
	 */
	private ICustomerBargainService customerBargainService;
	

	/**
	 * 成功：1--信用额度校验成功
	 */
	private int SUCCESS = 1;
	
	/**
	 * 失败：0--信用额度校验失败
	 */
	private int FAILURE = 0;
	
	/**
	 * 失败：0--信用额度校验失败
	 */
	private int TOW = 2;
	
	
	@Context
	HttpServletResponse res;
	
	@Override
	public @ResponseBody String canDebitIsBeBebt(@RequestBody String jsonStr) {
		res.setHeader("ESB-ResultCode" , "1");
		
		LineOfcreditResponseDto lineres = new LineOfcreditResponseDto();
		//生成返回实体
		String response = "";
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("信用额度校验失败：请求参数为空！");
			response = this.getResponse(FAILURE,"信用额度校验失败：请求参数为空！",lineres);
			return response;
		}
		try{
			JSONObject obj = JSONObject.parseObject(jsonStr);
			LineOfcreditRequest lest=JSONObject.parseObject(obj.get("requestEntity").toString(),LineOfcreditRequest.class);
			//LineOfcreditRequest lest=JSONObject.parseObject(jsonStr, LineOfcreditRequest.class);
			// 记录日志
			logger.info("信用额度校验开始:");
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
					.equals(lest.getPaidMethod()) ||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
					.equals(lest.getPaidMethod())){
				// 判断能否欠款 ；包括超期欠款和信用额度余额欠款
				lineres = customerBargainService.getIsBeBebt(lest.getDeliveryCustomerCode(),
						lest.getReceiveOrgCode(), lest.getPaidMethod(), lest.getPrePayAmount());
				
				if (StringUtils.isNotEmpty(lineres.getIsNot())) { // 每个客户和部门都必须有信用额度
					response=this.getResponse(FAILURE,lineres.getIsNot(),lineres);
				}else{
					// 记录日志
					logger.info("信用额度校验结束:");
					if(lineres.getLongCrdeitDate()==null){
						response=this.getResponse(TOW, "校验成功！", lineres);
					}else{
						//设置返回成功 1
						response = this.getResponse(SUCCESS,"校验成功！",lineres);
					}
				}
			}else{
				response = this.getResponse(FAILURE,"接口传入的付款方式非临欠或月结！",lineres);
			}
		}catch (BusinessException e){
			// 记录日志
			logger.info("信用额度校验失败：" + e.getErrorCode());
			// 日志处理
			logger.error(e.getMessage(), e);
			//设置返回失败2,声明返回异常信息
			response = this.getResponse(FAILURE , e.getErrorCode(),lineres);
		}catch (Exception e){
			// 记录日志
			logger.info("信用额度校验失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);
			//设置返回失败0,声明返回异常信息
			response = this.getResponse(FAILURE , "系统异常：" + e.getMessage(),lineres);
		}
		return response;
	}
	
	/**
	 * 根据参数生成返回值信息
	 * @param result
	 * @param reason
	 * @return
	 */
	private String getResponse(int result ,String isNot, LineOfcreditResponseDto lineres){
		LineOfcreditResponseDto response = new LineOfcreditResponseDto();
		response.setResult(result);
		response.setIsNot(isNot);
		response.setLongCrdeitDate(lineres.getLongCrdeitDate());
		response.setUnCrdeitAmount(lineres.getUnCrdeitAmount());
		return JSONObject.toJSONString(response);
	}
	
	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	/*public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		WaybillPickupService = waybillPickupService;
	}*/
}
