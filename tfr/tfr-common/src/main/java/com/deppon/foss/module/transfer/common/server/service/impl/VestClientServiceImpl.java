/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.cubcgray.model.RequestDO;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.server.utils.ApacheHttpClientUtils;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;

/**
 *<pre>
 *功能:提供CUBC归属封装接口，提供单一和批量的实现类
 *作者：132028
 *日期：2016年12月19日上午11:51:50
 *</pre>
 */
public class VestClientServiceImpl{
	private static final Logger LOGGER = LoggerFactory.getLogger(VestClientServiceImpl.class);
	
	private static final String VEST_CUSTOMER_REQUEST_URI;
	private static final String VEST_SOURCEBILLNO_REQUEST_URI;
	private static final String VEST_ASCRIPTION_REQUEST_URI;
	
	static{
		/*
		 * 335284
		 * 配置文件有误，所以先获取公共前缀。
		 * 待配置文件修正后直接获取即可
		 */
		final String CUBC_ASHY_URL = PropertiesUtil.getKeyValue("cubc.gray.vestByCustomer").substring(0,
				PropertiesUtil.getKeyValue("cubc.gray.vestByCustomer").lastIndexOf("/"));
		LOGGER.info("CUBC_ASHY_URL=" + CUBC_ASHY_URL);
		VEST_ASCRIPTION_REQUEST_URI = CUBC_ASHY_URL + "/vestAscription";
		VEST_CUSTOMER_REQUEST_URI = CUBC_ASHY_URL + "/vestCustomer";
		VEST_SOURCEBILLNO_REQUEST_URI = CUBC_ASHY_URL + "/vestSourceBillNo";
		LOGGER.info("VEST_ASCRIPTION_REQUEST_URI=" + VEST_ASCRIPTION_REQUEST_URI);
		LOGGER.info("VEST_CUSTOMER_REQUEST_URI=" + VEST_CUSTOMER_REQUEST_URI);
		LOGGER.info("VEST_SOURCEBILLNO_REQUEST_URI=" + VEST_SOURCEBILLNO_REQUEST_URI);
	}
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 根据客户(customer)进行分流
	 * @param serviceCode 灰度层自定义的服务编码
	 * @param customerType 客户类型（客户、供应商） 
	 * @param origin 来源系统
	 * @param customerCodes 客户编码
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public VestResponse vestByCustomer(String serviceCode, String customerType, String origin, String... customerCodes) throws JsonGenerationException, JsonMappingException, IOException {
		VestResponse result = null;
		//1.封装参数
		RequestDO request = new RequestDO();
		request.setServiceCode(serviceCode);
		request.setCustomerType(customerType);
		request.setOrigin(origin);
		request.setCustomerCodes(customerCodes);
		String requestJson;
		requestJson = mapper.writeValueAsString(request);
		LOGGER.info("vestByCustomer >>>> " + requestJson);
		//2.远程调用服务 含异常处理情况
		result = ApacheHttpClientUtils.excutePost(VEST_CUSTOMER_REQUEST_URI, requestJson);
		//3.返回调用信息
		return result;
	}


	public VestResponse vestBySourceBillNo(String serviceCode, String origin, String sourceBillType, String... sourceBillNos) throws JsonGenerationException, JsonMappingException, IOException {
		VestResponse result = null;
		
		//1.封装参数
		RequestDO request = new RequestDO();
		request.setServiceCode(serviceCode);
		request.setSourceBillType(sourceBillType);
		request.setOrigin(origin);
		request.setSourceBillNos(sourceBillNos);
		String requestJson = mapper.writeValueAsString(request);
		LOGGER.info("vestBySourceBillNo >>>> " + requestJson);
		//2.远程调用服务 含异常处理情况
		result = ApacheHttpClientUtils.excutePost(VEST_SOURCEBILLNO_REQUEST_URI, requestJson);
		
		//3.返回调用信息
		return result;
	}

	public VestResponse vestAscription(String serviceCode, String origin, String sourceBillType,
			String... sourceBillNos) throws JsonGenerationException, JsonMappingException, IOException {
		VestResponse result = null;
		//1.封装参数
		RequestDO request = new RequestDO();
		request.setServiceCode(serviceCode);
		request.setSourceBillType(sourceBillType);
		request.setOrigin(origin);
		request.setSourceBillNos(sourceBillNos);
		String requestJson = mapper.writeValueAsString(request);
		LOGGER.info("vestAscription >>>> " + requestJson);
		//2.远程调用服务 含异常处理情况
		result = ApacheHttpClientUtils.excutePost(VEST_ASCRIPTION_REQUEST_URI, requestJson);
		
		//3.返回调用信息
		return result;
	}

}
