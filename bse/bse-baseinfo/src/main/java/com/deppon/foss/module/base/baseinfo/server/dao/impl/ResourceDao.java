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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ResourceDao.java
 * 
 * FILE NAME        	: ResourceDao.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 权限DAO
 * 
 * 2012-08-30 钟庭杰 新增 </div>
 */
public class ResourceDao extends SqlSessionDaoSupport implements IResourceDao {

	/**
	 * 查询最后更新时间
	 * 
	 * @return
	 */
	@Override
	public Date getLastModifyTime() {
		long versionNo = (Long) getSqlSession().selectOne(
				NAMESPACE + "getLastModifyTime");

		return new Date(versionNo);
	}

	/**
	 * 通过URI得到当前URL定位的功能树下的所有功能
	 * 
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getAllChildResourceByURI(String uri) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uri", uri);
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "getAllChildResourceByURI", map);
	}

	/**
	 * 通过父资源code得到当前资源树节点的子级节点列表
	 * 
	 * @param parentCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getDirectChildResourceByCode(String parentCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentCode", parentCode);
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "getDirectChildResourceByCode", map);
	}

	/**
	 * 通过更新时间得到已经更新过的节点父节点的编码列表 getByLastModifyResourceParentCode
	 * 
	 * @param lastModifyDate
	 * @return Set<String>
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getByLastModifyResourceParentCode(Date lastModifyDate) {
		if (lastModifyDate == null) {
			return new HashSet<String>();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionNo", lastModifyDate.getTime());
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		Set<String> modifyResParentCodes = new HashSet<String>();
		modifyResParentCodes.addAll(getSqlSession().selectList(
				NAMESPACE + "getByLastModifyResourceParentCode", map));
		return modifyResParentCodes;
	}

	/**
	 * 能过更新时间得到已经更新过的资源列表 getByLastModifyResource
	 * 
	 * @param lastModifyDate
	 * @return
	 * @return List<ResourceEntity>
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getByLastModifyResource(Date lastModifyDate) {
		if (lastModifyDate == null) {
			return new ArrayList<ResourceEntity>();
		}
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionNo", lastModifyDate.getTime());
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		List<ResourceEntity> list = getSqlSession().selectList(
				NAMESPACE + "getByLastModifyResource", map);
		return list;
	}

	/**
	 * 通过资源URI得到资源对象
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#getResourceByUri()
	 *      getResourceByUri
	 * @return
	 * @since:
	 */
	@Override
	public ResourceEntity getResourceByUri(String uri) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uri", uri);
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		return (ResourceEntity) getSqlSession().selectOne(
				NAMESPACE + "getResourceByURI", map);
	}

	/**
	 * 
	 * <p>
	 * 根据多个标识URI批量查询
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-1-30 下午2:48:35
	 * @param uris
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#getResourceBatchByUri(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getResourceBatchByUri(String[] uris) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uris", uris);
		map.put("sign", "?");
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		List<ResourceEntity> resources = getSqlSession().selectList(
				NAMESPACE + "getResourceBatchByUri", map);
		return resources;
	}

	/**
	 * 通过资源CODE得到资源对象
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#getResourceByCode()
	 *      getResourceByCode
	 * @return
	 * @since:
	 */
	@Override
	public ResourceEntity getResourceByCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		return (ResourceEntity) getSqlSession().selectOne(
				NAMESPACE + "getResourceByCode", map);
	}

	/**
	 * 根据多个标识编码批量查询 getResourceBatchByCode
	 * 
	 * @param codes
	 * @return
	 * @return List<ResourceEntity>
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getResourceBatchByCode(String[] codes) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("resActive", FossConstants.ACTIVE);
		map.put("parentActive", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "getResourceBatchByCode",
				map);
	}

	/**
	 * 087584-foss-lijun 权限的数据库访问常用方法
	 */

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".resource.";

	/**
	 * 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#addResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public ResourceEntity addResource(ResourceEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}

		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setModifyUser(entity.getCreateUser());
		entity.setVersionNo(now.getTime());

		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addResource", entity);
		return result > NumberConstants.ZERO ? entity : null;
	}

	/**
	 * 通过CODE 标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResource(java.lang.String)
	 */
	@Override
	public ResourceEntity deleteResource(ResourceEntity entity) {
		// 请求参数合法性验证
		if (null == entity) {
			return null;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}

		// 处理删除时要更新的数据
		Date now = new Date();
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		// entity应包含modifyUser,因此不用处理
		entity.setActive(FossConstants.INACTIVE);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE + "deleteResource", map);
		return result > NumberConstants.ZERO ? entity : null;
	}

	/**
	 * 通过CODE 标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResourceMore(java.lang.String[],
	 *      java.lang.String)
	 */
	@Override
	public ResourceEntity deleteResourceMore(String[] codes, String deleteUser) {
		// 请求合法性判断：
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}

		// 处理删除时要更新的数据
		Date now = new Date();
		ResourceEntity entity = new ResourceEntity();

		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		entity.setModifyUser(deleteUser);
		entity.setActive(FossConstants.INACTIVE);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);

		int result = getSqlSession().update(NAMESPACE + "deleteResourceMore",
				map);
		return result > NumberConstants.ZERO ? entity : null;
	}

	/**
	 * 通过CODE标识更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#updateResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public ResourceEntity updateResource(ResourceEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return entity;
		}

		// 更新要先删除旧的数据：
		ResourceEntity result = this.deleteResource(entity);
		if (result == null) {
			String msg = "更新时，作废失败";
			LOGGER.error(msg);
		}

		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// 版本号始终取当前时间
		entity.setVersionNo(System.currentTimeMillis());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		Date now = new Date();
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(entity.getModifyUser());

		entity.setActive(FossConstants.ACTIVE);
		int resultNum = getSqlSession().insert(NAMESPACE + "addResource",
				entity);
		return resultNum > NumberConstants.ZERO ? entity : null;
	}

	/**
	 * 以下全为查询：
	 */

	/**
	 * 通过 标识编码查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		ResourceEntity entity = new ResourceEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);

		List<ResourceEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryResourceByCode", entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys.get(NumberConstants.ZERO);
		}
	}

	/**
	 * 精确查询 根据多个标识编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceBatchBy(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceBatchByCode(String[] codes) {
		// 请求参数合法性判断
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}

		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("active", FossConstants.ACTIVE);

		return getSqlSession().selectList(
				NAMESPACE + "queryResourceBatchByCode", map);
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceExactByEntity(
			ResourceEntity entity, int start, int limit) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				NAMESPACE + "queryResourceExactByEntity", queryEntity,
				rowBounds);
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public long queryResourceExactByEntityCount(ResourceEntity entity) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryResourceExactByEntityCount", queryEntity);
	}

	/**
	 * 模糊查询动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceMore(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity,
			int start, int limit) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryResourceByEntity",
				queryEntity, rowBounds);
	}
	
	/**
	 * 
	 * <p>模糊查询 动态的查询条件。</p> 
	 * @author ztjie
	 * @date 2013-3-20 上午9:40:43
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "queryResourceByEntity",queryEntity);
	}

	/**
	 * 模糊查询 动态的查询条件-查询总条数。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.server.dao.IResourceDao#queryResourceByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public long queryResourceByEntityCount(ResourceEntity entity) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryResourceByEntityCount", queryEntity);
	}

	/**
	 * 根据entity精确查询,用于数据下载 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午8:20:16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceForDownload(ResourceEntity entity) {
		ResourceEntity queryEntity;
		if (null == entity) {
			queryEntity = new ResourceEntity();
		} else {
			queryEntity = entity;
		}
		return (List<ResourceEntity>) getSqlSession().selectList(
				NAMESPACE + "queryResourceForDownload", queryEntity);
	}

	/**
	 * 下面为特殊查询
	 */

	/**
	 * 模糊查询
	 * 
	 * 根据权限名称NAME查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 * @param resourceName
	 *            权限名称
	 * @param roleCode
	 *            角色编码
	 */
	@Override
	public List<ResourceEntity> queryResourceByNameRole(String resourceName,
			String roleCode, int start, int limit) {
		// 构造查询条件：
		ResourceEntity resource = new ResourceEntity();
		RoleEntity role = new RoleEntity();
		resource.setName(resourceName);
		role.setCode(roleCode);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resource", resource);
		map.put("role", role);
		map.put("conditionActive", FossConstants.ACTIVE);

		RowBounds rowBounds = new RowBounds(start, limit);
		@SuppressWarnings("unchecked")
		List<ResourceEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryResourceByNameRole", map, rowBounds);

		return entitys;
	}

	/**
	 * 模糊查询
	 * 
	 * 根据权限名称NAME查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceUpByName(java.lang.String)
	 */
	@Override
	public List<ResourceEntity> queryResourceUpByName(String name) {
		// 构造查询条件：
		ResourceEntity entity = new ResourceEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setName(name);

		@SuppressWarnings("unchecked")
		List<ResourceEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryResourceUpByName", entity);

		return entitys;
	}

	/**
	 * 精确查询
	 * 
	 * 根据权限编码CODE查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceUpByName(java.lang.String)
	 * @param code
	 *            权限编码，对应数据库CODE字段
	 */
	@Override
	public List<ResourceEntity> queryResourceUpByCode(String code) {
		// 构造查询条件：
		ResourceEntity entity = new ResourceEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);

		@SuppressWarnings("unchecked")
		List<ResourceEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryResourceUpByCode", entity);

		return entitys;
	}

	/**
	 * 精确查询 查询权限的根结点 根据权限名称查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceUpByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceRoot(String belongSystemType) {
		// 构造查询条件：
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("conditionActive", FossConstants.ACTIVE);
		map.put("belongSystemType", belongSystemType);
		List<ResourceEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryResourceRoot", map);
		if (entitys != null && entitys.size() > 0) {
			return entitys.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 精确查询
	 * 
	 * 返回符合记录的条数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@Override
	public long queryResourceCountByRoleResource(String resourceCode,
			String roleCode) throws BusinessException {
		if (StringUtils.isBlank(resourceCode) || StringUtils.isBlank(roleCode)) {
			throw new ResourceException("权限编码或者角色编码不能为空");
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("resourceCode", resourceCode);
		map.put("roleCode", roleCode);
		map.put("conditionActive", FossConstants.ACTIVE);

		return (Long) getSqlSession().selectOne(
				NAMESPACE + "queryResourceCountByRoleResource", map);
	}

	/**
	 * 下面为特殊作废
	 */

	/**
	 * 作废角色权限
	 * 
	 * 返回作废的记录数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’,然后作废
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@Override
	public long deleteResourceByRoleResource(String resourceCode,
			String roleCode) throws BusinessException {
		if (StringUtils.isBlank(resourceCode) || StringUtils.isBlank(roleCode)) {
			throw new ResourceException("权限编码或者角色编码不能为空");
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("resourceCode", resourceCode);
		map.put("roleCode", roleCode);
		map.put("conditionActive", FossConstants.ACTIVE);
		map.put("deleteActive", FossConstants.INACTIVE);

		int result = getSqlSession().update(
				NAMESPACE + "deleteResourceByRoleResource", map);
		return result;
	}

	/**
	 * 通过CODE 标识来递归删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResource(java.lang.String)
	 */
	@Override
	public ResourceEntity deleteResourceRecursion(ResourceEntity entity) {
		// 请求参数合法性验证
		if (null == entity) {
			return null;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			return null;
		}

		// 处理删除时要更新的数据
		Date now = new Date();
		entity.setModifyDate(now);
		entity.setVersionNo(now.getTime());
		// entity应包含modifyUser,因此不用处理
		entity.setActive(FossConstants.INACTIVE);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(
				NAMESPACE + "deleteResourceRecursion", map);
		return result > NumberConstants.ZERO ? entity : null;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LimitedWarrantyItemsDao.class);

	/**
	 * 
	 * <p>得到所有的权限信息</p> 
	 * @author ztjie
	 * @date 2013-2-18 下午4:26:57
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#getAllResource()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> getAllResource() {
		ResourceEntity queryEntity = new ResourceEntity();
		queryEntity.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(
				NAMESPACE + "queryResourceExactByEntity", queryEntity);
	}

	/**
	 * 
	 * <p>根据节点查询所有子节点</p> 
	 * @author 何波
	 * @date 2013-3-1 上午8:14:54
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceByCodeAllChildNode(String code) {
		
		if(StringUtil.isEmpty(code)){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		 map.put("active", FossConstants.ACTIVE);
		 map.put("code", code);
		return getSqlSession().selectList(
				NAMESPACE + "queryResourceByCodeAllChildNode", map);
	}
	/**
	 * 
	 * 根据员工编码和部门编码查询GUI权限信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 下午4:31:33
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResourceEntity> queryGUIResourceByCode(String empCode,String orgCode,String belongSystemType) {
		HashMap map=new HashMap<String, Object>();
		map.put("empCode", empCode);
		map.put("orgCode", orgCode);
		map.put("belongSystemType", belongSystemType);
		map.put("active", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE+"queryGUIResourceByCode", map);
	}

	/**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 310854-liuzhenhua
	 */
	@Override
	public String queryIsECSByWayBillNo(String waybillNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryIsECSByWayBillNo", waybillNo);
	}
}
