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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PlatformExcelDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.PlatformService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 月台测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class PlatformServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IPlatformService platformService;
    private static final Logger log = Logger.getLogger(PlatformServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	platformService = (IPlatformService) SpringTestHelper.get().getBeanByClass(PlatformService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }

    @Test
    public void testQueryPlatformByCode() {
	PlatformEntity platform = platformService.queryPlatformByCode("W3100020616", "1004");
	log.debug(platform.getVirtualCode());
    }
    
    @Test
    public void testQueryPlatformListByVehicleType() {
	List<PlatformEntity> list = platformService.queryPlatformListByVehicleType("W3100020616", "960");
	Assert.assertTrue(list.size() > 0);
	for (PlatformEntity platform : list) {
	    log.debug(platform.getPlatformCode());
	}
    }
    
    @Test
    public void testImportExcelDtoList() {
	PlatformExcelDto dto1 = new PlatformExcelDto();
	dto1.setRowNo(1);
	dto1.setTransferCode("W060002070701");
	dto1.setPlatformCode("Z01");
	dto1.setHasLift("Y");
	dto1.setPosition("A区");
	dto1.setHeight("1.2");
	dto1.setWidth("9");
//	dto1.setFourPointTwo("Y");
//	dto1.setSixPointFive("Y");
//	dto1.setSevenPointSix("N");
//	dto1.setNinePointSix("N");
//	dto1.setSeventeenPointFive("Y");
	dto1.setNotes("zjyTest1");

	PlatformExcelDto dto2 = new PlatformExcelDto();
	dto2.setRowNo(2);
	dto2.setTransferCode("W060002070701");
	dto2.setPlatformCode("Z02");
	dto2.setHasLift("Y");
	dto2.setPosition("B区");
	dto2.setHeight("1.2");
	dto2.setWidth("9");
//	dto2.setFourPointTwo("Y");
//	dto2.setSixPointFive("Y");
//	dto2.setSevenPointSix("Y");
//	dto2.setNinePointSix("Y");
//	dto2.setSeventeenPointFive("Y");
	dto2.setNotes("zjyTest2");

	List<PlatformExcelDto> dtoList = new ArrayList<PlatformExcelDto>();
	dtoList.add(dto1);
	dtoList.add(dto2);
	
	List<Integer> resultList = platformService.importPlatformList(dtoList, "223222");
	if (CollectionUtils.isNotEmpty(resultList)) {
	    for (Integer rowNo : resultList) {
		log.debug(rowNo);
	    }
	}
    }
    
    @Test
    public void testQueryDistanceByPlatformGoodsAreaVir() {
	String orgCode = "W3100020616";
	String platformVirtualCode = "433c90e5-8719-4bcb-a4b8-dde7f2446402";
	String goodsAreaVirtualCode = "de196879-49d2-4e8a-8fe6-e6e4da4f03e8";
	Double d = platformService.queryDistanceByPlatformGoodsAreaVir(orgCode, platformVirtualCode, goodsAreaVirtualCode);
	Assert.assertNotNull(d);
	log.debug(d);
    }

    

}
