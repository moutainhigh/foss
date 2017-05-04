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

import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 集中开单组外场关系测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class BillingGroupTransferServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IBillingGroupTransFerService billingGroupTransFerService;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(BillingGroupTransferServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	billingGroupTransFerService = (IBillingGroupTransFerService) SpringTestHelper.get().getBeanByClass(BillingGroupTransFerService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }

    @Test
    public void testQueryTransFerListByBillingGroupCode() {
	BillingGroupTransFerEntity entity = billingGroupTransFerService.queryTransFerListByBillingGroupCode("W011303060810");
	Assert.assertNotNull(entity);
    }
    
    @Test
    public void testQueryTransferCenterByBillingGroupCode() {
	MapDto dto = billingGroupTransFerService.queryTransferCenterByBillingGroupCode("W011303060810");
	Assert.assertNotNull(dto);
	Assert.assertEquals(dto.getCode(), "W01140402");
	Assert.assertEquals(dto.getName(), "顺德陈村枢纽中心");
    }
    
    @Test
    public void testMergeBillingGroupTransfer(){
	// 修改
	BillingGroupTransFerEntity entity = new BillingGroupTransFerEntity();
	entity.setBillingGroupCode("W011303060810");
	entity.setBillingGroupName("综合集中开单组");
	entity.setTransFerCode("WWW001");
	entity.setTransFerName("综合外场");
	entity.setModifyUser("zonghe");
	billingGroupTransFerService.mergeBillingGroupTransfer(entity);

	// 新增 
	entity.setCreateUser("createMe");
	entity.setBillingGroupCode("WWW002");
	billingGroupTransFerService.mergeBillingGroupTransfer(entity);

	// 删除
	entity = new BillingGroupTransFerEntity();
	entity.setBillingGroupCode("WWW002");
	entity.setModifyUser("god");
	billingGroupTransFerService.mergeBillingGroupTransfer(entity);
	
    }
    

}
