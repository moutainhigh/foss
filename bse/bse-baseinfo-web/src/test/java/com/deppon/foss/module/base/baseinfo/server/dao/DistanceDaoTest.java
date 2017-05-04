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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/DistanceDaoTest.java
 * 
 * FILE NAME        	: DistanceDaoTest.java
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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDistanceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 月台到库位的距离
 * @author foss-zhujunyong
 * @date Oct 18, 2012 11:12:50 AM
 * @version 1.0
 */
public class DistanceDaoTest {

    private JdbcTemplate jdbc;
    private IDistanceDao distanceDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	distanceDao = (IDistanceDao) SpringTestHelper.get().getBeanByClass(DistanceDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from T_BAS_DIS_PLAT_STORAGE where STORAGE_VIRTUAL_CODE like 'zjy%'");
    }
    
    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#addDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.StoragePlatformDistanceEntity)}.
     */
    @Test
    public void testAddDistance() {
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode("zjy111");
	d.setPlatformVirtualCode("222");
	d.setDistance(100);
	d = distanceDao.addDistance(d);
	Assert.assertNotNull(d);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#deleteDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.StoragePlatformDistanceEntity)}.
     */
    @Test
    public void testDeleteDistance() {
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode("zjy111");
	d.setPlatformVirtualCode("222");
	d.setDistance(100);
	d = distanceDao.addDistance(d);
	
	int result = distanceDao.deleteDistance(d);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#deleteDistanceByStorage(java.lang.String)}.
     */
    @Test
    public void testDeleteDistanceByStorage() {
	String storageVirtualCode = "zjy222";
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode(storageVirtualCode);
	d.setPlatformVirtualCode("111");
	d.setDistance(100);
	d = distanceDao.addDistance(d);
	
	int result = distanceDao.deleteDistanceByStorage(storageVirtualCode);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#deleteDistanceByPlatform(java.lang.String)}.
     */
    @Test
    public void testDeleteDistanceByPlatform() {
	String storageVirtualCode = "zjy222";
	String platformVirtualCode = "111";
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode(storageVirtualCode);
	d.setPlatformVirtualCode(platformVirtualCode);
	d.setDistance(100);
	d = distanceDao.addDistance(d);
	
	int result = distanceDao.deleteDistanceByPlatform(platformVirtualCode);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#updateDistance(com.deppon.foss.module.base.baseinfo.api.shared.domain.StoragePlatformDistanceEntity)}.
     */
    @Test
    public void testUpdateDistance() {
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode("zjy111");
	d.setPlatformVirtualCode("222");
	d.setDistance(100);
	d = distanceDao.addDistance(d);
	d.setDistance(102);
	d = distanceDao.updateDistance(d);
	Assert.assertNotNull(d);
	Assert.assertEquals(d.getDistance(), new Integer(102));
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#queryDistanceByStorage(java.lang.String)}.
     */
    @Test
    public void testQueryDistanceByStorage() {
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode("zjy123");
	d.setPlatformVirtualCode("321");
	d.setDistance(100);
	distanceDao.addDistance(d);
	
	List<DistanceEntity> list = distanceDao.queryDistanceByStorage("zjy123");
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.DistanceDao#queryDistanceByPlatform(java.lang.String)}.
     */
    @Test
    public void testQueryDistanceByPlatform() {
	DistanceEntity d = new DistanceEntity();
	d.setStorageVirtualCode("zjy123");
	d.setPlatformVirtualCode("321");
	d.setDistance(100);
	distanceDao.addDistance(d);
	
	List<DistanceEntity> list = distanceDao.queryDistanceByPlatform("321");
	Assert.assertTrue(list.size() > 0);
    }

}
