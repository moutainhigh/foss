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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LeasedVehicleDaoTest.java
 * 
 * FILE NAME        	: LeasedVehicleDaoTest.java
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
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“外请车（厢式车、挂车、拖头）”的数据库对应数据访问DAO接口实现类：SUC-44、SUC-103、SUC-608
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-23 下午4:12:40</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-23 下午4:12:40
 * @since
 * @version
 */
public class LeasedVehicleDaoTest {

    //“外请车（拖头、挂车、厢式车）”数据访问类
    private ILeasedVehicleDao leasedVehicleDao;

    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“外请车（拖头、挂车、厢式车）”对象
    private LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	leasedVehicleDao = SpringTestHelper.get().getBeanByInterface(ILeasedVehicleDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete T_BAS_LEASED_TRUCK");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#deleteLeasedVehicle(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteLeasedVehicle() {
	//准备数据
	testAddLeasedVehicle();
	
	int result = leasedVehicleDao.deleteLeasedVehicle(leasedTruck.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#deleteLeasedVehicleActiveStatusByBatchIds(java.lang.String, java.lang.String, java.lang.String[])}.
     */
    @Ignore
    @Test
    public void testDeleteLeasedVehicleActiveStatusByBatchIds() {
	//准备数据
	String[] ids = new String[2];
	
	testAddLeasedVehicle();
	ids[0] = leasedTruck.getId();
	
	testAddLeasedVehicle();
	ids[1] = leasedTruck.getId();
	
	int result = leasedVehicleDao.deleteLeasedVehicleActiveStatusByBatchIds(ids, FossConstants.INACTIVE, "100847-foss-GaoPeng");
	Assert.assertTrue(result > 0);
	
	LeasedVehicleDao leasedVehicleDao = (LeasedVehicleDao) this.leasedVehicleDao;
	LeasedTruckEntity leasedTruck1 = leasedVehicleDao.queryLeasedVehicleBySelective(ids[0]);
	LeasedTruckEntity leasedTruck2 = leasedVehicleDao.queryLeasedVehicleBySelective(ids[1]);	
	
	Assert.assertEquals(leasedTruck1.getActive(), FossConstants.INACTIVE);
	Assert.assertEquals(leasedTruck2.getActive(), FossConstants.INACTIVE);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#addLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedVehicle() {
	leasedTruck.setVehicleNo("沪A-D4444");
	leasedTruck.setVehicleFrameNo("CJH-444444");
	leasedTruck.setEngineNo("44444444444");
	leasedTruck.setBridge("单桥");
	leasedTruck.setDeadLoad(new BigDecimal(50));
	leasedTruck.setSelfWeight(new BigDecimal(15));
	leasedTruck.setBeginTime(new Date());
	leasedTruck.setEndTime(new Date());
	leasedTruck.setVehicleLength(new BigDecimal(15.4));
	leasedTruck.setVehicleHeight(new BigDecimal(8));
	leasedTruck.setVehicleWidth(new BigDecimal(6));
	leasedTruck.setGps(FossConstants.ACTIVE);
	leasedTruck.setGpsProvider("上海设备供应商");
	leasedTruck.setBargainVehicle(FossConstants.ACTIVE);
	leasedTruck.setOperatingLic("YY-444444");
	leasedTruck.setBeginTimeOperatingLic(new Date());
	leasedTruck.setEndTimeOperatingLic(new Date());
	leasedTruck.setDrivingLicense("XS-444444");
	leasedTruck.setBeginTimeDrivingLic(new Date());
	leasedTruck.setEndTimeDrivingLic(new Date());
	leasedTruck.setInsureCard("BX-44444");
	leasedTruck.setBeginTimeInsureCard(new Date());
	leasedTruck.setEndTimeInsureCard(new Date());
	leasedTruck.setContact("张三");
	leasedTruck.setContactPhone("13333333333");
	leasedTruck.setContactAddress("上海市青浦区徐泾镇");
	leasedTruck.setNotes("备注");
	leasedTruck.setVehicleType("vehicletype_tractors");
	
	leasedTruck.setActive(FossConstants.ACTIVE);
	leasedTruck.setCreateUser("100847-foss-GaoPeng");
	
	int result = leasedVehicleDao.addLeasedVehicle(leasedTruck);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleDao leasedVehicleDao = (LeasedVehicleDao) this.leasedVehicleDao;
	Assert.assertNotNull(leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck.getId()));
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#addLeasedVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedVehicleBySelective() {
	leasedTruck.setVehicleNo("沪A-D2222");
	leasedTruck.setVehicleFrameNo("CJH-2222222222");
	leasedTruck.setEngineNo("22222222222222222");
	leasedTruck.setBridge("双桥");
	leasedTruck.setDeadLoad(new BigDecimal(60));
	leasedTruck.setSelfWeight(new BigDecimal(23));
	leasedTruck.setBeginTime(new Date());
	leasedTruck.setEndTime(new Date());
	leasedTruck.setVehicleLength(new BigDecimal(7.6));
	leasedTruck.setVehicleHeight(new BigDecimal(8));
	leasedTruck.setVehicleWidth(new BigDecimal(6));
	leasedTruck.setGps(FossConstants.ACTIVE);
	leasedTruck.setBargainVehicle(FossConstants.ACTIVE);
	leasedTruck.setOperatingLic("YY-111111");
	leasedTruck.setBeginTimeOperatingLic(new Date());
	leasedTruck.setEndTimeOperatingLic(new Date());
	leasedTruck.setDrivingLicense("XS-222222");
	leasedTruck.setBeginTimeDrivingLic(new Date());
	leasedTruck.setEndTimeDrivingLic(new Date());
	leasedTruck.setInsureCard("BX-333333");
	leasedTruck.setBeginTimeInsureCard(new Date());
	leasedTruck.setEndTimeInsureCard(new Date());
	leasedTruck.setContactPhone("13600000000");
	leasedTruck.setNotes("备注");
	leasedTruck.setVehicleType("挂车");
	
	leasedTruck.setActive(FossConstants.ACTIVE);
	leasedTruck.setCreateUser("100847-foss-GaoPeng");
	
	leasedTruck.setContact(null);
	leasedTruck.setGpsProvider(null);
	leasedTruck.setContactAddress(null);
	
	int result = leasedVehicleDao.addLeasedVehicleBySelective(leasedTruck);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleDao leasedVehicleDao = (LeasedVehicleDao) this.leasedVehicleDao;
	LeasedTruckEntity leasedTruckTemp = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck.getId()); 
	
	Assert.assertNotNull(leasedTruckTemp);
	Assert.assertNull(leasedTruckTemp.getContact());
	Assert.assertNull(leasedTruckTemp.getGpsProvider());
	Assert.assertNull(leasedTruckTemp.getContactAddress());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#queryLeasedVehicle(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedVehicle() {
	//准备数据
	testAddLeasedVehicle();
	
	String id = leasedTruck.getId();
	leasedTruck = null;
	
	leasedTruck = leasedVehicleDao.queryLeasedVehicleBySelective(id);
	Assert.assertNotNull(leasedTruck);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#queryLeasedVehicleBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedVehicleBySelectiveCondition() {
	//准备数据
	testAddLeasedVehicle();
	
	leasedTruck = new LeasedTruckEntity();
	leasedTruck.setActive(FossConstants.ACTIVE);
	
	LeasedTruckEntity leasedTruckTem = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck);
	Assert.assertNotNull(leasedTruckTem);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#queryLeasedVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedVehicleListBySelectiveCondition() {
	//准备数据
	testAddLeasedVehicle();
	
