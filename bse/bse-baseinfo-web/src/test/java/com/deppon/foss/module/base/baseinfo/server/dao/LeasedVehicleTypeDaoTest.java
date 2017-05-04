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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LeasedVehicleTypeDaoTest.java
 * 
 * FILE NAME        	: LeasedVehicleTypeDaoTest.java
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
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“车辆车型”的数据库对应数据访问DAO接口实现类：SUC-109 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-17 下午4:03:54</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-17 下午4:03:54
 * @since
 * @version
 */
public class LeasedVehicleTypeDaoTest {
    
    //“车辆车型”数据访问类
    private ILeasedVehicleTypeDao leasedVehicleTypeDao;
    
    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“车辆车型”对象
    private VehicleTypeEntity vehicleType = new VehicleTypeEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	leasedVehicleTypeDao = SpringTestHelper.get().getBeanByInterface(ILeasedVehicleTypeDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete T_BAS_VEHICLE_TYPE_LENGTH");
    }

    /**
     * 新增一个“车辆车型”实体入库（忽略实体中是否存在空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#addLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedVehicleType() {
	vehicleType.setSeq(new BigDecimal(1));
	vehicleType.setVehicleType("厢式车");
	vehicleType.setVehicleLength(new BigDecimal(7.6));
	vehicleType.setActive(FossConstants.ACTIVE);
	vehicleType.setVehicleLengthCode("CC-BM-001");
	vehicleType.setVehicleSort("外请车");
	vehicleType.setCreateUser("100847-foss-GaoPeng");
	
	int result = leasedVehicleTypeDao.addLeasedVehicleType(vehicleType);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleTypeDao leasedVehicleTypeDao = (LeasedVehicleTypeDao) this.leasedVehicleTypeDao;
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setId(vehicleType.getId());
	Assert.assertNotNull(leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleTypeParameter));
    }

    /**
     * 新增一个“车辆车型”实体入库 （只选择实体中非空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#addLeasedVehicleTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedVehicleTypeBySelective() {
	vehicleType.setSeq(new BigDecimal(2));
	vehicleType.setVehicleType("挂车");
	vehicleType.setActive(FossConstants.INACTIVE);
	vehicleType.setVehicleLengthCode("CC-BM-002");
	vehicleType.setCreateUser("100847-foss-GaoPeng");
	vehicleType.setVehicleSort("公司车");
	
	vehicleType.setVehicleLength(null);
	
	int result = leasedVehicleTypeDao.addLeasedVehicleTypeBySelective(vehicleType);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleTypeDao leasedVehicleTypeDao = (LeasedVehicleTypeDao) this.leasedVehicleTypeDao;
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setId(vehicleType.getId());
	VehicleTypeEntity vehicleTypeTemp = leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleTypeParameter);
	
	Assert.assertNotNull(vehicleTypeTemp);
	Assert.assertNull(vehicleTypeTemp.getVehicleLength());
    }
    
    /**
     * 根据“车辆车型”记录唯一标识作废一条“车辆车型”记录
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#deleteLeasedVehicleType(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteLeasedVehicleType() {
	//准备数据
	testAddLeasedVehicleType();
	
	int result = leasedVehicleTypeDao.deleteLeasedVehicleType(vehicleType.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * 修改一个“车辆车型”实体入库 （只选择实体中非空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#updateLeasedVehicleTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedVehicleTypeBySelective() {
	//准备数据
	testAddLeasedVehicleType();
		
	vehicleType.setVehicleType(null);
	vehicleType.setVehicleLength(null);
	vehicleType.setActive(FossConstants.INACTIVE);
		
	int result = leasedVehicleTypeDao.updateLeasedVehicleTypeBySelective(vehicleType);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleTypeDao leasedVehicleTypeDao = (LeasedVehicleTypeDao) this.leasedVehicleTypeDao;
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setId(vehicleType.getId());
	VehicleTypeEntity vehicleTypeTemp = leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleTypeParameter);
	
	Assert.assertNotNull(vehicleTypeTemp.getVehicleType());
	Assert.assertNotNull(vehicleTypeTemp.getVehicleLength());
	Assert.assertEquals(vehicleType.getActive(), vehicleTypeTemp.getActive());
    }

    /**
     * 修改一个“车辆车型”实体入库（忽略实体中是否存在空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#updateLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedVehicleType() {
	//准备数据
	testAddLeasedVehicleType();
	
	vehicleType.setVehicleType("拖头");
	vehicleType.setActive(FossConstants.INACTIVE);
	
	int result = leasedVehicleTypeDao.updateLeasedVehicleType(vehicleType);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleTypeDao leasedVehicleTypeDao = (LeasedVehicleTypeDao) this.leasedVehicleTypeDao;
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setId(vehicleType.getId());
	VehicleTypeEntity vehicleTypeTemp = leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleTypeParameter);
	
	Assert.assertEquals(vehicleType.getVehicleType(), vehicleTypeTemp.getVehicleType());
	Assert.assertEquals(vehicleType.getActive(), vehicleTypeTemp.getActive());
    }

    /**
     * 根据“车辆车型”记录唯一标识查询出一条“车辆车型”记录
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#queryLeasedVehicleType(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedVehicleType() {
	//准备数据
	testAddLeasedVehicleType();
	String id = vehicleType.getId();
	vehicleType = null;
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setId(vehicleType.getId());
	vehicleType = leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleTypeParameter);
	Assert.assertNotNull(vehicleType);
    }

    /**
     * 根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleTypeDao#queryLeasedVehicleTypeListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedVehicleTypeListBySelectiveCondition() {
	//准备数据
	testAddLeasedVehicleType();
	
	//设置条件
	vehicleType = new VehicleTypeEntity();
	vehicleType.setActive(FossConstants.ACTIVE);
	
	List<VehicleTypeEntity> vehicleTypeList = leasedVehicleTypeDao.queryLeasedVehicleTypeListBySelectiveCondition(vehicleType, 0, 2);
	Assert.assertTrue(vehicleTypeList.size() > 0);
    }
}
