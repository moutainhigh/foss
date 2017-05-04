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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/OwnVehicleServiceTest.java
 * 
 * FILE NAME        	: OwnVehicleServiceTest.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OwnVehicleService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
/**
 * 车辆公共服务测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-4 上午9:48:42</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-4 上午9:48:42
 * @since
 * @version
 */
public class OwnVehicleServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IOwnVehicleService ownVehicleService;
    private static final Logger log = Logger.getLogger(OwnVehicleServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	ownVehicleService = (IOwnVehicleService) SpringTestHelper.get().getBeanByClass(OwnVehicleService.class);
    }
    
    @After
    public void teardown() {
	jdbc = null;
    }

    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testQueryPlatformByCode() {
	PaginationDto paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(null, new ArrayList<String>(), null, "Y", 0, 1, null);
	for (VehicleAssociationDto vehicleAssociationDto : (List<VehicleAssociationDto>)paginationDto.getPaginationDtos()) {
	    log.info(vehicleAssociationDto.isHasGps());
	}
    }
}
