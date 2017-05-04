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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/test/dao/UserDaoTest.java
 * 
 * FILE NAME        	: UserDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.test.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.cache.UserCacheProvider;
import com.deppon.foss.module.base.baseinfo.test.BaseTestCase;

public class UserDaoTest extends BaseTestCase {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private UserCacheProvider userCacheProvider;
	
	@Test
	public void testGet(){
		Date startTime = new Date();
		IUser user=userCacheProvider.get("dc7e8c1c-9420-42dc-a1e1-b22b0be02d80");
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testGetUserWithRoleIdAndOrgIdById(){
		Date startTime = new Date();
		UserEntity userEntity=userDao.getUserWithRoleIdAndOrgIdById("dc7e8c1c-9420-42dc-a1e1-b22b0be02d80");
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		Assert.assertNotNull(userEntity);
	}
	
	@Test
	public void testGetOrgResCodeUrisByCode(){
		Date startTime = new Date();
		List<Set<String>> listInfo =userDao.getOrgResCodeUrisByCode("000000","W01");
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		Assert.assertNotNull(listInfo);
	}
	
	@Test
	public void testGetUserEmployeeInfoByCode(){
		Date startTime = new Date();
		EmployeeEntity emp = userDao.getUserEmployeeInfoByCode("000000");
		Date endTime = new Date();
		System.out.println(endTime.getTime() - startTime.getTime());
		Assert.assertNotNull(emp);
	}	
	
}
