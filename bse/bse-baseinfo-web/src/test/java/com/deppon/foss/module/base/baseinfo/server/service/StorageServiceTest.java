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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/StorageServiceTest.java
 * 
 * FILE NAME        	: StorageServiceTest.java
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
package com.deppon.foss.module.base.baseinfo.server.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IStorageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.StorageService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 库位测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 */
public class StorageServiceTest {

    private JdbcTemplate jdbc;
    private IStorageService storageService;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	jdbc.execute("delete from t_bas_storage where virtual_code like 'zjy%'");
	jdbc.execute("delete from t_bas_dis_plat_storage where platform_virtual_code like 'zjy%'");
	jdbc.execute("delete from t_bas_platform where virtual_code like 'zjy%'");
	jdbc.execute("insert into t_bas_platform (id, virtual_code, org_code, active) values('100', 'zjy100', '100', 'Y')");
	jdbc.execute("insert into t_bas_platform (id, virtual_code, org_code, active) values('200', 'zjy200', '100', 'Y')");
	storageService = (IStorageService) SpringTestHelper.get().getBeanByClass(StorageService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.StorageService#addStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    @Ignore
    public void testAddStorage() {
	StorageEntity s = new StorageEntity();
	s.setStorageCode("200");
	s.setOrganizationCode("100");
	
	List<DistanceEntity> list = new ArrayList<DistanceEntity> ();
	for (int i = 1; i <= 2; i++) {
	    DistanceEntity d = new DistanceEntity();
	    d.setPlatformVirtualCode((i*100) + "");
	    d.setDistance(i);
	    list.add(d);
	}
	//s.setDistanceList(list);
	//s = storageService.addStorage(s);
	Assert.assertNotNull(s);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.StorageService#deleteStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Ignore
    @Test
    public void testDeleteStorage() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.StorageService#updateStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    public void testUpdateStorage() {
	StorageEntity s = new StorageEntity();
	s.setStorageCode("STORAGECODE-200");
	s.setOrganizationCode("ORGCODE-100");

	List<DistanceEntity> list1 = new ArrayList<DistanceEntity> ();
	DistanceEntity d1 = new DistanceEntity();
	d1.setPlatformVirtualCode("zjy100");
	d1.setDistance(123);
	DistanceEntity d2 = new DistanceEntity();
	d2.setPlatformVirtualCode("zjy200");
	d2.setDistance(222);
	list1.add(d1);
	list1.add(d2);
	//s.setDistanceList(list1);
	//s = storageService.addStorage(s);
	
	d1.setDistance(null);
	
	List<DistanceEntity> list2 = new ArrayList<DistanceEntity> ();
	list2.add(d1);
	//s.setDistanceList(list2);
	//s = storageService.updateStorage(s);
	

    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.StorageService#queryStorageByVirtualCode(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryStorageByVirtualCode() {
	fail("Not yet implemented");
    }

}
