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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/AirlinesDaoTest.java
 * 
 * FILE NAME        	: AirlinesDaoTest.java
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

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 航空公司DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午1:56:58 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午1:56:58
 * @since
 * @version
 */
public class AirlinesDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IAirlinesDao airlinesDao;
    private AirlinesEntity entity = new AirlinesEntity();
    
    //航空公司ID
    private String airlineId;
    //航空公司名称
    private String name;
    //航空公司编码
    private String code;
    //航空公司简称
    private String simpleName;
    

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	airlinesDao = SpringTestHelper.get().getBeanByInterface(IAirlinesDao.class);
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
//	jdbc.execute("delete from T_BAS_AIRLINES");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#addAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)}.
     */
    @Ignore
    @Test
    public void testAddAirlines() {
	airlineId = UUIDUtils.getUUID();
	name = "重庆航空公司";
	code = "OQ";
	simpleName = "重庆航空";
	entity.setId(airlineId);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	entity.setName(name);
	entity.setSimpleName(simpleName);
	entity.setPrifixName("254");
	int result = airlinesDao.addAirlines(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#deleteAirlinesByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteAirlinesByCode() {
	//添加一条数据
	testAddAirlines();
	
	String[] codes = {airlineId};
	int result = airlinesDao.deleteAirlinesByCode(codes, "陈飞");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#updateAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateAirlines() {
	//添加一条数据
	testAddAirlines();
	
	entity.setId(airlineId);
	entity.setModifyDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setCode("OQ");
	entity.setName("重庆航空公司11");
	entity.setSimpleName("重庆航空11");
	entity.setPrifixName("254");
	int result = airlinesDao.updateAirlines(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#queryAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryAirlines() {
	//添加一条数据
	testAddAirlines();
	entity.setActive(FossConstants.ACTIVE);
	List<AirlinesEntity> list = airlinesDao.queryAirlines(entity,10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条数据
	testAddAirlines();
	entity.setActive(FossConstants.ACTIVE);
	Long result = airlinesDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#queryAirlineByCode(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryAirlineByCode() {
	//添加一条数据
	testAddAirlines();
	AirlinesEntity airlinesEntity = airlinesDao.queryAirlineByCode(code);
	
	Assert.assertNotNull(airlinesEntity);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#queryAirlineByName(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryAirlineByName() {
	//添加一条数据
	testAddAirlines();
	AirlinesEntity airlinesEntity = airlinesDao.queryAirlineByName(name);
	
	Assert.assertNotNull(airlinesEntity);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesDao#queryAirlineBySimpleName(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryAirlineBySimpleName() {
	//添加一条数据
	testAddAirlines();
	AirlinesEntity airlinesEntity = airlinesDao.queryAirlineBySimpleName(simpleName);
	
	Assert.assertNotNull(airlinesEntity);
    }

}
