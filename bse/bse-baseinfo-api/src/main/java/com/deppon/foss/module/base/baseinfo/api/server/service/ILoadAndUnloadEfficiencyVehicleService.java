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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILoadAndUnloadEfficiencyVehicleService.java
 * 
 * FILE NAME        	: ILoadAndUnloadEfficiencyVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;
/**
 * 装卸车标准-车-时间 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:14:13
 */
public interface ILoadAndUnloadEfficiencyVehicleService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    LoadAndUnloadEfficiencyVehicleEntity addLoadAndUnloadEfficiencyVehicle(
	    LoadAndUnloadEfficiencyVehicleEntity entity)
	    throws LoadAndUnloadEfficiencyVehicleException;

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity) throws BusinessException;
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    LoadAndUnloadEfficiencyVehicleEntity deleteLoadAndUnloadEfficiencyVehicleMore(String[] codes , String deleteUser) throws BusinessException;
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    LoadAndUnloadEfficiencyVehicleEntity updateLoadAndUnloadEfficiencyVehicle(LoadAndUnloadEfficiencyVehicleEntity entity) throws BusinessException;
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    LoadAndUnloadEfficiencyVehicleEntity queryLoadAndUnloadEfficiencyVehicleByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyVehicleDao#queryLoadAndUnloadEfficiencyVehicleByVirtualCode(java.lang.String)
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleExactByEntity(
	    LoadAndUnloadEfficiencyVehicleEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    long queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    List<LoadAndUnloadEfficiencyVehicleEntity> queryLoadAndUnloadEfficiencyVehicleByEntity(
	    LoadAndUnloadEfficiencyVehicleEntity entity,int start, int limit);
	
    /**
     * 查询queryLoadAndUnloadEfficiencyVehicleByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:13
     */
    long queryLoadAndUnloadEfficiencyVehicleByEntityCount(LoadAndUnloadEfficiencyVehicleEntity entity);
    
    /**
     * 下面是特殊查询方法
     */
	
    /**
     * 精确查询 
     * 
     * 通过部门编码，车长编码  查询出一条装卸车标准
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:14:1
     */
    LoadAndUnloadEfficiencyVehicleEntity queryLoadAndUnloadEfficiencyVehicleByOrgVehicleLength(
	    String orgCode, String vehicleLength);
	
}
