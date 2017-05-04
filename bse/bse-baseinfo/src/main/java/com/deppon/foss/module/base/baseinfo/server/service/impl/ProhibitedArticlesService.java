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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ProhibitedArticlesService.java
 * 
 * FILE NAME        	: ProhibitedArticlesService.java
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProhibitedArticlesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;

/**
 * 禁运物品 Service实现
 * 
 * 对应表 T_BAS_PROHIBIT_GOODS
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午9:20:24
 */
public class ProhibitedArticlesService implements IProhibitedArticlesService {

    /**
     * 禁运物品 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IProhibitedArticlesService#addProhibitedArticles(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    @Transactional
    public ProhibitedArticlesEntity addProhibitedArticles(ProhibitedArticlesEntity entity) {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	return prohibitedArticlesDao.addProhibitedArticles(entity);
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#deleteProhibitedArticles(java.lang.String)
     */
    @Override
    @Transactional
    public ProhibitedArticlesEntity deleteProhibitedArticles(ProhibitedArticlesEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	return prohibitedArticlesDao.deleteProhibitedArticles(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#deleteProhibitedArticlesMore(java.lang.String[])
     */
    @Override
    @Transactional
    public ProhibitedArticlesEntity deleteProhibitedArticlesMore(String[] codes , String deleteUser) {
	return prohibitedArticlesDao.deleteProhibitedArticlesMore(codes, deleteUser);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#updateProhibitedArticles(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    @Transactional
    public ProhibitedArticlesEntity updateProhibitedArticles(ProhibitedArticlesEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	return prohibitedArticlesDao.updateProhibitedArticles(entity);
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesService#queryProhibitedArticlesByCode(java.lang.String)
     */
    @Override
    public ProhibitedArticlesEntity queryProhibitedArticlesByVirtualCode(String code) {
	if (null == code) {
	    return null;
	}
	return prohibitedArticlesDao.queryProhibitedArticlesByVirtualCode(code);
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesService#queryProhibitedArticlesByCode(java.lang.String)
     */
    @Override
    public List<ProhibitedArticlesEntity> queryProhibitedArticlesBatchByVirtualCode(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	return prohibitedArticlesDao.queryProhibitedArticlesBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesService#queryProhibitedArticlesExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity, int, int)
     */
    @Override
    public List<ProhibitedArticlesEntity> queryProhibitedArticlesExactByEntity(
	    ProhibitedArticlesEntity entity, int start, int limit) {
	return prohibitedArticlesDao.queryProhibitedArticlesExactByEntity(
		entity, start, limit);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesService#queryProhibitedArticlesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    public long queryProhibitedArticlesExactByEntityCount(ProhibitedArticlesEntity entity) {
	return prohibitedArticlesDao.queryProhibitedArticlesExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesService#deleteProhibitedArticlesMore(java.lang.String[])
     */
    @Override
    public List<ProhibitedArticlesEntity> queryProhibitedArticlesByEntity(
	    ProhibitedArticlesEntity entity, int start, int limit) {
	return prohibitedArticlesDao.queryProhibitedArticlesByEntity(entity, start, limit);
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:20:24
     * @see com.deppon.foss.module.baseinfo.server.service.IProhibitedArticlesService#queryProhibitedArticlesCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    public long queryProhibitedArticlesByEntityCount(ProhibitedArticlesEntity entity) {
	return prohibitedArticlesDao.queryProhibitedArticlesByEntityCount(entity);
    }


    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private IProhibitedArticlesDao prohibitedArticlesDao;

    public void setProhibitedArticlesDao(IProhibitedArticlesDao prohibitedArticlesDao) {
	this.prohibitedArticlesDao = prohibitedArticlesDao;
    }
}
