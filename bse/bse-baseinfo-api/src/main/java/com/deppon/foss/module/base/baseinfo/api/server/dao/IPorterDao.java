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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPorterDao.java
 * 
 * FILE NAME        	: IPorterDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
/**
 * 理货员 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-10-30 上午12:46:3
 */
public interface IPorterDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    PorterEntity addPorter(PorterEntity entity);

    /**
     * 根据EMP_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    PorterEntity deletePorter(PorterEntity entity);

    /**
     * 根据EMP_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    PorterEntity deletePorterMore(String[] codes ,LoadAndUnloadSquadEntity loadAndUnloadSquadEntity, String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    PorterEntity updatePorter(PorterEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据EMP_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    PorterEntity queryPorterByEmpCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个EMP_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     * @see com.deppon.foss.module.base.dict.api.server.dao.IPorterDao#queryPorterByCode(java.lang.String)
     */
    List<PorterEntity> queryPorterBatchByEmpCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    List<PorterEntity> queryPorterExactByEntity(
	    PorterEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    long queryPorterExactByEntityCount(PorterEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    List<PorterEntity> queryPorterByEntity(PorterEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryPorterByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:4:48
     */
    long queryPorterByEntityCount(PorterEntity entity);

    
    
    /**
     * 下面为特殊查询
     */
    
		
    /**
     * 精确查询
     * 根据多个PARENT_ORG_CODE批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午5:21:54
     */
    List<PorterEntity> queryPorterBatchByParentOrgCode(String[] codes);
}
