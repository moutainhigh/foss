/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/OAErrorService.java
 * 
 * FILE NAME        	: OAErrorService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.esb.interceptor.ClientHeader;
import com.deppon.foss.esb.interceptor.WSHeaderHelper;
import com.deppon.foss.module.pickup.sign.api.shared.define.ReportConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.server.utils.JSONUtils;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OAErrorReportResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaErrorReportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.OaReportClearLose;
import com.deppon.foss.module.pickup.waybill.shared.dto.QmsLostRequest;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResponseShortGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.OAErrorException;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseDto;

import dip.integrateportal.businessmanagement.errors.foss.OAErrorsForFOSSImpl;

/**
 * 
 * OA差错编码查询
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-12-1 上午8:51:00
 */
public class OAErrorService implements IOAErrorService {

	private static final String ESB_FOSS2ESB_QUERY_RESULT = "ESB_FOSS2ESB_QUERY_RESULT";
	private static final String ESB_FOSS2ESB_REPORT_OVERWEIGHT = "ESB_FOSS2ESB_REPORT_OVERWEIGHT";
	private static final String ESB_FOSS2FOSS_REVERSE_SIGN="ESB_FOSS2FOSS_REVERSE_SIGN";
	
	private static final int NUM_100000 = 100000;
	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory.getLogger(OAErrorService.class.getName());
	
	/**
	 * OA 差错接口
	 */
	public OAErrorsForFOSSImpl oAErrorsForFOSSPKP;

	/**
	 * 设置OA接口
	 * 
	 * @param oAErrorsForFOSSPKP
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-12 下午6:37:22
	 */
	public void setoAErrorsForFOSSPKP(OAErrorsForFOSSImpl oAErrorsForFOSSPKP) {
		this.oAErrorsForFOSSPKP = oAErrorsForFOSSPKP;
	}
	
