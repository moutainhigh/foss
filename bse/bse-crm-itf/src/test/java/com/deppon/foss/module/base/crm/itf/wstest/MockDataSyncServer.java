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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/crm/itf/wstest/MockDataSyncServer.java
 * 
 * FILE NAME        	: MockDataSyncServer.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.crm.itf.wstest;

import java.util.UUID;

import javax.xml.ws.Holder;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.foss.ws.syncdata.ISyncDataService;
import com.deppon.foss.ws.syncdata.domain.Sync;
import com.deppon.foss.ws.syncdata.domain.SyncResponse;


public class MockDataSyncServer {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDataSyncServer.class);
    public static void main(String[] args) throws Exception{
	JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
	factory.setAddress("http://192.168.13.28:8087/ws3");
	factory.setServiceClass(ISyncDataService.class);
	factory.setServiceBean(new ISyncDataService(){

	    @Override
	    public SyncResponse sync(Sync syncRequest,
		    Holder<ESBHeader> esbHeader) throws CommonException {
		esbHeader.value.setRequestId(UUID.randomUUID().toString());
		SyncResponse rSyncResponse = new SyncResponse();
		rSyncResponse.setReturn(true);
		return rSyncResponse;
	    }});
	Server server = factory.create();
	server.start();
	LOGGER.info("Start ..................");
	Thread.sleep(1000*60*5);
    }
}
