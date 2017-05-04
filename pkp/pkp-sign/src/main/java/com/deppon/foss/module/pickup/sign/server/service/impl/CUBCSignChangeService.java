package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ICUBCSignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CUBCSignChangeRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CUBCSignChangeResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.CUBCSignChangeException;
import com.deppon.foss.module.settlement.common.server.service.impl.CUBCCommonService;


/**
 * CUBC签收变更服务FOSS客户端实现
 * @author 353654
 *
 */
public class CUBCSignChangeService extends CUBCCommonService implements ICUBCSignChangeService{

	@Override
	public CUBCSignChangeResultDto changeRepayment(CUBCSignChangeRequestDto dto) {
		if(dto==null){
			logger.error("调用CUBC签收变更服务参数异常");
			throw new CUBCSignChangeException("调用CUBC签收变更服务参数异常");
		}
		PostMethod post = null;
		try {
			logger.info("CUBC签收变更服务请求地址信息:"+esbAddress);
			String requestJson = JSONObject.toJSONString(dto);
			logger.info("调用CUBC签收变更服务请求JSON信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson,APPLICATION_JSON,CODE_FORMAT);
			post = new PostMethod(esbAddress);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader(CONTENT_TYPE,REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_15000);
			params.setSoTimeout(NumberConstants.NUMBER_2999);
			httpClient.executeMethod(post);
			InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,CODE_FORMAT));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resultJson = sbf.toString();
			logger.info("调用CUBC签收变更服务响应JSON信息："+resultJson);
			CUBCSignChangeResultDto resultDto = JSONObject.parseObject(resultJson,CUBCSignChangeResultDto.class);
			return resultDto;
		} catch (Exception e) {
			logger.error("调用CUBC签收变更服务连接异常");
			throw new CUBCSignChangeException("调用CUBC签收变更服务连接异常");
		} finally{
			if(post != null){
				post.releaseConnection();
			}
		}
	}
	
	
}
