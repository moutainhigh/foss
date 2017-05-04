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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/NonfixedCustomerDaoTest.java
 * 
 * FILE NAME        	: NonfixedCustomerDaoTest.java
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
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 散客信息DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 下午3:12:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 下午3:12:11
 * @since
 * @version
 */
public class NonfixedCustomerDaoTest {
    
    private JdbcTemplate jdbc;
    
    private INonfixedCustomerDao fixedCustomerDao;
    private NonfixedCustomerEntity entity = new NonfixedCustomerEntity();
    
    //编码
    private String code;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	fixedCustomerDao = SpringTestHelper.get().getBeanByInterface(INonfixedCustomerDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午3:12:11
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from BSE.T_BAS_NONFIXED_CUSTOMER T where T.id = '"+code+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NonfixedCustomerDao#addNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)}.
     */
    @Ignore
    @Test
    public void testAddNonfixedCustomer() {
	
	code = "0001";
	entity.setId(code);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCrmId(new BigInteger("23444"));
	entity.setType("swe");
	entity.setTempArrears(new BigDecimal(20303));
	
	int result = fixedCustomerDao.addNonfixedCustomer(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NonfixedCustomerDao#deleteNonfixedCustomerByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteNonfixedCustomerByCode() {
	//添加一条数据
	testAddNonfixedCustomer();
	int result = fixedCustomerDao.deleteNonfixedCustomerByCode("23444", "飞虎");
	
	Assert.assertTrue(result > 0 );
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.NonfixedCustomerDao#updateNonfixedCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateNonfixedCustomer() {
	//添加一条数据
	testAddNonfixedCustomer();
	entity.setId(code);
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCrmId(new BigInteger("23444"));
	entity.setType("swe");
	entity.setTempArrears(new BigDecimal(2303));
	
	int result = fixedCustomerDao.updateNonfixedCustomer(entity);
	
	Assert.assertTrue(result > 0 );
    }
    
    /**
     * <p>根据传入查询条件对象dto，查询符合条件的临欠散客信息集合list</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-31 下午5:35:59
     * @see
     */
    @Ignore
    @Test
    public void testQueryCustomerByCondition(){
	CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
	dto.setCustCode("023213");
	dto.setExactQuery(true);
	
	List<CustomerQueryConditionDto> list = fixedCustomerDao.queryCustomerByCondition(dto);
	
	Assert.assertTrue(list.size() > 0);
    }

}
