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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/CusBargainDaoTest.java
 * 
 * FILE NAME        	: CusBargainDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusBargainDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户合同信息DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 下午4:37:51 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 下午4:37:51
 * @since
 * @version
 */
public class CusBargainDaoTest {

    private JdbcTemplate jdbc;
    
    private ICusBargainDao cusBargainDao;
    private CusBargainEntity entity = new CusBargainEntity();
    
    //编码
    private String code;
    private BigDecimal crmId;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	cusBargainDao = SpringTestHelper.get().getBeanByInterface(ICusBargainDao.class);
    }


    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午4:37:51
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from BSE.T_BAS_CUS_BARGAIN t where t.id = '"+code+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusBargainDao#addCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)}.
     */
    @Ignore
    @Test
    public void testAddCusBargain() {
	code = "0001";
	crmId = new BigDecimal(33220);
	entity.setId(code);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setArrearsAmount(new BigDecimal(2300l));
	entity.setCrmId(crmId);
	

	int result = cusBargainDao.addCusBargain(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusBargainDao#deleteCusBargainByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteCusBargainByCode() {
	//添加一条数据
	testAddCusBargain();
	
	int result = cusBargainDao.deleteCusBargainByCode(crmId, "王龙");
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusBargainDao#updateCusBargain(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateCusBargain() {
	//添加一条数据
	testAddCusBargain();
	entity.setId(code);
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setArrearsAmount(new BigDecimal(2355));	
	entity.setCrmId(crmId);
	
	int result = cusBargainDao.updateCusBargain(entity);
	Assert.assertTrue(result > 0);
    }
    
    @Ignore
    @Test
    public void testqueryCusBargainByCustCode(){
	CusBargainEntity entity = cusBargainDao.queryCusBargainByCustCode("065170", new Date(),null);
	
	Assert.assertNotNull(entity);
    }
    
    /**
     * <p>根据合同编码和部门编码查询合同信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午11:53:40
     * @see
     */
    @Ignore
    @Test
    public void testQueryCusBargainByParams(){
	CusBargainEntity entity = cusBargainDao.queryCusBargainByParams("201301091423", "");
	Assert.assertNotNull(entity);
    }

}
