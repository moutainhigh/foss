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

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OutfieldService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 月台测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class OutfieldServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IOutfieldService outfieldService;
    private static final Logger log = Logger.getLogger(OutfieldServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	outfieldService = (IOutfieldService) SpringTestHelper.get().getBeanByClass(OutfieldService.class);
    }
    
    @After
    public void teardown() {
    }

    @Test
    public void testQueryOutfieldByMotorcadeCode() {
	String motorcadeCode = "W3100020619";
	List<OutfieldEntity> list = outfieldService.queryOutfieldByMotorcadeCode(motorcadeCode);
	Assert.assertNotNull(list);
	for (OutfieldEntity entity : list) {
	    log.info(entity.getCode());
	    log.info(entity.getName());
	}
    }

}
