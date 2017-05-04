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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/GoodsAreaDaoTest.java
 * 
 * FILE NAME        	: GoodsAreaDaoTest.java
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
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 库区Dao测试类
 * @author foss-zhujunyong
 * @date Oct 22, 2012 3:00:01 PM
 * @version 1.0
 */
public class GoodsAreaDaoTest {

    private JdbcTemplate jdbc;
    private IGoodsAreaDao goodsAreaDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	goodsAreaDao = (IGoodsAreaDao) SpringTestHelper.get().getBeanByClass(GoodsAreaDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_goods_area where NOTES like 'zjy%'");
    }
    

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#addGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)}.
     */
    @Test
    public void testAddGoodsArea() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#deleteGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)}.
     */
    @Test
    public void testDeleteGoodsArea() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);
	
	p = goodsAreaDao.deleteGoodsArea(p);
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#updateGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)}.
     */
    @Test
    public void testUpdateGoodsArea() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);

	p.setGoodsType("11111111111");
	p.setModifyUser("abc");
	p.setNotes("zjy111");
	p = goodsAreaDao.updateGoodsArea(p);
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#queryGoodsAreaByVirtualCode(java.lang.String)}.
     */
    @Test
    public void testQueryGoodsAreaByVirtualCode() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);

	p = goodsAreaDao.queryGoodsAreaByVirtualCode(p.getVirtualCode());
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity, int, int)}.
     */
    @Test
    public void testQueryGoodsAreaByCondition() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setOrganizationCode("33211");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);
	
	List<GoodsAreaEntity> list = goodsAreaDao.queryGoodsAreaByCondition(p, 0, 20);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#countGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)}.
     */
    @Test
    public void testCountGoodsAreaByCondition() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setOrganizationCode("33211");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);
	
	long count = goodsAreaDao.countGoodsAreaByCondition(p);
	Assert.assertTrue(count > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao#queryGoodsAreaListByOrganizationCode(java.lang.String)}.
     */
    @Test
    public void testQueryGoodsAreaListByOrganizationCode() {
	GoodsAreaEntity p = new GoodsAreaEntity();
	p.setGoodsAreaCode("12345");
	p.setOrganizationCode("33211");
	p.setGoodsAreaName("GoodsAreaName AAA");
	p.setGoodsType("123");
	p.setGoodsAreaType("321");
	p.setGoodsAreaUsage("123");
	p.setCreateUser("abbreviation");
	p.setNotes("zjy111");
	p = goodsAreaDao.addGoodsArea(p);
	Assert.assertNotNull(p);
	
	List<GoodsAreaEntity> list = goodsAreaDao.queryGoodsAreaListByOrganizationCode("33211", null);
	Assert.assertTrue(list.size() > 0);
    }

}
