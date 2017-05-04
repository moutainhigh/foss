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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/OutFieldEntityCacheProvider.java
 * 
 * FILE NAME        	: OutFieldEntityCacheProvider.java
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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;


/**
 * 省市区县cache provider类
 * @author foss-zhujunyong
 * @date Dec 10, 2012 10:46:45 AM
 * @version 1.0
 */
public class DistrictEntityCacheProvider implements ITTLCacheProvider<AdministrativeRegionsEntity> {


    private IAdministrativeRegionsDao administrativeRegionsDao;
    
    /**
     * @param administrativeRegionsDao the administrativeRegionsDao to set
     */
    public void setAdministrativeRegionsDao(
    	IAdministrativeRegionsDao administrativeRegionsDao) {
        this.administrativeRegionsDao = administrativeRegionsDao;
    }



    @Override
    public AdministrativeRegionsEntity get(String key) {
	if (StringUtils.isBlank(key)) {
	    return null;
	}
	return administrativeRegionsDao.queryAdministrativeRegionsByCode(key);
    }



}
