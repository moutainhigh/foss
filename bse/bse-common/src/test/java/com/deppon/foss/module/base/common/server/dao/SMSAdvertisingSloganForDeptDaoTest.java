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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/server/dao/SMSAdvertisingSloganForDeptDaoTest.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganForDeptDaoTest.java
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
package com.deppon.foss.module.base.common.server.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;
import com.deppon.foss.module.base.common.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门短信广告语DAO测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-17 上午10:17:52 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-17 上午10:17:52
 * @since
 * @version
 */
public class SMSAdvertisingSloganForDeptDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ISMSAdvertisingSloganForDeptDao sMSAdvertisingSloganForDeptDao;
    private SloganAppOrgEntity entity = new SloganAppOrgEntity();

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 上午10:17:52
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	sMSAdvertisingSloganForDeptDao = SpringTestHelper.get().getBeanByInterface(ISMSAdvertisingSloganForDeptDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 上午10:17:52
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_SLOGAN_APP_ORG");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganForDeptDao#addSMSAdvertisingSloganForDept(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testAddSMSAdvertisingSloganForDept() {
	entity.setCreateUser("谢艳涛");
	entity.setOrgCode("000012");
	entity.setSloganCode("000003");
	entity.setSloganContent("部门短信广告语测试");
	entity.setSloganSort("1");
	entity.setActive(FossConstants.ACTIVE);
	int result = sMSAdvertisingSloganForDeptDao.addSMSAdvertisingSloganForDept(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganForDeptDao#deleteSMSAdvertisingSloganForDeptByCode(java.lang.String[])}.
     */
    @Ignore
    @Test
    public void testDeleteSMSAdvertisingSloganForDeptByCode() {
	String[] codes = {"73e0e185-ebc9-4e6a-a929-05d2fc09563d","79e53f69-31ac-4ce3-b441-25bc36d79395"};
	int result = sMSAdvertisingSloganForDeptDao.deleteSMSAdvertisingSloganForDeptByCode(codes, "李四");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganForDeptDao#updateSMSAdvertisingSloganForDept(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSMSAdvertisingSloganForDept() {
	
	entity.setId("79e53f69-31ac-4ce3-b441-25bc36d79395");
	entity.setModifyUser("李峰");
	entity.setOrgCode("000011");
	entity.setSloganContent("部门短信广告语测试11");
	int result = sMSAdvertisingSloganForDeptDao.updateSMSAdvertisingSloganForDept(entity);
	Assert.assertTrue(result == 1);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganForDeptDao#querySMSAdvertisingSloganForDepts(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySMSAdvertisingSloganForDepts() {
	
	entity.setSloganSort("1");
	entity.setActive(FossConstants.ACTIVE);
	List<SloganAppOrgEntity> list = sMSAdvertisingSloganForDeptDao.querySMSAdvertisingSloganForDepts(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganForDeptDao#getCount(com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setSloganSort("1");
	entity.setActive(FossConstants.ACTIVE);
	Long result = sMSAdvertisingSloganForDeptDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }
    
    /**
     * <p>根据短信广告语虚拟编码、部门编码查询部门短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午5:17:03
     * @see
     */
    @Ignore
    @Test
    public void testQuerySloganAppOrgByCode(){
	SloganAppOrgEntity entity = sMSAdvertisingSloganForDeptDao.querySloganAppOrgByCode("000013", "00031",null);
	
	Assert.assertNotNull(entity);
    }

}
