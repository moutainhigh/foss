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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LoadAndUnloadSquadDao.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车小队 DAO
 * 
 * 无版本控制，无时间建模
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:52:01
 */
public class LoadAndUnloadSquadDao extends SqlSessionDaoSupport implements
	ILoadAndUnloadSquadDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".loadAndUnloadSquad.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#addLoadAndUnloadSquad(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public LoadAndUnloadSquadEntity addLoadAndUnloadSquad(
	    LoadAndUnloadSquadEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(now);
	entity.setModifyDate(now);
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(
		NAMESPACE + "addLoadAndUnloadSquad", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#deleteLoadAndUnloadSquad(java.lang.String)
     */
    @Override
    public LoadAndUnloadSquadEntity deleteLoadAndUnloadSquad(
	    LoadAndUnloadSquadEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return null;
	}

	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(
		NAMESPACE + "deleteLoadAndUnloadSquad", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#deleteLoadAndUnloadSquadMore(java.lang.String[],
     *      java.lang.String)
     */
    @Override
    public LoadAndUnloadSquadEntity deleteLoadAndUnloadSquadMore(
	    String[] codes, String deleteUser) {
	// 请求合法性判断：
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}

	// 处理删除时要更新的数据
	Date now = new Date();
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);

	int result = getSqlSession().update(
		NAMESPACE + "deleteLoadAndUnloadSquadMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#updateLoadAndUnloadSquad(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public LoadAndUnloadSquadEntity updateLoadAndUnloadSquad(
	    LoadAndUnloadSquadEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return entity;
	}

	// 更新“无版本控制，无下载”的数据：
	Date now = new Date();
	entity.setModifyDate(now);
	/**
	 * modifyUser在entity中，此处不用设置
	 */

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entity", entity);
	// 只更新active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(
		NAMESPACE + "updateLoadAndUnloadSquad", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 以下全为查询：
     */

    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public LoadAndUnloadSquadEntity queryLoadAndUnloadSquadByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);

	List<LoadAndUnloadSquadEntity> entitys = this
		.getSqlSession()
		.selectList(NAMESPACE + "queryLoadAndUnloadSquadByCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(0);
	}
    }

    /**
     * 精确查询 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadBatchByCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (codes == null || codes.length == 0) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);

	return getSqlSession().selectList(
		NAMESPACE + "queryLoadAndUnloadSquadBatchByCode", map);
    }

    /**
     * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadExactByEntity(
	    LoadAndUnloadSquadEntity entity, int start, int limit) {
	LoadAndUnloadSquadEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadSquadEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(
		NAMESPACE + "queryLoadAndUnloadSquadExactByEntity",
		queryEntity, rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页 动态的查询条件。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public long queryLoadAndUnloadSquadExactByEntityCount(
	    LoadAndUnloadSquadEntity entity) {
	LoadAndUnloadSquadEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadSquadEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession().selectOne(
		NAMESPACE + "queryLoadAndUnloadSquadExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadByEntity(
	    LoadAndUnloadSquadEntity entity, int start, int limit) {
	LoadAndUnloadSquadEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadSquadEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(
		NAMESPACE + "queryLoadAndUnloadSquadByEntity", queryEntity,
		rowBounds);
    }

    /**
     * 模糊查询 动态的查询条件-查询总条数。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:22:38
     * @see com.deppon.foss.module.base.baseinfo.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity)
     */
    @Override
    public long queryLoadAndUnloadSquadByEntityCount(
	    LoadAndUnloadSquadEntity entity) {
	LoadAndUnloadSquadEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadSquadEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession()
		.selectOne(NAMESPACE + "queryLoadAndUnloadSquadByEntityCount",
			queryEntity);
    }

}
