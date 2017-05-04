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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/VehicleBrandDaoTest.java
 * 
 * FILE NAME        	: VehicleBrandDaoTest.java
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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“车辆品牌”的数据库对应数据访问DAO接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-29 上午11:28:26</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-29 上午11:28:26
 * @since
 * @version
 */
public class VehicleBrandDaoTest {
    
    //“车辆品牌”数据访问类
    private IVehicleBrandDao vehicleBrandDao;
    
    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“车辆品牌”对象
    private VehicleBrandEntity vehicleBrand = new VehicleBrandEntity();

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	vehicleBrandDao = SpringTestHelper.get().getBeanByInterface(IVehicleBrandDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete BSE.T_BAS_VEHICLE_BRAND");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#addVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)}.
     */
    @Ignore
    @Test
    public void testAddVehicleBrand() {
	vehicleBrand.setBrandCode("PP-001-09");
	vehicleBrand.setBrandName("斯堪尼亚");
	vehicleBrand.setVehicleType("挂车");
	vehicleBrand.setActive(FossConstants.ACTIVE);
	vehicleBrand.setCreateUser("100847-foss-GaoPeng");
	
	int result = vehicleBrandDao.addVehicleBrand(vehicleBrand);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#addVehicleBrandBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)}.
     */
    @Ignore
    @Test
    public void testAddVehicleBrandBySelective() {
	vehicleBrand.setBrandCode("PP-001-09");
	vehicleBrand.setVehicleType("厢式车");
	vehicleBrand.setActive(FossConstants.ACTIVE);
	vehicleBrand.setCreateUser("100847-foss-GaoPeng");
	
	vehicleBrand.setBrandName(null);
	
	int result = vehicleBrandDao.addVehicleBrand(vehicleBrand);
	Assert.assertTrue(result > 0);
	
	VehicleBrandDao vehicleBrandDao = (VehicleBrandDao) this.vehicleBrandDao;
//	VehicleBrandEntity vehicleBrandTemp = vehicleBrandDao.queryVehicleBrand(vehicleBrand.getId());
//	
//	Assert.assertNotNull(vehicleBrandTemp);
//	Assert.assertNull(vehicleBrandTemp.getBrandName());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#deleteVehicleBrand(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteVehicleBrand() {
	//准备数据
	testAddVehicleBrand();
	
	int result = vehicleBrandDao.deleteVehicleBrand(vehicleBrand.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#updateVehicleBrandBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateVehicleBrandBySelective() {
	//准备数据
	testAddVehicleBrand();
	
	vehicleBrand.setVehicleType("拖头");
	vehicleBrand.setActive(FossConstants.INACTIVE);
	vehicleBrand.setCreateUser(null);
	
	int result = vehicleBrandDao.updateVehicleBrandBySelective(vehicleBrand);
	Assert.assertTrue(result > 0);
	
	VehicleBrandDao vehicleBrandDao = (VehicleBrandDao) this.vehicleBrandDao;
//	VehicleBrandEntity vehicleBrandTemp = vehicleBrandDao.queryVehicleBrand(vehicleBrand.getId());
//	
//	Assert.assertNotNull(vehicleBrandTemp);
//	Assert.assertEquals(vehicleBrand.getVehicleType(), vehicleBrandTemp.getVehicleType());
//	Assert.assertEquals(vehicleBrand.getActive(), vehicleBrandTemp.getActive());
//	Assert.assertNotNull(vehicleBrandTemp.getCreateUser());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#updateVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateVehicleBrand() {
	//准备数据
	testAddVehicleBrand();
	
	vehicleBrand.setVehicleType("拖头");
	vehicleBrand.setActive(FossConstants.INACTIVE);
	
	int result = vehicleBrandDao.updateVehicleBrand(vehicleBrand);
	Assert.assertTrue(result > 0);
	
	VehicleBrandDao vehicleBrandDao = (VehicleBrandDao) this.vehicleBrandDao;
//	VehicleBrandEntity vehicleBrandTemp = vehicleBrandDao.queryVehicleBrand(vehicleBrand.getId());
//	
//	Assert.assertNotNull(vehicleBrandTemp);
//	Assert.assertEquals(vehicleBrand.getVehicleType(), vehicleBrandTemp.getVehicleType());
//	Assert.assertEquals(vehicleBrand.getActive(), vehicleBrandTemp.getActive());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#queryVehicleBrand(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryVehicleBrand() {
	//准备数据
	testAddVehicleBrand();
	
//	VehicleBrandEntity vehicleBrandTemp = vehicleBrandDao.queryVehicleBrand(vehicleBrand.getId());
//	Assert.assertNotNull(vehicleBrandTemp);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleBrandDao#queryVehicleBrandBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryVehicleBrandBySelectiveCondition() {
	//准备数据
	testAddVehicleBrand();
	
	List<VehicleBrandEntity> vehicleBrandTemps = vehicleBrandDao.queryVehicleBrandBySelectiveCondition(vehicleBrand, 0, 2);
	Assert.assertNotNull(vehicleBrandTemps);
	Assert.assertTrue(vehicleBrandTemps.size() > 0);
    }
}
