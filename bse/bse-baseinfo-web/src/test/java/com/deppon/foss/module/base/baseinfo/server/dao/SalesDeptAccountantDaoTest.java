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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/SalesDeptAccountantDaoTest.java
 * 
 * FILE NAME        	: SalesDeptAccountantDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao
 * FILE    NAME: SalesDeptAccountantDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;


/**
 * @author 027443-foss-zhaopeng
 * @date 2012-11-7 上午11:07:32
 */
public class SalesDeptAccountantDaoTest {
    
    private JdbcTemplate jdbc;
    private ISalesDeptAccountantDao salesDeptAccountantDao;
    private SalesDeptAccountantEntity entity = null;
    private String id = null;

    /**
     * @author 027443-foss-zhaopeng
     * @date 2012-11-7 上午11:07:32
     */
    @Before
    public void setUp() throws Exception {
	entity = new SalesDeptAccountantEntity();
	jdbc = (JdbcTemplate)SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	salesDeptAccountantDao = SpringTestHelper.get().getBeanByClass(SalesDeptAccountantDao.class);
	
    }

    /**
     * @author 027443-foss-zhaopeng
     * @date 2012-11-7 上午11:07:32
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao#addSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)}.
     */
    @Ignore
    @Test
    public void testAddSalesDeptAccountant() {
	
	id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setAccountantNO("027443");
	entity.setAccountantName("赵鹏");
	entity.setAccountantDept("所属部门ID");
	entity.setSalesDepartment("所辖部门ID");
	entity.setCreateDate(new Date());
	entity.setCreateUser("000123");
	entity.setModifyDate(new Date());
	entity.setModifyUser("000123");
	int result = salesDeptAccountantDao.addSalesDeptAccountant(entity);
	org.junit.Assert.assertTrue(result>0);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao#updateSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSalesDeptAccountant() {
	
	entity.setId(id);
	entity.setAccountantNO("027444");
	entity.setAccountantName("赵大鹏");
	entity.setAccountantDept("所属部门ID");
	entity.setSalesDepartment("所辖部门ID");
	entity.setCreateDate(new Date());
	entity.setCreateUser("000123");
	entity.setModifyDate(new Date());
	entity.setModifyUser("000123");
	
	int result = salesDeptAccountantDao.updateSalesDeptAccountant(entity);
	org.junit.Assert.assertTrue(result>0);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao#deleteSalesDeptAccountantByCodes(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSalesDeptAccountantByCodes() {
	
	String[] ids = {"fc6e1780-b570-470c-8168-55c6aa3e1b01","bd9a82e7-396e-45e3-92d8-529686f3b14a"};
	
	int result = salesDeptAccountantDao.deleteSalesDeptAccountantByCodes(ids, "赵小鹏");
	Assert.assertTrue(result>0);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)}.
     */
    @Test
    public void testQueryRecordCount() {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDeptAccountantDao#querySalesDeptAccountantGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySalesDeptAccountantGroup() {
	
	entity.setAccountantNO("027443");
	List<SalesDeptAccountantEntity>  list = salesDeptAccountantDao.querySalesDeptAccountantGroup(entity, 10, 0);
	
	Assert.assertTrue(list.size()>0);
    }

}
