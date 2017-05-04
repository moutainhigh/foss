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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/FreightRouteLineDaoTest.java
 * 
 * FILE NAME        	: FreightRouteLineDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 线路Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 1:19:12 PM
 * @version 1.0
 */
public class FreightRouteLineDaoTest {

    private JdbcTemplate jdbc;
    private IFreightRouteLineDao freightRouteLineDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	freightRouteLineDao = (IFreightRouteLineDao) SpringTestHelper.get().getBeanByClass(FreightRouteLineDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from T_BAS_FREIGHT_ROUTE_LINE where LINE_VIRTUAL_CODE like 'zjy%'");
    }
    
    
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao#addFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)}.
     */
    @Test
    public void testAddFreightRouteLine() {
	FreightRouteLineEntity c = new FreightRouteLineEntity();
	c.setFreightRouteVirtualCode("123");
	c.setLineVirtualCode("zjyline123");
	c = freightRouteLineDao.addFreightRouteLine(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao#deleteFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)}.
     */
    @Test
    public void testDeleteFreightRouteLine() {
	FreightRouteLineEntity c = new FreightRouteLineEntity();
	c.setFreightRouteVirtualCode("123");
	c.setLineVirtualCode("zjyline123");
	c = freightRouteLineDao.addFreightRouteLine(c);
	c = freightRouteLineDao.deleteFreightRouteLine(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao#updateFreightRouteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity)}.
     */
    @Test
    public void testUpdateFreightRouteLine() {
	FreightRouteLineEntity c = new FreightRouteLineEntity();
	c.setFreightRouteVirtualCode("123");
	c.setLineVirtualCode("zjyline123");
	c = freightRouteLineDao.addFreightRouteLine(c);
	c.setAging(100L);
	c = freightRouteLineDao.updateFreightRouteLine(c);
	Assert.assertNotNull(c);

    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao#queryFreightRouteLineById(java.lang.String)}.
     */
    @Test
    public void testQueryFreightRouteLineById() {
	FreightRouteLineEntity c = new FreightRouteLineEntity();
	c.setFreightRouteVirtualCode("123");
	c.setLineVirtualCode("zjyline123");
	c = freightRouteLineDao.addFreightRouteLine(c);
	
	c = freightRouteLineDao.queryFreightRouteLineById(c.getId());
	Assert.assertNotNull(c);
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao#queryFreightRouteLineById(java.lang.String)}.
     */
    @Test
    public void testQueryFreightRouteLineByFreightRoute() {
	FreightRouteLineEntity c = new FreightRouteLineEntity();
	c.setFreightRouteVirtualCode("123");
	c.setLineVirtualCode("zjyline123");
	c = freightRouteLineDao.addFreightRouteLine(c);

	c.setLineVirtualCode(null);
	List<FreightRouteLineEntity> list = freightRouteLineDao.queryFreightRouteLineListByFreightRoute(c);
	Assert.assertNotNull(list);
	Assert.assertTrue(list.size() > 0);
    }


}
