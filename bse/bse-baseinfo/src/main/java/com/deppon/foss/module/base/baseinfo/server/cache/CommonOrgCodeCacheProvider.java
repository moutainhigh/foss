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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/CommonOrgCodeCacheProvider.java
 * 
 * FILE NAME        	: CommonOrgCodeCacheProvider.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 组织编码cache provider类
 * @author foss-zhujunyong
 * @date Dec 10, 2012 10:46:45 AM
 * @version 1.0
 */
public class CommonOrgCodeCacheProvider implements ITTLCacheProvider<CommonOrgEntity> {

    private IPricingOrgDao pricingOrgDao;
    
    /**
     * @param pricingOrgDao the pricingOrgDao to set
     */
    public void setPricingOrgDao(IPricingOrgDao pricingOrgDao) {
        this.pricingOrgDao = pricingOrgDao;
    }

 
    @Override
    public CommonOrgEntity get(String key) {
	if (StringUtils.isBlank(key)) {
	    return null;
	}
	List<String> codes = new ArrayList<String> ();
	codes.add(key);
	List<CommonOrgEntity> list = pricingOrgDao.queryBatchOrgByCondition(codes, null, FossConstants.ACTIVE, null);
	if (CollectionUtils.isEmpty(list)) {
	    return null;
	}
	return list.get(0);
    }


    
   


}
