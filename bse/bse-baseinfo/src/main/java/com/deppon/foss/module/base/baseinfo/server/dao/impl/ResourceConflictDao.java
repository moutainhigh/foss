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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ResourceConflictDao.java
 * 
 * FILE NAME        	: ResourceConflictDao.java
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

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 权限互斥 DAO
 * 
 * 对应表 T_BAS_RESOURCES_CONFLICT
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:05:03
 */
public class ResourceConflictDao extends SqlSessionDaoSupport implements
	IResourceConflictDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".resourceConflict.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:22:7
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#addResourceConflict(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity)
     */
    @Override
    public ResourceConflictEntity addResourceConflict(ResourceConflictEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if(StringUtils.isBlank(entity.getVirtualCode())){
	    entity.setVirtualCode(entity.getId());
	}
	entity.setCreateDate(now);
	entity.setModifyDate(now);
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addResourceConflict", entity);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:22:7
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#deleteResourceConflict(java.lang.String)
     */
    @Override
    public ResourceConflictEntity deleteResourceConflict(ResourceConflictEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
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
	int result = getSqlSession().update(NAMESPACE + "deleteResourceConflict", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:22:7
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#deleteResourceConflictMore(java.lang.String[], java.lang.String)
     */
    @Override
    public ResourceConflictEntity deleteResourceConflictMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	ResourceConflictEntity entity = new ResourceConflictEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteResourceConflictMore", map);
	return result > 0 ? entity : null;
    }
  
    /** 
     * 通过权限编码批量删除互斥信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-5-29 下午5:08:46
     */
    @Override
    public int deleteResourceConflictByCode(String code,String modifyUserCode) {
    Map<String, Object> map = new HashMap<String, Object>();
	Date now = new Date();
	map.put("active", FossConstants.ACTIVE);
	map.put("inActive", FossConstants.INACTIVE);
	map.put("code", code);
	map.put("modifyUserCode", modifyUserCode);
	map.put("modifyDate", now);  
	
	return getSqlSession().update(NAMESPACE + "deleteResourceConflictByCode", map);
    }




    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:22:7
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResourceConflictEntity queryResourceConflictByVirtualCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	ResourceConflictEntity entity=new ResourceConflictEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setVirtualCode(code);
	
	List<ResourceConflictEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryResourceConflictByVirtualCode", entity);
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
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ResourceConflictEntity> queryResourceConflictBatchByVirtualCode(
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
		NAMESPACE + "queryResourceConflictBatchByVirtualCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ResourceConflictEntity> queryResourceConflictExactByEntity(
	    ResourceConflictEntity entity, int start, int limit) {
	ResourceConflictEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ResourceConflictEntity();
	}else{
	    queryEntity = entity;
	}
	//何波 2013-3-1注释
	//	if(StringUtils.isBlank(entity.getFirstCode()) && !StringUtils.isBlank(entity.getSecondCode())){
	//	    entity.setFirstCode(entity.getSecondCode());
	//	    entity.setSecondCode(null);
	//	}
	//	
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryResourceConflictExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity)
     */
    @Override
    public long queryResourceConflictExactByEntityCount(ResourceConflictEntity entity) {
	ResourceConflictEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ResourceConflictEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryResourceConflictExactByEntityCount",
		queryEntity);
    }


    /**
     * 下面为特殊查询
     */
	
    /**
     * 根据 两批权限CODE 查询哪些是互斥的：
     * 不支持两个codes都为空，如果两个codes都为空，则应调queryResourceConflictExactByEntity
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictByCodes(String[] codes1, String[] codes2) {
	// 检查参数
	if (ArrayUtils.isEmpty(codes1) && ArrayUtils.isEmpty(codes2)) {
	    return null;
	}
	// 如果第一个为空，第二个不为空
	if (ArrayUtils.isEmpty(codes1) && !ArrayUtils.isEmpty(codes2)) {
	    codes1 = codes2;
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("codes1", codes1);  //active
	    map.put("active", FossConstants.ACTIVE); 
	    @SuppressWarnings("unchecked")
	    // 根据一批权限CODE 查询跟哪些权限是互斥的
	    List<ResourceConflictEntity> retnEntitys = this.getSqlSession().selectList(NAMESPACE + "queryResourceConflictByOneCodes",map);
	    return retnEntitys;
	}
	// 如果第二个为空，第一个不为空
	if (!ArrayUtils.isEmpty(codes1)	&& ArrayUtils.isEmpty(codes2)) {
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("codes1", codes1);
	    map.put("active", FossConstants.ACTIVE); 
	    @SuppressWarnings("unchecked")
	    // 根据一批权限CODE 查询跟哪些权限是互斥的
	    List<ResourceConflictEntity> retnEntitys = this.getSqlSession().selectList(NAMESPACE + "queryResourceConflictByOneCodes",map);
	    return retnEntitys;
	}

	// 两个都不为空的情况：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes1", codes1);
	map.put("codes2", codes2);
	map.put("active", FossConstants.ACTIVE);
	@SuppressWarnings("unchecked")
	List<ResourceConflictEntity> retnEntitys = this.getSqlSession().selectList(NAMESPACE + "queryResourceConflictByTwoCodes", map);
	return retnEntitys;

    }
	
	
    /**
     * 根据权限CODE查询这些CODE中哪些是互斥的：
     * 如果CODE为空，则应调queryResourceConflictExactByEntity
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictByCodes(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 两个都不为空的情况：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	@SuppressWarnings("unchecked")
	List<ResourceConflictEntity> retnEntitys = this.getSqlSession()
		.selectList(NAMESPACE + "queryResourceConflictByCodes", map);
	return retnEntitys;

    }	
    
    /**
     * 根据2个角色编码查询这个两角色包含的权限有哪些是互斥的
     * 
     * 在给用户部门分配角色时判断角色互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictBy2Role(
	    String firstRoleCode, String secondRoleCode) {
	if (StringUtils.isBlank(firstRoleCode)
		|| StringUtils.isBlank(secondRoleCode)) {
	    return null;
	}
	
	// 两个都不为空的情况：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("firstRoleCode", firstRoleCode);
	map.put("secondRoleCode", secondRoleCode);
	map.put("conditionActive", FossConstants.ACTIVE);
	@SuppressWarnings("unchecked")
	List<ResourceConflictEntity> retnEntitys = this.getSqlSession()
		.selectList(NAMESPACE + "queryResourceConflictBy2Role", map);
	return retnEntitys;

    }
	


}
