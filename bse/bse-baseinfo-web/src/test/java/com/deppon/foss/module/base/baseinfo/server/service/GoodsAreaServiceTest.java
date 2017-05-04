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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/GoodsAreaServiceTest.java
 * 
 * FILE NAME        	: GoodsAreaServiceTest.java
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

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.GoodsAreaService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库区服务测试类
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:53:20 AM
 * @version 1.0
 */
public class GoodsAreaServiceTest {

    private JdbcTemplate jdbc;
    private IGoodsAreaService goodsAreaService;
    private static final Logger log = Logger.getLogger(GoodsAreaServiceTest.class);
    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	goodsAreaService = (IGoodsAreaService) SpringTestHelper.get().getBeanByClass(GoodsAreaService.class);
//	((RefreshableCache) CacheManager.getInstance().getCache(GoodsAreaCache.class.getName())).refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.GoodsAreaService#queryNameByCode(java.lang.String, java.lang.String)}.
     */
    @Test
    @Ignore
    public void testQueryNameByCode() {
	
	
	String code = "zhujunyong1002";
	String name = "zhujunyong广东卡";
	String organizationCode = "zhujunyong1001";
	
	GoodsAreaEntity c = new GoodsAreaEntity();
	c.setGoodsAreaCode(code);
	c.setGoodsAreaName(name);
	c.setOrganizationCode(organizationCode);
	
	c = goodsAreaService.addGoodsArea(c);
	
	String queryedName = goodsAreaService.queryNameByCode(organizationCode, code);
	Assert.assertEquals(name, queryedName);
	
	jdbc.execute("delete from t_bas_goods_area where id = '" + c.getId() + "'");
	
    }

    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.GoodsAreaService#queryCodeByArriveRegionCode(java.lang.String, java.lang.String)}.
     */
    @SuppressWarnings("deprecation")
    @Test
    @Ignore
    public void testQueryCodeByArriveRegionCode() {
	String code = "zhujunyong1002";
	String organizationCode = "zhujunyong1001";
	String arriveRegionCode = "zhujunyong1002";
	
	GoodsAreaEntity c = new GoodsAreaEntity();
	c.setGoodsAreaCode(code);
	c.setOrganizationCode(organizationCode);
	c.setArriveRegionCode(arriveRegionCode);
	
	c = goodsAreaService.addGoodsArea(c);
	
	String queryedCode = goodsAreaService.queryCodeByArriveRegionCode(organizationCode, arriveRegionCode);
	Assert.assertEquals(code, queryedCode);

	jdbc.execute("delete from t_bas_goods_area where id = '" + c.getId() + "'");
	
    }
    
    @Test
    @Ignore
    public void testQueryCode() {
	String orgCode = "W040002060401";
	String goodsAreaCode = "003";
	
	long s = System.currentTimeMillis();
	jdbc.execute("update t_bas_goods_area set version_no = "+s+" where id = 'KQ-ID-00003'");
	try {
	    Thread.sleep(1000L);
	} catch (InterruptedException e) {
	    log.error("", e);
	}
//	goodsAreaService.flushCache();

	Date d = new Date();
	GoodsAreaEntity entity = goodsAreaService.queryGoodsAreaByCode(orgCode, goodsAreaCode);
	Date d2 = new Date();
	log.info("d2-d1=" + (d2.getTime() - d.getTime()));
	Assert.assertNotNull(entity);
	log.info("code=" + entity.getGoodsAreaCode());
	log.info("name=" + entity.getGoodsAreaName());
    }
    
    @Test
    public void testQueryGoodsAreaByCondition(){
	GoodsAreaEntity c = new GoodsAreaEntity();
	c.setActive(FossConstants.ACTIVE);
	c.setOrganizationCode("W3100020616");
	c.setArriveRegionCode("W3100020620");
	List<GoodsAreaEntity> list = goodsAreaService.queryGoodsAreaByCondition(c, 0, 1);
	Assert.assertNotNull(list);
    }
    
}
