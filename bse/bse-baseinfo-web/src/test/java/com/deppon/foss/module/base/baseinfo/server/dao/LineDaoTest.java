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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LineDaoTest.java
 * 
 * FILE NAME        	: LineDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 线路Dao测试类
 * @author foss-zhujunyong
 * @date Oct 25, 2012 1:19:12 PM
 * @version 1.0
 */
public class LineDaoTest {

    private JdbcTemplate jdbc;
    private ILineDao lineDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	lineDao = (ILineDao) SpringTestHelper.get().getBeanByClass(LineDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_line where line_name like 'zjy%'");
    }
    
    
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#addLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)}.
     */
    @Test
    public void testAddLine() {
	LineEntity c = new LineEntity();
	c.setLineName("zjy112333");
	c.setCommonAging(100L);
	c.setNotes("notes");
	c = lineDao.addLine(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#deleteLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)}.
     */
    @Test
    public void testDeleteLine() {
	LineEntity c = new LineEntity();
	c.setLineName("zjyxxxx");
	c = lineDao.addLine(c);
	c = lineDao.deleteLine(c);
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#updateLine(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)}.
     */
    @Test
    public void testUpdateLine() {
	LineEntity c = new LineEntity();
	c.setLineName("zjy上海-北京特快");
	c = lineDao.addLine(c);
	c.setLineName("zjy上海-北京普快");
	c = lineDao.updateLine(c);
	Assert.assertNotNull(c);

    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#queryLineById(java.lang.String)}.
     */
    @Test
    public void testQueryLineById() {
	LineEntity c = new LineEntity();
	c.setLineName("zjy上海-北京特快");
	c = lineDao.addLine(c);
	
	c = lineDao.queryLineById(c.getId());
	Assert.assertNotNull(c);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity, int, int)}.
     */
    @Test
    public void testQueryLineListByCondition() {
	LineEntity c = new LineEntity();
	c.setLineName("zjy上海-北京特快");
	c = lineDao.addLine(c);
	c.setLineName("zjy上海");
	List<LineEntity> list = (List<LineEntity>) lineDao.queryLineListByCondition(c, 0, 20);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao#countLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)}.
     */
    @Test
    public void testCountLineListByCondition() {
	LineEntity c = new LineEntity();
	c.setLineName("zjy上海-北京特快");
	c = lineDao.addLine(c);
	c.setLineName("zjy上海");
	long count = lineDao.countLineListByCondition(c);
	Assert.assertTrue(count > 0);
    }

}
