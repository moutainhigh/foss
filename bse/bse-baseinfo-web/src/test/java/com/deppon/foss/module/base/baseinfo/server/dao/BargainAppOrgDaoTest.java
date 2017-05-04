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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/BargainAppOrgDaoTest.java
 * 
 * FILE NAME        	: BargainAppOrgDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBargainAppOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 合同适用部门DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 下午5:53:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 下午5:53:34
 * @since
 * @version
 */
public class BargainAppOrgDaoTest {

    private JdbcTemplate jdbc;
    
    private IBargainAppOrgDao bargainAppOrgDao;
    private BargainAppOrgEntity entity = new BargainAppOrgEntity();
    
    //编码
    private String id;
    private BigDecimal crmId;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	bargainAppOrgDao = SpringTestHelper.get().getBeanByInterface(IBargainAppOrgDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午5:15:34
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from BSE.T_BAS_CUS_BARGAIN_APP_ORG where id = '"+id+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BargainAppOrgDao#addBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testAddBargainAppOrg() {
	//生成UUID
	id = UUIDUtils.getUUID();
	crmId = new BigDecimal(33220);
	entity.setId(id);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setUnifiedCode("0000898");
	entity.setCrmId(crmId);
	
	int result = bargainAppOrgDao.addBargainAppOrg(entity);
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BargainAppOrgDao#deleteBargainAppOrgByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteBargainAppOrgByCode() {
	//添加一条数据
	testAddBargainAppOrg();
	
	int result = bargainAppOrgDao.deleteBargainAppOrgByCode(crmId, "王辉");
	Assert.assertTrue(result > 0);		
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BargainAppOrgDao#updateBargainAppOrg(com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateBargainAppOrg() {
	//添加一条数据
	testAddBargainAppOrg();
	
	entity.setId(id);
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setUnifiedCode("0000888");
	entity.setCrmId(crmId);
	
	int result = bargainAppOrgDao.updateBargainAppOrg(entity);
	
	Assert.assertTrue(result > 0);
    }

}
