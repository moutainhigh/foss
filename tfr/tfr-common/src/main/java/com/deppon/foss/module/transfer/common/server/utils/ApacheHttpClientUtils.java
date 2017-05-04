/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.common.server.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.util.DateUtils;

/**
 *<pre>
 *功能:restful 基于http请求的工具类
 *作者：132028
 *日期：2016年12月19日上午10:49:48
 *</pre>
 */
public class ApacheHttpClientUtils {
	/***
	 * 
	 *<pre>
	 * 方法体说明：执行HTTP Post请求并获取返回值
	 * 作者：132028
	 * 日期： 2016年12月23日 下午4:34:58
	 * @param url
	 * @param requestJson
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *</pre>
	 */
	public static VestResponse excutePost(String url,String requestJson){
		//获取httpClient
		HttpClient httpClient = new HttpClient();
        //json工具类
        ObjectMapper objectMapper = null;
        objectMapper = obtainJSONObjectMapper();
		try{
			//设置请求路径
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
			//设置Post参数
			RequestEntity requestEntity = new StringRequestEntity(requestJson);
			//设置Content
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseBody = postMethod.getResponseBodyAsString();
			return objectMapper.readValue(responseBody, VestResponse.class);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
    /**
     * 设置 json 数据格式
     *
     * @author ibm-liuzhaowei
     * @date 2013-08-01 上午9:21:30
     */
    public static ObjectMapper obtainJSONObjectMapper() {
        // 获取objectMapper
        ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
        // 设置时间转换格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        // 设置到objectMapper
        objectMapper.setDateFormat(dateFormat);
        return objectMapper;
    }
}
