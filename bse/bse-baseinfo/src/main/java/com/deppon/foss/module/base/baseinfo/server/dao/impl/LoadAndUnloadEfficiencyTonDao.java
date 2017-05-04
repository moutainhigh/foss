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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LoadAndUnloadEfficiencyTonDao.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonDao.java
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
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车标准-吨-时间 DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:02:38
 */
public class LoadAndUnloadEfficiencyTonDao extends SqlSessionDaoSupport implements
	ILoadAndUnloadEfficiencyTonDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".loadAndUnloadEfficiencyTon.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#addLoadAndUnloadEfficiencyTon(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    @Transactional
    public LoadAndUnloadEfficiencyTonEntity addLoadAndUnloadEfficiencyTon(LoadAndUnloadEfficiencyTonEntity entity) {
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
	int result = getSqlSession().insert(NAMESPACE + "addLoadAndUnloadEfficiencyTon", entity);
	return result > 0 ? entity : null;
    }

    /**
     * 通过ORG_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#deleteLoadAndUnloadEfficiencyTon(java.lang.String)
     */
    @Override
    public LoadAndUnloadEfficiencyTonEntity deleteLoadAndUnloadEfficiencyTon(LoadAndUnloadEfficiencyTonEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getOrgCode())) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteLoadAndUnloadEfficiencyTon", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过ORG_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#deleteLoadAndUnloadEfficiencyTonMore(java.lang.String[], java.lang.String)
     */
    @Override
    public LoadAndUnloadEfficiencyTonEntity deleteLoadAndUnloadEfficiencyTonMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(codes == null || codes.length == 0) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	LoadAndUnloadEfficiencyTonEntity entity = new LoadAndUnloadEfficiencyTonEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteLoadAndUnloadEfficiencyTonMore", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过ORG_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     */
    @Override
    public LoadAndUnloadEfficiencyTonEntity updateLoadAndUnloadEfficiencyTon(LoadAndUnloadEfficiencyTonEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getOrgCode())) {
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
		NAMESPACE + "updateLoadAndUnloadEfficiencyTon", map);
	return result > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public LoadAndUnloadEfficiencyTonEntity queryLoadAndUnloadEfficiencyTonByOrgCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	LoadAndUnloadEfficiencyTonEntity entity=new LoadAndUnloadEfficiencyTonEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setOrgCode(code);
	
	List<LoadAndUnloadEfficiencyTonEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryLoadAndUnloadEfficiencyTonByOrgCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(0);
	}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonBatchByOrgCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	return getSqlSession().selectList(
		NAMESPACE + "queryLoadAndUnloadEfficiencyTonBatchByOrgCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonExactByEntity(
	    LoadAndUnloadEfficiencyTonEntity entity, int start, int limit) {
	LoadAndUnloadEfficiencyTonEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadEfficiencyTonEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryLoadAndUnloadEfficiencyTonExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyTonExactByEntityCount(LoadAndUnloadEfficiencyTonEntity entity) {
	LoadAndUnloadEfficiencyTonEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadEfficiencyTonEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryLoadAndUnloadEfficiencyTonExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LoadAndUnloadEfficiencyTonEntity> queryLoadAndUnloadEfficiencyTonByEntity(
	    LoadAndUnloadEfficiencyTonEntity entity, int start, int limit) {
	LoadAndUnloadEfficiencyTonEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadEfficiencyTonEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryLoadAndUnloadEfficiencyTonByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:5:50
     * @see com.deppon.foss.module.base.baseinfo.server.dao.ILoadAndUnloadEfficiencyTonDao#queryLoadAndUnloadEfficiencyTonByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity)
     */
    @Override
    public long queryLoadAndUnloadEfficiencyTonByEntityCount(LoadAndUnloadEfficiencyTonEntity entity) {
	LoadAndUnloadEfficiencyTonEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LoadAndUnloadEfficiencyTonEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryLoadAndUnloadEfficiencyTonByEntityCount", queryEntity);
    }


}
