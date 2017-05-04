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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/FreightRouteDaoTest.java
 * 
 * FILE NAME        	: FreightRouteDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 线路Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 1:19:12 PM
 * @version 1.0
 */
public class FreightRouteDaoTest {

    private JdbcTemplate jdbc;
    private IFreightRouteDao freightRouteDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	freightRouteDao = (IFreightRouteDao) SpringTestHelper.get().getBeanByClass(FreightRouteDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from T_BAS_FREIGHT_ROUTE where notes like 'zjy%'");
    }
    
    
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#addFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)}.
     */
    @Test
    public void testAddFreightRoute() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setDefaultRoute(FossConstants.YES);
	c.setAging(100L);
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#deleteFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)}.
     */
    @Test
    public void testDeleteFreightRoute() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	c = freightRouteDao.deleteFreightRoute(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#updateFreightRoute(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)}.
     */
    @Test
    public void testUpdateFreightRoute() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setDefaultRoute(FossConstants.YES);
	c.setAging(100L);
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	c.setTransType("123");
	c = freightRouteDao.updateFreightRoute(c);
	Assert.assertNotNull(c);

    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#queryFreightRouteById(java.lang.String)}.
     */
    @Test
    public void testQueryFreightRouteById() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setDefaultRoute(FossConstants.YES);
	c.setAging(100L);
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	
	c = freightRouteDao.queryFreightRouteById(c.getId());
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#queryFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity, int, int)}.
     */
    @Test
    public void testQueryFreightRouteListByCondition() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setDefaultRoute(FossConstants.YES);
	c.setOrginalOrganizationCode("123321");
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	c.setOrginalOrganizationCode("123321");
	List<FreightRouteEntity> list = (List<FreightRouteEntity>) freightRouteDao.queryFreightRouteListByCondition(c, 0, 20);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao#countFreightRouteListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity)}.
     */
    @Test
    public void testCountFreightRouteListByCondition() {
	FreightRouteEntity c = new FreightRouteEntity();
	c.setOrginalOrganizationCode("123321");
	c.setNotes("zjynotes");
	c = freightRouteDao.addFreightRoute(c);
	c.setOrginalOrganizationCode("123321");
	long count = freightRouteDao.countFreightRouteListByCondition(c);
	Assert.assertTrue(count > 0);
    }

}
