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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/OrgLayerCacheProvider.java
 * 
 * FILE NAME        	: OrgLayerCacheProvider.java
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
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrganizationLayerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;

/**
 * 组织层级Provider
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-22 上午9:54:58
 */
public class OrgLayerCacheProvider implements ITTLCacheProvider<List<OrganizationLayerEntity>> {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(OrgLayerCacheProvider.class);
	
    private IOrganizationLayerDao organizationLayerDao;

    /** 
     * 根据组织code查询组织层级实体信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-2-22 上午10:16:46
     */
    @Override
    public List<OrganizationLayerEntity> get(String code) {
    	if (StringUtils.isBlank(code)) {
    	    return null;
    	}
    	return organizationLayerDao.queryOrgLayerInfoByCode(code);
    }

	public void setOrganizationLayerDao(IOrganizationLayerDao organizationLayerDao) {
		this.organizationLayerDao = organizationLayerDao;
	}
}
