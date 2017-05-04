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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/AirlinesAccountServiceTest.java
 * 
 * FILE NAME        	: AirlinesAccountServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AirlinesAccountService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

public class AirlinesAccountServiceTest {
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IAirlinesAccountService airlinesAccountService;
    private static final Logger log = Logger.getLogger(OwnVehicleServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	airlinesAccountService = (IAirlinesAccountService) SpringTestHelper.get().getBeanByClass(AirlinesAccountService.class);
    }
    
    @After
    public void teardown() {
	jdbc = null;
    }

    @Ignore
    @Test
    public void testAirlinesAccountService() {
	AirlinesAccountEntity airlinesAccount = new AirlinesAccountEntity();
	airlinesAccount.setId("sdfasdf");
	airlinesAccount.setAirlines("xyz");
	airlinesAccount.setAirlinesAccount("sdfsdf");
	PaginationDto paginationDto = airlinesAccountService.queryAirlinesAccountListBySelectiveCondition(airlinesAccount, 0, 10);
	log.info(paginationDto.getPaginationDtos());
    }
}
