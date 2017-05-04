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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IResourceService.java
 * 
 * FILE NAME        	: IResourceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;

/**
 * Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-23 下午7:59:57
 */
public interface IResourceService extends IService {

	/**
	 * 通过资源的URL,得到资源对象,先从缓存中拿，如果拿不到，在从数据库中获得 queryResourceByUri
	 * 
	 * @param uri
	 * @return
	 * @throws ResourceException
	 * @return ResourceEntity
	 * @since:
	 */
	ResourceEntity queryResourceByUri(String uri) throws ResourceException;

	/**
	 * 插入
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	ResourceEntity addResource(ResourceEntity entity) throws ResourceException;

	/**
	 * 根据CODE删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	ResourceEntity deleteResource(ResourceEntity entity);

	/**
	 * 根据CODE批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	ResourceEntity deleteResourceMore(String[] codes, String deleteUser);

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	ResourceEntity updateResource(ResourceEntity entity);

	/**
	 * 以下全为查询
	 */

	/**
	 * 根据编码查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	ResourceEntity queryResourceByCode(String code);

	/**
	 * 精确查询 根据多个编码查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#queryResourceByCode(java.lang.String)
	 */
	List<ResourceEntity> queryResourceBatchByCode(String[] codes);

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	List<ResourceEntity> queryResourceExactByEntity(ResourceEntity entity,
			int start, int limit);

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	long queryResourceExactByEntityCount(ResourceEntity entity);

	/**
	 * 根据entity模糊查询，
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	List<ResourceEntity> queryResourceByEntity(ResourceEntity entity,
			int start, int limit);
	
	/**
	 * 
	 * <p>根据entity模糊查询</p> 
	 * @author ztjie
	 * @date 2013-3-20 上午9:36:01
	 * @param entity
	 * @return
	 * @see
	 */
	List<ResourceEntity> queryResourceByEntity(ResourceEntity entity);

	/**
	 * 查询queryResourceByEntity返回的记录总数,用于分页
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:22:35
	 */
	long queryResourceByEntityCount(ResourceEntity entity);

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
	List<ResourceEntity> queryResourceByNameRole(String resourceName,
			String roleCode, int start, int limit);

	/**
	 * 模糊查询
	 * 
	 * 根据权限名称NAME查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 */
	List<ResourceEntity> queryResourceUpByName(String name);

	/**
	 * 精确查询
	 * 
	 * 根据权限编码CODE查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-14 下午10:04:28
	 */
	List<ResourceEntity> queryResourceUpByCode(String code);

	/**
	 * 精确查询 查询权限的根结点 根据权限名称查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-12 上午9:06:35
	 */
	ResourceEntity queryResourceRoot(String belongSystemType);

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
	long queryResourceCountByRoleResource(String resourceCode, String roleCode);

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
	long deleteResourceByRoleResource(String resourceCode, String roleCode);

	/**
	 * 通过CODE 标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	ResourceEntity deleteResourceRecursion(ResourceEntity entity);

	/**
	 * 精确查询 (不走缓存) 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @param code权限编码
	 */
	ResourceEntity queryResourceByCodeNoCache(String code);

	/**
	 * 下面是包装后的业务方法
	 */

	/**
	 * 精确查询 通过 CODE 查询 全路径
	 * 
	 * // 全路径示例：“.JT00001.GS00002.DQ00003.XQ00003.YYB00008”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @param code权限编码
	 * @return 权限的全路径，父结点在前面
	 * 
	 */
	String gainFullPathByCode(String code);

	/**
	 * 下面是工具方法
	 */

	/**
	 * 给部门加上部门名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	ResourceEntity attachParentName(ResourceEntity entity);

	/**
	 * 给部门加上部门名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	List<ResourceEntity> attachParentName(List<ResourceEntity> entitys);

	/**
	 * 根据父菜单编码，得到菜单下的子节点列表 queryResourcesByParentCode
	 * 
	 * @param parentCode
	 * @return void
	 * @since:
	 */
	List<ResourceEntity> queryResourcesByParentCode(String parentCode);

	/**
	 * 
	 * <p>
	 * 得到所有的权限信息
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-2-18 下午3:43:55
	 * @return
	 * @see
	 */
	List<ResourceEntity> queryAllResource();

	/**
	 * 
	 * <p>
	 * 根据节点查询所有子节点
	 * </p>
	 * 
	 * @author 何波
	 * @date 2013-3-1 上午8:14:54
	 * @return
	 * @see
	 */
	List<ResourceEntity> queryResourceByCodeAllChildNode(String code);

	/**
	 * 
	 * 根据员工编码和部门编码查询出所有的GUI权限信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 上午10:20:49
	 */
	List<ResourceEntity> queryGUIResourceByUserCode(String empCode,
			String orgCode);
	
	 /**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 310854-liuzhenhua
	 */
	String queryIsECSByWayBillNo(String waybillNo) ;
}
