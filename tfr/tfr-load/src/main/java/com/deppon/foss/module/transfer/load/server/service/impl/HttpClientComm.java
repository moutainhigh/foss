package com.deppon.foss.module.transfer.load.server.service.impl;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;

/**
* @description httpClient 工具类
* @version 1.0
* @author 14022-foss-songjie
* @update 2016年4月11日 下午2:07:36
*/
public class HttpClientComm {
	/**
	* @fields httpClient
	* @author 14022-foss-songjie
	* @update 2016年4月13日 上午8:52:53
	* @version V1.0
	*/
	public static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	public static HttpClient httpClient = new HttpClient(connectionManager);
	public static HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	public static HttpClientParams httpClientParams = new HttpClientParams();
	static {
		params.setConnectionTimeout(LoadConstants.SONAR_NUMBER_20000);
		params.setSoTimeout(LoadConstants.SONAR_NUMBER_20000);
		// 最大连接数
		params.setMaxTotalConnections(LoadConstants.SONAR_NUMBER_500);
		params.setDefaultMaxConnectionsPerHost(LoadConstants.SONAR_NUMBER_500);
		params.setStaleCheckingEnabled(true);
		connectionManager.setParams(params);
		
		// 设置httpClient的连接超时，对连接管理器设置的连接超时是无用的
		httpClientParams.setConnectionManagerTimeout(LoadConstants.SONAR_NUMBER_20000); //等价于4.2.3中的CONN_MANAGER_TIMEOUT
		httpClient.setParams(httpClientParams);
		 
		//另外设置http client的重试次数，默认是3次；当前是禁用掉（如果项目量不到，这个默认即可）
		httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
	    httpClient.setHttpConnectionManager(connectionManager);
	    //字符集设置 UTF-8
	    httpClient.getParams().setContentCharset("UTF-8");
	}
	
	/**
	* @description post 请求
	* @param url
	* @param requestEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	 * @throws Exception 
	* @update 2016年4月13日 上午8:54:54
	*/
	public static String postRequest(String url,RequestEntity requestEntity) throws Exception{
		String returnStr = "";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(requestEntity);
		 try {
			httpClient.executeMethod(postMethod);
			returnStr = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			throw e;
		}finally{
			if(postMethod!=null){
				postMethod.releaseConnection();
			}
//			if (httpClient != null) {
//           	 httpClient.getHttpConnectionManager().closeIdleConnections(0L);
//           }
		}
		return returnStr;
	}
	
} 