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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/BankDaoTest.java
 * 
 * FILE NAME        	: BankDaoTest.java
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


import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 银行Dao单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午5:31:32 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午5:31:32
 * @since
 * @version
 */
public class BankDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IBankDao bankDao;
    private BankEntity entity = new BankEntity();
    
    //银行编码
    private String code;
    

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	bankDao = SpringTestHelper.get().getBeanByInterface(IBankDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 下午1:56:58
     * @throws java.lang.Exception
     * @see
     */
   @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete from T_BAS_BANK");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#addAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)}.
     */
    @Ignore
    @Test
    public void testAddBank() {
	
	code = "0001";
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	entity.setName("中国工商银行");
	entity.setProvId("北京");
	entity.setCityCode("北京");
	entity.setHeadOffice(FossConstants.YES);
	int result = bankDao.addBank(entity);
	
	Assert.assertTrue(result > 0);
    }
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BankDao#deleteBank(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteBank() {
	//添加一条数据
	testAddBank();
	
	String[] codes = {code};
	int result = bankDao.deleteBank(codes, "陈飞");
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BankDao#updateBank(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateBank() {
	//添加一条数据
	testAddBank();
	
	entity.setModifyUser("张飞");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	entity.setName("中国工商银行");
	entity.setProvId("北京");
	entity.setCityCode("北京");
	entity.setHeadOffice(FossConstants.YES);
	entity.setIntraDayType(FossConstants.YES);
	int result = bankDao.updateBank(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BankDao#queryBanks(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryBanks() {
	//添加一条数据
	testAddBank();
	entity.setActive(FossConstants.ACTIVE);
	List<BankEntity> list = bankDao.queryBanks(entity,10, 0);
	Assert.assertTrue(list.size()>0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条数据
	testAddBank();
	entity.setActive(FossConstants.ACTIVE);
	Long result = bankDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);	
    }
    
    /**
     * <p>下载银行数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午2:31:56
     * @see
     */
    @Ignore
    @Test
    public void testQueryBanksForDownLoad(){
	//添加一条数据
	testAddBank();
	entity.setActive(FossConstants.ACTIVE);
	List<BankEntity> list = bankDao.queryBanksForDownLoad(entity);
	
	Assert.assertTrue(list.size() > 0);
	
    }

}
