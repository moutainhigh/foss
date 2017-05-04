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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/StorageDaoTest.java
 * 
 * FILE NAME        	: StorageDaoTest.java
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

import static com.deppon.foss.util.define.FossConstants.ACTIVE;


import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IStorageDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;


/**
 * 库位Dao测试类
 * @author foss-zhujunyong
 * @date Oct 17, 2012 9:34:30 AM
 * @version 1.0
 */
public class StorageDaoTest {

    private JdbcTemplate jdbc;
    private IStorageDao storageDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	storageDao = (IStorageDao) SpringTestHelper.get().getBeanByClass(StorageDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_storage where notes like 'zjy%'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#addStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    public void testAddStorage() {
	StorageEntity s = new StorageEntity();
	s.setCreateUser("110");
	s.setModifyUser("111");
	s.setOrganizationCode("1234");
	s.setStorageCode("002");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	s = storageDao.addStorage(s);
	Assert.assertNotNull(s);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#deleteStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    public void testDeleteStorage() {
	String id = UUIDUtils.getUUID();
	StorageEntity s = new StorageEntity();
	s.setNotes("zjysomebody, anybody");
	s.setId(id);
	storageDao.addStorage(s);
	StorageEntity result = storageDao.deleteStorage(s);
	Assert.assertNotNull(result);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#updateStorage(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    public void testUpdateStorage() {
	String id = UUIDUtils.getUUID();
	StorageEntity s = new StorageEntity();
	s.setId(id);
	s.setOrganizationCode("1122");
	s.setNotes("zjysomebody, anybody");
	storageDao.addStorage(s);
	s.setCreateDate(new Date());
	s.setCreateUser("110");
	s.setModifyDate(new Date());
	s.setModifyUser("111");
	s.setOrganizationCode("1234");
	s.setStorageCode("001");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	s = storageDao.updateStorage(s);
	Assert.assertNotNull(s);
	Assert.assertEquals(s.getStorageCode(), "001");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#queryStorageByVirtualCode(java.lang.String)}.
     */
    @Test
    public void testQueryStorageByVirtualCode() {
	StorageEntity s = new StorageEntity();
	s.setId(UUIDUtils.getUUID());
	s.setVirtualCode(s.getId());
	s.setCreateDate(new Date());
	s.setCreateUser("110");
	s.setModifyDate(new Date());
	s.setModifyUser("111");
	s.setOrganizationCode("1234");
	s.setStorageCode("001");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	storageDao.addStorage(s);

	String virtualCode = s.getVirtualCode();
//	StorageEntity e = storageDao.queryStorageByVirtualCode(virtualCode);
//	Assert.assertNotNull(e);
//	Assert.assertEquals(e.getActive(), s.getActive());
//	Assert.assertEquals(e.getOrganizationCode(), s.getOrganizationCode());
//	Assert.assertEquals(e.getCreateUser(), s.getCreateUser());
//	Assert.assertEquals(e.getNotes(), s.getNotes());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#queryStorageListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity, int, int)}.
     */
    @Test
    public void testQueryStorageListByCondition() {
	StorageEntity s = new StorageEntity();
	s.setOrganizationCode("12345");
	s.setStorageCode("1");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	storageDao.addStorage(s);

	List<StorageEntity> list = storageDao.queryStorageListByCondition(s, 0, 20);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#countStorageListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.StorageEntity)}.
     */
    @Test
    public void testCountStorageListByCondition() {
	StorageEntity s = new StorageEntity();
	s.setOrganizationCode("12345");
	s.setStorageCode("1");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	storageDao.addStorage(s);

	long count = storageDao.countStorageListByCondition(s);
	Assert.assertTrue(count > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.StorageDao#queryStorageListByOrganizationCode(java.lang.String)}.
     */
    @Test
    public void testQueryStorageListByOrganizationCode() {
	String organizationCode = "12345";
	
	StorageEntity s = new StorageEntity();
	s.setOrganizationCode(organizationCode);
	s.setStorageCode("1");
	s.setActive(ACTIVE);
	s.setNotes("zjysomebody, anybody");
	storageDao.addStorage(s);

	List<StorageEntity> list = storageDao.queryStorageListByOrganizationCode(organizationCode);
	Assert.assertTrue(list.size() > 0);
    }

}
