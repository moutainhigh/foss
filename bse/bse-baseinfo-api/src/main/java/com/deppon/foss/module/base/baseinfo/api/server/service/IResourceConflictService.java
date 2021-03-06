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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IResourceConflictService.java
 * 
 * FILE NAME        	: IResourceConflictService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceConflictException;
/**
 *  SERVICE接口
 * @author 087584-foss-lijun
 * @date 2012-10-26 下午4:36:7
 */
public interface IResourceConflictService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     */
    ResourceConflictEntity addResourceConflict(ResourceConflictEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     */
    ResourceConflictEntity deleteResourceConflict(ResourceConflictEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     */
    ResourceConflictEntity deleteResourceConflictMore(String[] codes , String deleteUser);

	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     */
    ResourceConflictEntity queryResourceConflictByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictByVirtualCode(java.lang.String)
     */
    List<ResourceConflictEntity> queryResourceConflictBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
     */
    List<ResourceConflictEntity> queryResourceConflictExactByEntity(
	    ResourceConflictEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:37:28
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
    public List<ResourceConflictEntity> queryResourceConflictByCodes(String[] codes1,String[] codes2);
	
    /**
     * 根据权限CODE查询这些CODE中哪些是互斥的：
     * 如果CODE为空，则应调queryResourceConflictExactByEntity
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    List<ResourceConflictEntity> queryResourceConflictByCodes(String[] codes);
	
    /**
     * 
     * <p>验证用户已有的权限与新增的权限是否互斥</p> 
     * @author 何波
     * @date 2013-2-26 上午9:06:00
     * @param codeList 用户已有权限编码
     * @param code  新增给用户的编码
     * @return
     * @see
     */
    List<ResourceConflictEntity>  checkUserResourceIsConflict(List<String> codeList, List<String> code) throws ResourceConflictException;
    
    /** 
     * 根据权限编删除权限互斥信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-5-29 下午5:15:44
     */
    int deleteResourceConflictByCode(String code , String modifyUserCode);
}
