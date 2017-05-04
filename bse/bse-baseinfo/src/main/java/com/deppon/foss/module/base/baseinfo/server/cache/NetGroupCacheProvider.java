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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/NetGroupCacheProvider.java
 * 
 * FILE NAME        	: NetGroupCacheProvider.java
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

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 网点组cache provider类
 * @author foss-zhujunyong
 * @date Dec 29, 2012 10:46:45 AM
 * @version 1.0
 */
public class NetGroupCacheProvider implements ITTLCacheProvider<List<NetGroupEntity>>{

    private INetGroupDao netGroupDao;
    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 11, 2013 3:07:33 PM
     * @param netGroupDao
     * @see
     */
    public void setNetGroupDao(INetGroupDao netGroupDao) {
        this.netGroupDao = netGroupDao;
    }

    @Override
    public List<NetGroupEntity> get(String key) {
	if (StringUtils.isBlank(key) || !key.contains(SymbolConstants.EN_COLON)) {
	    return null;
	}
	
	String[] keys = key.split(SymbolConstants.EN_COLON);
	NetGroupEntity entity = new NetGroupEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setSourceOrganizationCode(keys[0]);
	entity.setTargetOrganizationCode(keys[1]);
	return netGroupDao.queryNetGroupBySourceTarget(entity);
    }

}
