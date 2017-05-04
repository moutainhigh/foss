package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OPPNeedAirWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.util.DateUtils;
/**
 * 
* @description FOSS数据同步至OPP  FOSS作为客户端时接口
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午9:51:40
 */
public class FossToOppService {

	//logger
		private static final Logger LOGGER = LoggerFactory.getLogger(FossToOppService.class);
		
		//读取超时设置
		private static final int timeOut = 1000*60;
		
		private static FossToOppService instatce;
		
		public static FossToOppService getInstatce() {
			if(instatce==null){
				instatce = new FossToOppService();
			}
			return instatce;
		}
		/**
		 * @Description: FOSS同步合大票清单信息至OPP
		 * @date 2016-04-05 下午3:06:04   
		 * @author 269701 
		 * @param String requestStr
		 * @throws IOException 
		 */
		 public void syncAirPickUpToOPP(AirPickUpInfoRequest airPickUpInfoParam) throws Exception{
		  
			String responseStr = "";
			HttpClient httpClient = new HttpClient();  
			//设置esbCode
			String url = PropertiesUtil.getKeyValue("esb.opp.airPickUpFromFoss");
			//本地地址
			//String url ="http://10.224.69.21:8081/opp-web/opprestService/airPickUpFromFoss";
			//OPP测试地址
			//String url ="http://10.224.173.116:8180/opp-web/opprestService/airPickUpFromFoss";
			
			PostMethod postMethod = new PostMethod(url);  
			Date startDate = new Date();
			//json工具类
			ObjectMapper objectMapper =	null;
			objectMapper = obtainJSONObjectMapper();
		
			try{
				//请求数据 json格式
				String requestJsonStr = objectMapper.writeValueAsString(airPickUpInfoParam);
				LOGGER.error("***************调用接口，同步合大票清单信息至OPP 请求数据json:"+requestJsonStr+"*************");

				//设置报文信息
				httpClient.getParams().setContentCharset("UTF-8");
				postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
				postMethod.setRequestEntity(reqEntity);
				
				// 设置连接超时和读取超时
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
				//推送数据json格式：{"airPickUpNo":"airPickUpNo（清单号）",**********}
				int status = httpClient.executeMethod(postMethod); 
				if(status==TransferConstants.SONAR_NUMBER_200){
					responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
					LOGGER.error("***************同步合大票清单信息至OPP 返回数据json:"+responseStr+"*************");
					/**
					 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
					 */
					if(responseStr.contains("exceptionCode")&&
							responseStr.contains("S000099")){
						LOGGER.error("FOSS同步合大票清单信息至OPP Esb报错返回'S000099'和'exceptionCode'");
						return ;
					}
					
				}else{
					responseStr = "请求"+url+"传输数据异常：status="+status;
					//将异常数据保存在FOSS
					LOGGER.error("***************调用接口，同步合大票清单信息至OPP 异常结束*********"+responseStr+"*************");
					throw new Exception("同步合大票清单信息至OPP 异常结束:" + responseStr);
				}
			}catch(Exception e){
				//推送合大票清单信息异常
					responseStr = "请求"+url+"FOSS同步合大票清单信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
						+ExceptionUtils.getFullStackTrace(e);				
					LOGGER.error("***************调用接口，同步合大票清单信息至OPP 异常结束*****异常信息:"+responseStr+"*************");
					LOGGER.error(responseStr);
					throw new Exception("同步合大票清单信息至OPP 异常结束:" + e);
			}finally{
				//释放连接
				postMethod.releaseConnection();
			}
			 
		 }
		  
