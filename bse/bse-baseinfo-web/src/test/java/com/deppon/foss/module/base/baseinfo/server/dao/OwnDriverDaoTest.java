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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/OwnDriverDaoTest.java
 * 
 * FILE NAME        	: OwnDriverDaoTest.java
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
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“公司司机”的数据库对应数据访问DAO接口：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-31 下午2:36:52</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-31 下午2:36:52
 * @since
 * @version
 */
public class OwnDriverDaoTest {

    //“公司司机”数据访问类
    private IOwnDriverDao ownDriverDao;
    
    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“公司司机”对象
    private OwnDriverEntity ownDriver = new OwnDriverEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	ownDriverDao = SpringTestHelper.get().getBeanByInterface(IOwnDriverDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete BSE.T_BAS_OWNDRIVER");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#addOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)}.
     */
    @Ignore
    @Test
    public void testAddOwnDriver() {
	ownDriver.setEmpCode("YG-0001");
	ownDriver.setEmpName("张三");
	ownDriver.setEmpPhone("137569823654");
	ownDriver.setOrgId("BM-0001");
	ownDriver.setCreateUser("100847-foss-GaoPeng");
	
	int result = ownDriverDao.addOwnDriver(ownDriver);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#addOwnDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)}.
     */
    @Ignore
    @Test
    public void testAddOwnDriverBySelective() {
	ownDriver.setEmpCode("YG-0002");
	ownDriver.setEmpName("李四");
	ownDriver.setOrgId("BM-0002");
	ownDriver.setCreateUser("100847-foss-GaoPeng");
	ownDriver.setEmpPhone(null);
	
	int result = ownDriverDao.addOwnDriverBySelective(ownDriver);
	Assert.assertTrue(result > 0);
	
	OwnDriverDao ownDriverDao = (OwnDriverDao) this.ownDriverDao;
	OwnDriverEntity ownDriveremp = ownDriverDao.queryOwnDriver(ownDriver.getId());
	
	Assert.assertNotNull(ownDriveremp);
	Assert.assertNull(ownDriveremp.getEmpPhone());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#deleteOwnDriver(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteOwnDriver() {
	//准备数据
	testAddOwnDriver();
	
	int result = ownDriverDao.deleteOwnDriver(ownDriver.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#updateOwnDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateOwnDriverBySelective() {
	//准备数据
	testAddOwnDriver();
	
	ownDriver.setEmpName(null);
	
	int result = ownDriverDao.updateOwnDriverBySelective(ownDriver);
	Assert.assertTrue(result > 0);
	
	OwnDriverDao ownDriverDao = (OwnDriverDao) this.ownDriverDao;
	OwnDriverEntity ownDriveremp = ownDriverDao.queryOwnDriver(ownDriver.getId());
	
	Assert.assertNotNull(ownDriveremp);
	Assert.assertNotNull(ownDriveremp.getEmpName());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#updateOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateOwnDriver() {
	//准备数据
	testAddOwnDriver();
	
	ownDriver.setEmpCode("YG-0003");
	ownDriver.setEmpName("王五");
	
	int result = ownDriverDao.updateOwnDriver(ownDriver);
	Assert.assertTrue(result > 0);
	
	OwnDriverDao ownDriverDao = (OwnDriverDao) this.ownDriverDao;
	OwnDriverEntity ownDriveremp = ownDriverDao.queryOwnDriver(ownDriver.getId());
	
	Assert.assertNotNull(ownDriveremp);
	Assert.assertEquals(ownDriver.getEmpCode(), ownDriveremp.getEmpCode());
	Assert.assertEquals(ownDriver.getEmpName(), ownDriveremp.getEmpName());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#queryOwnDriver(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryOwnDriver() {
	//准备数据
	testAddOwnDriver();
	
	OwnDriverEntity ownDriveremp = ownDriverDao.queryOwnDriver(ownDriver.getId());
	Assert.assertNotNull(ownDriveremp);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnDriverDao#queryOwnDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryOwnDriverListBySelectiveCondition() {
	//准备数据
	testAddOwnDriver();
	
	ownDriver.setActive(FossConstants.ACTIVE);
	
	List<OwnDriverEntity> ownDriveremps = ownDriverDao.queryOwnDriverListBySelectiveCondition(ownDriver, 0, 2);
	Assert.assertNotNull(ownDriveremps);
	Assert.assertTrue(ownDriveremps.size() > 0);
    }
}
