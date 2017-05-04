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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/PlatformServiceTest.java
 * 
 * FILE NAME        	: PlatformServiceTest.java
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

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

/**
 * 
 * 装卸车标准测试类
 * @author foss-zhujunyong
 * @date Apr 1, 2013 3:38:44 PM
 * @version 1.0
 */
public class LoadAndUnloadEfficiencyTonServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(LoadAndUnloadEfficiencyTonServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	loadAndUnloadEfficiencyTonService = (ILoadAndUnloadEfficiencyTonService) SpringTestHelper.get().getBeanByClass(LoadAndUnloadEfficiencyTonService.class);
    }
    
    @After
    public void teardown() {
    }

    @Test
    public void testQueryLoadAndUnloadEfficiencyTonByOrgCode() {
	LoadAndUnloadEfficiencyTonEntity entity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode("W01140402");
	Assert.assertNotNull(entity);
    }

    

}
