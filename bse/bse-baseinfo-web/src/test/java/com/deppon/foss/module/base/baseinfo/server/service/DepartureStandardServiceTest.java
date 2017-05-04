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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/DepartureStandardServiceTest.java
 * 
 * FILE NAME        	: DepartureStandardServiceTest.java
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

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.DepartureStandardService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 发车标准测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class DepartureStandardServiceTest {

    private JdbcTemplate jdbc;
    private IDepartureStandardService departureStandardService;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(DepartureStandardServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	departureStandardService = (IDepartureStandardService) SpringTestHelper.get().getBeanByClass(DepartureStandardService.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_departure_std where line_virtual_code like 'zjy%'");
    }

    @Test
    public void testQueryPlatformByCode() {
	DepartureStandardEntity entity = new DepartureStandardEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setArriveTime("0900");
	entity.setArriveDay(0L);
	entity.setLeaveTime("0100");
	entity.setOrder(1);
	entity.setLineVirtualCode("zjy100");
	DepartureStandardEntity standard = departureStandardService.addDepartureStandard(entity);
	Assert.assertNotNull(standard);
    }
    

}
