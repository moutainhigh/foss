package com.deppon.foss.module.transfer.oa.server.ws;

import java.util.Date;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**@description FOSS数据同步至QMS  FOSS作为客户端时接口
 * @author zhangdandan
 * @function FOSS上报丢货信息给QMS的调用方类
 */
public class QMSToFOSSResponseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QMSToFOSSResponseService.class);
	
		//读取超时设置
		private static final int timeOut = 1000*130;
		
		private static QMSToFOSSResponseService instatce;
		
		public static QMSToFOSSResponseService getInstatce() {
			if(instatce==null){
				instatce = new QMSToFOSSResponseService();
			}
			return instatce;
		}
		/**
		 * @Description: 上报丢货预警数据到QMS
		 * @date 2016-11-26 下午3:06:04   
		 * @author 263072 
		 * @param LostReportEntity
		 * @return
		 */
		public String reportWarningData(String requestStr ){
			String requestJsonStr="";
			HttpClient httpClient = new HttpClient();  
			//String url="http://192.168.67.12:8180/esb2/rs/ESB_FOSS2ESB_AUTOMATICLOST_DATA";
			String url = PropertiesUtil.getKeyValue("esb.rs") + "/ESB_FOSS2ESB_AUTOMATICLOST_DATA";
			//String url="http://10.224.66.16:8081/qms/ws/lostgoods/syncSysAutoLostGoodsReportData";
			LOGGER.info("零担丢货上报给qms开始");
		    PostMethod postMethod = new PostMethod(url);  
		    Date startDate = new Date();
		    try {  
		    	LOGGER.error("***************调用接口，同步信息至QMS 请求数据json:"+requestStr+"*************");
				//设置报文信息
		    	httpClient.getParams().setContentCharset("UTF-8");
				RequestEntity reqEntity = new StringRequestEntity(requestStr,"application/json","UTF-8");
				postMethod.setRequestEntity(reqEntity);
				postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				// 设置连接超时和读取超时
			    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			    httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		        int status = httpClient.executeMethod(postMethod);  
		        if(status==200){
		        	requestJsonStr = postMethod.getResponseBodyAsString();
		        	LOGGER.info("零担丢货上报qms连接成功，传输数据成功"+requestJsonStr+"*************");
		        }else{
		        	LOGGER.info("零担丢货上报qms连接失败，传输数据失败"+requestJsonStr+"*************");
		        	requestJsonStr = "请求"+url+"传输数据异常：status="+status;
		        }
		    } catch (Exception e) {
		    	LOGGER.info("零担丢货上报qms请求，上报丢货异常"+requestJsonStr+"*************");
		    	requestJsonStr = "请求"+url+"上报丢货异常，开始时间："+startDate+"，结束时间"+new Date()+ExceptionUtils.getFullStackTrace(e);
		    	LOGGER.error(requestJsonStr);
		    } finally {
		    	//释放连接
		    	postMethod.releaseConnection();
		    }
		    return  requestJsonStr;
		}
		
		 
}
