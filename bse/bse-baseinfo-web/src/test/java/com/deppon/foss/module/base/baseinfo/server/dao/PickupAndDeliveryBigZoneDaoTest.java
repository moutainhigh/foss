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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/PickupAndDeliveryBigZoneDaoTest.java
 * 
 * FILE NAME        	: PickupAndDeliveryBigZoneDaoTest.java
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
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BigZoneDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;


/**
 * 集中接送货大区DAO测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-24 下午4:45:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-24 下午4:45:17
 * @since
 * @version
 */
public class PickupAndDeliveryBigZoneDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IPickupAndDeliveryBigZoneDao pickupAndDeliveryBigZoneDao;
    private BigZoneEntity entity = new BigZoneEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	pickupAndDeliveryBigZoneDao = SpringTestHelper.get().getBeanByInterface(IPickupAndDeliveryBigZoneDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-24 下午4:45:17
     * @throws java.lang.Exception
     * @see
     */
    /*@After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_BUSINESS_PARTNER");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliveryBigZoneDao#addPickupAndDeliveryBigZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)}.
     */
    @Ignore
    @Test
    public void testAddPickupAndDeliveryBigZone() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("杨坤");
	entity.setModifyUser(entity.getCreateUser());
	entity.setRegionCode("0507S001");
	entity.setRegionName("北京集中送货大区");
	entity.setType("送货区");
	entity.setManagement("北京车队调度组");
	entity.setTransDepartmentCode("北京车队丰台区接送货小组");
	
	
	int result = pickupAndDeliveryBigZoneDao.addPickupAndDeliveryBigZone(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliveryBigZoneDao#deletePickupAndDeliveryBigZoneByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeletePickupAndDeliveryBigZoneByCode() {
	
	String[] codes = {"f7e5d7b7-6649-46e2-9009-f4a66907c402","8c52841c-6f36-4ce6-9a0e-7081a48eb162"};
	int result = pickupAndDeliveryBigZoneDao.deletePickupAndDeliveryBigZoneByCode(codes, "刘德华");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliveryBigZoneDao#updatePickupAndDeliveryBigZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)}.
     */
    @Ignore
    @Test
    public void testUpdatePickupAndDeliveryBigZone() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("杨坤");
	entity.setModifyUser(entity.getCreateUser());
	entity.setRegionCode("0507S002");
	entity.setRegionName("北京集中送货大区11");
	entity.setType("送货区");
	entity.setManagement("北京车队调度组11");
	entity.setTransDepartmentCode("北京车队丰台区接送货小组11");
	entity.setVirtualCode("f7e5d7b7-6649-46e2-9009-f4a66907c402");
	
	//定义一个虚拟编码code
	String[] codes = {entity.getVirtualCode()};
		    
	//作废历史记录
	int flag = pickupAndDeliveryBigZoneDao.deletePickupAndDeliveryBigZoneByCode(codes, entity.getModifyUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    int res = pickupAndDeliveryBigZoneDao.addPickupAndDeliveryBigZone(entity);
	    Assert.assertTrue(res == 1);
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliveryBigZoneDao#queryPickupAndDeliveryBigZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryPickupAndDeliveryBigZones() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setType("全部");
	
	List<BigZoneEntity> list = pickupAndDeliveryBigZoneDao.queryPickupAndDeliveryBigZones(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliveryBigZoneDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setType("全部");
	
	Long result = pickupAndDeliveryBigZoneDao.queryRecordCount(entity);
	
	Assert.assertTrue(result >  0);
    }
    
    /**
     * 根据区域编码查询集中接送货大区详细信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午2:50:10
     * @see
     */
    @Ignore
    @Test
    public void testQueryBigzoneByCode(){
	BigZoneEntity bigZone = pickupAndDeliveryBigZoneDao.queryBigzoneByCode("0507S008");
	
	Assert.assertNotNull(bigZone);
    }
    
    /**
     * 根据集中接送货大区生成的前五位编码模糊查询集中接送货大区
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午2:50:10
     * @see
     */
    @Ignore
    @Test
    public void testQueryBigZonesByGenerateCode(){
	List<BigZoneEntity> list = pickupAndDeliveryBigZoneDao.queryBigZonesByGenerateCode("0507S");
	
	Assert.assertTrue(list.size() > 0);
    }

}