	leasedTruck = new LeasedTruckEntity();
	leasedTruck.setActive(FossConstants.ACTIVE);
	
	List<LeasedTruckEntity> leasedTruckTems = leasedVehicleDao.queryLeasedVehicleListBySelectiveCondition(leasedTruck, 0, 2);
	Assert.assertNotNull(leasedTruckTems);
	Assert.assertTrue(leasedTruckTems.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#updateLeasedVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedVehicleBySelective() {
	//准备数据
	testAddLeasedVehicle();
	leasedTruck.setVehicleType("厢式车");
	
	int result = leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruck);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleDao leasedVehicleDao = (LeasedVehicleDao) this.leasedVehicleDao;
	LeasedTruckEntity leasedTruckTemp = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck.getId());
	
	Assert.assertNotNull(leasedTruckTemp);
	Assert.assertEquals(leasedTruck.getVehicleType(), leasedTruckTemp.getVehicleType());
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedVehicleDao#updateLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedVehicle() {
	//准备数据
	testAddLeasedVehicle();
	
	leasedTruck.setVehicleType("厢式车");
	leasedTruck.setContact(null);
	leasedTruck.setGpsProvider(null);
	leasedTruck.setContactAddress(null);
	
	int result = leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruck);
	Assert.assertTrue(result > 0);
	
	LeasedVehicleDao leasedVehicleDao = (LeasedVehicleDao) this.leasedVehicleDao;
	LeasedTruckEntity leasedTruckTemp = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck.getId());
	
	Assert.assertNotNull(leasedTruckTemp);
	Assert.assertEquals(leasedTruck.getVehicleType(), leasedTruckTemp.getVehicleType());
	Assert.assertNotNull(leasedTruckTemp.getContact());
	Assert.assertNotNull(leasedTruckTemp.getGpsProvider());
	Assert.assertNotNull(leasedTruckTemp.getContactAddress());
    }
    
    @Ignore
    @Test
    public void testQueryLeasedVehicleWithDriverDtosByCondition(){
	//准备数据
	testAddLeasedVehicle();
	
	List<LeasedVehicleWithDriverDto> leasedVehicleWithDriverDtos; 
	leasedVehicleWithDriverDtos = leasedVehicleDao.queryLeasedVehicleWithDriverDtosByCondition(leasedTruck.getVehicleNo(), null, null, null, null, "Y");
	
	Assert.assertNotNull(leasedVehicleWithDriverDtos);
	Assert.assertTrue(leasedVehicleWithDriverDtos.size() > 0);
    }
}
