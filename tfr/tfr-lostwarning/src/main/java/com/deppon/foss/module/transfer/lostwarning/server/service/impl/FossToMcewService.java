package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;

public class FossToMcewService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FossToMcewService.class);
	
	//读取超时设置
	private static final int timeOut = 1000*130;
	
	private static FossToMcewService instatce;
	
	public static FossToMcewService getInstatce() {
		if(instatce==null){
			instatce = new FossToMcewService();
		}
		return instatce;
	}

	/**
	 * @Description: 上报丢货预警数据
	 * @date 2015-7-10 下午3:06:04   
	 * @author 263072 
	 * @param list
	 * @return
	 */
	public String reportWarningData(String requestStr){
		String responseStr = "";
		HttpClient httpClient = new HttpClient();  
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/ESB_FOSS2ESB_AUTOMATICLOST_DATA";
//		String url="http://192.168.10.146:8081/qms/ws/lostgoods/syncSysAutoLostGoodsReportData";
		LOGGER.info("丢货预警上报qms开始");
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
	        if(status==LostWarningConstant.SONAR_NUMBER_200){
	        	LOGGER.info("丢货预警上报qms连接成功，传输数据成功");
	        	responseStr = postMethod.getResponseBodyAsString();
	        }else{
	        	LOGGER.info("丢货预警上报qms连接失败，传输数据失败");
	        	responseStr = "请求"+url+"传输数据异常：status="+status;
	        }
	    } catch (Exception e) {
	    	LOGGER.info("丢货预警上报qms请求，上报丢货异常");
	    	responseStr = "请求"+url+"上报丢货异常，开始时间："+startDate+"，结束时间"+new Date()
	    			+ExceptionUtils.getFullStackTrace(e);
	    	LOGGER.error(responseStr);
	    } finally {
	    	//释放连接
	    	postMethod.releaseConnection();
	    }
	    return responseStr;
	}
	
	/**
	 * @Description: 上报丢货找到信息
	 * @date 2015-8-8 下午3:00:07   
	 * @author 263072 
	 * @param requestStr
	 * @return
	 */
	public String reportFindGoodsData(String requestStr){
		String responseStr = "";
		LOGGER.info("丢货上报找到传送的数据信息 ：" + requestStr);
		HttpClient httpClient = new HttpClient();  
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/ESB_FOSS2ESB_LOSTHANDLE_DATA";
//		String url="http://192.168.10.146:8081/qms/ws/lostgoods/syncSysAutoLostGoodsReportDealData";
	    PostMethod postMethod = new PostMethod(url);  
	    try {  
	    	
			RequestEntity reqEntity = new StringRequestEntity(requestStr,"application/json","UTF-8");
			postMethod.setRequestEntity(reqEntity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			
			// 设置连接超时和读取超时
		    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		    httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			
	        int status = httpClient.executeMethod(postMethod);  
	        if(status==LostWarningConstant.SONAR_NUMBER_200){
	        	responseStr = postMethod.getResponseBodyAsString();
	        }else{
	        	responseStr = "请求"+url+"丢货找到接口传输数据异常：status="+status;
	        }
	    } catch (Exception e) {  
	    	responseStr = "请求"+url+"丢货找到接口异常："+ExceptionUtils.getFullStackTrace(e);
	    	LOGGER.error(responseStr);
	    } finally {
	    	//释放连接
	    	postMethod.releaseConnection();
	    }
	    return responseStr;
	}
	
	/**
	 * @Description: 根据运单号查询已上报货物
	 * @date 2015-9-14 下午2:36:08   
	 * @author 263072 
	 * @param wayBillNum
	 * @return
	 */
	public static String queryReportGoodsByBill(String wayBillNum){
		String responseStr = "ERROR";
		HttpClient httpClient = new HttpClient();  
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/ESB_FOSS2ESB__AWBNOTFIND_SERIAL2QMS";
	
//		String url="http://192.168.10.146:8081/qms/ws/query/queryNoFindFlowCodesByWaybillNum";
		PostMethod postMethod = new PostMethod(url);  
	    try {  
			RequestEntity reqEntity = new StringRequestEntity(wayBillNum,"application/json","UTF-8");
			postMethod.setRequestEntity(reqEntity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			
			// 设置连接超时和读取超时
		    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
		    httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			
	        int status = httpClient.executeMethod(postMethod);  
	        if(status==LostWarningConstant.SONAR_NUMBER_200){
	        	responseStr = postMethod.getResponseBodyAsString();
	        }else{
	        	responseStr = "请求"+url+"查询已上报货物接口传输数据异常：status="+status;
	        }
	    } catch (Exception e) {  
	    	responseStr = "请求"+url+"查询已上报货物接口异常："+ExceptionUtils.getFullStackTrace(e);
	    	LOGGER.error(responseStr);
	    } finally {
	    	//释放连接
	    	postMethod.releaseConnection();
	    }
	    return responseStr;
	}
	
}
