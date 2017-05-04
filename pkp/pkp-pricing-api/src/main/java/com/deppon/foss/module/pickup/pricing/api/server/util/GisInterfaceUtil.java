/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/util/GisInterfaceUtil.java
 * 
 * FILE NAME        	: GisInterfaceUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/* 
 * PROJECT NAME: basedata 
 * PACKAGE NAME: com.deppon.gis.module.basedata.server.util 
 * FILE NAME: UtilTest.java 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved. 
 */

package com.deppon.foss.module.pickup.pricing.api.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.util.CollectionUtils;


/**
 * gis接口调用工具类
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午10:04:05
 */
@SuppressWarnings("unchecked")
public class GisInterfaceUtil {
//	/**
//	 * 部门行政区域匹配
//	 */
//	public static final String DEPT_ADMIN_MATCH = "deptAdminMatch";
//	/**
//	 * 网点查询
//	 */
//	public static final String COMPOSITE_STATION_MATCH = "compositeStationMatch";
	
	static HttpClient httpClient;
	
	private static final int TIMEOUT = 300000;

	/**
	 * 配置文件对应的key
	 */
//	private static final String URL_KEY = "gisurl";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GisInterfaceUtil.class);

	/**
	 * gis app url
	 */
//	private static String appUrl = Constants.getKeyValue(URL_KEY);

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static final int OK = 200;

	// 构造HttpClient的实例
	static {
		httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT);
	}

	/**
	 * 
	 * 发送http请求
	 * 
	 * @author ibm-wangfei
	 * @date Dec 10, 2012 2:49:52 PM
	 */
	public static String sendHttp(String url, Map<String, Object> param) {
		// 创建Post方法的实例
		try {
			PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			NameValuePair[] params = getParameter(param);
			postMethod.setRequestBody(params);
			// 使用系统提供的默认的恢复策略
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != OK) {
				throw new IOException("can not found[" + url + "]");
			}
			LOGGER.info("postMethod.getResponseBodyAsString()" + postMethod.getResponseBodyAsString());
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			LOGGER.error("error", e);
		}

		return "";
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
	public static Map<String, Object> callGisInterface(String gisInterface, Map<String, Object> param) {
		// 使用get请求，拼接url
//		String url = appUrl + gisInterface + ".action";
		String url = gisInterface;
		Map<String, Object> returnMap = null;
		try {
			String jsonStr = sendHttp(url,param);
			returnMap = objectMapper.readValue(jsonStr.replaceAll("'","\""), Map.class);
		} catch (JsonParseException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return returnMap;
	}

	/**
	 * 拼接调用参数
	 * 
	 * @author 078774-foss-jeffery
	 * @date 2012-11-3 上午11:24:00
	 */

	@SuppressWarnings("rawtypes")
	private static NameValuePair[] getParameter(Map<String, Object> param) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(Entry<String, Object> entry : param.entrySet()){
			if(StringUtil.equals(entry.getKey(), "matchtypes")) {
				List<String> matchtypes = (List)entry.getValue();
				if(CollectionUtils.isNotEmpty(matchtypes)) {
					for (int i = 0; i < matchtypes.size(); i++) {
						NameValuePair valuePair = new NameValuePair("queryData." + entry.getKey(), matchtypes.get(i));
						params.add(valuePair);
					}
				}
			} else if(StringUtil.equals(entry.getKey(), "transportways")) {
				List<String> transportway = (List)entry.getValue();
				if(CollectionUtils.isNotEmpty(transportway)) {
					for (int i = 0; i < transportway.size(); i++) {
						NameValuePair valuePair = new NameValuePair("queryData." + entry.getKey(), transportway.get(i));
						params.add(valuePair);
					}
				}
			} else {
				NameValuePair valuePair = new NameValuePair("queryData." + entry.getKey(), (String)entry.getValue());
				params.add(valuePair);
			}
		}
		NameValuePair[] pairs = new NameValuePair[params.size()];
		return params.toArray(pairs);
	}

}