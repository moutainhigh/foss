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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILoadAndUnloadEfficiencyVehicleDao.java
 * 
 * FILE NAME        	: ILoadAndUnloadEfficiencyVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
/**
 * 装卸车标准-车-时间 DAO接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:13:24
 */
public interface ILoadAndUnloadEfficiencyVehicleDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    LoadAndUnloadEfficiencyVehicleEntity addLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicleMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    LoadAndUnloadEfficiencyVehicleEntity updateLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    LoadAndUnloadEfficiencyVehicleEntity queryLoadAndUnloadEfficiencyVehicleByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     * @see com.deppon.foss.module.base.dict.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao#queryLoadAndUnloadEfficiencyVehicleByCode(java.lang.String)
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleExactByEntity(
	    LoadAndUnloadEfficiencyVehicleEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    long queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleByEntity(LoadAndUnloadEfficiencyVehicleEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryLoadAndUnloadEfficiencyVehicleByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:13:24
     */
    long queryLoadAndUnloadEfficiencyVehicleByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity);
		
	
}