		  /**
			 * @Description: FOSS同步合大票清单明细信息至OPP
			 * @date 2016-04-05 下午3:06:04   
			 * @author 269701 
			 * @param String requestStr
		 * @throws IOException 
			 */
		  public void syncAirPickUpDetialToOPP(AirPickUpDetialRequest airPickUpDetialInfoParam) throws Exception{
			//返回
			String responseStr = "";
			HttpClient httpClient = new HttpClient();  
			//设置esbCode
			String url = PropertiesUtil.getKeyValue("esb.opp.airPickUpDetialFromFoss");
			//本地地址
			//String url ="http://10.224.69.21:8081/opp-web/opprestService/airPickUpDetialFromFoss";
			//opp测试地址
			//String url ="http://10.224.173.116:8180/opp-web/opprestService/airPickUpDetialFromFoss";

			PostMethod postMethod = new PostMethod(url);  
			Date startDate = new Date();
			//json工具类
			ObjectMapper objectMapper =	null;
			objectMapper = obtainJSONObjectMapper();
			try{
				//请求数据 json格式
				String 	requestJsonStr = objectMapper.writeValueAsString(airPickUpDetialInfoParam);
				LOGGER.error("***************调用接口，同步合大票清单明细信息至OPP 请求数据json："+requestJsonStr);

				//设置报文信息
				httpClient.getParams().setContentCharset("UTF-8");
				postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
				postMethod.setRequestEntity(reqEntity);
				
				// 设置连接超时和读取超时
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
				//推送数据json格式：{"detialId":"airPickUpID","airPickUpNo":"airPickUpNo（清单号）"}
				int status = httpClient.executeMethod(postMethod);  
				if(status==TransferConstants.SONAR_NUMBER_200){
					responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
					LOGGER.error("***************同步合大票清单信息至OPP 返回数据json:"+responseStr+"*************");
					/**
					 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
					 */
					if(responseStr.contains("exceptionCode")&&
							responseStr.contains("S000099")){
						LOGGER.error("FOSS同步合大票清单明细信息至OPP Esb报错返回'S000099'和'exceptionCode'");
						return ;
					}
				}else{
					responseStr = "请求"+url+"传输数据异常：status="+status;
					//将异常数据保存在FOSS
					LOGGER.error("***************调用接口，同步合大票清单明细信息至OPP 异常结束******信息："+responseStr);
					throw new Exception("同步合大票清单明细信息至OPP 异常结束:" + responseStr);
				}
			
			}catch(Exception e){
				//推送合大票清单明细信息异常
					responseStr = "请求"+url+"FOSS同步合大票清单明细信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
						+ExceptionUtils.getFullStackTrace(e);				 
				LOGGER.error("同步合大票清单明细信息至OPP 异常信息:" + responseStr);
				LOGGER.error("***************调用接口，同步合大票清单明细信息至OPP 异常结束******信息："+responseStr);
				throw new Exception("同步合大票清单明细信息至OPP 异常结束:" + e);

			}finally{
				//释放连接
				postMethod.releaseConnection();
			}
	
		 }
		  /**
			 * @Description: FOSS同步合大票清单明细流水号信息至OPP
			 * @date 2016-04-05 下午3:06:04   
			 * @author 269701 
			 * @param String requestStr
		 * @throws IOException 
			 */
		  public void syncAirPickUpSerialToOPP(AirPickUpSerialRequest airPickUpSerialInfoParam) throws Exception{
		  
			String responseStr = "";
			HttpClient httpClient = new HttpClient();  
			//设置esbCode
			String url = PropertiesUtil.getKeyValue("esb.opp.airPickUpSerialFromFoss");
			//本地地址
			//String url ="http://10.224.69.21:8081/opp-web/opprestService/airPickUpSerialFromFoss";
			//opp测试地址
			//String url ="http://10.224.173.116:8180/opp-web/opprestService/airPickUpSerialFromFoss";

			PostMethod postMethod = new PostMethod(url);  
			Date startDate = new Date();
			//json工具类
			ObjectMapper objectMapper =	null;
			objectMapper = obtainJSONObjectMapper();
			try{
				//请求数据 json格式
				String requestJsonStr = objectMapper.writeValueAsString(airPickUpSerialInfoParam);
				LOGGER.error("***************调用接口，同步合大票清单明细流水至OPP 请求数据json："+requestJsonStr);

				//设置报文信息
				httpClient.getParams().setContentCharset("UTF-8");
				postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
				postMethod.setRequestEntity(reqEntity);
				
				// 设置连接超时和读取超时
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
				//推送数据json格式：{"operStatus":"operStatus","detialId":"detialId","airPickUpNo":"airPickUpNo（清单号）","requestEntity":{"serialNo":"","wayBillNo":"","serialId":""}}
				int status = httpClient.executeMethod(postMethod);  
				if(status==TransferConstants.SONAR_NUMBER_200){
					responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
					LOGGER.error("***************同步合大票清单明细流水信息至OPP 返回数据json:"+responseStr+"*************");
					/**
					 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
					 */
					if(responseStr.contains("exceptionCode")&&
							responseStr.contains("S000099")){
						LOGGER.error("FOSS同步合大票清单明细流水信息至OPP Esb报错返回'S000099'和'exceptionCode'");
						return ;
					}
				}else{
					responseStr = "请求"+url+"传输数据异常：status="+status;
				LOGGER.error("***************调用接口，同步合大票清单明细流水至OPP 异常结束："+responseStr);
				throw new Exception("同步合大票清单明细流水至OPP 异常结束:" + responseStr);
				}
			
			}catch(Exception e){
					//推送合大票清单信息
					responseStr = "请求"+url+"FOSS同步合大票清单明细流水号信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
					+ExceptionUtils.getFullStackTrace(e);				
					LOGGER.error("***************调用接口，同步合大票清单明细流水至OPP 异常结束："+responseStr);
				LOGGER.error("同步合大票清单明细流水至OPP 异常信息:" +responseStr);
				throw new Exception("同步合大票清单明细流水至OPP 异常结束:" + e);

			}finally{
				//释放连接
				postMethod.releaseConnection();
			}
		 }
		  
		  
		  /**
			 * @Description: FOSS同步航空正单信息至OPP
			 * @date 2016-04-05 下午3:06:04   
			 * @author 269701 
			 * @param String requestStr
		    * @throws IOException 
			 */
			 public void syncAirWaybillToOPP(OPPNeedAirWaybillEntity oppAirWaybillNoInfo) throws Exception{
			  
				String responseStr = "";
				HttpClient httpClient = new HttpClient();  
				//设置esbCode
				String url = PropertiesUtil.getKeyValue("esb.opp.airWaybillFromFoss");
				//本地地址
				//String url ="http://10.224.69.21:8081/opp-web/opprestService/airWaybillFromFoss";
				//opp测试地址
				//String url ="http://10.224.173.116:8180/opp-web/opprestService/airWaybillFromFoss";
				
				PostMethod postMethod = new PostMethod(url);  
				Date startDate = new Date();
				//json工具类
				ObjectMapper objectMapper =	null;
				objectMapper = obtainJSONObjectMapper();
			
				try{
					//请求数据 json格式
					//推送航空正单信息
					 String requestJsonStr = objectMapper.writeValueAsString(oppAirWaybillNoInfo);
						LOGGER.error("***************调用接口，同步航空正单信息至OPP 请求数据json信息："+requestJsonStr+"*****************");
					//设置报文信息
					httpClient.getParams().setContentCharset("UTF-8");
					postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
					RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
					postMethod.setRequestEntity(reqEntity);
					
					// 设置连接超时和读取超时
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
					//推送数据json格式：{"airPickUpNo":"airPickUpNo（清单号）",**********}
					int status = httpClient.executeMethod(postMethod);  
					if(status==TransferConstants.SONAR_NUMBER_200){
						responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
						LOGGER.error("***************同步航空正单信息至OPP 返回数据json:"+responseStr+"*************");
						/**
						 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
						 */
						if(responseStr.contains("exceptionCode")&&
								responseStr.contains("S000099")){
							LOGGER.error("FOSS同步航空正单信息至OPP Esb报错返回'S000099'和'exceptionCode'");
							return ;
						}
					}else{
						responseStr = "请求"+url+"传输数据异常：status="+status;
						LOGGER.error("***************调用接口，同步航空正单信息至OPP 异常结束*****异常信息："+responseStr+"*****************");
						throw new Exception("同步航空正单信息至OPP 异常结束:" + responseStr);
					}
				
				}catch(Exception e){
						//推送航空正单信息异常
						responseStr = "请求"+url+"FOSS同步航空正单信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
						+ExceptionUtils.getFullStackTrace(e);
						LOGGER.error("***************调用接口，FOSS同步航空正单信息至OPP 异常结束*****异常信息："+responseStr+"*****************");
					LOGGER.error("同步航空正单信息至OPP 异常信息:" +responseStr);
					throw new Exception("同步航空正单信息至OPP 异常结束:" + e);
				}finally{
					//释放连接
					postMethod.releaseConnection();
				}
				 
			 }
			  
