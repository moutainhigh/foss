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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/OwnVehicleDaoTest.java
 * 
 * FILE NAME        	: OwnVehicleDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“公司车辆”的数据库对应数据访问DAO接口实现类：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-31 下午4:05:14</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-31 下午4:05:14
 * @since
 * @version
 */
public class OwnVehicleDaoTest {

    //“公司车辆”数据访问类
    private IOwnVehicleDao ownVehicleDao;
    
    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“公司车辆”对象
    private OwnTruckEntity ownTruck = new OwnTruckEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	ownVehicleDao = SpringTestHelper.get().getBeanByInterface(IOwnVehicleDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete BSE.T_BAS_OWN_TRUCK");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#addOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testAddOwnVehicle() {
	ownTruck.setVehicleNo("沪L-1551");
	ownTruck.setUsedType("物流班车");
	ownTruck.setVehicleHeight(NumberUtils.createBigDecimal("52"));
	ownTruck.setContainerCode("HG-90890234");
	ownTruck.setStatus(FossConstants.ACTIVE);
	ownTruck.setOrgId("BM-001");
	
	ownTruck.setActive(FossConstants.ACTIVE);
	ownTruck.setBrand("PP-001-09");
	ownTruck.setCreateUser("100847-foss-GaoPeng");
	
	int result = ownVehicleDao.addOwnVehicle(ownTruck);
	Assert.assertTrue(result > 0);
	
	OwnVehicleDao leasedVanDao = (OwnVehicleDao) this.ownVehicleDao;
	Assert.assertNotNull(leasedVanDao.queryOwnVehicleBySelective(ownTruck.getId()));
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#addOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testAddOwnVehicleBySelective() {
	ownTruck.setVehicleNo("沪L-1551");
	ownTruck.setUsedType("接送货");
	ownTruck.setContainerCode("HG-90890234");
	ownTruck.setStatus(FossConstants.ACTIVE);
	ownTruck.setOrgId("BM-001");
	ownTruck.setActive(FossConstants.ACTIVE);
	ownTruck.setBrand("PP-001-09");
	ownTruck.setCreateUser("100847-foss-GaoPeng");
	
	ownTruck.setVehicleHeight(null);
	ownTruck.setVehicleLength(null);
	
	int result = ownVehicleDao.addOwnVehicleBySelective(ownTruck);
	Assert.assertTrue(result > 0);
	
	OwnVehicleDao leasedVanDao = (OwnVehicleDao) this.ownVehicleDao;
	OwnTruckEntity ownTruckTemp = leasedVanDao.queryOwnVehicleBySelective(ownTruck.getId()); 
	Assert.assertNotNull(ownTruckTemp);
	Assert.assertNull(ownTruckTemp.getVehicleHeight());
	Assert.assertNull(ownTruckTemp.getVehicleLength());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#deleteOwnVehicle(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteOwnVehicle() {
	//准备数据
	testAddOwnVehicle();
	
	int result = ownVehicleDao.deleteOwnVehicle(ownTruck.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#updateOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateOwnVehicleBySelective() {
	//准备数据
	testAddOwnVehicle();
	
	ownTruck.setVehicleHeight(NumberUtils.createBigDecimal("12"));
	ownTruck.setVehicleLength(NumberUtils.createBigDecimal("15"));
	ownTruck.setVehicleWidth(null);
	
	int result = ownVehicleDao.updateOwnVehicleBySelective(ownTruck);
	Assert.assertTrue(result > 0);
	
	OwnVehicleDao leasedVanDao = (OwnVehicleDao) this.ownVehicleDao;
	OwnTruckEntity ownTruckTemp = leasedVanDao.queryOwnVehicleBySelective(ownTruck.getId());
	
	Assert.assertNotNull(ownTruckTemp);
	Assert.assertEquals(ownTruck.getVehicleHeight(), ownTruckTemp.getVehicleHeight());
	Assert.assertEquals(ownTruck.getVehicleLength(), ownTruckTemp.getVehicleLength());
	Assert.assertNull(ownTruckTemp.getVehicleWidth());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#updateOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateOwnVehicle() {
	//准备数据
	testAddOwnVehicle();
	
	ownTruck.setVehicleHeight(NumberUtils.createBigDecimal("12"));
	ownTruck.setVehicleLength(NumberUtils.createBigDecimal("15"));
	
	int result = ownVehicleDao.updateOwnVehicle(ownTruck);
	Assert.assertTrue(result > 0);
	
	OwnVehicleDao leasedVanDao = (OwnVehicleDao) this.ownVehicleDao;
	OwnTruckEntity ownTruckTemp = leasedVanDao.queryOwnVehicleBySelective(ownTruck.getId());
	
	Assert.assertNotNull(ownTruckTemp);
	Assert.assertEquals(ownTruck.getVehicleHeight(), ownTruckTemp.getVehicleHeight());
	Assert.assertEquals(ownTruck.getVehicleLength(), ownTruckTemp.getVehicleLength());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#queryOwnVehicleBySelective(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryOwnVehicle() {
	//准备数据
	testAddOwnVehicle();
	
	OwnTruckEntity ownTruckTemp = ownVehicleDao.queryOwnVehicleBySelective(ownTruck.getId());
	Assert.assertNotNull(ownTruckTemp);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#queryOwnVehicleBySelectiveBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testQueryOwnVehicleBySelectiveCondition() {
	//准备数据
	testAddOwnVehicle();
	
	ownTruck.setActive(FossConstants.ACTIVE);
	
	OwnTruckEntity ownTruckTemp = ownVehicleDao.queryOwnVehicleBySelective(ownTruck);
	Assert.assertNotNull(ownTruckTemp);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#queryOwnVehicleBySelectiveRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)}.
     */
    @Ignore
    @Test
    public void testQueryOwnVehicleRecordCountBySelectiveCondition() {
	//准备数据
	testAddOwnVehicle();
	
	ownTruck.setActive(FossConstants.ACTIVE);
	
	long recordCount = ownVehicleDao.queryOwnVehicleCountBySelectiveCondition(ownTruck);
	Assert.assertNotNull(recordCount > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#queryOwnVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryOwnVehicleListBySelectiveCondition() {
	//准备数据
	testAddOwnVehicle();
		
	ownTruck.setActive(FossConstants.ACTIVE);
	
	List<OwnTruckEntity> ownTruckTemps = ownVehicleDao.queryOwnVehicleListBySelectiveCondition(ownTruck, 0, 2);
	Assert.assertNotNull(ownTruckTemps);
	Assert.assertTrue(ownTruckTemps.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.OwnVehicleDao#queryOwnVehicleBrandOrganizationDtoByVehicleNo(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryVehicleBaseDtosByVehicleNos() {
	//准备数据
	testAddOwnVehicle();
	List<String> vehicleNos = new ArrayList<String>(1);
	vehicleNos.add(ownTruck.getVehicleNo());
	List<VehicleBaseDto> vehicleBaseDtos = ownVehicleDao.queryOwnVehicleBaseDtoListByVehicleNos(vehicleNos, null, null, null, 0, 1, null);
	Assert.assertNull(vehicleBaseDtos);
    }
}
