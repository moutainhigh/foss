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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILoadAndUnloadSquadDao.java
 * 
 * FILE NAME        	: ILoadAndUnloadSquadDao.java
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
/**
 * 装卸车小队 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:23:3
 */
public interface ILoadAndUnloadSquadDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    LoadAndUnloadSquadEntity addLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    LoadAndUnloadSquadEntity deleteLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    LoadAndUnloadSquadEntity deleteLoadAndUnloadSquadMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    LoadAndUnloadSquadEntity updateLoadAndUnloadSquad(LoadAndUnloadSquadEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    LoadAndUnloadSquadEntity queryLoadAndUnloadSquadByCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     * @see com.deppon.foss.module.base.dict.api.server.dao.ILoadAndUnloadSquadDao#queryLoadAndUnloadSquadByCode(java.lang.String)
     */
    List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadExactByEntity(
	    LoadAndUnloadSquadEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    long queryLoadAndUnloadSquadExactByEntityCount(LoadAndUnloadSquadEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    List<LoadAndUnloadSquadEntity> queryLoadAndUnloadSquadByEntity(LoadAndUnloadSquadEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryLoadAndUnloadSquadByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:23:3
     */
    long queryLoadAndUnloadSquadByEntityCount(LoadAndUnloadSquadEntity entity);
		
	
}
