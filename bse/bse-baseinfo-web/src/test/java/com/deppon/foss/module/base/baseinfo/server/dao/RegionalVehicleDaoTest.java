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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/RegionalVehicleDaoTest.java
 * 
 * FILE NAME        	: RegionalVehicleDaoTest.java
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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 车辆定区DAO测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-26 下午3:17:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-26 下午3:17:19
 * @since
 * @version
 */
public class RegionalVehicleDaoTest {

    private JdbcTemplate jdbc;
    
    private IRegionalVehicleDao regionalVehicleDao;
    private RegionVehicleEntity entity = new RegionVehicleEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	regionalVehicleDao = SpringTestHelper.get().getBeanByInterface(IRegionalVehicleDao.class);
    }


    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-26 下午3:17:19
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_REGION_VEHICLE");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.RegionalVehicleDao#addRegionalVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)}.
     */
    @Ignore
    @Test
    public void testAddRegionalVehicle() {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	entity.setRegionNature(ComnConst.REGION_NATURE_BQ);
	entity.setCreateUser("杨坤");
	entity.setModifyUser(entity.getCreateUser());
	entity.setRegionCode("0507S001");
	entity.setRegionName("广州集中送货大区");
	entity.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
	entity.setVehicleNo("粤A8888");
	entity.setVehicleType("机动车");
	entity.setVehicleDept("广州短途车队");
	
	int result = regionalVehicleDao.addRegionalVehicle(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.RegionalVehicleDao#deleteRegionalVehicleByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteRegionalVehicleByCode() {
//	String[] codes = {"f7e5d7b7-6649-46e2-9009-f4a66907c402","8c52841c-6f36-4ce6-9a0e-7081a48eb162"};
    List<String> codes=new ArrayList<String>();
    codes.add("f7e5d7b7-6649-46e2-9009-f4a66907c402");
    codes.add("8c52841c-6f36-4ce6-9a0e-7081a48eb162");
	int result = regionalVehicleDao.deleteRegionalVehicleByCode(codes, "刘德华");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.RegionalVehicleDao#updateRegionalVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateRegionalVehicle() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("杨东煜");
	entity.setModifyUser(entity.getCreateUser());
	entity.setRegionCode("0507J001");
	entity.setRegionName("广州集中接货大区");
	entity.setRegionNature(ComnConst.REGION_NATURE_BQ);
	entity.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
	entity.setVehicleNo("粤A8999");
	entity.setVehicleType("机动车");
	entity.setVehicleDept("广州短途车队");
	entity.setVirtualCode("087b5406-aa4c-4f0b-87b0-e8225c08fcbf");
	
	//定义一个虚拟编码code
//	String[] codes = {entity.getVirtualCode()};
	List<String> codes=new ArrayList<String>();
	codes.add(entity.getVirtualCode());
		    
	//作废历史记录
	int flag = regionalVehicleDao.deleteRegionalVehicleByCode(codes, entity.getModifyUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    int res = regionalVehicleDao.addRegionalVehicle(entity);
	    Assert.assertTrue(res == 1);
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.RegionalVehicleDao#queryRegionalVehicles(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryRegionalVehicles() {
	
	entity.setActive(FossConstants.ACTIVE);
	
	List<RegionVehicleInfoDto> list = regionalVehicleDao.queryRegionalVehicles(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.RegionalVehicleDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	
	Long result = regionalVehicleDao.queryRecordCount(entity);
	
	Assert.assertTrue(result >  0);
    }
    
    /**
     * 根据区域编码查询定车定区绑定的车辆信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午4:46:29
     * @see
     */
    @Ignore
    @Test
    public void testQueryRegionVehiclesByCode(){
	
	List<RegionVehicleEntity> list = regionalVehicleDao.queryRegionVehiclesByCode("05071J001");
	
	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>(根据传入的参数查询车辆定区信息)</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-7 下午2:55:37
     * @see
     */
    @Ignore
    @Test
    public void testQueryRegionVehiclesByParam(){
	
	List<String> vehicleNoList = new ArrayList<String>();
	vehicleNoList.add("粤A8888");
	vehicleNoList.add("粤A8999");
	
	List<RegionVehicleEntity> list = regionalVehicleDao.queryRegionVehiclesByParam(vehicleNoList, "接货区", "BQ");
	
	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>根据gis的区域id匹配接货小区id及车辆车牌号</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午2:18:29
     * @see
     */
    @Ignore
    @Test
    public void testQuerySmallZoneInfoByGisId(){
	
	List<RegionVehicleEntity> entity = regionalVehicleDao.querySmallZoneInfoByGisId("0001");
	
	Assert.assertNotNull(entity);
    }
    
    /**
     * <p>通过传入一个车队或车队下调度组的code，查询出车队下的所有接货小区</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午2:18:00
     * @see
     */
    @Test
    public void testQueryRegionVehiclesByOrgCode(){
	List<String> list = new ArrayList<String>();
	list.add("00001");
	list.add("00002");
	list.add("00003");
	List<RegionVehicleEntity> entitys = regionalVehicleDao.queryRegionVehiclesByOrgCode(list);
	
	Assert.assertTrue(entitys.size() > 0);
    }

}
