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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/DepartureStandardDaoTest.java
 * 
 * FILE NAME        	: DepartureStandardDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 发车标准测试类
 * @author foss-zhujunyong
 * @date Oct 26, 2012 1:35:50 PM
 * @version 1.0
 */
public class DepartureStandardDaoTest {

    private JdbcTemplate jdbc;
    private IDepartureStandardDao departureStandardDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	departureStandardDao = (IDepartureStandardDao) SpringTestHelper.get().getBeanByClass(DepartureStandardDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_departure_std where LINE_VIRTUAL_CODE like 'zjy%'");
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao#addDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)}.
     */
    @Test
    public void testAddDepartureStandard() {
	DepartureStandardEntity c = new DepartureStandardEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = departureStandardDao.addDepartureStandard(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao#deleteDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)}.
     */
    @Test
    public void testDeleteDepartureStandard() {
	DepartureStandardEntity c = new DepartureStandardEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = departureStandardDao.addDepartureStandard(c);
	Assert.assertNotNull(c);

	c = departureStandardDao.deleteDepartureStandard(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao#updateDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)}.
     */
    @Test
    public void testUpdateDepartureStandard() {
	DepartureStandardEntity c = new DepartureStandardEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = departureStandardDao.addDepartureStandard(c);
	Assert.assertNotNull(c);
	
	c.setLeaveTime("2050");
	c = departureStandardDao.updateDepartureStandard(c);
	Assert.assertEquals("2050", c.getLeaveTime());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao#queryDepartureStandardById(java.lang.String)}.
     */
    @Test
    public void testQueryDepartureStandardById() {
	DepartureStandardEntity c = new DepartureStandardEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = departureStandardDao.addDepartureStandard(c);
	Assert.assertNotNull(c);

	c = departureStandardDao.queryDepartureStandardById(c.getId());
	Assert.assertNotNull(c);
	Assert.assertEquals(c.getCreateUser(), "abcd");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao#queryDepartureStandardListByLineVirtualCode(java.lang.String)}.
     */
    @Test
    public void testQueryDepartureStandardListByLineVirtualCode() {
	String lineVirtualCode = "zjy12345";
	
	DepartureStandardEntity c = new DepartureStandardEntity();
	c.setLineVirtualCode(lineVirtualCode);
	c.setCreateUser("abcd");
	c = departureStandardDao.addDepartureStandard(c);
	Assert.assertNotNull(c);

	List<DepartureStandardEntity> list = departureStandardDao.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
	Assert.assertTrue(list.size() > 0);
    }

}
