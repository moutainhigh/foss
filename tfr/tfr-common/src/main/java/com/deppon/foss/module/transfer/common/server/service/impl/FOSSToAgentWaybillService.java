package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToAgentWaybillService;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillResponseDto;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;

public class FOSSToAgentWaybillService implements IFOSSToAgentWaybillService {

	/**
	 * @author nly
	 * @date 2015年2月4日 上午8:11:51
	 * @function 推送代理单号给快递100
	 * @param dto
	 * @return
	 */
	@Override
	public AgentWaybillResponseDto pushAgentWaybillNo(AgentWaybillNoRequestDto dto) {
		//esb服务编码待确定
		String code = "ESB_FOSS2ESB_PUSH_PROXYNUM2DOP";
		HttpClient httpClient = new HttpClient();
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		/*	System.out.println("ESB的地址为：  "+url);
		url = "http://192.168.67.12:8180/esb2/rs";
		url = url + "/" + code;*/
		PostMethod  postMethod=new PostMethod(url);
		
		AgentWaybillResponseDto responseDto = new AgentWaybillResponseDto();
		String requestStr = JSONObject.toJSONString(dto);
		//NameValuePair[] param = {new NameValuePair("param", requestStr)};
		postMethod.addRequestHeader("Content-type","application/json; charset=utf-8");  
	   RequestEntity requestEntity = new StringRequestEntity(requestStr);
	     postMethod.setRequestEntity(requestEntity);
		//postMethod.setRequestBody(param);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			if(responseStr.contains("exceptionCode")&&
					responseStr.contains("S000099")){
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr, AgentWaybillResponseDto.class);
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
	
	/**
	 * @author nly
	 * @date 2015年2月4日 上午8:16:20
	 * @function 推送运单轨迹给快递100
	 * @return
	 */
	@Override
	public AgentWaybillResponseDto pushWaybillTrack() {
		// TODO Auto-generated method stub
		return null;
	}

}
