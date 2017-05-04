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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IResourceConflictDao.java
 * 
 * FILE NAME        	: IResourceConflictDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
/**
 * 权限互斥 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-10-26 下午4:36:7
 */
public interface IResourceConflictDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    ResourceConflictEntity addResourceConflict(ResourceConflictEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    ResourceConflictEntity deleteResourceConflict(ResourceConflictEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    ResourceConflictEntity deleteResourceConflictMore(String[] codes , String deleteUser);

    /** 
     * 通过权限编码批量删除互斥信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-5-29 下午5:08:46
     */
    int deleteResourceConflictByCode(String code,String modifyUserCode);
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    ResourceConflictEntity queryResourceConflictByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     * @see com.deppon.foss.module.base.dict.api.server.dao.IResourceConflictDao#queryResourceConflictByCode(java.lang.String)
     */
    List<ResourceConflictEntity> queryResourceConflictBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    List<ResourceConflictEntity> queryResourceConflictExactByEntity(
	    ResourceConflictEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:23:23
     */
    long queryResourceConflictExactByEntityCount(ResourceConflictEntity entity);
    

    
    /**
     * 下面为特殊查询
     */
	
    /**
     * 根据 两批权限CODE 查询哪些是互斥的：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    List<ResourceConflictEntity> queryResourceConflictByCodes(String[] codes1,String[] codes2);
	
	
	
    /**
     * 根据权限CODE查询这些CODE中哪些是互斥的：
     * 如果CODE为空，则应调queryResourceConflictExactByEntity
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictByCodes(java.lang.String[], java.lang.String[])
     */
    List<ResourceConflictEntity> queryResourceConflictByCodes(
	    String[] codes);
    
    
    /**
     * 根据2个角色编码查询这个两角色包含的权限有哪些是互斥的
     * 
     * 在给用户部门分配角色时判断角色互斥
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    List<ResourceConflictEntity> queryResourceConflictBy2Role(
	    String firstRoleCode, String secondRoleCode);
	
}
