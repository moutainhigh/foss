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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/RoleResourceDao.java
 * 
 * FILE NAME        	: RoleResourceDao.java
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class RoleResourceDao extends SqlSessionDaoSupport implements
	IRoleResourceDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".roleResource.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#addRoleResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
     */
    @Override
    public RoleResourceEntity addRoleResource(RoleResourceEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}

	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if(StringUtils.isBlank(entity.getVirtualCode())){
	    entity.setVirtualCode(entity.getId());
	}
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addRoleResource", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#deleteRoleResource(java.lang.String)
     */
    @Override
    public RoleResourceEntity deleteRoleResource(RoleResourceEntity entity) {
	// 请求参数合法性验证
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("versionNo", now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteRoleResource", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#deleteRoleResourceMore(java.lang.String[], java.lang.String)
     */
    @Override
    public RoleResourceEntity deleteRoleResourceMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	RoleResourceEntity entity = new RoleResourceEntity();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	entity.setModifyUser(deleteUser);
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteRoleResourceMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#updateRoleResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
     */
    @Override
    public RoleResourceEntity updateRoleResource(RoleResourceEntity entity) {
	// 请求合法性判断：
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	RoleResourceEntity result = this.deleteRoleResource(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}

	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());

	if (entity.getModifyDate() == null) {
	    entity.setModifyDate(new Date());
	}
	// 版本号始终取当前时间
	entity.setVersionNo(System.currentTimeMillis());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());

		
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addRoleResource", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public RoleResourceEntity queryRoleResourceByVirtualCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	RoleResourceEntity entity=new RoleResourceEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setVirtualCode(code);
	
	List<RoleResourceEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryRoleResourceByVirtualCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceBatchByVirtualCode(
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
		NAMESPACE + "queryRoleResourceBatchByVirtualCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceExactByEntity(
	    RoleResourceEntity entity, int start, int limit) {
	RoleResourceEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleResourceEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryRoleResourceExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
     */
    @Override
    public long queryRoleResourceExactByEntityCount(RoleResourceEntity entity) {
	RoleResourceEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleResourceEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryRoleResourceExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceByEntity(
	    RoleResourceEntity entity, int start, int limit) {
	RoleResourceEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleResourceEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryRoleResourceByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为???糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:23:45
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IRoleResourceDao#queryRoleResourceByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
     */
    @Override
    public long queryRoleResourceByEntityCount(RoleResourceEntity entity) {
	RoleResourceEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleResourceEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryRoleResourceByEntityCount", queryEntity);
    }
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:43:24
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceForDownload(RoleResourceEntity entity){
	RoleResourceEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleResourceEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<RoleResourceEntity>) getSqlSession().selectList(NAMESPACE + "queryRoleResourceForDownload", queryEntity);
    }
    
    
    /**
     * 根据entity分页查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:43:24
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceForDownloadByPage(RoleResourceEntity entity,int started,
			int limited){
		RoleResourceEntity queryEntity;
		RowBounds rowBounds = new RowBounds(started, limited);
		if (null == entity) {
		    queryEntity = new RoleResourceEntity();
		}else{
		    queryEntity = entity;
		}
		return (List<RoleResourceEntity>) getSqlSession().selectList(NAMESPACE + "queryRoleResourceForDownload", queryEntity, rowBounds);
    }

    
    
    /**
     * 下面为特殊方法
     */
    
    /**
     * 根据ROLE_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-25 下午3:27:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#deleteRoleResourceByRoleCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
     */
    @Override
    public RoleResourceEntity deleteRoleResourceByRoleCode(RoleResourceEntity entity){
	if(null == entity||StringUtil.isBlank(entity.getRoleCode())){
	    return null;
	}
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	map.put("conditionActive", FossConstants.ACTIVE);
//	添加版本号更新
	map.put("versionNo", new Date().getTime());
	
	int retnFlag = this.getSqlSession().update(
		NAMESPACE + "deleteRoleResourceByRoleCode", map);
	return retnFlag > NumberConstants.ZERO ? entity : null;
    }
    
    /**
     * 特殊删除
     * 根据ROLE_CODE, RESOURCE_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-25 下午3:27:19
     */
    public RoleResourceEntity deleteRoleResourceByRoleResource(RoleResourceEntity entity){
	if(null == entity||StringUtil.isBlank(entity.getRoleCode())
		|| StringUtil.isBlank(entity.getResourceCode())){
	    return null;
	}
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	map.put("conditionActive", FossConstants.ACTIVE);
	map.put("versionNo",new Date().getTime());
	
	int retnFlag = this.getSqlSession().update(
		NAMESPACE + "deleteRoleResourceByRoleResource", map);
	return retnFlag > NumberConstants.ZERO ? entity : null;
    }
    
    
    /**
     * 特殊查询，精确查询
     * 根据多个标识和列名批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RoleResourceEntity> queryRoleResourceMoreByColumnName(
	    String[] codes, String columnName) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("columnName", columnName);
	map.put("codes", codes);
	map.put("conditionActive", FossConstants.ACTIVE);
	
	return (List<RoleResourceEntity>)getSqlSession().selectList(
		NAMESPACE + "queryRoleResourceMoreByColumnName", map);
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedWarrantyItemsDao.class);

    /**
     * 
     * <p>查询用户拥有的所有权限</p> 
     * @author 何波
     * @date 2013-2-26 下午4:03:41
     * @param roleResource
     * @return
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleResourceEntity> queryRoleResourceExactByEntity(
			RoleResourceEntity roleResource) {
		RoleResourceEntity queryEntity;
		if (null == roleResource) {
		    queryEntity = new RoleResourceEntity();
		}else{
		    queryEntity = roleResource;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return getSqlSession()
			.selectList(NAMESPACE + "queryRoleResourceExactByEntity",
				queryEntity);
	    
	}

}
