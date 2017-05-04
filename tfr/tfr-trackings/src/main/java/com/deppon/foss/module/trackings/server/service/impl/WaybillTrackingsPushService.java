package com.deppon.foss.module.trackings.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsPushService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.dto.ResponseDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsRequestDto;

public class WaybillTrackingsPushService implements IWaybillTrackingsPushService{
	
	private static Logger LOGGER = LogManager.getLogger(WaybillTrackingsPushService.class);
	/**
	 * @author nly
	 * @date 2015年4月1日 下午5:08:14
	 * @function 推送轨迹给快递100
	 * @param requestDto
	 * @return
	 */
	@Override
	public ResponseDto pushWaybillTrack(WaybillTrackingsRequestDto requestDto) {
		//esb编码
		String code = "ESB_FOSS2ESB_PUSH_FORM_TRAJECTORY2DOP";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
//		String url = "http://10.224.197.121:8080/dop/webservice/dop/bookPushService/pushOrder";
		PostMethod  postMethod=new PostMethod(url);
		
		ResponseDto responseDto = new ResponseDto();
		
		String requestStr = JSONObject.toJSONString(requestDto);
		String msgId = requestDto.getMsgId();
		String[] str = msgId.split(":");
		String[] str1 = str[1].split(",");
		if(StringUtils.equals(WaybillTrackingsConstants.WATCH_STATUS_ABORT, requestDto.getWatchStatus())
				|| (StringUtils.equals(WaybillTrackingsConstants.WATCH_STATUS_STOP, requestDto.getWatchStatus())
						&& str1.length < 2)) {
			JSONObject o = new JSONObject();
			o.put("msgId", msgId);
			o.put("watchStatus", requestDto.getWatchStatus());
			o.put("company", requestDto.getCompany());
			o.put("code", requestDto.getCode());
			o.put("callback", requestDto.getCallback());
			o.put("reasonCode", requestDto.getCallback());
			o.put("reasonMessage", requestDto.getCallback());

			requestStr = o.toJSONString();
		} 
		//NameValuePair[] param = {new NameValuePair("param", requestStr)};
		LOGGER.error("----FOSS推送轨迹给快递100，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
//		postMethod.setRequestBody(param);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----FOSS推送轨迹给快递100，返回参数：" + responseStr);
			if(responseStr.contains("exceptionCode")&&
					responseStr.contains("S000099")){
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr, ResponseDto.class);
			return responseDto;
			
		} catch (HttpException e) {
			e.printStackTrace();
			responseDto.setResult("false");
			responseDto.setReturnCode("404");
			responseDto.setMessage(e.getMessage());
			return responseDto;
		} catch (IOException e) {
			e.printStackTrace();
			responseDto.setResult("false");
			responseDto.setReturnCode("404");
			responseDto.setMessage(e.getMessage());
			return responseDto;
		}			
	}
	
}
