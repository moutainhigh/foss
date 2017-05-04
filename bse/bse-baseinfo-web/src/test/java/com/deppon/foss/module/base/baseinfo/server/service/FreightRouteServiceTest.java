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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/FreightRouteServiceTest.java
 * 
 * FILE NAME        	: FreightRouteServiceTest.java
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;


/**
 * 走货路径测试类
 * @author foss-zhujunyong
 * @date Nov 9, 2012 9:15:05 AM
 * @version 1.0
 */
public class FreightRouteServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IFreightRouteService freightRouteService;
    private static final Logger log = Logger.getLogger(FreightRouteServiceTest.class);
    
    @Before
    public void setUp() throws Exception {
//	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	freightRouteService = (IFreightRouteService) SpringTestHelper.get().getBeanByClass(FreightRouteService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteService#queryFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)}.
     */
    @Test
    public void testQueryFreightRouteListByCondition() {
	FreightRouteEntity c = new FreightRouteEntity();
//	c.setOrginalOrganizationCode("123321");
	@SuppressWarnings("unused")
	List<FreightRouteEntity> list = freightRouteService.queryFreightRouteListByCondition(c, 0, 20);
//	Assert.assertNotNull(list);
//	Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testQueryFreightRouteBySourceTarget(){
//	List<FreightRouteLineDto> list = freightRouteService.queryFreightRouteBySourceTarget("W040002060401", "W060002070701", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, new Date());
//	List<FreightRouteLineDto> list = freightRouteService.queryFreightRouteBySourceTarget("GS00002", "W06071401", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, new Date());
//	List<FreightRouteLineDto> list = freightRouteService.queryFreightRouteBySourceTarget("W04061418", "W0600307063509", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, new Date());
	List<FreightRouteLineDto> list = freightRouteService.queryFreightRouteBySourceTarget("W0600307063509", "W011305080717", PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, new Date());
	Assert.assertNotNull(list);
	for (FreightRouteLineDto dto : list) {
	    log.debug(dto.getSourceCode());
	    log.debug(dto.getTargetCode());
	    log.debug(dto.getLineVirtualCode());
	    log.debug("lineSort=" + dto.getLineSort());
	    log.debug("lineType=" + dto.getLineType());
	    if (dto.getLeaveDate() != null)
		log.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dto.getLeaveDate()));
	    if (dto.getArriveDate() != null)
		log.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dto.getArriveDate()));
	}
    }
    
    @Test
    public void testQueryRouteString(){
	String route = freightRouteService.queryRouteStringByVirtualCode("W011302020515", "PX0408WD", "c389c537-7b74-4d76-96d0-375a126f8f35");
	System.out.println(route);
    }
    
}
