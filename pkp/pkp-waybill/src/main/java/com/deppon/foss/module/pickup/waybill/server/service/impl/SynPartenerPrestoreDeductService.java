package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.deppon.dpap.module.dubbo.server.service.IPartnerBondBalanceQueryService;
import com.deppon.dpap.module.dubbo.shared.domain.PartnerBondBalanceQueryRequest;
import com.deppon.dpap.module.dubbo.shared.domain.PartnerBondBalanceQueryResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerPrestoreDeductService;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

//合伙人预存资金开单，校验资金额度
public class SynPartenerPrestoreDeductService implements ISynPartenerPrestoreDeductService {
	// 日志
	private Logger logger = LoggerFactory.getLogger(SynPartenerPrestoreDeductService.class);
	// 月结校验地址
	private String partenerPrestoreUrl;

	private static final int NUMBER_60000 = 60000;
	@Autowired
	private com.deppon.dpap.routeswitch.RouteSwitch routeSwitch;
	@Autowired
	private IPartnerBondBalanceQueryService partnerBondBalanceQueryService;

	public SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse(
			PartenerPrestoreDeductDto prestoreDeductDto) {
		SynPartenerPrestoreDeductResponse responseEntity = new SynPartenerPrestoreDeductResponse();
		String jsonString = JSON.toJSONString(prestoreDeductDto);
		logger.info("合伙人预存资金开单，校验金额开始:" + partenerPrestoreUrl);
		logger.info("合伙人预存资金开单，校验金额请求JSON:" + jsonString);

		if (routeSwitch.isOpened()) {
			logger.info("合伙人预存资金开单校验资金额度 ==开始走dubbo");
			PartnerBondBalanceQueryRequest req = castFromLocalToDubbo(prestoreDeductDto);
			try {
				return castFromDubboToLocal(getResponseByDubbo(req));
			} catch (Exception e) {
				logger.error("走dubbo校验资金额度异常", e);
				responseEntity.setSuccess(false);
				responseEntity.setErrorInfo("校验资金额度异常，信息：" + e.getLocalizedMessage());
				return responseEntity;
			}
		}

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(partenerPrestoreUrl);
		// 设置响应超时时间
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUMBER_60000);
		// 设置连接超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_5000);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
			// 设置字符集编码
			method.getParams().setContentCharset("UTF-8");
			method.getParams().setHttpElementCharset("UTF-8");
			method.setRequestEntity(requestEntity);
			int statuCode = client.executeMethod(method);
			// 返回状态如果是200为正常
			if (statuCode == HttpStatus.SC_OK) {
				String responseString = method.getResponseBodyAsString();
				JSONObject obj = JSONObject.fromObject(responseString); // 获取反回类
				Boolean isSuccess = (Boolean) obj.get("result");
				String errorinfo = "";
				if (!isSuccess)// 如果验证不成功
				{
					// errorinfo = "您的预存款资金不足，未避免影响你的业务，请充值";
					errorinfo = (String) obj.get("errorInfo");
				}
				// 将返回信息添加到返回类中
				responseEntity.setSuccess(isSuccess);
				responseEntity.setErrorInfo(errorinfo);
				logger.info("合伙人预存资金开单，校验金额返回JSON:" + responseString);
			} else {// 如果返回状态码不是200，那么默认为异常
				responseEntity.setSuccess(false);
				responseEntity.setErrorInfo("合伙人预存资金池校验接口内部异常");
				logger.info("合伙人预存资金开单，校验金额服务端异常" + statuCode);
			}
			logger.info("合伙人预存资金开单，校验金额结束:" + statuCode);
		} catch (Exception e) {
			responseEntity.setSuccess(false);
			responseEntity.setErrorInfo("未能连接合伙人预存资金池校验接口");
			// 如果是服务端GIS异常，那么ESB会返回相应的错误信息，这会导致上面JSONObject.toBean转换异常抛出JSONException
			if (e instanceof JSONException) {
				responseEntity.setErrorInfo("未能连接合伙人预存资金池校验接口");
				// responseEntity.setErrorInfo("Server Error");
			}
			logger.info("合伙人预存资金开单，校验金额异常:" + e.getMessage());
		}
		return responseEntity;
	}

	private PartnerBondBalanceQueryRequest castFromLocalToDubbo(PartenerPrestoreDeductDto prestoreDeductDto) {
		logger.info("=== SynPartenerPrestoreDeductService.castFromLocalToDubbo(PartenerPrestoreDeductDto)");
		PartnerBondBalanceQueryRequest request = new PartnerBondBalanceQueryRequest();
		request.setPartnerOrgCode(prestoreDeductDto.getPartnerOrgCode());
		request.setAmount(BigDecimal.valueOf(Double.parseDouble(prestoreDeductDto.getAmount())));
		logger.warn("castFromLocalToDubbo amount = " + request.getAmount());
		return request;
	}

	private SynPartenerPrestoreDeductResponse castFromDubboToLocal(PartnerBondBalanceQueryResponse responseByDubbo) {
		logger.info("=== SynPartenerPrestoreDeductService.castFromDubboToLocal()");
		SynPartenerPrestoreDeductResponse response = new SynPartenerPrestoreDeductResponse();
		response.setSuccess(responseByDubbo.getResult());
		response.setErrorInfo(responseByDubbo.getErrorInfo());
		logger.warn("castFromDubboToLocal = " + response.isSuccess() + response.getErrorInfo());
		return response;
	}

	private PartnerBondBalanceQueryResponse getResponseByDubbo(PartnerBondBalanceQueryRequest arg0) {
		PartnerBondBalanceQueryResponse response = partnerBondBalanceQueryService.queryBalance(arg0);
		return response;
	}

	public String getPartenerPrestoreUrl() {
		return partenerPrestoreUrl;
	}

	public void setPartenerPrestoreUrl(String partenerPrestoreUrl) {
		this.partenerPrestoreUrl = partenerPrestoreUrl;
	}

}
