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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IResourceDao.java
 * 
 * FILE NAME        	: IResourceDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;

/**
 * 权限 DAO 接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:01:12
 */
public interface IResourceDao {

	 /**
     * 查询最后更新时间
     * 
     * @return
     */
    Date getLastModifyTime();

    /**
     * 通过URI得到当前URL定位的功能树下的所有功能
     * 
     * @param URI
     * @return List<ResourceEntity>
     */
    List<ResourceEntity> getAllChildResourceByURI(String uRI);
    
    /**
     * 通过父资源code得到当前资源树节点的子级节点列表
     * 
     * @param parentCode
     * @return List<ResourceEntity>
     */
    List<ResourceEntity> getDirectChildResourceByCode(String parentCode);
    
    /**
     * 通过更新时间得到已经更新过的节点父节点的编码列表
     * getByLastModifyResourceParentCode
     * @param lastModifyDate
     * @return Set<String>
     * @since:
     */
	Set<String> getByLastModifyResourceParentCode(Date lastModifyDate);
	
	/**
	 * 能过更新时间得到已经更新过的资源列表
	 * getByLastModifyResource
	 * @param lastModifyDate
	 * @return
	 * @return List<ResourceEntity>
	 * @since:
	 */
	List<ResourceEntity> getByLastModifyResource(Date lastModifyDate);
    
    /**
     * 通过资源URI得到资源对象
     * getResourceByUri
     * @param uri
     * @return
     * @return ResourceEntity
     * @since:
     */
	ResourceEntity getResourceByUri(String uri);
	
	 /**
     * 根据多个标识URI批量查询
     * getResourceBatchByUri
     * @param uris
     * @return
     * @return List<ResourceEntity>
     * @since:
     */
	List<ResourceEntity> getResourceBatchByUri(String[] uris);
	
	/**
     * 通过资源CODE得到资源对象
     * @param code
     * @return
     * @since:
     */
	ResourceEntity getResourceByCode(String code);
	
    /**
     * 根据多个标识编码批量查询
     * getResourceBatchByCode
     * @param codes
     * @return
     * @return List<ResourceEntity>
     * @since:
     */
	List<ResourceEntity> getResourceBatchByCode(String[] codes);

    /**
     * 087584-foss-lijun 权限的数据库访问常用方法
     */

    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    ResourceEntity addResource(ResourceEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    ResourceEntity deleteResource(ResourceEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    ResourceEntity deleteResourceMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    ResourceEntity updateResource(ResourceEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    ResourceEntity queryResourceByCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     * @see com.deppon.foss.module.base.dict.api.server.dao.IResourceDao#queryResourceByCode(java.lang.String)
     */
    List<ResourceEntity> queryResourceBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    List<ResourceEntity> queryResourceExactByEntity(
	    ResourceEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    long queryResourceExactByEntityCount(ResourceEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    List<ResourceEntity> queryResourceByEntity(ResourceEntity entity,
	    int start, int limit);
    
    /**
     * 
     * <p>根据entity模糊查询</p> 
     * @author ztjie
     * @date 2013-3-20 上午9:39:47
     * @param entity
     * @return
     * @see
     */
	List<ResourceEntity> queryResourceByEntity(ResourceEntity entity);
	
    /**
     * 查询queryResourceByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:45:17
     */
    long queryResourceByEntityCount(ResourceEntity entity);
	
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:20:45
     */
    List<ResourceEntity> queryResourceForDownload(ResourceEntity entity);

    
    
    /**
     * 下面为特殊查询
     */
    
    /**
     * 模糊查询 
     * 
     * 根据权限名称NAME查询权限的所有上级  ，上下级通过CODE,PARENT_RES来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-23 下午4:52:39
     * @param resourceName 权限名称
     * @param roleCode 角色编码
     */
    List<ResourceEntity> queryResourceByNameRole(String resourceName,
	    String roleCode, int start, int limit);
	    
    /**
     * 模糊查询 
     * 
     * 根据权限名称查询权限的所有上级  ，上下级通过CODE,PARENT_RES来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-23 下午4:52:39
     */
    List<ResourceEntity> queryResourceUpByName(String name);

    /**
     * 精确查询 
     * 
     * 根据权限编码查询权限的所有上级  ，上下级通过CODE,PARENT_RES来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午10:04:28
     */
    List<ResourceEntity> queryResourceUpByCode(String code);
    
    /**
     * 精确查询
     * 查询权限的根结点
     * 根据权限名称查询权限的所有上级  ，上下级通过CODE,PARENT_RES来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-12 上午9:06:35
     */
    ResourceEntity queryResourceRoot(String belongSystemType);
    

    /**
     *  精确查询 
     *  
     *  返回符合记录的条数
     *  
     *  根据上级权限，角色查询‘角色所包含的这个权限的下级权限’
     *  
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:44:19
     */
    long queryResourceCountByRoleResource(String resourceCode, String roleCode) throws BusinessException;

    
    /**
     * 下面为特殊作废
     */
    
    /**
     *  作废角色权限	
     *  
     *  返回作废的记录数
     *  
     *  根据上级权限，角色查询‘角色所包含的这个权限的下级权限’,然后作废
     *   
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:44:19
     */
    long deleteResourceByRoleResource(String resourceCode, String roleCode) throws BusinessException;
    
    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:44:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResourceRecursion(java.lang.String)
     */
    ResourceEntity deleteResourceRecursion(ResourceEntity entity);

    /**
     * 
     * <p>得到所有的权限信息</p> 
     * @author ztjie
     * @date 2013-2-18 下午4:27:45
     * @return
     * @see
     */
	List<ResourceEntity> getAllResource();
	
	/**
	 * 
	 * <p>根据节点查询所有子节点</p> 
	 * @author 何波
	 * @date 2013-3-1 上午8:14:54
	 * @return
	 * @see
	 */
	List<ResourceEntity> queryResourceByCodeAllChildNode(String code);
	
	/**
	 * 
	 * 根据员工编码和部门编码查询GUI权限信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 下午4:31:33
	 */
	List<ResourceEntity> queryGUIResourceByCode(String empCode,String orgCode,String belongSystemType);

	/**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 310854-liuzhenhua
	 */
	String queryIsECSByWayBillNo(String waybillNo) ;
}