	private String url;
	
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * 查询OA虚开单编码
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-1 上午10:17:45
	 */
	@Override
	public QueryVirtualResultDto queryVirtrualWaybillFromOA(String handingID) {
		/**
		 * 返回值初始化
		 */
		String response = null;
		/**
		 * 设置客户端header头信息
		 */
		ClientHeader header = new ClientHeader();
		/**
		 * 设置ESBService的code 类型
		 */
		header.setEsbServiceCode(ESB_FOSS2ESB_QUERY_RESULT);
		/**
		 * 设置处理ID
		 */
		header.setBusinessId(handingID);
		/**
		 * 设置表头
		 */
		WSHeaderHelper.setClientHeader(header);

		/**
		 * 如果处理编码ID为空或者空字符，抛出异常 
		 */
		if (handingID == null || "".equals(handingID.trim())) {
			throw new OAErrorException(OAErrorException.HANDINGID_NULL);
		}

		try {
			/**
			 * 返回处理结果
			 * 
			 * @author 
			 *         026113-foss-linwensheng
			 * @date 2012-12-1 上午8:51:00
			 */
			response = oAErrorsForFOSSPKP.queryVirtrualWaybill(handingID);
		} catch (SOAPFaultException e) {
			/**
			 * OA接口无法访问异常
			 */
			logger.info("OA差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.OAERRORS_INACCESSIBLE);
		} catch (WebServiceException e) {
			logger.info("OA差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.OAERRORS_INACCESSIBLE);
		} catch (LinkageError e) {
			logger.info("OA差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.OAERRORS_INACCESSIBLE);
		} catch (Exception e) {
			logger.info("OA差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.OAERRORS_INACCESSIBLE);
		}
		QueryVirtualResultDto dto;
		/**
		 * 通过解析实体，把JSON格式的字符串转换为对象
		 */
		dto = JSONObject.parseObject(response, QueryVirtualResultDto.class);
		return dto;
	}

	@Override
	public Object reportOAError(OaErrorReportDto oaErrorDto) {
		OAErrorReportResultDto resultDto = null;
		try{
			/**
			 * 设置客户端header头信息
			 */
			ClientHeader header = new ClientHeader();
			/**
			 * 设置ESBService的code 类型
			 */
			header.setEsbServiceCode(ESB_FOSS2ESB_REPORT_OVERWEIGHT);
			/**
			 * 设置处理ID
			 */
			header.setBusinessId(oaErrorDto.getWaybillNo());
			/**
			 * 设置表头
			 */
			WSHeaderHelper.setClientHeader(header);
			
			String rspMessage = oAErrorsForFOSSPKP.reportAttachoverweight(JSONObject.toJSONString(oaErrorDto));
			Log.info("rspMessage="+rspMessage);
			resultDto = JSONObject.parseObject(rspMessage, OAErrorReportResultDto.class);
		}catch(SOAPFaultException e){
			logger.info("OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (WebServiceException e) {
			logger.info("OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (LinkageError e) {
			logger.info("OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (Exception e) {
			logger.info("OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		}
		return resultDto;
	}
	
	/**
	 * 反签收上报OA差错
	  * Description: 
	  * @author deppon-076234-pgy
	  * @version 1.0 2014-3-26 下午5:04:16
	  * @param record
	  * @return
	 */
	@Override
	public Object reverseSignOAError(OaErrorReportDto oaErrorDto) {
		OAErrorReportResultDto resultDto = null;
		try{
			/**
			 * 设置客户端header头信息
			 */
			ClientHeader header = new ClientHeader();
			/**
			 * 设置ESBService的code 类型
			 */
			header.setEsbServiceCode(ESB_FOSS2FOSS_REVERSE_SIGN);
			/**
			 * 设置处理ID
			 */
			header.setBusinessId(oaErrorDto.getWaybillNo());
			/**
			 * 设置表头
			 */
			WSHeaderHelper.setClientHeader(header);
			
			String rspMessage = oAErrorsForFOSSPKP.reportReverseSign(JSONObject.toJSONString(oaErrorDto));
			Log.info("rspMessage="+rspMessage);
			resultDto = JSONObject.parseObject(rspMessage, OAErrorReportResultDto.class);
		}catch(SOAPFaultException e){
			logger.info("反签收OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (WebServiceException e) {
			logger.info("反签收OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (LinkageError e) {
			logger.info("反签收OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		} catch (Exception e) {
			logger.info("反签收OA上报差错接口无法访问：", e.getMessage());
			throw new OAErrorException(OAErrorException.REPORT_OAERRORS_SYS_INACCESSIBLE);
		}
		return resultDto;
	}

	/**
	 * 上报快递代理理外发XX天未签收丢货 DMANA-3046
	 * @Title: reportLessGoods 
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午9:50:29
	 * @throws
	 */
	public ResponseDto reportLessGoods(OaReportClearless oaReportClearless) {
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		// 转为字符串
		String lessGoods;
		ResponseDto responseDto = new ResponseDto();
		try {
			lessGoods = objectMapper.writeValueAsString(oaReportClearless);
			//
			ClientHeader header = new ClientHeader();
			header.setBusinessId(oaReportClearless.getWayBillId());
			header.setEsbServiceCode(TransferConstants.OA_LESS_GOODS_SERVICE_CODE);
			WSHeaderHelper.setClientHeader(header);
			logger.error("#############开始调用上报OA少货接口############waybillNo:" + oaReportClearless.getWayBillId());
			// 调用OA接口
			logger.error("###########传入OA的字符串：" + lessGoods);
			String lessResponse = oAErrorsForFOSSPKP.reportLessGoods(lessGoods);
			logger.error("###########OA返回的字符串：" + lessResponse);
			// 获取返回对象
			responseDto = obtainResponse(lessResponse);
			logger.error("#############调用上报OA少货接口结束############waybillNo:" + oaReportClearless.getWayBillId());
			return responseDto;
		} catch (JsonGenerationException e) {
			logger.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (JsonMappingException e) {
			logger.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		} catch (IOException e) {
			logger.error("上报OA少货发生异常：waybillNo:" + oaReportClearless.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}
	}
	
	/**
	 * 
	 * @Title: obtainJSONObjectMapper 
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午10:00:28
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	private ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置到objectMapper
		objectMapper.getSerializationConfig().setDateFormat(dateFormat);
		// objectMapper.getSerializationConfig().withDateFormat(dateFormat);
		return objectMapper;
	}
	
	/**
	 * 将字符串转为JAVA对象
	 * @Title: obtainResponse 
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午10:00:42
	 * @throws
	 */
	private ResponseDto obtainResponse(String response) {

		if (response == null) {
			return null;
		} else {
			ResponseDto responseDto;
			try {
				responseDto = JSONObject.parseObject(response, ResponseDto.class);
				return responseDto;
			} catch (Exception e) {
				return null;
			}
		}

	}
	
	/**
	 * 调用OA接口上报丢货差错
	 * @date 2014-12-3 下午3:58:10
	 * @param oaReportClearless
	 * @author foss-yuting
	 * @return  
	 * @see com.deppon.foss.module.pickup.waybill.server.service.impl.OAErrorService#reportLostGoods(com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearless)
	 */
	@Override
	public ResponseDto reportLostGoods(OaReportClearLose oaReportClearLose) {
		ObjectMapper objectMapper = obtainJSONObjectMapper();
		ResponseDto responseDto=new ResponseDto();
		try {
			String lostGoodsMsg  = objectMapper.writeValueAsString(oaReportClearLose);												
			//设置头信息
			ClientHeader header = new ClientHeader();
			header.setBusinessId(oaReportClearLose.getWayBillId());
			header.setEsbServiceCode(ReportConstants.OA_LOST_GOODS_SERVICE_CODE);
			WSHeaderHelper.setClientHeader(header);
			logger.info("#############开始调用上报OA丢货接口############waybillNo:" + oaReportClearLose.getWayBillId());
			// 调用OA接口
			logger.info("###########传入OA的字符串：" + lostGoodsMsg);
			String lessResponse = oAErrorsForFOSSPKP.reportLessGoods(lostGoodsMsg);
			logger.info("#############调用上报OA丢货接口结束############waybillNo:" + oaReportClearLose.getWayBillId());
			if(StringUtils.isNotEmpty(lessResponse)){
				responseDto  =	JSON.parseObject(lessResponse, ResponseDto.class);//序列化成对象
			}
			logger.info("############序列化后的返回值:" + ReflectionToStringBuilder.toString(responseDto));
			
			return responseDto;
			
		} catch (Exception e) {
			
			logger.error("上报OA丢货发生异常：waybillNo:" + oaReportClearLose.getWayBillId() + e.getMessage());
			responseDto.setFailureReason(e.toString());
			return responseDto;
		}
		
	}
	
	/**
	 * 新增内物短少差错-FOSS自动上报	每10分钟执行一次  </br>
	 * @date 2014-12-8 下午3:58:10
	 * @param java.lang.String json
	 * @author foss-yuting
	 * @return  
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.ShortGoodsNotifyService#processNotifyShortGoods()
	 */
	@Override
	public ResponseShortGoodsDto reportExceptionGoods(String json,String waybillNo) {
		ResponseShortGoodsDto resultDto = null;
		try{
			/**
			 * 设置客户端header头信息
			 */
			ClientHeader header = new ClientHeader();
			/**
			 * 设置ESBService的code 类型
			 */
			header.setEsbServiceCode(ReportConstants.ESB_FOSS2FOSS_REPORT_SHORTGOODS);
			/**
			 * 设置处理ID
			 */
			header.setBusinessId(waybillNo);
			/**
			 * 设置表头
			 */
			WSHeaderHelper.setClientHeader(header);
			
			String rspMessage = oAErrorsForFOSSPKP.reportReverseSign(json);
			Log.info("rspMessage="+rspMessage);
			resultDto = JSONObject.parseObject(rspMessage, ResponseShortGoodsDto.class);
		}catch (RuntimeException e) {
			logger.error("上报OA异物短少发生异常：waybillNo:" + waybillNo + e.getMessage());
			if(resultDto != null){
				resultDto.setFailureReason(e.toString());
			}
			return resultDto;
		}
		return resultDto;
	}
	
	/**
	 * 
	 * 查询OA虚开单差错处理结果
	 * 
	 * @author huangwei
	 * @date 2015-11-14 下午2:30:01
	 */
	@Override
	public LostRepDto queryLostRepDto(String lostRepCode, String wayBillNo) {
		QmsLostRequest request=new QmsLostRequest();
		request.setLostRepCode(lostRepCode);
		request.setWayBillNo(wayBillNo);
	    String jsonString = JSON.toJSONString(request);
	    HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod(url);
	    method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NUM_100000);
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(NUM_100000);
	    method.getParams().setContentCharset("UTF-8");
	    method.getParams().setHttpElementCharset("UTF-8");
	    LostRepDto lostRepDto = null;
	    try {
	    	StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", "UTF-8");
	    	method.setRequestEntity(requestEntity);
	    	logger.info("开始查询QMS差错接口"+url);
	    	int statuCode = client.executeMethod(method);
	    	logger.info("开始查询QMS差错接口结束"+statuCode);
			if(statuCode==HttpStatus.SC_OK){
				String jsonResponse = method.getResponseBodyAsString();
				lostRepDto = JSONObject.parseObject(jsonResponse, LostRepDto.class);
			}else{
				logger.info("链接异常"+url);
			}
		} catch (Exception e) {
			logger.info("查询QMS差错接口异常"+e.getMessage());
	    }
	    return lostRepDto;
	}
}