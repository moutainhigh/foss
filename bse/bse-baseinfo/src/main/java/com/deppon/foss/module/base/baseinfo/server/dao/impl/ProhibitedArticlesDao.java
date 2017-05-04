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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ProhibitedArticlesDao.java
 * 
 * FILE NAME        	: ProhibitedArticlesDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 禁运物品 DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:04:32
 */
public class ProhibitedArticlesDao extends SqlSessionDaoSupport implements
	IProhibitedArticlesDao {
	
    /**
     * 声明日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleDepartmentDao.class);
	
    /**
     * SQL命名空间常量
     */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".prohibitedArticles.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#addProhibitedArticles(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
	public ProhibitedArticlesEntity addProhibitedArticles(
			ProhibitedArticlesEntity entity) {
		//参数非空验证
		if (null == entity) {
			//返回实体对象
			return entity;
		}
		//获取当前时间
		Date now = new Date();
		//设置PrimaryKey
		entity.setId(UUIDUtils.getUUID());
		//对虚拟编码进行非空判断
		if (StringUtils.isBlank(entity.getVirtualCode())) {
			//如果虚拟编码为空的话
			entity.setVirtualCode(entity.getId());
		}
		// CreateUser为传入的用户编码，CreateDate为当前时间
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//设置更改人
		entity.setModifyUser(entity.getCreateUser());
		//设置版本信息
		entity.setVersionNo(now.getTime());
		//设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		//返回影响的记录条数
		int result = getSqlSession().insert(
				NAMESPACE + "addProhibitedArticles", entity);
		//如果影响的记录条数大于零则返回对象本身，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
	}

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#deleteProhibitedArticles(java.lang.String)
     */
    @Override
	public ProhibitedArticlesEntity deleteProhibitedArticles(
			ProhibitedArticlesEntity entity) {
		// 请求参数合法性验证
		if (null == entity) {
			// 返回空值
			return null;
		}
		//对虚拟编码做非空判断
		if (StringUtils.isBlank(entity.getVirtualCode())) {
			//返回空值
			return null;
		}
		// 处理删除时要更新的数据
		Date now = new Date();
		//设置修改的时间
		entity.setModifyDate(now);
		//设置版本信息
		entity.setVersionNo(now.getTime());
		//entity应包含modifyUser,因此不用处理
		entity.setActive(FossConstants.INACTIVE);
		//声明传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//设置查询的实体对象
		map.put("entity", entity);
		//只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		//返回影响的记录条数
		int result = getSqlSession().update(
				NAMESPACE + "deleteProhibitedArticles", map);
		//如果影响的记录条数大于零则返回对象本身，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
	}

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#deleteProhibitedArticlesMore(java.lang.String[], java.lang.String)
     */
    @Override
    public ProhibitedArticlesEntity deleteProhibitedArticlesMore(String[] codes , String deleteUser) {
		// 请求合法性判断：
		if (ArrayUtils.isEmpty(codes)) {
			//返回空值
			return null;
		}
		// 处理删除时要更新的数据
		Date now = new Date();
		//声明用来传参的对象
		ProhibitedArticlesEntity entity = new ProhibitedArticlesEntity();
		//设置修改时间为当前时间
		entity.setModifyDate(now);
		//当前时间的毫秒数为版本信息
		entity.setVersionNo(now.getTime());
		//设置更改用户
		entity.setModifyUser(deleteUser);
		//设置是否有效，因为是逻辑删除，所以只是把active修改成N
		entity.setActive(FossConstants.INACTIVE);
		//设置传参的Map
		Map<String, Object> map = new HashMap<String, Object>();
		//设置codes传到后台
		map.put("codes", codes);
		//设置entity为参数
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		//返回查询的结果
		int result = getSqlSession().update(
				NAMESPACE + "deleteProhibitedArticlesMore", map);
		//如果影响的条数大于零则返回entity，否则返回null
		return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#updateProhibitedArticles(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    public ProhibitedArticlesEntity updateProhibitedArticles(ProhibitedArticlesEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
			//返回为空
			return entity;
		}
		//对虚拟编码进行非空判断
		if (StringUtils.isBlank(entity.getVirtualCode())) {
			//返回对象实体
			return entity;
		}
		// 更新要先删除旧的数据：
		ProhibitedArticlesEntity result = this.deleteProhibitedArticles(entity);
		//对影响的结果进行非空严重
		if (result == null) {
			//打印日志信息
			LOGGER.error("更新时，作废失败");
		}
		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// 版本号始终取当前时间
		entity.setVersionNo(System.currentTimeMillis());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		Date now = new Date();
		//设置创建日期
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//设置创建人
		entity.setCreateUser(entity.getModifyUser());
		//设置是否有效
		entity.setActive(FossConstants.ACTIVE);
		//返回影响的结果条数
		int resultNum = getSqlSession().insert(
				NAMESPACE + "addProhibitedArticles", entity);
		//如果影响的结果条数大于零则返回对象本身，否则返回null
		return resultNum > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ProhibitedArticlesEntity queryProhibitedArticlesByVirtualCode(String code) {
    	//变标识编码进行非空校验
		if (StringUtils.isBlank(code)) {
			//返回null
			return null;
		}
		// 构造查询条件：
		ProhibitedArticlesEntity entity = new ProhibitedArticlesEntity();
		//设置为有效
		entity.setActive(FossConstants.ACTIVE);
		//设置虚拟编码
		entity.setVirtualCode(code);
		//执行查询
		List<ProhibitedArticlesEntity> entitys = this.getSqlSession()
				.selectList(NAMESPACE + "queryProhibitedArticlesByVirtualCode",
						entity);
		//对查询结果结果进行非空验证
		if (CollectionUtils.isEmpty(entitys)) {
			//是空的话返回null
			return null;
		} else {
			//否则取list下标的第一个对象
			return entitys.get(NumberConstants.ZERO);
		}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<ProhibitedArticlesEntity> queryProhibitedArticlesBatchByVirtualCode(
			String[] codes) {
		// 请求参数合法性判断
		if (ArrayUtils.isEmpty(codes)) {
			//返回为空
			return null;
		}
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		//设置codes
		map.put("codes", codes);
		//设置是否有效
		map.put("active", FossConstants.ACTIVE);
		//执行查询并返回查询结果
		return getSqlSession().selectList(
				NAMESPACE + "queryProhibitedArticlesBatchByVirtualCode", map);
	}

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
	public List<ProhibitedArticlesEntity> queryProhibitedArticlesExactByEntity(
			ProhibitedArticlesEntity entity, int start, int limit) {
    	//三目运算符确保参数不为空
		ProhibitedArticlesEntity queryEntity = entity == null ? new ProhibitedArticlesEntity()
				: entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//设置分页对象
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询并返回结果
		return getSqlSession().selectList(
				NAMESPACE + "queryProhibitedArticlesExactByEntity",
				queryEntity, rowBounds);
	}

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    public long queryProhibitedArticlesExactByEntityCount(ProhibitedArticlesEntity entity) {
    	//三目运算符确保参数不为空
		ProhibitedArticlesEntity queryEntity = entity == null ? new ProhibitedArticlesEntity():entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//执行查询并返回结果
		return (Long)getSqlSession().selectOne(
			NAMESPACE + "queryProhibitedArticlesExactByEntityCount",
			queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProhibitedArticlesEntity> queryProhibitedArticlesByEntity(
	    ProhibitedArticlesEntity entity, int start, int limit) {
    	//三目运算符确保参数不为空
		ProhibitedArticlesEntity queryEntity = entity == null ? new ProhibitedArticlesEntity()
				: entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//设置分页对象
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询并返回结果
		return getSqlSession().selectList(
				NAMESPACE + "queryProhibitedArticlesByEntity", queryEntity,
				rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:24
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
     */
    @Override
    public long queryProhibitedArticlesByEntityCount(ProhibitedArticlesEntity entity) {
    	//三目运算符确保参数不为空
		ProhibitedArticlesEntity queryEntity = entity == null ? new ProhibitedArticlesEntity()
				: entity;
		//设置是否有效
		queryEntity.setActive(FossConstants.ACTIVE);
		//执行查询并返回结果
		return (Long) getSqlSession()
				.selectOne(NAMESPACE + "queryProhibitedArticlesByEntityCount",
						queryEntity);
    }
    
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午7:26:56
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProhibitedArticlesEntity> queryProhibitedArticlesForDownload(ProhibitedArticlesEntity entity){
    	//三目运算符判断
		ProhibitedArticlesEntity queryEntity = entity == null ? new ProhibitedArticlesEntity(): entity;
		//执行查询
		return (List<ProhibitedArticlesEntity>) getSqlSession().selectList(
				NAMESPACE + "queryProhibitedArticlesForDownload", queryEntity);
	}
}