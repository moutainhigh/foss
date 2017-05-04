package com.deppon.foss.module.pickup.waybill.server.utils;

import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * Restful客户端公共请求类
 * HttpClientUtil
 * @author 332469-bingo
 * 2016-12-14 上午9:47:24
 */
public class HttpClientListUtil {
	/**
	 * 常量设置
	 */
	public static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	public static HttpClient httpClient = new HttpClient(connectionManager);
	public static HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	public static HttpClientParams httpClientParams = new HttpClientParams();
	
	public static final int TWENTY_THOUSAND = 200;
	
	public static final int TEN_HUNDRED = 1000;
	
	public static final int FIVE_HUNDRED = 500;
	
	static {
		params.setConnectionTimeout(TWENTY_THOUSAND);
		params.setSoTimeout(TWENTY_THOUSAND);
		params.setMaxTotalConnections(TEN_HUNDRED);
		params.setDefaultMaxConnectionsPerHost(FIVE_HUNDRED);
		params.setStaleCheckingEnabled(true);
		connectionManager.setParams(params);
		// 设置连接超时两分钟
		httpClientParams.setConnectionManagerTimeout(TWENTY_THOUSAND);
		httpClient.setParams(httpClientParams);

		httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0, false));
		httpClient.setHttpConnectionManager(connectionManager);
		// 编码设置
		httpClient.getParams().setContentCharset("UTF-8");
	}

	/**
	 * 客户端发送请求
	 * 
	 * @Title: postRequest
	 * @author 198771-zhangwei
	 * 2016-12-14 上午9:47:24
	 */
	public static <T> T postRequest(String url, String requestparams,Class<T> clz)
			throws Exception {
		RequestEntity requestEntity = new StringRequestEntity(requestparams, "application/json", "UTF-8");
		String returnStr = "";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			returnStr = postMethod.getResponseBodyAsString();
			T responseObject = JSON.parseObject(returnStr, clz);
			return responseObject;
		} catch (Exception e) {
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}

	/**
	 * 客户端发送请求
	 * 
	 * @Title: postRequest
	 * @author 198771-zhangwei
	 * 2016-12-14 上午9:47:24
	 */
	public static List<?>  postRequestList(String url, String requestparams,Class<?> object)
			throws Exception {
		RequestEntity requestEntity = new StringRequestEntity(requestparams, "application/json", "UTF-8");
		String returnStr = "";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			returnStr = postMethod.getResponseBodyAsString();
			if(StringUtils.isEmpty(returnStr)){
				return null;
			}
			List<?> T =JSON.parseArray(returnStr,object);
			return T;
		} catch (Exception e) {
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}
	
	
}