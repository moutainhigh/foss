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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/LineServiceTest.java
 * 
 * FILE NAME        	: LineServiceTest.java
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
package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.NetGroupService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 线路服务测试类
 * @author foss-zhujunyong
 * @date Nov 6, 2012 11:36:41 AM
 * @version 1.0
 */
public class NetGroupServiceTest {

    
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private INetGroupService netGroupService;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(NetGroupServiceTest.class);
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	netGroupService = (INetGroupService) SpringTestHelper.get().getBeanByClass(NetGroupService.class);
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testQueryFreightRouteCode() {
	List<String> list = netGroupService.queryFreightRouteCode("W03000305060513", "W0113080208");
	Assert.assertNotNull(list);
	for (String code : list) {
	    System.out.println(code);
	}
    }
    
}
