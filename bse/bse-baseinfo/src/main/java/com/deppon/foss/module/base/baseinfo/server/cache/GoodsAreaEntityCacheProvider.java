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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/GoodsAreaEntityCacheProvider.java
 * 
 * FILE NAME        	: GoodsAreaEntityCacheProvider.java
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
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库区cache provider类
 * @author foss-zhujunyong
 * @date Dec 17, 2012 10:46:45 AM
 * @version 1.0
 */
public class GoodsAreaEntityCacheProvider implements ITTLCacheProvider<GoodsAreaEntity>{

    private IGoodsAreaDao goodsAreaDao;
    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 11, 2013 2:50:33 PM
     * @param goodsAreaDao
     * @see
     */
    public void setGoodsAreaDao(IGoodsAreaDao goodsAreaDao) {
        this.goodsAreaDao = goodsAreaDao;
    }


    @Override
    public GoodsAreaEntity get(String key) {
	if (StringUtils.isBlank(key)) {
	    return null;
	}
	// 有冒号分割的，说明是通过库区编号查询
	if (key.contains(SymbolConstants.EN_COLON)) {
	    String[] keys = key.split(SymbolConstants.EN_COLON);
	    GoodsAreaEntity c = new GoodsAreaEntity();
	    c.setActive(FossConstants.ACTIVE);
	    c.setOrganizationCode(keys[0]);
	    c.setGoodsAreaCode(keys[1]);
	    List<GoodsAreaEntity> list = goodsAreaDao.queryGoodsAreaByCondition(c, 0, 1);
	    return CollectionUtils.isEmpty(list) ? null : list.get(0);
	} else { // 没有冒号的，说明是通过虚拟编码查询
	    return goodsAreaDao.queryGoodsAreaByVirtualCode(key);
	}
    }


}
