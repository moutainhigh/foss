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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/PlatformDaoTest.java
 * 
 * FILE NAME        	: PlatformDaoTest.java
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
import static com.deppon.foss.util.define.FossConstants.INACTIVE;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;

/**
 * 月台Dao测试类
 * @author zhujunyong
 * @date Oct 12, 2012 11:50:21 AM
 * @version
 */
public class PlatformDaoTest {

    
    private JdbcTemplate jdbc;
    private IPlatformDao platformDao;
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	platformDao = (IPlatformDao) SpringTestHelper.get().getBeanByClass(PlatformDao.class);
    }
    
    @After
    public void teardown() {
	jdbc.execute("delete from t_bas_platform where notes like 'zjy%'");
    }
    

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#addPlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)}.
     */
    @Test
    public void testAddPlatform() {
	PlatformEntity p = new PlatformEntity();
	p.setCreateUser("110");
	p.setModifyUser("111");
	p.setOrganizationCode("1234");
	p.setPlatformCode("001");
	p.setPosition("DistrictA");
	p.setActive(ACTIVE);
	p.setHasLift(INACTIVE);
//	p.setFourPointTwo(ACTIVE);
//	p.setSixPointFive(ACTIVE);
//	p.setSevenPointSix(ACTIVE);
//	p.setNinePointSix(INACTIVE);
//	p.setSeventeenPointFive(INACTIVE);
	p.setNotes("zjysomebody, anybody");
	p = platformDao.addPlatform(p);
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#deletePlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)}.
     */
    @Test
    public void testDeletePlatform() {
	String id = UUIDUtils.getUUID();
	PlatformEntity p = new PlatformEntity();
	p.setId(id);
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);
	PlatformEntity result = platformDao.deletePlatform(p);
	Assert.assertNotNull(result);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#updatePlatform(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)}.
     */
    @Test
    public void testUpdatePlatform() {
	String id = UUIDUtils.getUUID();
	PlatformEntity p = new PlatformEntity();
	p.setId(id);
	p.setOrganizationCode("1122");
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);
	p.setCreateDate(new Date());
	p.setCreateUser("110");
	p.setModifyDate(new Date());
	p.setModifyUser("111");
	p.setOrganizationCode("1234");
	p.setPlatformCode("001");
	p.setPosition("DistrictA");
	p.setActive(ACTIVE);
	p.setHasLift(INACTIVE);
//	p.setFourPointTwo(ACTIVE);
//	p.setSixPointFive(ACTIVE);
//	p.setSevenPointSix(ACTIVE);
//	p.setNinePointSix(INACTIVE);
//	p.setSeventeenPointFive(INACTIVE);
	p.setNotes("zjysomebody, anybody");
	p = platformDao.updatePlatform(p);
	Assert.assertNotNull(p);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#queryPlatformByVirtualCode(java.lang.String)}.
     */
    @Test
    public void testGetPlatformByVirtualCode() {
	PlatformEntity p = new PlatformEntity();
	p.setId(UUIDUtils.getUUID());
	p.setVirtualCode(p.getId());
	p.setCreateDate(new Date());
	p.setCreateUser("110");
	p.setModifyDate(new Date());
	p.setModifyUser("111");
	p.setOrganizationCode("1234");
	p.setPlatformCode("001");
	p.setPosition("DistrictA");
	p.setActive(ACTIVE);
	p.setHasLift(INACTIVE);
//	p.setFourPointTwo(ACTIVE);
//	p.setSixPointFive(ACTIVE);
//	p.setSevenPointSix(ACTIVE);
//	p.setNinePointSix(INACTIVE);
//	p.setSeventeenPointFive(INACTIVE);
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);

	String virtualCode = p.getVirtualCode();
	PlatformEntity e = platformDao.queryPlatformByVirtualCode(virtualCode);
	Assert.assertNotNull(e);
	Assert.assertEquals(e.getActive(), p.getActive());
	Assert.assertEquals(e.getOrganizationCode(), p.getOrganizationCode());
	Assert.assertEquals(e.getCreateUser(), p.getCreateUser());
	Assert.assertEquals(e.getPlatformCode(), p.getPlatformCode());
	Assert.assertEquals(e.getHasLift(), p.getHasLift());
	Assert.assertEquals(e.getHeight(), p.getHeight());
	Assert.assertEquals(e.getNotes(), p.getNotes());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#queryPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity, int, int)}.
     */
    @Test
    public void testQueryPlatformListByCondition() {
	PlatformEntity p = new PlatformEntity();
	p.setOrganizationCode("12345");
	p.setPlatformCode("1");
	p.setPosition("A");
//	p.setFourPointTwo(ACTIVE);
	p.setActive(ACTIVE);
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);

	List<PlatformEntity> list = platformDao.queryPlatformListByCondition(p, 0, 20);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PlatformDao#countPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)}.
     */
    @Test
    public void testCountPlatformListByCondition() {
	PlatformEntity p = new PlatformEntity();
	p.setOrganizationCode("12345");
	p.setPlatformCode("1");
	p.setPosition("A");
//	p.setFourPointTwo(ACTIVE);
	p.setActive(ACTIVE);
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);

	long count = platformDao.countPlatformListByCondition(p);
	Assert.assertTrue(count > 0);
    }


    
    @Test
    public void testQueryPlatformListByOrgCodeAndPlatformCodeLimit(){
	String organizationCode = "12345";
	
	PlatformEntity p = new PlatformEntity();
	p.setOrganizationCode(organizationCode);
	p.setPlatformCode("001");
	p.setPosition("A");
//	p.setFourPointTwo(ACTIVE);
	p.setActive(ACTIVE);
	p.setNotes("zjysomebody, anybody");
	platformDao.addPlatform(p);
	p.setPlatformCode("002");
	platformDao.addPlatform(p);
	p.setPlatformCode("003");
	platformDao.addPlatform(p);
	p.setPlatformCode("004");
	platformDao.addPlatform(p);

	List<PlatformEntity> list = platformDao.queryPlatformListByOrgCodeAndPlatformCodeLimit(organizationCode, null, null);
	Assert.assertTrue(list.size() > 0);
	for (PlatformEntity entity : list) {
	    Assert.assertNotNull(entity.getPlatformCode());
	}
	
    }
    
}
