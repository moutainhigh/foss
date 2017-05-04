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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/ProvinceCityInfoDaoTest.java
 * 
 * FILE NAME        	: ProvinceCityInfoDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;

/**
 * 银行省市信息单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午5:37:08 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午5:37:08
 * @since
 * @version
 */
public class ProvinceCityInfoDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IProvinceCityInfoDao provinceCityInfoDao;
    private ProvinceCityEntity entity = new ProvinceCityEntity();
    
    //编码
    private String code;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	provinceCityInfoDao = SpringTestHelper.get().getBeanByInterface(IProvinceCityInfoDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午5:37:09
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete from BSE.T_BAS_BANK_DISTRICT");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ProvinceCityInfoDao#addProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)}.
     */
    @Ignore
    @Test
    public void testAddProvinceCity() {
	
	code = "0001";
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setDistrictCode(code);
	entity.setDistrictName("广东省");
	entity.setParentDistrictCode("");
	entity.setDistrictDegree("1");
	
	int result = provinceCityInfoDao.addProvinceCity(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ProvinceCityInfoDao#deleteProvinceCity(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteProvinceCity() {
	//添加一条数据
	testAddProvinceCity();
	
	int result = provinceCityInfoDao.deleteProvinceCity(code, "张三");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.ProvinceCityInfoDao#updateProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateProvinceCity() {
	//添加一条数据
	testAddProvinceCity();
	
	entity.setDistrictCode(code);
	entity.setDistrictName("广东省");
	entity.setParentDistrictCode("");
	entity.setDistrictDegree("1");
	entity.setModifyUser("李四");
	
	int result = provinceCityInfoDao.updateProvinceCity(entity);
	
	Assert.assertTrue(result > 0);
    }

}
