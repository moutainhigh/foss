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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/LeasedDriverServiceTest.java
 * 
 * FILE NAME        	: LeasedDriverServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LeasedDriverService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

/**
 * 外请司机服务测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-4 下午2:04:13</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-4 下午2:04:13
 * @since
 * @version
 */
public class LeasedDriverServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private ILeasedDriverService leasedDriverService;
    private static final Logger log = Logger.getLogger(OwnVehicleServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	leasedDriverService = (ILeasedDriverService) SpringTestHelper.get().getBeanByClass(LeasedDriverService.class);
    }
    
    @After
    public void teardown() {
	jdbc = null;
    }

    @Ignore
    @Test
    public void testAirlinesAccountService() {
	LeasedDriverEntity leasedDriver = new LeasedDriverEntity();
	leasedDriver.setDriverName("张");
	leasedDriver.setIdCard("ddddd");
	PaginationDto paginationDto = leasedDriverService.queryLeasedDriverListBySelectiveCondition(leasedDriver, 0, 10);
	log.info(paginationDto.getPaginationDtos());
    }
    
    @Ignore
    @Test
    public void testQueryLesasedDriverAssociationDtoByTruckVehicleNo() {
    	String vehicleNo = "蒙M07231";
    	List<DriverAssociationDto> rtnDto = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(vehicleNo);
    	log.info("查询结果条数："+rtnDto.size());
    }
    
}
