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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/NetGroupDaoTest.java
 * 
 * FILE NAME        	: NetGroupDaoTest.java
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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 网点组Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 1:19:12 PM
 * @version 1.0
 */
public class NetGroupDaoTest {

    private JdbcTemplate jdbc;
    private INetGroupDao netGroupDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	netGroupDao = (INetGroupDao) SpringTestHelper.get().getBeanByClass(NetGroupDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from T_BAS_NET_GROUP where NET_GROUP_NAME like 'zjy%'");
    }
    
    
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao#addNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)}.
     */
    @Test
    public void testAddNetGroup() {
	NetGroupEntity c = new NetGroupEntity();
	c.setNetGroupName("zjy112333");
	c.setFreightRouteVirtualCode("112233");
	c = netGroupDao.addNetGroup(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao#deleteNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)}.
     */
    @Test
    public void testDeleteNetGroup() {
	NetGroupEntity c = new NetGroupEntity();
	c.setNetGroupName("zjy112333");
	c.setFreightRouteVirtualCode("112233");
	c = netGroupDao.addNetGroup(c);
	c = netGroupDao.deleteNetGroup(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao#updateNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)}.
     */
    @Test
    public void testUpdateNetGroup() {
	NetGroupEntity c = new NetGroupEntity();
	c.setNetGroupName("zjy112333");
	c.setFreightRouteVirtualCode("112233");
	c = netGroupDao.addNetGroup(c);
	c = netGroupDao.updateNetGroup(c);
	Assert.assertNotNull(c);

    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao#queryNetGroupById(java.lang.String)}.
     */
    @Test
    public void testQueryNetGroupById() {
	NetGroupEntity c = new NetGroupEntity();
	c.setNetGroupName("zjy112333");
	c.setFreightRouteVirtualCode("112233");
	c = netGroupDao.addNetGroup(c);
	
	c = netGroupDao.queryNetGroupById(c.getId());
	Assert.assertNotNull(c);
    }


}
