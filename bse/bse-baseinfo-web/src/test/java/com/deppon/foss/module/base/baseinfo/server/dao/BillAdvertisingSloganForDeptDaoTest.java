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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/BillAdvertisingSloganForDeptDaoTest.java
 * 
 * FILE NAME        	: BillAdvertisingSloganForDeptDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门单据广告语测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-18 上午10:41:40 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-18 上午10:41:40
 * @since
 * @version
 */
public class BillAdvertisingSloganForDeptDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IBillAdvertisingSloganForDeptDao billAdvertisingSloganForDeptDao;
    private BillSloganAppOrgEntity entity = new BillSloganAppOrgEntity();

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午10:41:40
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	billAdvertisingSloganForDeptDao = SpringTestHelper.get().getBeanByInterface(IBillAdvertisingSloganForDeptDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午10:41:40
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	
	jdbc.execute("delete from T_BAS_SLOGAN_APP_ORG");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganForDeptDao#addBillAdvertisingSloganForDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testAddBillAdvertisingSloganForDept() {
	
	entity.setCreateUser("谢艳涛");
	entity.setOrgCode("000013");
	entity.setSloganCode("00031");
	entity.setSloganContent("部门单据广告语测试");
	entity.setSloganSort("2");
	entity.setActive(FossConstants.ACTIVE);
	int result = billAdvertisingSloganForDeptDao.addBillAdvertisingSloganForDept(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganForDeptDao#deleteBillAdvertisingSloganForDeptByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteBillAdvertisingSloganForDeptByCode() {
	
	String[] codes = {"5ae38182-1116-472a-aadf-9d5a5866ccc5","efc2094b-9af8-46fe-a6d1-9692079910bc"};
	int result = billAdvertisingSloganForDeptDao.deleteBillAdvertisingSloganForDeptByCode(codes, "陈飞");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganForDeptDao#updateBillAdvertisingSloganForDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateBillAdvertisingSloganForDept() {
	
	entity.setId("5ae38182-1116-472a-aadf-9d5a5866ccc5");
	entity.setModifyUser("李小龙");
	entity.setOrgCode("000025");
	entity.setSloganContent("部门单据广告语测试11");
	int result = billAdvertisingSloganForDeptDao.updateBillAdvertisingSloganForDept(entity);
	Assert.assertTrue(result == 1);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganForDeptDao#queryBillAdvertisingSloganForDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryBillAdvertisingSloganForDepts() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("2");
	List<BillSloganAppOrgEntity> list = billAdvertisingSloganForDeptDao.queryBillAdvertisingSloganForDepts(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganForDeptDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("2");
	Long result = billAdvertisingSloganForDeptDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }

}
