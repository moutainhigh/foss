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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/LineEntityCacheProvider.java
 * 
 * FILE NAME        	: LineEntityCacheProvider.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递线路cache provider类
 * @author foss-zhujunyong
 * @date Dec 29, 2012 10:46:45 AM
 * @version 1.0
 */
public class ExpressLineEntityCacheProvider implements ITTLCacheProvider<ExpressLineEntity>{

    private IExpressLineDao expresslineDao;
    
    /**
     * @param storageDao the storageDao to set
     */
    public void setExpresslineDao(IExpressLineDao expresslineDao) {
        this.expresslineDao = expresslineDao;
    }

    @Override
    public ExpressLineEntity get(String key) {
	if (StringUtils.isBlank(key)) {
	    return null;
	}
	ExpressLineEntity result = expresslineDao.queryLineByVirtualCode(key);
	// 如果通过虚拟编码查不到，再通过simpleCode查一遍
	if (result == null) {
		ExpressLineEntity condition = new ExpressLineEntity();
	    condition.setActive(FossConstants.ACTIVE);
	    condition.setSimpleCode(key);
	    List<ExpressLineEntity> resultList = expresslineDao.queryLineListByCondition(condition, 0, 1);
	    if (CollectionUtils.isEmpty(resultList)) {
		return null;
	    }
	    result = resultList.get(0);
	}
	return result;
    }


}
