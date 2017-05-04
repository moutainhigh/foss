package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpPushService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoResponseDto;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;

public class WaybillHomeImpPushService implements IWaybillHomeImpPushService {
	protected final static Logger LOGGER = LoggerFactory.getLogger(WaybillHomeImpPushService.class.getName());

	@Override
	public WaybillHomeInfoResponseDto pushWaybillHomeInfo(
			WaybillHomeInfoDto requestDto) {

		
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		//esb编码
		String url = PropertiesUtil.getKeyValue("dop.home.address");
		
	    // String url = "http://10.224.66.26:8580/jzws/webservice/rest/waybillInfoService/jzWayBillInfo";
		LOGGER.error("----FOSS推送家装运单信息给DOP，访问ESB接口地址：" + url);
		PostMethod  postMethod=new PostMethod(url);
		WaybillHomeInfoResponseDto responseDto = new WaybillHomeInfoResponseDto();
		String requestStr = JSONObject.toJSONString(requestDto);
		LOGGER.error("----FOSS推送家装运单信息给DOP，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----FOSS推送家装运单信息给DOP，返回参数：" + responseStr);
			if(responseStr.contains("exceptionCode")&&responseStr.contains("S000099")){
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr, WaybillHomeInfoResponseDto.class);
			return responseDto;
			
		} catch (HttpException e) {
			e.printStackTrace();
			responseDto.setId(requestDto.getId());
			responseDto.setMailNo(requestDto.getMailNo());
			responseDto.setErrorMessage("HTTP数据发送异常");
			responseDto.setResultCode("0");
			return responseDto;
		} catch (IOException e) {
			e.printStackTrace();
			responseDto.setId(requestDto.getId());
			responseDto.setMailNo(requestDto.getMailNo());
			responseDto.setErrorMessage("输入输出IO异常");
			responseDto.setResultCode("0");
			return responseDto;
		}			
	
	}

}
