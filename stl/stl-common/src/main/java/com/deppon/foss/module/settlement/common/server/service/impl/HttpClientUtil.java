package com.deppon.foss.module.settlement.common.server.service.impl;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Restful客户端公共请求类
 * 
 * @ClassName: HttpClientUtil
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-7-3 上午11:34:57
 */
public class HttpClientUtil {
	/**
	 * 常亮设置
	 */
	public static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	public static HttpClient httpClient = new HttpClient(connectionManager);
	public static HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	public static HttpClientParams httpClientParams = new HttpClientParams();
	
	public static final int TWENTY_THOUSAND = 20000;
	
	public static final int FIVE_HUNDRED = 500;
	
	static {
		params.setConnectionTimeout(TWENTY_THOUSAND);
		params.setSoTimeout(TWENTY_THOUSAND);
		params.setMaxTotalConnections(FIVE_HUNDRED);
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
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-7-3 上午11:36:39
	 */
	public static String postRequest(String url, RequestEntity requestEntity)
			throws Exception {
		String returnStr = "";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			returnStr = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return returnStr;
	}

}