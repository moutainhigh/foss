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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LimitedWarrantyItemsService.java
 * 
 * FILE NAME        	: LimitedWarrantyItemsService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILimitedWarrantyItemsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;

/**
 * 限保物品 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午9:4:21
 */
public class LimitedWarrantyItemsService implements ILimitedWarrantyItemsService {

    /**
     * 限保物品 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILimitedWarrantyItemsService#addLimitedWarrantyItems(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity addLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	return limitedWarrantyItemsDao.addLimitedWarrantyItems(entity);
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#deleteLimitedWarrantyItems(java.lang.String)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity deleteLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	return limitedWarrantyItemsDao.deleteLimitedWarrantyItems(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#deleteLimitedWarrantyItemsMore(java.lang.String[])
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity deleteLimitedWarrantyItemsMore(String[] codes , String deleteUser) {
	return limitedWarrantyItemsDao.deleteLimitedWarrantyItemsMore(codes, deleteUser);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#updateLimitedWarrantyItems(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity updateLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	return limitedWarrantyItemsDao.updateLimitedWarrantyItems(entity);
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsService#queryLimitedWarrantyItemsByCode(java.lang.String)
     */
    @Override
    public LimitedWarrantyItemsEntity queryLimitedWarrantyItemsByVirtualCode(String code) {
	if (null == code) {
	    return null;
	}
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsByVirtualCode(code);
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsService#queryLimitedWarrantyItemsByCode(java.lang.String)
     */
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsBatchByVirtualCode(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsService#queryLimitedWarrantyItemsExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity, int, int)
     */
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsExactByEntity(
	    LimitedWarrantyItemsEntity entity, int start, int limit) {
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsExactByEntity(
		entity, start, limit);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsService#queryLimitedWarrantyItemsExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    public long queryLimitedWarrantyItemsExactByEntityCount(LimitedWarrantyItemsEntity entity) {
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsService#deleteLimitedWarrantyItemsMore(java.lang.String[])
     */
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsByEntity(
	    LimitedWarrantyItemsEntity entity, int start, int limit) {
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsByEntity(entity, start, limit);
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:4:21
     * @see com.deppon.foss.module.baseinfo.server.service.ILimitedWarrantyItemsService#queryLimitedWarrantyItemsCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    public long queryLimitedWarrantyItemsByEntityCount(LimitedWarrantyItemsEntity entity) {
	return limitedWarrantyItemsDao.queryLimitedWarrantyItemsByEntityCount(entity);
    }


    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ILimitedWarrantyItemsDao limitedWarrantyItemsDao;

    public void setLimitedWarrantyItemsDao(ILimitedWarrantyItemsDao limitedWarrantyItemsDao) {
	this.limitedWarrantyItemsDao = limitedWarrantyItemsDao;
    }
}
