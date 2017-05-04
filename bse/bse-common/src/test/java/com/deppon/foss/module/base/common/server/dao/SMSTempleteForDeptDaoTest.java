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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/server/dao/SMSTempleteForDeptDaoTest.java
 * 
 * FILE NAME        	: SMSTempleteForDeptDaoTest.java
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


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.module.base.common.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 部门短信模板Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-19 上午10:36:47 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-19 上午10:36:47
 * @since
 * @version
 */
public class SMSTempleteForDeptDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ISMSTempleteForDeptDao sMSTempleteForDeptDao;
    private TemplateAppOrgEntity entity = new TemplateAppOrgEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	sMSTempleteForDeptDao = SpringTestHelper.get().getBeanByInterface(ISMSTempleteForDeptDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 上午10:36:47
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	
	jdbc.execute("delete from T_BAS_SMS_TEMPLATE_APP_ORG");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteForDeptDao#addSMSTempleteForDept(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testAddSMSTempleteForDept() {
	
	entity.setCreateUser("谢艳涛");
	entity.setOrgId("000012");
	entity.setSmsContent("部门短信模板测试");
	entity.setSmsVirtualCode("002231");
	entity.setActive(FossConstants.ACTIVE);
	int result = sMSTempleteForDeptDao.addSMSTempleteForDept(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteForDeptDao#deleteSMSTempleteForDeptByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSMSTempleteForDeptByCode() {
	
	String[] codes = {"1d1b948f-645a-4a1c-a512-bf906a7ed7cb","6253a522-31d2-4aac-a72a-f1c83870603e"};
	int result = sMSTempleteForDeptDao.deleteSMSTempleteForDeptByCode(codes, "李四");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteForDeptDao#updateSMSTempleteForDept(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSMSTempleteForDept() {
	
	entity.setId("1d1b948f-645a-4a1c-a512-bf906a7ed7cb");
	entity.setModifyUser("李峰");
	entity.setOrgId("000011");
	entity.setSmsContent("部门短信模板测试11");
	int result = sMSTempleteForDeptDao.updateSMSTempleteForDept(entity);
	Assert.assertTrue(result == 1);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteForDeptDao#querySMSTempleteForDepts(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySMSTempleteForDepts() {
	
	entity.setActive(FossConstants.ACTIVE);
	List<TemplateAppOrgEntity> list = sMSTempleteForDeptDao.querySMSTempleteForDepts(entity);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteForDeptDao#getCount(com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	Long result = sMSTempleteForDeptDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }
    
    @Ignore
    @Test
    public void testQueryAppOrgSmsByParams(){
	TemplateAppOrgEntity entity = sMSTempleteForDeptDao.queryAppOrgSmsByParams("000011", "7d7844f0-8436-40fb-9af1-3a3d716a0820",null);
	
	Assert.assertNotNull(entity);
    }

}
