package com.deppon.foss.module.settlement.common.server.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;

/**
 * CUBC灰度层接口实现类
 * @author 353654
 *
 */
public class CUBCGrayService extends CUBCCommonService implements ICUBCGrayService {
	
	private final static String PARAMETERS_ERROR;
	private final static String CONNECTIONS_ERROR;
	static{
		PARAMETERS_ERROR = "调用CUBC灰度接口失败，参数异常...";
		CONNECTIONS_ERROR = "调用CUBC灰度接口失败，连接异常...";
	}
	CUBCGrayResultDto resultDto = new CUBCGrayResultDto();
	@Override
	public CUBCGrayResultDto queryDistributeType(CUBCGrayRequestDto dto) {
		if(null==dto || StringUtils.isBlank(dto.getBillNumber())){
			logger.error(PARAMETERS_ERROR);
			resultDto.setDistributeType(SettlementConstants.TYPE_CUBC);
			return resultDto;
		}
		logger.info("CUBC灰度分发请求地址信息:" + esbAddress);
		PostMethod post = null;
		try {
			String requestJson = JSONObject.toJSONString(dto);
			logger.info("调用CUBC灰度分发服务请求JSON信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson,APPLICATION_JSON,CODE_FORMAT);
			post = new PostMethod(esbAddress);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader(CONTENT_TYPE,REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_15000);
			params.setSoTimeout(NumberConstants.NUMBER_2999);
			httpClient.executeMethod(post);
			String resultJson = post.getResponseBodyAsString();
			logger.info("调用CUBC灰度分发服务响应JSON信息："+resultJson);
			resultDto = JSONObject.parseObject(resultJson,CUBCGrayResultDto.class);
			return resultDto;
		} catch (Exception e) {
			logger.error(CONNECTIONS_ERROR + e.getMessage());
			resultDto.setDistributeType(SettlementConstants.TYPE_CUBC);
			resultDto.setMessage(e.getMessage());
			return resultDto;
		} finally{
			if(post!=null){
				post.releaseConnection();
			}
		}
	}
}
