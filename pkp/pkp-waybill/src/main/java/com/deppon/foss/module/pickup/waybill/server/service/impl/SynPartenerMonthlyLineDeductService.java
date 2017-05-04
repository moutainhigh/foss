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
import com.deppon.dpap.module.dubbo.server.service.IMonthlyLinePublicQueryService;
import com.deppon.dpap.module.dubbo.shared.domain.MonthlyLinePublicQueryRequest;
import com.deppon.dpap.module.dubbo.shared.domain.MonthlyLinePublicQueryResponse;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerMonthlyLineDeductService;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerMonthlyLineDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerDeductDto;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

//合伙人月结开单，校验预存款资金池的资金额度
public class SynPartenerMonthlyLineDeductService implements ISynPartenerMonthlyLineDeductService {
	// 日志
	private Logger logger = LoggerFactory.getLogger(SynPartenerMonthlyLineDeductService.class);
	// 月结校验地址
	private String partenerMonthlyUrl;

	private static final int NUMBER_60000 = 60000;
	@Autowired
	private com.deppon.dpap.routeswitch.RouteSwitch routeSwitch;
	@Autowired
	private IMonthlyLinePublicQueryService monthlyLinePublicQueryService;

	public SynPartenerMonthlyLineDeductResponse partenerMonthlyLineDeduct(PartenerDeductDto deductDto) {
		String jsonString = JSON.toJSONString(deductDto);
		logger.info("合伙人月结开单，校验预存款资金池的资金额度请求JSON:" + jsonString);
		SynPartenerMonthlyLineDeductResponse responseEntity = null;
		if (routeSwitch.isOpened()) {
			logger.info("合伙人月结开单 ==开始走dubbo");
			MonthlyLinePublicQueryRequest req = castFromLocalToDubbo(deductDto);
			try {
				return castFromDubboToLocal(getResponseByDubbo(req));
			} catch (Exception e) {
				logger.error("走dubbo月结开单异常", e);
				responseEntity = new SynPartenerMonthlyLineDeductResponse();
				responseEntity.setSuccess(false);
				responseEntity.setErrorMsg("月结开单异常，信息：" + e.getLocalizedMessage());
				return responseEntity;
			}
		}
		logger.info("合伙人月结开单，校验预存款资金池的资金额度开始:" + partenerMonthlyUrl);
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(partenerMonthlyUrl);
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
				JSONObject obj = JSONObject.fromObject(responseString).getJSONObject("MonthlyLinePublicQueryResponse"); // 获取反回类
				responseEntity = (SynPartenerMonthlyLineDeductResponse) JSONObject.toBean(obj,
						SynPartenerMonthlyLineDeductResponse.class);
				// 后来跟踪ESB访问失败时也会返回 200 状态导致 responseEntity 没有能正常被创建
				if (null == responseEntity) {
					responseEntity = new SynPartenerMonthlyLineDeductResponse();
					responseEntity.setSuccess(false);
					responseEntity.setErrorMsg("访问合伙人月结资金池校验接口失败");
				}
				logger.info("合伙人月结开单，校验预存款资金池的资金额度返回JSON:" + responseString);
			} else {// 如果返回状态码不是200，那么默认为异常
				if (responseEntity == null) {
					responseEntity = new SynPartenerMonthlyLineDeductResponse();
				}
				responseEntity.setSuccess(false);
				responseEntity.setErrorMsg("合伙人月结资金池校验接口内部异常");
				logger.info("合伙人月结开单，校验预存款资金池的资金额度服务端异常" + statuCode);
			}
			logger.info("合伙人月结开单，校验预存款资金池的资金额度结束:" + statuCode);
		} catch (Exception e) {
			if (responseEntity == null) {
				responseEntity = new SynPartenerMonthlyLineDeductResponse();
			}
			responseEntity.setSuccess(false);
			responseEntity.setErrorMsg("未能连接合伙人月结资金池校验接口");
			// 如果是服务端GIS异常，那么ESB会返回相应的错误信息，这会导致上面JSONObject.toBean转换异常抛出JSONException
			if (e instanceof JSONException) {
				responseEntity.setErrorMsg("未能连接合伙人月结资金池校验接口");
				// responseEntity.setErrorMsg("Server Error");
			}
			logger.info("合伙人月结开单，校验预存款资金池的资金额度异常:" + e.getMessage());
		}
		return responseEntity;
	}

	private MonthlyLinePublicQueryRequest castFromLocalToDubbo(PartenerDeductDto deductDto) {
		logger.info("++++ SynPartenerMonthlyLineDeductService.castFromLocalToDubbo(PartenerDeductDto)");
		MonthlyLinePublicQueryRequest request = new MonthlyLinePublicQueryRequest();
		request.setPartnerDeptCode(deductDto.getPartnerDeptCode());
		request.setLimit(BigDecimal.valueOf(Double.parseDouble(deductDto.getLimit())));
		logger.warn("castFromLocalToDubbo = " + request.getLimit());
		return request;
	}

	private MonthlyLinePublicQueryResponse getResponseByDubbo(MonthlyLinePublicQueryRequest arg0) {
		MonthlyLinePublicQueryResponse response = monthlyLinePublicQueryService.queryMonthlyLine(arg0);
		return response;
	}

	private SynPartenerMonthlyLineDeductResponse castFromDubboToLocal(MonthlyLinePublicQueryResponse mResponse) {
		logger.info("++++ SynPartenerMonthlyLineDeductService.castFromDubboToLocal()");
		SynPartenerMonthlyLineDeductResponse response = new SynPartenerMonthlyLineDeductResponse();
		response.setSuccess(mResponse.isSuccess());
		response.setErrorMsg(mResponse.getErrorMsg());
		logger.warn("castFromDubboToLocal = " + response.isSuccess() + response.getErrorMsg());
		return response;
	}

	public String getPartenerMonthlyUrl() {
		return partenerMonthlyUrl;
	}

	public void setPartenerMonthlyUrl(String partenerMonthlyUrl) {
		this.partenerMonthlyUrl = partenerMonthlyUrl;
	}
}
