package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;



public class FossToDopService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FossToDopService.class);
	
	//读取超时设置
	private static final int timeOut = 1000*130;
	
	private static FossToDopService instatce;
	
	public static FossToDopService getInstatce() {
		if(instatce==null){
			instatce = new FossToDopService();
		}
		return instatce;
	}

	/**
	 * @Description: 审核德邦家装送装签收信息
	 * @date 2015-12-15 下午3:06:04   
	 * @author 269701 
	 * @param list
	 * @return
	 */
	public String checkResultData(String requestStr){
		LOGGER.info("推送--德邦家装送装签收信息--审核结果给DOP--");
		String responseStr = "";
		HttpClient httpClient = new HttpClient();  
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/ESB_DOP2ESB_TO_JZ_SIGNAUDIT_INFO";
		//本地地址 
		// http://10.224.66.135:8080/jz-ws/webservice/rest/signAuditService/sendSignAuditResult
	  //String url="http://10.224.66.135:8080/jz-ws/webservice/rest/signAuditService/sendSignAuditResult";
		PostMethod postMethod = new PostMethod(url);  
	    Date startDate = new Date();
	    try {  
	    	httpClient.getParams().setContentCharset("UTF-8");
	    	postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			RequestEntity reqEntity = new StringRequestEntity(requestStr,"application/json","UTF-8");
			postMethod.setRequestEntity(reqEntity);
			
			// 设置连接超时和读取超时
		    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		    httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			
	        int status = httpClient.executeMethod(postMethod);  
	        if(status==ConstantsNumberSonar.SONAR_NUMBER_200){
	        	responseStr = postMethod.getResponseBodyAsString();
	        }else{
	        	responseStr = "请求"+url+"传输数据异常：status="+status;
	        }
	    } catch (Exception e) {  
	    	responseStr = "请求"+url+"反写德邦家装送装签收信息失败，开始时间："+startDate+"，结束时间"+new Date()
	    			+ExceptionUtils.getFullStackTrace(e);
	    	LOGGER.error(responseStr);
	    } finally {
	    	//释放连接
	    	postMethod.releaseConnection();
	    }
	    return responseStr;
	}	
}
