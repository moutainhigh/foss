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

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 营业部集中开单组测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class SalesBillingGroupServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private ISalesBillingGroupService salesBillingGroupService;
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(SalesBillingGroupServiceTest.class);
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	salesBillingGroupService = (ISalesBillingGroupService) SpringTestHelper.get().getBeanByClass(SalesBillingGroupService.class);
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService#mergeSalesBillingGroupDto(com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupDto)}.
     */
    @Test
    public void testMergeSalesBillingGroupDto() {
	SalesBillingGroupDto dto = new SalesBillingGroupDto();
	dto.setSalesDeptCode("sales001");
	dto.setSalesDeptName("营业部001");
	dto.setModifyUserCode("zjy001");
	List<MapDto> list = new ArrayList<MapDto> ();
	MapDto c1 = new MapDto();
	c1.setCode("bill001");
	c1.setName("开单组001");
	MapDto c2 = new MapDto();
	c2.setCode("bill002");
	c2.setName("开单组002");
	list.add(c1);
//	list.add(c2);
	dto.setBillingGroupList(list);
	salesBillingGroupService.mergeSalesBillingGroupDto(dto);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService#deleteSalesBillingGroupDtoBySalesCode(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testDeleteSalesBillingGroupDtoBySalesCode() {
	salesBillingGroupService.deleteSalesBillingGroupDtoBySalesCode("sales001", "somebody");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService#querySalesListByBillingGroupCode(java.lang.String)}.
     */
    @Test
    public void testQuerySalesListByBillingGroupCode() {
	List<MapDto> list = salesBillingGroupService.querySalesListByBillingGroupCode("bill001");
	Assert.assertNotNull(list);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService#queryBillingGroupListBySalesCode(java.lang.String)}.
     */
    @Test
    public void testQueryBillingGroupListBySalesCode() {
	List<MapDto> list = salesBillingGroupService.queryBillingGroupListBySalesCode("sales001");
	Assert.assertNotNull(list);
    }


    
    

}
