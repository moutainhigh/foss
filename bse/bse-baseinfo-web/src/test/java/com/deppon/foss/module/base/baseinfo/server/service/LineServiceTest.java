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

import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineStationsDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LineService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 线路服务测试类
 * @author foss-zhujunyong
 * @date Nov 6, 2012 11:36:41 AM
 * @version 1.0
 */
public class LineServiceTest {

    
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private ILineService lineService;
    private static final Logger log = Logger.getLogger(LineServiceTest.class);
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	lineService = (ILineService) SpringTestHelper.get().getBeanByClass(LineService.class);
    }

    @After
    public void tearDown() throws Exception {
    }
    


    @Test
    public void testQueryDepartureStandardListBySourceTarget(){
	List<DepartureStandardDto> list = lineService.queryDepartureStandardListBySourceTarget("W04061620", "W04061620", "C30001");
	Assert.assertNotNull(list);
	for (DepartureStandardDto dto : list) {
	    Assert.assertNotNull(dto.getLeaveTime());
	    log.debug(dto.getSourceCode());
	    log.debug(dto.getTargetCode());
	    log.debug(dto.getLeaveTime());
	    log.debug(dto.getArriveTime());
	    log.debug(dto.getArriveDay());
	    log.debug(dto.getLeaveDate(new Date()));
	    log.debug(dto.getArriveDate(new Date()));
	}
    }

    @Test
    public void testQueryDepartureStandardListBySourceTarget2(){
	List<DepartureStandardDto> list = lineService.queryDepartureStandardListBySourceTarget("W1200030122", "W3100020616");
	Assert.assertNotNull(list);
	for (DepartureStandardDto dto : list) {
	    Assert.assertNotNull(dto.getLeaveTime());
	    log.debug(dto.getSourceCode());
	    log.debug(dto.getTargetCode());
	    log.debug(dto.getLeaveTime());
	    log.debug(dto.getArriveTime());
	    log.debug(dto.getArriveDay());
	    log.debug(dto.getLeaveDate(new Date()));
	    log.debug(dto.getArriveDate(new Date()));
	}
	
    }
    
    @Test
    public void testQueryLineStations() {
	LineStationsDto dto =  lineService.queryLineStations("b222d819-0668-4f88-9fb0-8300e940f895");
	Assert.assertNotNull(dto);
	Map<String, String> map = dto.getStationMap();
	log.info(map);
    }
    
    @Test
    public void testQueryDepartureStandardListBySourceTargetDirectly() {
	DepartureStandardDto dto = lineService.queryDepartureStandardListBySourceTargetDirectly("W011303070309", "W0114040401", new Date());
	Assert.assertNotNull(dto);
	log.debug(dto);
    }

    @Test
    public void testRename() {
	lineService.rename("W011302020515", "上海闵行区浦江镇营业部", null);
    }
    
}
