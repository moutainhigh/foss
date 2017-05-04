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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/crm/itf/client/SysCustomerInfoServiceTest.java
 * 
 * FILE NAME        	: SysCustomerInfoServiceTest.java
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

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.deppon.foss.ws.syncdata.ISyncDataService;


/**
 * WEB SERVICE客户端测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-23 下午4:12:53 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-23 下午4:12:53
 * @since
 * @version
 */
@ContextConfiguration(locations = {"classpath:/com/deppon/foss/module/base/crm/itf/client/META-INF/spring.xml"})
public class SysCustomerInfoServiceTest extends
	AbstractTransactionalJUnit4SpringContextTests {
    
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ISyncDataService syncDataService;
    
    @Ignore
    @Test
    public void testSync(){
	LOGGER.info("calling ......");
	
	LOGGER.info("end ....");
	
    }

}
