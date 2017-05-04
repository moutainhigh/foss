package com.deppon.foss.module.pickup.waybill.server.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressDeliveryAddressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISynPartenerCodAmountUpperLimitService;
import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerCodAmountUpperLimitResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerCodAmountUpperLimitDto;

//合伙人代收货款上限额度校验
public class SynPartenerCodAmountUpperLimitService implements
		ISynPartenerCodAmountUpperLimitService{
	//日志
    private Logger logger = LoggerFactory.getLogger(ExpressDeliveryAddressService.class);
    //月结校验地址
    private String partenerCodAmountUpperLimitUrl;
    
    private static final int NUMBER_60000 = 60000;
    
    public SynPartenerCodAmountUpperLimitResponse partenerCodAmountUpperLimitResponse(PartenerCodAmountUpperLimitDto partenerCodAmountUpperLimit){
    	//接口只需要接收部门编号
    	String partnerDeptCode = partenerCodAmountUpperLimit.getPartnerDeptCode();
    	logger.info("合伙人代收款上限开始:"+partenerCodAmountUpperLimitUrl);
	    logger.info("合伙人代收款上限请求JSON:"+partnerDeptCode);
	    HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod(partenerCodAmountUpperLimitUrl);
	    //设置响应超时时间
	    method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUMBER_60000);
	    //设置连接超时时间
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_5000);
	    SynPartenerCodAmountUpperLimitResponse responseEntity = null;
	    try {
	    	StringRequestEntity requestEntity = new StringRequestEntity(partnerDeptCode, "application/json", "UTF-8");
	    	//设置字符集编码
	    	method.getParams().setContentCharset("UTF-8");
	    	method.getParams().setHttpElementCharset("UTF-8");
	    	method.setRequestEntity(requestEntity);
			int statuCode = client.executeMethod(method);
			//返回状态如果是200为正常
			if(statuCode==HttpStatus.SC_OK){
				String responseString = method.getResponseBodyAsString();
				JSONObject obj = JSONObject.fromObject(responseString).getJSONObject("PartnerContractResponse"); //获取反回类
				responseEntity = (SynPartenerCodAmountUpperLimitResponse)JSONObject.toBean(obj, SynPartenerCodAmountUpperLimitResponse.class);
				if(null == responseEntity){
					responseEntity = new SynPartenerCodAmountUpperLimitResponse();
					responseEntity.setSuccess(false);
					responseEntity.setErrorMsg("访问合伙人代收款上限校验接口失败");
				}
				logger.info("合伙人代收款上限返回JSON:"+responseString);
			}else{//如果返回状态码不是200，那么默认为异常
				if(responseEntity==null){
		    		responseEntity = new SynPartenerCodAmountUpperLimitResponse();
		    	}
				responseEntity.setSuccess(false);
				responseEntity.setErrorMsg("合伙人代收款上限校验接口内部异常");
				logger.info("合伙人代收款上限返回"+statuCode);
			}
			logger.info("合伙人代收款上限结束:"+statuCode);
	    } catch (Exception e) {
	    	if(responseEntity==null){
	    		responseEntity = new SynPartenerCodAmountUpperLimitResponse();
	    	}
	    	responseEntity.setSuccess(false);	
	    	responseEntity.setErrorMsg("未能访问合伙人代收款上限校验接口");
			logger.info("合伙人代收款上限异常:"+e.getMessage());
		}
    	return responseEntity;
    }

	public String getPartenerCodAmountUpperLimitUrl() {
		return partenerCodAmountUpperLimitUrl;
	}

	public void setPartenerCodAmountUpperLimitUrl(
			String partenerCodAmountUpperLimitUrl) {
		this.partenerCodAmountUpperLimitUrl = partenerCodAmountUpperLimitUrl;
	}
}
