package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTPSService;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.util.DateUtils;


/**
 * foss掉用TPS接口服务类
 * @author 200978  xiaobingcheng
 * 2014-12-29
 */
public class FOSSToTPSService implements IFOSSToTPSService {
	
	private static final Logger log = LoggerFactory.getLogger(FOSSToTPSService.class);

	/**
	 * 同步约车信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-29
	 * @param requestParameter
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	public ResponseParameterEntity updateOrderVehicleInfo(RequestParameterEntity requestParameter) throws Exception{
		
		log.error("FOSS同步约车信息到TPS开始！");
		String code="ESB_FOSS2ESB_AGREEMENT_TPS";
	   
		//TPS固定的约车同步借口方法名
		requestParameter.setType("orderVehicleNumberService");
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		//设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod  postMethod=new PostMethod(url);
		postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
		
		//json工具类
		ObjectMapper objectMapper =	null;
		objectMapper = obtainJSONObjectMapper();
		ResponseParameterEntity responseParameter = new ResponseParameterEntity();
	
		try{
			String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
			
			 //填入各个表单域的值
		/*	NameValuePair[] data = {
	                new NameValuePair("type", type),
	                new NameValuePair("requestEntity", requestEntity),
	        };*/
	      //将表单的值放入postMethod中
	        RequestEntity requestEntity = new StringRequestEntity(requestJsonStr);
	        postMethod.setRequestEntity(requestEntity);
			
			httpClient.executeMethod(postMethod);
			String responseBody = postMethod.getResponseBodyAsString();
			/**
			 * {"message":"异常信息:HTTP operation failed invoking http://10.224.169.37:8081/tps/webservice/tpsFossSynscInfoRest/receiveFossRestFulMethod?null with statusCode: 500","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2015-01-08 12:25:19"}
			 */
			if(responseBody.contains("exceptionCode")&&
					responseBody.contains("S000099")){
				log.error("FOSS同步约车信息到TPS，反回'S000099'和'exceptionCode'");
				return null;
				
			}
			//------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
			responseParameter = objectMapper.readValue(responseBody, ResponseParameterEntity.class);
			
			log.info("FOSS同步约车信息到TPS结束,TPS响应信息：" + responseBody);
			return responseParameter;
		} catch (JsonGenerationException e) {
			log.error("FOSS同步约车信息到TPS失败："+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		} catch (JsonMappingException e) {
			log.error("FOSS同步约车信息到TPS失败："+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		} catch (IOException e) {
			log.error("FOSS同步约车信息到TPS失败："+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		}
		
	}
	
	
	/***
	 * foss 出发 到达，同步约车信息给tps
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-30
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 */
	public ResponseParameterEntity updateDepartArriveinfo(RequestParameterEntity requestParameter) throws Exception{
		HttpClient httpClient = new HttpClient();
		log.error("FOSS出发 到达同步信息到TPS开始！");
		String code="ESB_FOSS2ESB_COMMAND_TPS";
		//TPS固定的约车同步借口方法名
		requestParameter.setType("synsFossStartArriveInfo");
		//设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod  postMethod=new PostMethod(url);
		postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
//		postMethod.addRequestHeader("version","0.1");
//		postMethod.addRequestHeader("esbServiceCode",code1);
		
		//json工具类
		ObjectMapper objectMapper =	null;
		objectMapper = obtainJSONObjectMapper();
		ResponseParameterEntity responseParameter = new ResponseParameterEntity();
		try{
			String requestJsonStr = objectMapper.writeValueAsString(requestParameter);
			
			 //将表单的值放入postMethod中
	        RequestEntity requestEntity = new StringRequestEntity(requestJsonStr);
	        postMethod.setRequestEntity(requestEntity);
			
	        
			httpClient.executeMethod(postMethod);
			String responseBody = postMethod.getResponseBodyAsString();
			/**
			 * 	{"message":"异常信息:HTTP operation failed invoking http://10.224.169.37:8081/tps/webservice/tpsFossSynscInfoRest?null 
			 * with statusCode: 404","exceptionType":"SYS","detailedInfo":null,"exceptionCode":"S000099","createdTime":"2015-01-08 18:01:53"}
			 */
			if(responseBody.contains("exceptionCode")&&
					responseBody.contains("S000099")){
				log.error("FOSS出发 到达同步约车信息到TPS返回'S000099'和'exceptionCode'");
				return null;
			}
			//------------------------------JSON转换成实体  其中包含List复杂类型------------------------------------//
			responseParameter = objectMapper.readValue(responseBody, ResponseParameterEntity.class);
			log.info("FOSS出发 到达同步信息到TPS结束，TPS响应信息：" + responseBody);
			return responseParameter;
		} catch (JsonGenerationException e) {
			log.error("FOSS出发 到达同步信息到TPS失败"+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		} catch (JsonMappingException e) {
			log.error("FOSS出发 到达同步信息到TPS失败"+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		} catch (IOException e) {
			log.error("FOSS出发 到达同步信息到TPS失败"+e.getMessage());
			e.printStackTrace();
			responseParameter.setResultFlag(false);
			responseParameter.setFailureReason(e.getMessage());
			return responseParameter;
		}
	}
	
	/**
	 * 设置 json 数据格式
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01 上午9:21:30
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
	
	
}
