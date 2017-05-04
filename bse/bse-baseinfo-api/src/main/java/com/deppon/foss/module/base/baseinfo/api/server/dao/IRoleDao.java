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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IRoleDao.java
 * 
 * FILE NAME        	: IRoleDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:角色数据访问 </small></b> </br> <b
 * style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public interface IRoleDao {

	/**
	 * 
	 * <p>提供缓存加载所有的角色对象与角色所对应的功能ID</p> 
	 * @author ztjie
	 * @date 2013-1-30 下午3:53:09
	 * @return
	 * @see
	 */
    List<RoleEntity> getRoleAndResIds();
    
    /**
     * 
     * <p>缓存根据角色编码加载角色与功能Code</p> 
     * @author ztjie
     * @date 2013-1-30 下午3:53:31
     * @param roleCode
     * @return
     * @see
     */
    List<RoleEntity> getRoleAndResCodesByCode(String roleCode);
    
    /**
     * 
     * <p>缓存根据更新版本加载角色与功能Code</p> 
     * @author ztjie
     * @date 2013-1-30 下午3:53:39
     * @param lastVersionNo
     * @return
     * @see
     */
    List<RoleEntity> getByLastModifyRole(Date lastModyfyTime);
    
    /**
     * 
     * <p>根据 CODE 批量精确查询角色与功能Code</p> 
     * @author ztjie
     * @date 2013-1-30 下午3:54:13
     * @param codes
     * @return
     * @see
     */
    List<RoleEntity> getRoleBatchByCode(String[] codes);

    /**
     * 查询最后更新时间
     * 
     * @return
     */
    Date getLastModifyTime();
    
    
    
    /**
     * 下面是基本的DAO操作
     */
    
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    RoleEntity addRole(RoleEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    RoleEntity deleteRole(RoleEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    RoleEntity deleteRoleMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    RoleEntity updateRole(RoleEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    RoleEntity queryRoleByCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     * @see com.deppon.foss.module.base.dict.api.server.dao.IRoleDao#queryRoleByCode(java.lang.String)
     */
    List<RoleEntity> queryRoleBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    List<RoleEntity> queryRoleExactByEntity(
	    RoleEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    long queryRoleExactByEntityCount(RoleEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    List<RoleEntity> queryRoleByEntity(RoleEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryRoleByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:56
     */
    long queryRoleByEntityCount(RoleEntity entity);
	
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:49:21
     */
    List<RoleEntity> queryRoleForDownload(RoleEntity entity);
		
    /**
     * 根据用户部门角色 批量精确查询数据
     * 
     * @author 313353-foss-邱鹏
     * @date 2016-11-2 下午8:49:21
     */
    List<RoleEntity> queryRoleByUserOrgRole(UserOrgRoleEntity entity);
	
}
