package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrToQmsErrorService;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsErrResponseParam;
import com.deppon.foss.module.transfer.common.api.shared.domain.QmsNolableRequestParam;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseFromQmsDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.RequestParamEntity;

public class TfrToQmsErrorService implements ITfrToQmsErrorService {
	private static Logger LOGGER = LogManager.getLogger(TfrToQmsErrorService.class);
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:07:03
	 * @function 用于上传卸车多货/分批配载
	 * @param request
	 * @return
	 */
	@Override
	public QmsErrResponseParam reportQmsError(QmsErrRequestParam request,String esbcode) {
		LOGGER.error("----FOSS上传QMS差错开始----");
		
		//TODO esb编码
	//	String code = "ESB_FOSS2ESB_PUSH_FORM_TRAJECTORY2DOP";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbcode;
		//String url = "http://10.224.197.121:8080/dop/webservice/dop/bookPushService/pushOrder";
		//String url = "http://10.224.71.18:8081/qms/ws/notagmg/report";
		PostMethod  postMethod=new PostMethod(url);
		
		QmsErrResponseParam response = new QmsErrResponseParam();
		
		String requestStr = JSONObject.toJSONString(request);
		
		LOGGER.error("----FOSS上传QMS差错{" + url + "}，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			
			httpClient.executeMethod(postMethod);
			
			String responseStr = postMethod.getResponseBodyAsString();
			
			LOGGER.error("----FOSS上传QMS差错，返回参数：" + responseStr);
			
			if(responseStr.contains("exceptionCode") && responseStr.contains("S000099")){
				return null;
			}
			response = JSONObject.parseObject(responseStr, QmsErrResponseParam.class);
		} catch (HttpException e) {
			LOGGER.error("----FOSS上传QMS差错异常，e：" + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("----FOSS上传QMS差错异常，e：" + e.getMessage());
		}
		LOGGER.error("----FOSS上传QMS差错结束-----");
		return response;	
	
	}
	
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:25:25
	 * @function 上报无标签多货
	 * @param request
	 * @return
	 */
	@Override
	public ResponseFromQmsDto reportQmsNolabel(QmsNolableRequestParam request) {
		LOGGER.error("----FOSS上传无标签多货差错开始----");
		
		//TODO esb编码
		String code = "ESB_FOSS2ESB_REPORT_NO_MORE2QMS";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		//String url = "http://10.224.197.121:8080/dop/webservice/dop/bookPushService/pushOrder";
		//url = "http://10.224.72.19:8081/qms/ws/notagmg/report?_wadl";
		PostMethod  postMethod=new PostMethod(url);
		
		ResponseFromQmsDto response = new ResponseFromQmsDto();
		
		String requestStr = JSONObject.toJSONString(request);
		
		LOGGER.error("----FOSS上传无标签多货差错异常，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			
			httpClient.executeMethod(postMethod);
			
			String responseStr = postMethod.getResponseBodyAsString();
			
			LOGGER.error("----FOSS上传无标签多货差错异常，返回参数：" + responseStr);
			
			if(responseStr.contains("exceptionCode") && responseStr.contains("S000099")){
				return null;
			}
			response = JSONObject.parseObject(responseStr, ResponseFromQmsDto.class);
		} catch (HttpException e) {
			LOGGER.error("----FOSS上传无标签多货差错异常，传入参数：" + requestStr);
		} catch (IOException e) {
			LOGGER.error("----FOSS上传无标签多货差错异常，传入参数：" + requestStr);
		}
		LOGGER.error("----FOSS上传无标签多货差错结束，传入参数：" + requestStr);
		return response;
	}
	
	/**
	 * 将超重超方的信息传输给QMS
	 * @param rpEntity
	 * @return
	 */
	@Override
	public String reportQmsOverWeight(RequestParamEntity rpEntity) {
		LOGGER.error("----FOSS上传超方超重到QMS开始----");
		
		//TODO esb编码
		String code = "ESB_FOSS2ESB_ERROR_AUTO_REPORT_FOSS";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		//String url = "http://10.224.112.114:8081/qms/ws/error/autoReportDeal";
		PostMethod  postMethod=new PostMethod(url);
		
		//ResponseFromQmsOverWeightDto response = new ResponseFromQmsOverWeightDto();
		String responseStr="";
		String requestStr = JSONObject.toJSONString(rpEntity);
		
		LOGGER.error("----FOSS上传超方超重，传入参数：" + requestStr);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			
			httpClient.executeMethod(postMethod);
			
			 responseStr = postMethod.getResponseBodyAsString();
			
			LOGGER.error("----FOSS上传超方超重，返回参数：" + responseStr);
			
			if(responseStr.contains("exceptionCode") && responseStr.contains("S000099")){
				return null;
			}
			//response = JSONObject.parseObject(responseStr, ResponseFromQmsOverWeightDto.class);
		} catch (HttpException e) {
			LOGGER.error("----FOSS上传超方超重异常，传入参数：" + requestStr);
		} catch (IOException e) {
			LOGGER.error("----FOSS上传超方超重异常，传入参数：" + requestStr);
		}
		LOGGER.error("----FOSS上传超方超重，传入参数：" + requestStr);
		return responseStr;
	}
	
}
