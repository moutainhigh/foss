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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/VehicleAgencyCompanyDaoTest.java
 * 
 * FILE NAME        	: VehicleAgencyCompanyDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 偏线代理公司Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-23 下午3:10:40 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-23 下午3:10:40
 * @since
 * @version
 */
public class VehicleAgencyCompanyDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IVehicleAgencyCompanyDao vehicleAgencyCompanyDao;
    private BusinessPartnerEntity entity = new BusinessPartnerEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	vehicleAgencyCompanyDao = SpringTestHelper.get().getBeanByInterface(IVehicleAgencyCompanyDao.class);
    }

    /**
     * 清空测试数据
     * @author 094463-foss-xieyantao
     * @date 2012-10-23 下午3:10:40
     * @throws java.lang.Exception
     * @see
     */
    /*@After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_BUSINESS_PARTNER");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#addVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testAddVehicleAgencyCompany() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanyCode("00021");
	entity.setSimplename("天地纵横");
	entity.setAgentCompanyName("广州天地纵横物流公司");
	entity.setCreateUser("杨坤");
	entity.setModifyUser(entity.getCreateUser());
	entity.setManagement("广州汽运偏线");
	entity.setProvCode("广东");
	entity.setCityCode("广州");
	entity.setAgentCompanySort("偏线");
	
	
	int result = vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#deleteVehicleAgencyCompanyByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteVehicleAgencyCompanyByCode() {
	
	String[] codes = {"d5ae9566-d4af-483c-a20a-605095c1a06d","fd15c6ef-aad4-4e04-bf1c-1a2f77cea00f"};
	int result = vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyByCode(codes, "刘德华");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#updateVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateVehicleAgencyCompany() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanyCode("00012");
	entity.setSimplename("天地纵横");
	entity.setAgentCompanyName("广州天地纵横物流公司");
	entity.setCreateUser("王菲");
	entity.setModifyUser(entity.getCreateUser());
	entity.setManagement("广州汽运偏线");
	entity.setProvCode("江苏");
	entity.setCityCode("南京");
	entity.setAgentCompanySort("偏线");
	entity.setVirtualCode("8f979c3a-42e3-4838-8f99-e82dd93c9e95");
	
	//定义一个虚拟编码code
	String[] codes = {entity.getVirtualCode()};
		    
	//作废历史记录
	int flag = vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyByCode(codes, entity.getModifyUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    int res = vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
	    Assert.assertTrue(res == 1);
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#queryVehicleAgencyCompanys(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryVehicleAgencyCompanys() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanySort("偏线");
	List<BusinessPartnerEntity> list = vehicleAgencyCompanyDao.queryVehicleAgencyCompanys(entity, 10, 0);
	
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setAgentCompanySort("偏线");
	Long result = vehicleAgencyCompanyDao.queryRecordCount(entity);
	
	Assert.assertTrue(result >  0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#queryEntityByName(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryEntityByName() {
	
	BusinessPartnerEntity bEntity = vehicleAgencyCompanyDao.queryEntityByName("广州天地纵横物流公司");
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getAgentCompanyName());
	}else {
	    Assert.assertNull(bEntity);
	}
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#queryEntityBySimplename(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryEntityBySimplename() {
	
	BusinessPartnerEntity bEntity = vehicleAgencyCompanyDao.queryEntityBySimplename("天地纵横");
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getSimplename());
	}else {
	    Assert.assertNull(bEntity);
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao#queryEntityByCode(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryEntityByCode() {
	
	BusinessPartnerEntity bEntity = vehicleAgencyCompanyDao.queryEntityByCode("00005",null);
	
	if(null != bEntity){
	    Assert.assertNotNull(bEntity.getAgentCompanyCode());
	}else {
	    Assert.assertNull(bEntity);
	}
    }
    @Ignore
    @Test
    public void testQueryBusinessPartnersForDownload() {
	BusinessPartnerEntity entity = new BusinessPartnerEntity();
	Date date = new Date();
	entity.setVersionNo(1355554990405L);
	List<BusinessPartnerEntity> list = vehicleAgencyCompanyDao.queryBusinessPartnersForDownload(entity);
	Assert.assertTrue(list.size()> 0);
    }
    

}
