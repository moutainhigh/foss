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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LineItemDaoTest.java
 * 
 * FILE NAME        	: LineItemDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 线段Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 4:32:38 PM
 * @version 1.0
 */
public class LineItemDaoTest {

    private JdbcTemplate jdbc;
    private ILineItemDao lineItemDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	lineItemDao = (ILineItemDao) SpringTestHelper.get().getBeanByClass(LineItemDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_line_item where LINE_VIRTUAL_CODE like 'zjy%'");
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao#addLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)}.
     */
    @Test
    public void testAddLineItem() {
	LineItemEntity c = new LineItemEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c.setCommonAging(12000L);
	c.setFastAging(10000L);
	c.setPassbyAging(2000L);
	c = lineItemDao.addLineItem(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao#deleteLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)}.
     */
    @Test
    public void testDeleteLineItem() {
	LineItemEntity c = new LineItemEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = lineItemDao.addLineItem(c);
	Assert.assertNotNull(c);

	c = lineItemDao.deleteLineItem(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao#updateLineItem(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity)}.
     */
    @Test
    public void testUpdateLineItem() {
	LineItemEntity c = new LineItemEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = lineItemDao.addLineItem(c);
	Assert.assertNotNull(c);
	
	c.setDistance(100L);
	c = lineItemDao.updateLineItem(c);
	Assert.assertEquals(new Long(100), c.getDistance());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao#queryLineItemById(java.lang.String)}.
     */
    @Test
    public void testQueryLineItemById() {
	LineItemEntity c = new LineItemEntity();
	c.setLineVirtualCode("zjy112233");
	c.setCreateUser("abcd");
	c = lineItemDao.addLineItem(c);
	Assert.assertNotNull(c);

	c = lineItemDao.queryLineItemById(c.getId());
	Assert.assertNotNull(c);
	Assert.assertEquals(c.getCreateUser(), "abcd");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao#queryLineItemListByLineVirtualCode(java.lang.String)}.
     */
    @Test
    public void testQueryLineItemListByLineVirtualCode() {
	String lineVirtualCode = "zjy12345";
	
	LineItemEntity c = new LineItemEntity();
	c.setLineVirtualCode(lineVirtualCode);
	c.setCreateUser("abcd");
	c = lineItemDao.addLineItem(c);
	Assert.assertNotNull(c);

	List<LineItemEntity> list = lineItemDao.queryLineItemListByLineVirtualCode(lineVirtualCode);
	Assert.assertTrue(list.size() > 0);
    }

}
