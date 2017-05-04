package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillRegionImpPushService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.WaybillRegionInfoDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.WaybillRegionInfoResponseDto;
import com.deppon.foss.module.pickup.pricing.api.shared.util.PropertiesUtil;

/**
 * 
 * FOSS同步到PTP的类
 * 
 * @author 265475	 DP-Foss-zoushengli
 * 
 * @date 2016-1-18 下午11:35:08
 * 
 */
public class WaybillRegionImpPushService implements IWaybillRegionImpPushService{

	private static final int NUMBER_5000 = 5000;
	private static final int NUMBER_15000 = 15000;

	protected final static Logger LOGGER = LoggerFactory.getLogger(WaybillRegionImpPushService.class.getName());

	//FOSS推送价格出发区域信息给DTD
	@Override
	public String pushWaybillHomeInfo(
			WaybillRegionInfoDto requestDto) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		//esb编码
		String url = PropertiesUtil.getKeyValue("dop.region.address");
		LOGGER.error("----FOSS推送价格出发区域信息给DTD，访问ESB接口地址：" + url);
		PostMethod  postMethod=new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUMBER_15000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NUMBER_5000);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setHttpElementCharset("UTF-8");
	    
		WaybillRegionInfoResponseDto responseDto = new WaybillRegionInfoResponseDto();
		String requestStr = JSONObject.toJSONString(requestDto);
		LOGGER.error("----FOSS推送价格出发区域信息给DTD，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String responseStr = null;
		try {
			httpClient.executeMethod(postMethod);
			responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----FOSS推送价格出发区域信息给DTD，返回参数：" + responseStr);
			return responseStr;
			
		} catch (HttpException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("HTTP数据发送异常");
			responseDto.setResultCode("0");
			return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("输入输出IO异常");
			responseDto.setResultCode("0");
			return responseStr;
		}			
	
	}

	//FOSS推送价格到达区域信息给DTD
	@Override
	public String pushArriveWaybillHomeInfo(WaybillRegionInfoDto requestDto) {
	
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		//esb编码
		String url = PropertiesUtil.getKeyValue("dop.arriverRegion.address");
		LOGGER.error("----FOSS推送价格到达区域信息给DTD，访问ESB接口地址：" + url);
		PostMethod  postMethod=new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUMBER_15000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NUMBER_5000);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setHttpElementCharset("UTF-8");
		WaybillRegionInfoResponseDto responseDto = new WaybillRegionInfoResponseDto();
		String requestStr = JSONObject.toJSONString(requestDto);
		LOGGER.error("----FOSS推送价格到达区域信息给DTD，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String responseStr = null;
		try {
			httpClient.executeMethod(postMethod);
			responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----FOSS推送价格到达区域信息给DTD，返回参数：" + responseStr);
			return responseStr;
			
		} catch (HttpException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("HTTP数据发送异常");
			responseDto.setResultCode("0");
			return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("输入输出IO异常");
			responseDto.setResultCode("0");
			return responseStr;
		}	
	}
	
	//FOSS推送产品定义信息给DTD
	@Override
	public String pushProductWaybillHomeInfo(WaybillRegionInfoDto requestDto,String url) {
	
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		LOGGER.error("----FOSS推送产品定义信息给DTD，访问ESB接口地址：" + url);
		PostMethod  postMethod=new PostMethod(url);
		
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUMBER_15000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NUMBER_5000);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setHttpElementCharset("UTF-8");
		WaybillRegionInfoResponseDto responseDto = new WaybillRegionInfoResponseDto();
		String requestStr = JSONObject.toJSONString(requestDto);
		LOGGER.error("----FOSS推送产品定义信息给DTD，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String responseStr = null;
		try {
			httpClient.executeMethod(postMethod);
			responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----FOSS推送价产品定义信息给DTD，返回参数：" + responseStr);
			return responseStr;
			
		} catch (HttpException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("HTTP数据发送异常");
			responseDto.setResultCode("0");
			return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
			responseDto.setErrorMessage("输入输出IO异常");
			responseDto.setResultCode("0");
			return responseStr;
		}	
	}




}