			  /**
				 * @Description: FOSS同步航空正单明细信息至OPP
				 * @date 2016-04-05 下午3:06:04   
				 * @author 269701 
				 * @param String requestStr
				 * @throws IOException 
				 */
			  public void syncAirWaybillDetialToOPP(AirWaybillDetialRequest  oppAirWaybillDetialLis) throws Exception{
				//返回
				String responseStr = "";
				HttpClient httpClient = new HttpClient();  
				//设置esbCode
				String url = PropertiesUtil.getKeyValue("esb.opp.airWaybillDetialFromFoss");
				//String url ="http://10.224.69.21:8081/opp-web/opprestService/airWaybillDetialFromFoss";
				//OPP测试地址
				//String url ="http://10.224.173.116:8180/opp-web/opprestService/airWaybillDetialFromFoss";

				PostMethod postMethod = new PostMethod(url);  
				Date startDate = new Date();
				//json工具类
				ObjectMapper objectMapper =	null;
				objectMapper = obtainJSONObjectMapper();
				try{
					//请求数据 json格式
					 String  requestJsonStr = objectMapper.writeValueAsString(oppAirWaybillDetialLis);
					LOGGER.error("***************调用接口，同步航空正单明细信息至OPP 请求数据json："+requestJsonStr+"*******************");

					//设置报文信息
					httpClient.getParams().setContentCharset("UTF-8");
					postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
					RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
					postMethod.setRequestEntity(reqEntity);
					
					// 设置连接超时和读取超时
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
					//推送数据json格式：{"detialId":"airPickUpID","airPickUpNo":"airPickUpNo（清单号）","requestEntity":{"airPickUpNo":"","wayBillNo":"","detialId":""*********}}
					int status = httpClient.executeMethod(postMethod);  
					if(status==TransferConstants.SONAR_NUMBER_200){
						responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
						LOGGER.error("***************同步航空正单明细信息至OPP 返回数据json:"+responseStr+"*************");
						/**
						 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
						 */
						if(responseStr.contains("exceptionCode")&&
								responseStr.contains("S000099")){
							LOGGER.error("FOSS同步航空正单明细信息至OPP Esb报错返回'S000099'和'exceptionCode'");
							return ;
						}
					}else{
						responseStr = "请求"+url+"传输数据异常：status="+status;
						LOGGER.error("***************调用接口，同步航空正单明细信息至OPP 异常结束***异常信息："+responseStr+"*******************");
						throw new Exception("同步航空正单明细信息至OPP 异常结束:" + responseStr);
					}
				
				}catch(Exception e){
						//推送航空正单明细信息异常
						responseStr = "请求"+url+"FOSS同步航空正单明细信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
						+ExceptionUtils.getFullStackTrace(e);
						LOGGER.error("***************调用接口，同步航空正单明细信息至OPP 异常结束***异常信息："+responseStr+"*******************");
						LOGGER.error(responseStr);
						throw new Exception("同步航空正单明细信息至OPP 异常结束:" + e);

				}finally{
					//释放连接
					postMethod.releaseConnection();
				}
		
			 }
			  /**
				 * @Description: FOSS同步航空正單明细流水号信息至OPP
				 * @date 2016-04-05 下午3:06:04   
				 * @author 269701 
				 * @param String requestStr
			 * @throws IOException 
				 */
			  public void syncAirWaybillSerialToOPP(AirWaybillSerialRequest oppAirWaybillSerial) throws Exception{
			  
				String responseStr = "";
				HttpClient httpClient = new HttpClient();  
				//设置esbCode
				String url = PropertiesUtil.getKeyValue("esb.opp.airWaybillSerialFromFoss");
				//本地地址
				//String url ="http://10.224.69.21:8081/opp-web/opprestService/airWaybillSerialFromFoss";
				//opp测试地址
				//String url ="http://10.224.173.116:8180/opp-web/opprestService/airWaybillSerialFromFoss";

				PostMethod postMethod = new PostMethod(url);  
				Date startDate = new Date();
				//json工具类
				ObjectMapper objectMapper =	null;
				objectMapper = obtainJSONObjectMapper();
				try{
					//请求数据 json格式
					String requestJsonStr = objectMapper.writeValueAsString(oppAirWaybillSerial);
					LOGGER.error("***************调用接口，同步航空正单流水信息至OPP请求数据json:："+requestJsonStr+"********************");

					//设置报文信息
					httpClient.getParams().setContentCharset("UTF-8");
					postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
					RequestEntity reqEntity = new StringRequestEntity(requestJsonStr,"application/json","UTF-8");
					postMethod.setRequestEntity(reqEntity);
					
					// 设置连接超时和读取超时
					httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
					httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
					//推送数据json格式：{"detialId":"detialId","airPickUpNo":"airPickUpNo（清单号）","requestEntity":{"serialNo":"","wayBillNo":"","serialId":""}}
					int status = httpClient.executeMethod(postMethod);  
					if(status==TransferConstants.SONAR_NUMBER_200){
						responseStr=convertStreamToString(postMethod.getResponseBodyAsStream());
						LOGGER.error("***************同步航空正单流水信息至OPP 返回数据json:"+responseStr+"*************");
						/**
						 * {"message":"异常信息:Connection reset","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2016-07-11 09:10:02"}/n*************
						 */
						if(responseStr.contains("exceptionCode")&&
								responseStr.contains("S000099")){
							LOGGER.error("FOSS同步航空正单流水信息至OPP Esb报错返回'S000099'和'exceptionCode'");
							return ;
						}
					}else{
						responseStr = "请求"+url+"传输数据异常：status="+status;
						LOGGER.error("***************调用接口，同步航空正单流水信息至OPP 异常结束:："+responseStr+"********************");
						throw new Exception("同步航空正单流水信息至OPP 异常结束:" + responseStr);

					}
				
				}catch(Exception e){
						//推送正单信息
						responseStr = "请求"+url+"FOSS同步航空正单流水号信息至OPP，开始时间："+startDate+"，结束时间"+new Date()
						+ExceptionUtils.getFullStackTrace(e);				
					LOGGER.error("同步航空正单流水信息至OPP 异常信息:" +responseStr);
					LOGGER.error("***************调用接口，同步航空正单流水信息至OPP 异常结束:："+responseStr+"********************");
					throw new Exception("同步航空正单流水信息至OPP 异常结束:" + e);

				}finally{
					//释放连接
					postMethod.releaseConnection();
				}
			 }
			  
			 
		  
		  /**
			 * 设置 json 数据格式
			 * 
			 * @author foss-lln
			 * @date 2016-04-28 上午9:21:30
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
			
			/**
			 * InputStream 转换成String
			 * @param is
			 * @return 
			 */
			 private String convertStreamToString(InputStream is) {  
				   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
				        StringBuilder sb = new StringBuilder();   
				        String line = null;   
				        try {   
				            while ((line = reader.readLine()) != null) {   
				                sb.append(line + "/n");   
				            }   
				        } catch (IOException e) {   
				            e.printStackTrace();   

				        } finally {   
				            try {   
				                is.close();   
				            } catch (IOException e) {   
				                e.printStackTrace();   
				            }   
				        }   
				        	return sb.toString();   
				    }  
}
