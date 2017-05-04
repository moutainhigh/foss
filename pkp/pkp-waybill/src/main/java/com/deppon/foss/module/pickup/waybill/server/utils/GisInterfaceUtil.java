/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/utils/GisInterfaceUtil.java
 * 
 * FILE NAME        	: GisInterfaceUtil.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/* 
 * PROJECT NAME: basedata 
 * PACKAGE NAME: com.deppon.gis.module.basedata.server.util 
 * FILE NAME: UtilTest.java 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved. 
 */

package com.deppon.foss.module.pickup.waybill.server.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.server.service.impl.GisQueryService;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;

/**
 * gis接口调用工具类
 * @author 078774-foss-jeffery
 * @date 2012-11-3 上午10:04:05
 */

public class GisInterfaceUtil {
	public static final Logger LOGGER = LoggerFactory.getLogger(GisQueryService.class);

	/**
	 * 接收对象
	 */
	private static ObjectMapper OBJECT_MAP = new ObjectMapper();
	
	private static final int NUMBER_2500 = 2500;

	/**
	 * GIS的接口
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午10:27:37
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> postGisInterface(String gisInterface, Map<String, String> par) {
		Map<String, Object> returnMap = null;
		try {
			returnMap = OBJECT_MAP.readValue(sendPostHttp(gisInterface, par), Map.class);
		} catch (JsonParseException e) {
			LOGGER.error("GIS地址["+ gisInterface +"]匹配失败，原因："+e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOGGER.error("GIS地址["+ gisInterface +"]匹配失败，原因："+e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("GIS地址["+ gisInterface +"]匹配失败，原因："+e.getMessage(), e);
		}
		return returnMap;
	}
	
	/**
	 * @author 078774-foss-jeffery
	 * @param par
	 * @throws IOException
	 * @date 2012-12-21 下午5:48:24
	 */
	private static String sendPostHttp(String url, Map<String, String> par) throws IOException {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NUMBER_2500);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(NUMBER_2500);
		HttpMethod method = postMethod(url, par);
		httpClient.executeMethod(method);
		String response = method.getResponseBodyAsString();
		return response;
	}
	
	/**
	 * 调用GIS接口使用
	 * @param url
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String sendPostHttp(String url, String obj) throws IOException {
	  RequestEntity reqEntity = new StringRequestEntity(obj,"application/json","UTF-8");
	  PostMethod post = new PostMethod(url);
	  post.setRequestEntity(reqEntity);
	  post.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
	  new HttpClient().executeMethod(post);
	  return post.getResponseBodyAsString();
	}

	/**
	 * post请求方法
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static HttpMethod postMethod(String url, Map<String, String> pars) throws IOException {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair[] param = getPars(pars);
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}

	/**
	 * 获得参数的值
	 * 
	 * @author 078774-foss-jeffery
	 * @date 2012-12-21 下午5:46:24
	 */
	private static NameValuePair[] getPars(Map<String, String> pars) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> set = pars.entrySet();
		for (Entry<String, String> entry : set) {
			NameValuePair value = new NameValuePair("queryData." + entry.getKey(), entry.getValue());
			params.add(value);
		}
		NameValuePair[] param = new NameValuePair[params.size()];
		param = params.toArray(param);
		return param;
	}
	
	/**
	 * 
	 * 示例代码
	 * 
	 * @author 078774-foss-jeffery
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws JsonParseException
	 * @date 2012-11-3 上午11:38:14
	 */
	public static void main(String[] args) throws JsonParseException, FileNotFoundException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", "13524304157");
		map.put("province", "上海省");
		map.put("city", "上海市");
		map.put("county", "青浦区");
		map.put("transportway", "motor");
		map.put("matchtypes", "pickup");
		map.put("otherAddress", "明珠路1018号");
		map.put("tel", "234234");
		Map<String, Object> jsonStr = postGisInterface(GisConstants.ARRIVE_DEPT_MATCH, map);
		LOGGER.info("$$$$$$$$$$$$$$$$"+jsonStr);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> depts=((List<Map<String, String>>) jsonStr.get("depts"));
		for (Map<String, String> m : depts) {
		    LOGGER.info("**************"+m.get("deptNo"));
		}
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("phone", "13524304157");
		map2.put("province", "四川省");
		map2.put("city", "绵阳市");
		map2.put("county", "安县");
		map2.put("transportway", "motor");
		map2.put("matchtypes", "pickup");
		map2.put("otherAddress", "花荄镇中心路");
		map2.put("tel", "234234");
		Map<String, Object> jsonStr2 = postGisInterface(GisConstants.ADDRESS_REMARK_SEARCH, map2);
		LOGGER.info("=================================");
		LOGGER.info("$$$$$$$$$$$$$$$$"+jsonStr2);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> depts2=((List<Map<String, String>>) jsonStr.get("depts"));
		for (Map<String, String> m : depts2) {
			LOGGER.info("**************"+m.get("deptNo"));
		}
	}
}