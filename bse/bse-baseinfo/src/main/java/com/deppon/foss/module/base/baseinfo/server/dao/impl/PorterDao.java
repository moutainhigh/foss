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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PorterDao.java
 * 
 * FILE NAME        	: PorterDao.java
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车小车中的理货员 DAO
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:03:40
 */
public class PorterDao extends SqlSessionDaoSupport implements
	IPorterDao {

	/**
	 * 声明LOGGER对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SaleDepartmentDao.class);

	/**
	 * SQL在内存地址命名空间
	 */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".porter.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#addPorter(com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity)
     */
    @Override
    public PorterEntity addPorter(PorterEntity entity) {
		// 参数非空验证
		if (null == entity) {
			// 如果非空返回原值
			return entity;
		}
		// 获取当前时间
		Date now = new Date();
		// 设置primaryKey
		entity.setId(UUIDUtils.getUUID());
		// 设置创建时间
		entity.setCreateDate(now);
		// 设置更改时间
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		// 设置更改用户
		entity.setModifyUser(entity.getCreateUser());
		// 设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		// 获取生效的个数
		int result = getSqlSession().insert(NAMESPACE + "addPorter", entity);
		// 返回值，如果影响的记录数大于零则返回原对象，否则返回空
		return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过EMP_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#deletePorter(java.lang.String)
     */
    @Override
    public PorterEntity deletePorter(PorterEntity entity) {
		// 请求参数合法性验证
		if (null == entity) {
			// 返回空值
			return null;
		}
		// 对entity对象的员工工号对非空判断
		if (StringUtils.isBlank(entity.getEmpCode())) {
			// 如果为空则返回空值
			return null;
		}
		// 处理删除时要更新的数据
		Date now = new Date();
		// 设置是否有效
		entity.setActive(FossConstants.INACTIVE);
		// 设置更改时间
		entity.setModifyDate(now);
		// 声明用来传参的Map对象
		Map<String, Object> map = new HashMap<String, Object>();
		// 往map里面设置entity对象
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		// 返回生效记录条数
		int result = getSqlSession().update(NAMESPACE + "deletePorter", map);
		// 如果影响的记录条数大于零则返回本身，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过EMP_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#deletePorterMore(java.lang.String[], java.lang.String)
     */
    @Override
    public PorterEntity deletePorterMore(String[] codes ,LoadAndUnloadSquadEntity loadAndUnloadSquadEntity, String deleteUser) {
		// 请求合法性判断：
		if (ArrayUtils.isEmpty(codes)) {
			// 返回空值
			return null;
		}
		// 处理删除时要更新的数据
		Date now = new Date();
		// 声明一个参数的entity对象
		PorterEntity entity = new PorterEntity();
		// 设置是否有效
		entity.setActive(FossConstants.INACTIVE);
		// 设置更新时间
		entity.setModifyDate(now);
		// 设置更新的用户
		entity.setModifyUser(deleteUser);
		entity.setParentOrgCode(loadAndUnloadSquadEntity.getCode());
		// 声明传参的map对象
		Map<String, Object> map = new HashMap<String, Object>();
		// 传员工号数组
		map.put("codes", codes);
		// 传entity对象
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		// 获取影响的记录条数
		int result = getSqlSession()
				.update(NAMESPACE + "deletePorterMore", map);
		// 影响的条数大于0的时候返回对象本身，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过EMP_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#updatePorter(com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity)
     */
    @Override
    public PorterEntity updatePorter(PorterEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
			//返回对象实体
			return entity;
		}
		//员工号非空判断
		if (StringUtils.isBlank(entity.getEmpCode())) {
			//返回对象实体
			return entity;
		}
		// 更新要先删除旧的数据：
		PorterEntity result = this.deletePorter(entity);
		//判断是否删除成功
		if (result == null) {
			//如果删除失败则生成日志信息
			LOGGER.error("更新时，作废失败");
		}
		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		Date now = new Date();
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		// 设置创建人
		entity.setCreateUser(entity.getModifyUser());
		// 设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		// 执行插入操作并获取影响的记录条数
		int resultNum = getSqlSession().insert(NAMESPACE + "addPorter", entity);
		// 如果影响的记录条数大于零则返回对象本身，否则返回null
		return resultNum > NumberConstants.ZERO ? entity : null;
    }
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PorterEntity queryPorterByEmpCode(String code) {
    	//code非空验证
		if (StringUtils.isBlank(code)) {
			//返回空
			return null;
		}
		// 构造查询条件：
		PorterEntity entity = new PorterEntity();
		//设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		//设置员工工号
		entity.setEmpCode(code);
		//执行查询
		List<PorterEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryPorterByEmpCode", entity);
		//对查询结果非空验证
		if (CollectionUtils.isEmpty(entitys)) {
			//如果为空则返回null
			return null;
		} else {
			//否则返回List下标的第一行记录
			return entitys.get(NumberConstants.ZERO);
		}
    }
    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PorterEntity> queryPorterBatchByEmpCode(
	    String[] codes) {
		// 请求参数合法性判断
		if (ArrayUtils.isEmpty(codes)) {
			//否则返回空
			return null;
		}
		// 声明查询参数的map
		Map<String, Object> map = new HashMap<String, Object>();
		//设置codes数组
		map.put("codes", codes);
		//设置是否有效
		map.put("active", FossConstants.ACTIVE);
		//返回查询结果
		return getSqlSession().selectList(
				NAMESPACE + "queryPorterBatchByEmpCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<PorterEntity> queryPorterExactByEntity(PorterEntity entity,
			int start, int limit) {
    	//声明PorterEntity并让其不为空 否则就new一个新对象
		PorterEntity queryEntity = entity == null ? new PorterEntity(): entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//声明分页查询对象
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询并返回结果
		return getSqlSession().selectList(
				NAMESPACE + "queryPorterExactByEntity", queryEntity, rowBounds);
	}

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity)
     */
    @Override
	public long queryPorterExactByEntityCount(PorterEntity entity) {
    	//声明PorterEntity并让其不为空 否则就new一个新对象
		PorterEntity queryEntity = entity == null ? new PorterEntity(): entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//执行查询并返回查询结果
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryPorterExactByEntityCount", queryEntity);
	}

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<PorterEntity> queryPorterByEntity(PorterEntity entity,
			int start, int limit) {
    	//声明PorterEntity并让其不为空 否则就new一个新对象
		PorterEntity queryEntity = entity == null ? new PorterEntity():entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//声明分页查询对象
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询并返回查询结果
		return getSqlSession().selectList(NAMESPACE + "queryPorterByEntity",
				queryEntity, rowBounds);
	}

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:14
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IPorterDao#queryPorterByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity)
     */
    @Override
	public long queryPorterByEntityCount(PorterEntity entity) {
    	//声明PorterEntity并让其不为空 否则就new一个新对象
		PorterEntity queryEntity = entity == null ? new PorterEntity():entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//执行查询并返回查询结果
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryPorterByEntityCount", queryEntity);
	}
    
    /**
     * 精确查询
     * 根据多个PARENT_ORG_CODE批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPorterDao#queryPorterBatchByParentOrgCode(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PorterEntity> queryPorterBatchByParentOrgCode(String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
		//返回空
	    return null;
	}
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	//设置员工code
	map.put("codes", codes);
	//设置生效为Y
	map.put("active", FossConstants.ACTIVE);
	//执行查询
	List<PorterEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryPorterBatchByParentOrgCode", map);
	//返回查询结果
	return entitys;
    }
}
