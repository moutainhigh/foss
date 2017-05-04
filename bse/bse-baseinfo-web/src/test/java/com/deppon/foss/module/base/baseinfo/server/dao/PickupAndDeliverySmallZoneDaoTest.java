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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/PickupAndDeliverySmallZoneDaoTest.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 集中接送货小区DAO实现类测试
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 上午9:28:21 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 上午9:28:21
 * @since
 * @version
 */
public class PickupAndDeliverySmallZoneDaoTest {
    
    private JdbcTemplate jdbc;
    //集中接送货小区DAO接口
    private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;
    private SmallZoneEntity smallZoneEntity = new SmallZoneEntity();

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-16 上午9:28:21
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	pickupAndDeliverySmallZoneDao = SpringTestHelper.get().getBeanByInterface(IPickupAndDeliverySmallZoneDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-16 上午9:28:21
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	
	jdbc.execute("delete from t_bas_service_smallzone");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliverySmallZoneDao#addPickupAndDeliverySmallZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)}.
     */
    @Ignore
    @Test
    public void testAddPickupAndDeliverySmallZone() {
	
	smallZoneEntity.setRegionCode("0001");
	smallZoneEntity.setRegionName("青浦接送货小区");
	smallZoneEntity.setManagement("青浦车队调度组");
	smallZoneEntity.setCreateDate(new Date());
	smallZoneEntity.setCreateUser("xieyantao");
	smallZoneEntity.setActive(FossConstants.ACTIVE);
	
	int result = pickupAndDeliverySmallZoneDao.addPickupAndDeliverySmallZone(smallZoneEntity);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliverySmallZoneDao#deletePickupAndDeliverySmallZoneByCode(java.lang.String[])}.
     */
    @Ignore
    @Test
    public void testDeletePickupAndDeliverySmallZoneByCode() {
	String[] codes = {"0001","0002","0003"};
	int result = pickupAndDeliverySmallZoneDao.deletePickupAndDeliverySmallZoneByCode(codes,"张三");
	
	Assert.assertTrue(result > 0);
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliverySmallZoneDao#updatePickupAndDeliverySmallZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)}.
     */
    @Ignore
    @Test
    public void testUpdatePickupAndDeliverySmallZone() {
	
	smallZoneEntity.setRegionCode("0001");
	smallZoneEntity.setRegionName("青浦接送货小区111");
	smallZoneEntity.setManagement("青浦车队调度组111");
	smallZoneEntity.setCreateUser("王菲");
	smallZoneEntity.setManagement("北京车队调度组");
	
	//定义一个虚拟编码code
	String[] codes = {smallZoneEntity.getRegionCode()};
	    
	//作废历史记录
	int flag = pickupAndDeliverySmallZoneDao.deletePickupAndDeliverySmallZoneByCode(codes, smallZoneEntity.getCreateUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    smallZoneEntity.setActive(FossConstants.ACTIVE);
	    smallZoneEntity.setId(UUIDUtils.getUUID());
	    
	    int res = pickupAndDeliverySmallZoneDao.addPickupAndDeliverySmallZone(smallZoneEntity);
	    Assert.assertTrue(res == 1);
	}	
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliverySmallZoneDao#queryPickupAndDeliverySmallZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryPickupAndDeliverySmallZones() {
	smallZoneEntity.setActive(FossConstants.ACTIVE);
	List<SmallZoneEntity> list = pickupAndDeliverySmallZoneDao.queryPickupAndDeliverySmallZones(smallZoneEntity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PickupAndDeliverySmallZoneDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	smallZoneEntity.setActive(FossConstants.ACTIVE);
	Long result = pickupAndDeliverySmallZoneDao.queryRecordCount(smallZoneEntity);
	Assert.assertTrue(result >  0);
    }
    
    /**
     * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件
     * 的集中接送货小区  
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 下午5:34:35
     * @see
     */
    @Ignore
    @Test
    public void testQuerySmallZonesByDeptCode(){
	
	List<SmallZoneEntity> list = pickupAndDeliverySmallZoneDao.querySmallZonesByDeptCode("北京车队调度组", "1","DE");
	
	Assert.assertTrue(list.size() > 0);
	
    }
    
    /**
     * 根据区域编码查询集中接送货小区详细信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午3:23:52
     * @see
     */
    @Test
    public void testQuerySmallZoneByCode(){
	
	SmallZoneEntity smallZoneEntity = pickupAndDeliverySmallZoneDao.querySmallZoneByCode("0004");
	
	Assert.assertNotNull(smallZoneEntity);
    }
    
    

}
