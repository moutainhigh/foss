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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IRoleResourceDao.java
 * 
 * FILE NAME        	: IRoleResourceDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
/**
 * 角色权限 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午4:24:21
 */
public interface IRoleResourceDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    RoleResourceEntity addRoleResource(RoleResourceEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    RoleResourceEntity deleteRoleResource(RoleResourceEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    RoleResourceEntity deleteRoleResourceMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    RoleResourceEntity updateRoleResource(RoleResourceEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    RoleResourceEntity queryRoleResourceByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     * @see com.deppon.foss.module.base.dict.api.server.dao.IRoleResourceDao#queryRoleResourceByCode(java.lang.String)
     */
    List<RoleResourceEntity> queryRoleResourceBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    List<RoleResourceEntity> queryRoleResourceExactByEntity(
	    RoleResourceEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    long queryRoleResourceExactByEntityCount(RoleResourceEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    List<RoleResourceEntity> queryRoleResourceByEntity(RoleResourceEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryRoleResourceByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:24:21
     */
    long queryRoleResourceByEntityCount(RoleResourceEntity entity);
		
	
    
    /**
     * 下面为特殊方法
     */
    
    /**
     * 根据ROLE_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-25 下午3:27:19
     */
    RoleResourceEntity deleteRoleResourceByRoleCode(RoleResourceEntity entity);
	
    
    /**
     * 特殊删除
     * 根据ROLE_CODE, RESOURCE_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-25 下午3:27:19
     */
    RoleResourceEntity deleteRoleResourceByRoleResource(RoleResourceEntity entity);
    
    
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:43:42
     */
    List<RoleResourceEntity> queryRoleResourceForDownload(RoleResourceEntity entity);
    
    /**
     * 特殊查询，精确查询
     * 根据多个标识和列名批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceBatchBy(java.lang.String[])
     */
    List<RoleResourceEntity> queryRoleResourceMoreByColumnName(
	    String[] codes, String columnName);
    /**
     * 
     * <p>查询用户拥有的所有权限</p> 
     * @author 何波
     * @date 2013-2-26 下午4:03:41
     * @param roleResource
     * @return
     * @see
     */
    List<RoleResourceEntity> queryRoleResourceExactByEntity(RoleResourceEntity roleResource);

	/**
	 * 根据entity精确查询,用于数据下载分页
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
    List<RoleResourceEntity>  queryRoleResourceForDownloadByPage(RoleResourceEntity entity, int started,
			int limited);
    
}
