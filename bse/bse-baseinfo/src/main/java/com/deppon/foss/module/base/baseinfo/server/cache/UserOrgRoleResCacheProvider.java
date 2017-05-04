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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/UserCacheProvider.java
 * 
 * FILE NAME        	: UserCacheProvider.java
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
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:用户对象数据缓存提供者</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public class UserOrgRoleResCacheProvider implements ITTLCacheProvider<List<Set<String>>> {

	private IUserDao userDao;

	public Date getLastModifyTime() {
		return userDao.getLastModifyTime();
	}

	/**
	 * 从数据库加载数据到缓存中
	 * 
	 * 如果要在加入缓存之前附加一些数据，在此方法中操作
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-28 上午9:06:55
	 * @see com.deppon.foss.framework.cache.provider.ILazyCacheProvider#get(java.lang.Object)
	 */
	public List<Set<String>> get(String key) {
		String[] strs = key.split("#");
		String userCode = strs[0];
		String deptCode  = strs[1];
		List<Set<String>> listInfo = userDao.getOrgResCodeUrisByCode(userCode, deptCode);
		return listInfo;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
