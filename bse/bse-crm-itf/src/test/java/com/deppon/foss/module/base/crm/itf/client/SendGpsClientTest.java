/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-crm-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/crm/itf/client/SendGpsClientTest.java
 * 
 * FILE NAME        	: SendGpsClientTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.base.crm.itf.client;

import javax.xml.ws.Holder;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.example.deppon_gps_service.CommonException;
import org.example.deppon_gps_service.DepponGpsService;
import org.example.deppon_gps_service.domain.QueryTransmitLineRequest;
import org.example.deppon_gps_service.domain.QueryTransmitLineResponse;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;

/**
 * Foss向GPS传送班线数据信息Web Service客户端接口单元测试类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-12-3
 * 下午8:31:50
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-3 下午8:31:50
 * @since
 * @version
 */
public class SendGpsClientTest {
    
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendGpsClientTest.class);
    /**
     * 测试调用GPS新增班线信息接口
     * 
     * @param args
     */
    public static void main(String[] args) {
	
	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setAddress("http://192.168.17.141:18080/esb2/ws/foss2gps/gpsService");
	factory.setServiceClass(DepponGpsService.class);
	//factory.getOutInterceptors().add(new ClientHeaderProcessor());
	//ClientHeader header = new ClientHeader();
	//设置服务编码
	
	ESBHeader header = new ESBHeader();
	header.setEsbServiceCode("ESB_FOSS2ESB_TRANSMIT_LINE");
	header.setMessageFormat("SOAP");
	header.setExchangePattern(1);
	header.setVersion("1.0");
	header.setRequestId(UUID.randomUUID().toString());
	
	Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
	DepponGpsService service = (DepponGpsService) factory.create();
	
	QueryTransmitLineRequest request = new QueryTransmitLineRequest();
	request.setArrivalsiteId("JwRB6AEuEADgJwXBwKgCZcznrtQ=");
	request.setIsDeleted(0);
	request.setLineId("10023884");
	request.setLineName("徐泾营业部-上海外场");
	request.setStartsiteId("JwfkiQEuEADgE2azwKgCZcznrtQ=");
	try {
	    QueryTransmitLineResponse response = service.queryTransmitLine(request,holder);
	    LOGGER.info("同步信息结果："+response.isResult());
	} catch (CommonException e) {
	    LOGGER.info(e.getMessage(), e);
	}
    }

}
