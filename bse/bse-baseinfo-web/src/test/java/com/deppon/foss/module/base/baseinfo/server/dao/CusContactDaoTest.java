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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/CusContactDaoTest.java
 * 
 * FILE NAME        	: CusContactDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContactDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户联系人信息DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 下午3:48:41 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 下午3:48:41
 * @since
 * @version
 */
public class CusContactDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ICusContactDao cusContactDao;
    private ContactEntity entity = new ContactEntity();
    
    //编码
    private String code;
    private BigDecimal crmId;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	cusContactDao = SpringTestHelper.get().getBeanByInterface(ICusContactDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午3:48:41
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from T_BAS_CUS_CONTACT T where T.id = '"+code+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusContactDao#addCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)}.
     */
    @Ignore
    @Test
    public void testAddCusContact() {
	code = "0001";
	crmId = new BigDecimal(33220);
	entity.setId(code);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setGender("N");
	entity.setCrmId(crmId);
	
	int result = cusContactDao.addCusContact(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusContactDao#deleteCusContactByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteCusContactByCode() {
	
	//添加一条数据
	testAddCusContact();
	int result = cusContactDao.deleteCusContactByCode(crmId, "李四");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusContactDao#updateCusContact(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateCusContact() {
	//添加一条数据
	testAddCusContact();
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setGender("w");
	entity.setCrmId(new BigDecimal(33220));
	
	
	int result = cusContactDao.updateCusContact(entity);
	
	Assert.assertTrue(result > 0);
    }

}
