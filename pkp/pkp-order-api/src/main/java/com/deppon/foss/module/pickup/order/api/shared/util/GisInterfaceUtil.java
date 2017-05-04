/**
 *  initial comments.
 */
/* 
 * PROJECT NAME: basedata 
 * PACKAGE NAME: com.deppon.gis.module.basedata.server.util 
 * FILE NAME: UtilTest.java 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved. 
 */

package com.deppon.foss.module.pickup.order.api.shared.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * gis接口调用工具类
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午10:04:05
 */
@SuppressWarnings("unchecked")
public class GisInterfaceUtil {
	
	// httpclient
	static HttpClient httpClient;
	// 超时
	private static final int TIMEOUT = 30000; //14.7.28 gcl AUTO-209
	// logger
	private static final Logger LOGGER = LoggerFactory.getLogger(GisInterfaceUtil.class);
	// 返回状态
	private static final int OK = 200;
	// 最大连接数
	private static final int MAX_TOTAL_CONNECTIONS = 300;
	//每个路由最大连接数
	public final static int MAX_HOST_CONNECTIONS = 10;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	

	// 构造HttpClient的实例
	static {
		httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
		httpClient.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
	}

	/**
	 * 
	 * 发送http请求
	 * 
	 * @author ibm-wangfei
	 * @date Dec 10, 2012 2:49:52 PM
	 * @update zxy 20140724  修改:请求超时及日志优化
	 */
	public static String sendHttp(String url, Map<String, String> param) {
		StringBuffer sbRsp = new StringBuffer();
//		// 创建Post方法的实例
		PostMethod postMethod = new PostMethod(url);
		try {
//			// 参数
			NameValuePair[] params = getParameter(param);
			// http头
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			
			postMethod.setRequestBody(params);
//			 使用系统提供的默认的恢复策略
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
			httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
			 // 设置每个主机最大连接数  
	        httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(MAX_HOST_CONNECTIONS);
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != OK) {
				sbRsp.append("{\"errorCode\":\"GIS_REQUEST_ERROR_URL_CANNOTFOUND\"}");
				throw new IOException("can not found[" + url + "]");
			}
			return postMethod.getResponseBodyAsString();
			
		} catch (Exception e) {
			LOGGER.error("error", e); 
			sbRsp.append("{\"errorCode\":\"GIS_REQUEST_ERROR\"}");
		}finally{
			postMethod.releaseConnection();
		}
		return sbRsp.toString();
	}


	/**
	 * 访问gis接口
	 * 
	 * @author 078774-foss-jeffery
	 * @date 2012-11-3 上午11:35:46
	 * @param gisInterface
	 *            对应的gis调用功能
	 * @param par
	 *            调用功能传递的参数
	 */
	public static Map<String, Object> callGisInterface(String url, Map<String, String> param) {
		// 使用get请求，拼接url
		Map<String, Object> returnMap = null;
		String resp = "";
		try {
			resp = sendHttp(url,param);
			returnMap = objectMapper.readValue(resp, Map.class);
			String errorCode = (String)returnMap.get("errorCode");
			if(StringUtils.isNotBlank(errorCode)){
				String message = (String)returnMap.get("message");
				if(StringUtils.isNotBlank(message)){
					LOGGER.error("message => " + message);
				}
				throw new RuntimeException("GISEXCEPTION=>"+errorCode);
			}
		} catch (JsonParseException e) {
			LOGGER.error(e.getMessage() + "resp=" + resp,e);
		} catch (JsonMappingException e) {
			LOGGER.error(e.getMessage() + "resp=" + resp,e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage() + "resp=" + resp,e);
		}
		return returnMap;
	}

	/**
	 * 拼接调用参数
	 * 
	 * @author 078774-foss-jeffery
	 * @date 2012-11-3 上午11:24:00
	 */

	private static NameValuePair[] getParameter(Map<String, String> param) {
		// 参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(Entry<String, String> entry : param.entrySet()){
			// 拼接map参数
			NameValuePair valuePair = new NameValuePair("queryData." + entry.getKey(), entry.getValue());
			params.add(valuePair);
		}
		NameValuePair[] pairs = new NameValuePair[params.size()];
		return params.toArray(pairs);
	}

}