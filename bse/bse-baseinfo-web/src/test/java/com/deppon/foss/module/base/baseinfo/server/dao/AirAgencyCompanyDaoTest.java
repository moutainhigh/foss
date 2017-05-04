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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/AirAgencyCompanyDaoTest.java
 * 
 * FILE NAME        	: AirAgencyCompanyDaoTest.java
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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 空运代理公司Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-23 上午9:59:14 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-23 上午9:59:14
 * @since
 * @version
 */
public class AirAgencyCompanyDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IAirAgencyCompanyDao airAgencyCompanyDao;
    private BusinessPartnerEntity entity = new BusinessPartnerEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	airAgencyCompanyDao = SpringTestHelper.get().getBeanByInterface(IAirAgencyCompanyDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 上午9:59:14
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_BUSINESS_PARTNER");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao#addAirAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testAddAirAgencyCompany() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanyCode("00001");
	entity.setAgentCompanyName("上海锦标物流有限公司");
	entity.setCreateUser("王菲");
	entity.setModifyUser(entity.getCreateUser());
	entity.setManagement("广州空运总调");
	entity.setProvCode("江苏");
	entity.setCityCode("南京");
	entity.setAgentCompanySort("空运");
	
	
	int result = airAgencyCompanyDao.addAirAgencyCompany(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao#deleteAirAgencyCompanyByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteAirAgencyCompanyByCode() {
	
	String[] codes = {"d5ae9566-d4af-483c-a20a-605095c1a06d","fd15c6ef-aad4-4e04-bf1c-1a2f77cea00f"};
	int result = airAgencyCompanyDao.deleteAirAgencyCompanyByCode(codes, "刘德华");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao#updateAirAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateAirAgencyCompany() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanyCode("00002");
	entity.setAgentCompanyName("上海腾龙物流有限公司");
	entity.setCreateUser("王语嫣");
	entity.setManagement("广州空运总调");
	entity.setProvCode("江苏");
	entity.setCityCode("南京");
	entity.setAgentCompanySort("空运");
	entity.setVirtualCode("d5ae9566-d4af-483c-a20a-605095c1a06d");
	
	//定义一个虚拟编码code
	String[] codes = {entity.getVirtualCode()};
		    
	//作废历史记录
	int flag = airAgencyCompanyDao.deleteAirAgencyCompanyByCode(codes, entity.getModifyUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    int res = airAgencyCompanyDao.addAirAgencyCompany(entity);
	    Assert.assertTrue(res == 1);
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao#queryAirAgencyCompanys(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryAirAgencyCompanys() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanySort("空运");
	List<BusinessPartnerEntity> list = airAgencyCompanyDao.queryAirAgencyCompanys(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanySort("空运");
	Long result = airAgencyCompanyDao.queryRecordCount(entity);
	
	Assert.assertTrue(result >  0);
    }
    
    @Ignore
    @Test
    public void testQueryEntityByName(){
	
	BusinessPartnerEntity bEntity = airAgencyCompanyDao.queryEntityByCode("00001");
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getAgentCompanyCode());
	}else {
	    Assert.assertNull(bEntity);
	}
	
	
    }
    
    @Ignore
    @Test
    public void testQueryEntityBySimplename(){
	
	BusinessPartnerEntity bEntity = airAgencyCompanyDao.queryEntityByName("上海腾龙物流有限公司12");
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getAgentCompanyName());
	}else {
	    Assert.assertNull(bEntity);
	}
    }
    @Ignore
    @Test
    public void testQueryEntityByCode(){
	
	BusinessPartnerEntity bEntity = airAgencyCompanyDao.queryEntityBySimplename("腾龙物流");
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getAgentCompanyCode());
	}else {
	    Assert.assertNull(bEntity);
	}
    }
    
    @Ignore
    @Test
    public void testQueryBusinessPartnerByAgencyDeptCode(){
	BusinessPartnerEntity entity = airAgencyCompanyDao.queryBusinessPartnerByAgencyDeptCode("454545454");
    
	Assert.assertNotNull(entity);
    }

}
