package com.deppon.foss.module.pickup.deliver.server.utils;

/**
 * HttpClient post请求  仅支持JSON数据格式传输
 * @author 
 *		foss-dongyangyang
 * @date 
 *      2016-12-19 下午10:37:11
 * @since
 * @version
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {
	private static final Logger LOGGER;
	private static final String POST_PARMS_ERROR;
	private static final String POST_URL_ERROR;
	private static final String POST_CONNECTION_ERROR;
	private static final String REQUEST_JSON;
	private static final String RESULT_JSON;
	private final static String APPLICATION_JSON;
	private final static String CODE_FORMAT;
	private final static String CONTENT_TYPE;
	private final static String REQUEST_HEADER;
	private static final int HTTP_CONNECTIONTIMEOUT;
	private static final int HTTP_SOTIMEOUT;
	private static final String REQUEST_URL_HEAD;
	static{
		LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
		POST_PARMS_ERROR = "发送Post请求实体参数为空" ;
		POST_URL_ERROR = "发送Post请求路径参数为空";
		REQUEST_JSON = "请求的Json信息：";
		RESULT_JSON = "响应的Json信息：";
		APPLICATION_JSON = "application/json";
		CODE_FORMAT = "UTF-8";
		CONTENT_TYPE = "Content-Type";
		REQUEST_HEADER = "application/json;charset=UTF-8";
		HTTP_SOTIMEOUT = 2999;
		HTTP_CONNECTIONTIMEOUT = 15000;
		POST_CONNECTION_ERROR = "发送Post请求异常,异常信息为：";
		REQUEST_URL_HEAD = "本次请求服务路径信息：";
	}
	/**
	 * 
	 * @param requestDto 请求的对象
	 * @param responseDto 要响应被序列化的对象
	 * @param url post请求路径
	 * @return Object resultDto
	 */
	public static Object postMethod(Object requestDto,Object responseDto,String url){
		if(requestDto == null){
			throw new HttpCilentUtilsException(POST_PARMS_ERROR);
		}
		if(StringUtils.isBlank(url)){
			throw new HttpCilentUtilsException(POST_URL_ERROR);
		}
		PostMethod post = null;
		try {
			String requestJson = JSONObject.toJSONString(requestDto);
			LOGGER.info( REQUEST_URL_HEAD + url);
			LOGGER.info(REQUEST_JSON + requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson, APPLICATION_JSON, CODE_FORMAT);
			post = new PostMethod(url);
			post.setRequestEntity(reqEntity);
			post.addRequestHeader(CONTENT_TYPE, REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(HTTP_CONNECTIONTIMEOUT);
			params.setSoTimeout(HTTP_SOTIMEOUT);
			httpClient.executeMethod(post);
//			String resultJson = post.getResponseBodyAsString();
			//post.getResponseBodyAsString()方法非常容易出现警告，影响远程接口响应  modify by 353654
			InputStream inputStream = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,CODE_FORMAT));
 			StringBuffer sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				LOGGER.info("读取响应数据失败！" + e.getMessage());
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resultJson = sbf.toString();
			LOGGER.info(RESULT_JSON + resultJson);
			Object resultDto = JSONObject.parseObject(resultJson, responseDto.getClass());
			return resultDto;
		} catch (Exception e) {
			LOGGER.error( POST_CONNECTION_ERROR + e.getMessage());
			throw new HttpCilentUtilsException(POST_CONNECTION_ERROR + e.getMessage());
		} finally{
			if(post != null){
				post.releaseConnection();
			}
		}
	}
	
	static class HttpCilentUtilsException extends RuntimeException{
		
		private static final long serialVersionUID = 1L;

		public HttpCilentUtilsException() {
			super();
		}

		public HttpCilentUtilsException(String paramString,
				Throwable paramThrowable) {
			super(paramString, paramThrowable);
		}

		public HttpCilentUtilsException(String paramString) {
			super(paramString);
		}

		public HttpCilentUtilsException(Throwable paramThrowable) {
			super(paramThrowable);
		}
	}
}
