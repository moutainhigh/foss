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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LeasedDriverDaoTest.java
 * 
 * FILE NAME        	: LeasedDriverDaoTest.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 用来测试交互“外请车司机”的数据库对应数据访问DAO接口实现类：SUC-211 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午11:14:32</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午11:14:32
 * @since
 * @version
 */
public class LeasedDriverDaoTest {

    //“外请司机”数据访问类
    private ILeasedDriverDao leasedDriverDao;
    
    //JDBC辅助数据访问类
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    
    //“外请司机”对象
    private LeasedDriverEntity leasedDriver = new LeasedDriverEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	leasedDriverDao = SpringTestHelper.get().getBeanByInterface(ILeasedDriverDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete T_BAS_LEASED_DRIVER");
    }

    /**
     * 根据“外请车司机”记录唯一标识作废一条“外请车司机”记录
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#deleteLeasedDriver(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteLeasedDriver() {
	//准备数据
	testAddLeasedDriver();
	
	int result = leasedDriverDao.deleteLeasedDriver(leasedDriver.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * 新增一个“外请车司机”实体入库（忽略实体中是否存在空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#addLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedDriver() {
	leasedDriver.setDriverName("刘七");
	leasedDriver.setDriverPhone("13844444444");
	leasedDriver.setIdCard("44444444444444444");
	leasedDriver.setAddress("上海市青浦区徐泾镇");
	leasedDriver.setRelativeName("王二麻子");
	leasedDriver.setRelativePhone("18044444444");
	leasedDriver.setDriverLicense("JSZ-444444");
	leasedDriver.setQualification("CYZG-444444");
	leasedDriver.setLicenseType("B");
	leasedDriver.setBeginTime(new Date());
	leasedDriver.setExpirationDate(new BigDecimal(9));
	leasedDriver.setNotes("备注");
	leasedDriver.setActive(FossConstants.ACTIVE);
	leasedDriver.setCreateUser("100847-foss-GaoPeng");
	
	int result = leasedDriverDao.addLeasedDriver(leasedDriver);
	Assert.assertTrue(result > 0);
	
	LeasedDriverDao leasedDriverDao = (LeasedDriverDao) this.leasedDriverDao;
	Assert.assertNotNull(leasedDriverDao.queryLeasedDriverBySelective(leasedDriver.getId()));
    }

    /**
     * 新增一个“外请车司机”实体入库（忽略实体中是否存在空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#addLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedDriverBySelective() {
	leasedDriver.setDriverName("李四");
	leasedDriver.setDriverPhone("18756923458");
	leasedDriver.setIdCard("2222222222222222222222");
	leasedDriver.setAddress("上海市青浦区徐泾镇");
	leasedDriver.setLicenseType("B");
	leasedDriver.setBeginTime(new Date());
	leasedDriver.setExpirationDate(new BigDecimal(6));
	leasedDriver.setNotes("备注");
	leasedDriver.setActive(FossConstants.INACTIVE);
	leasedDriver.setCreateUser("100847-foss-GaoPeng");
	
	leasedDriver.setRelativeName(null);
	leasedDriver.setRelativePhone(null);
	leasedDriver.setDriverLicense(null);
	leasedDriver.setQualification(null);
	
	int result = leasedDriverDao.addLeasedDriverBySelective(leasedDriver);
	Assert.assertTrue(result > 0);
	
	LeasedDriverDao leasedDriverDao = (LeasedDriverDao) this.leasedDriverDao;
	LeasedDriverEntity leasedDriverTemp = leasedDriverDao.queryLeasedDriverBySelective(leasedDriver.getId());
	
	Assert.assertNotNull(leasedDriverTemp);
	Assert.assertNull(leasedDriverTemp.getRelativeName());
	Assert.assertNull(leasedDriverTemp.getRelativePhone());
	Assert.assertNull(leasedDriverTemp.getDriverLicense());
	Assert.assertNull(leasedDriverTemp.getQualification());
    }

    /**
     * 根据“外请车司机”记录唯一标识查询出一条“外请车司机”记录
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#queryLeasedDriver(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedDriver() {
	//准备数据
	testAddLeasedDriver();
	
	leasedDriver = leasedDriverDao.queryLeasedDriverBySelective(leasedDriver.getId());
	Assert.assertNotNull(leasedDriver); 
    }

    /**
     * 根据条件有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#queryLeasedDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedDriverListBySelectiveCondition() {
	//准备数据
	testAddLeasedDriver();
	
	leasedDriver = new LeasedDriverEntity();
	leasedDriver.setActive(FossConstants.ACTIVE);
	
	List<LeasedDriverEntity> leasedDriverList = leasedDriverDao.queryLeasedDriverListBySelectiveCondition(leasedDriver, 0, 2);
	Assert.assertTrue(leasedDriverList.size() >0);
    }

    /**
     * 修改一个“外请车司机”实体入库 （只选择实体中非空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#updateLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedDriverBySelective() {
	//准备数据
	testAddLeasedDriver();
	
	leasedDriver.setDriverPhone(null);
	leasedDriver.setRelativeName(null);
	leasedDriver.setRelativePhone(null);
	leasedDriver.setDriverLicense(null);
	leasedDriver.setQualification(null);
	leasedDriver.setActive(FossConstants.INACTIVE);
	
	int result = leasedDriverDao.updateLeasedDriverBySelective(leasedDriver);
	Assert.assertTrue(result > 0);
	
	LeasedDriverDao leasedDriverDao = (LeasedDriverDao) this.leasedDriverDao;
	LeasedDriverEntity leasedDriverTemp = leasedDriverDao.queryLeasedDriverBySelective(leasedDriver.getId());
	Assert.assertNotNull(leasedDriverTemp);
	
	Assert.assertEquals(leasedDriverTemp.getActive(), leasedDriver.getActive());
	Assert.assertNotNull(leasedDriverTemp.getDriverPhone());
	Assert.assertNotNull(leasedDriverTemp.getRelativeName());
	Assert.assertNotNull(leasedDriverTemp.getRelativePhone());
	Assert.assertNotNull(leasedDriverTemp.getDriverLicense());
	Assert.assertNotNull(leasedDriverTemp.getQualification());
    }

    /**
     * 修改一个“外请车司机”实体入库（忽略实体中是否存在空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedDriverDao#updateLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedDriver() {
	//准备数据
	testAddLeasedDriver();
	
	leasedDriver.setDriverName("赵六");
	leasedDriver.setDriverPhone("188222233333");
	leasedDriver.setIdCard("44444444444444444444444");
	leasedDriver.setLicenseType("A");
	leasedDriver.setActive(FossConstants.INACTIVE);
	leasedDriver.setRelativeName(null);
	leasedDriver.setRelativePhone(null);
	leasedDriver.setDriverLicense(null);
	leasedDriver.setQualification(null);
	
	int result = leasedDriverDao.updateLeasedDriver(leasedDriver);
	Assert.assertTrue(result > 0);
	
	LeasedDriverDao leasedDriverDao = (LeasedDriverDao) this.leasedDriverDao;
	LeasedDriverEntity leasedDriverTemp = leasedDriverDao.queryLeasedDriverBySelective(leasedDriver.getId());
	Assert.assertNotNull(leasedDriverTemp);
	
	Assert.assertEquals(leasedDriverTemp.getDriverName(), leasedDriver.getDriverName());
	Assert.assertEquals(leasedDriverTemp.getDriverPhone(), leasedDriver.getDriverPhone());
	Assert.assertEquals(leasedDriverTemp.getIdCard(), leasedDriver.getIdCard());
	Assert.assertEquals(leasedDriverTemp.getLicenseType(), leasedDriver.getLicenseType());
	Assert.assertEquals(leasedDriverTemp.getActive(), leasedDriver.getActive());
	
	Assert.assertNull(leasedDriverTemp.getRelativeName());
	Assert.assertNull(leasedDriverTemp.getRelativePhone());
	Assert.assertNull(leasedDriverTemp.getDriverLicense());
	Assert.assertNull(leasedDriverTemp.getQualification());
    }
}
