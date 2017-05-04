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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/refresh/RefreshCacheTest.java
 * 
 * FILE NAME        	: RefreshCacheTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.refresh;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.logdesc.LogErrorDesc;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.RefreshableCache;
import com.deppon.foss.framework.entity.IFunction;
import com.deppon.foss.framework.entity.IRole;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IWorkdayComplexService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.WorkdayComplexService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

@Ignore
public class RefreshCacheTest {
    

    /**
     * 刷新角色缓存
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    @Test
    public void refreshResource(){
	/**
	 * 执行刷新用户缓存的操作
	 */
	@SuppressWarnings("unchecked")
	RefreshableCache<String, Object> refreshableCache = (RefreshableCache<String, Object>) CacheManager
		.getInstance().getCache(IFunction.class.getName());

	boolean isRefresh = false;
	if (refreshableCache != null) {
	    refreshableCache.invalid();
	}

	if (!isRefresh) {
	    LOGGER.error(LogErrorDesc.USER_CACHE_REFRESH_FAIL);
	}
    }    
    

    /**
     * 刷新角色缓存
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    @Test
    public void refreshRole(){
	/**
	 * 执行刷新用户缓存的操作
	 */
	@SuppressWarnings("unchecked")
	RefreshableCache<String, Object> refreshableCache = (RefreshableCache<String, Object>) CacheManager
		.getInstance().getCache(IRole.class.getName());

	boolean isRefresh = false;
	if (refreshableCache != null) {
	    refreshableCache.invalid();
	}

	if (!isRefresh) {
	    LOGGER.error(LogErrorDesc.USER_CACHE_REFRESH_FAIL);
	}
    }    

    /**
     * 刷新角色缓存
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    @Test
    public void refreshUser(){
	/**
	 * 执行刷新用户缓存的操作
	 */
	@SuppressWarnings("unchecked")
	RefreshableCache<String, Object> refreshableCache = (RefreshableCache<String, Object>) CacheManager
		.getInstance().getCache(IUser.class.getName());

	boolean isRefresh = false;
	if (refreshableCache != null) {
	    refreshableCache.invalid();
	}

	if (!isRefresh) {
	    LOGGER.error(LogErrorDesc.USER_CACHE_REFRESH_FAIL);
	}
    }
    



    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshCacheTest.class);

    
    /**
     * 下面是测试用的工具
     */
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_WORKDAY where id = '" + id + "'");
	
    }

    static int count=0;
    private JdbcTemplate jdbc;
    private IWorkdayComplexService workdayComplexService;
    
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	// jdbc.execute("delete from t_bas_storage");
	workdayComplexService = (IWorkdayComplexService) SpringTestHelper
		.get().getBeanByClass(WorkdayComplexService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }
}
