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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IWorkdayService.java
 * 
 * FILE NAME        	: IWorkdayService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WorkdayException;
/**
 * 工作日 Service接口
 * 
 * 目前 结算组不使用，接送货组使用
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-28 下午5:11:36
 */
public interface IWorkdayService extends IService {
    /**
     * 插入 工作日,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    WorkdayEntity addWorkday(WorkdayEntity entity)throws WorkdayException ;
    
    /**
     * <p>保存或修改工作日</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-27 下午3:26:49
     * @param entity 工作日实体
     * @return
     * @see
     */
    WorkdayEntity saveOrUpdate(WorkdayEntity entity);
	
    /**
     * 根据CODE删除 工作日,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    WorkdayEntity deleteWorkday(WorkdayEntity entity);
	
    /**
     * 根据CODE批量删除 工作日,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    WorkdayEntity deleteWorkdayMore(String[] codes , String deleteUser);
	
    /**
     * 更新  工作日,
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    WorkdayEntity updateWorkday(WorkdayEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询 工作日,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    WorkdayEntity queryWorkdayByWorkMonth(String code);	
	
	
    /**
     * 精确查询 工作日,
    
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IWorkdayDao#queryWorkdayByWorkMonth(java.lang.String)
     */
    List<WorkdayEntity> queryWorkdayBatchByWorkMonth(String[] codes);
    
    /**
     * 精确查询 工作日,
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    List<WorkdayEntity> queryWorkdayExactByEntity(
	    WorkdayEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    long queryWorkdayExactByEntityCount(WorkdayEntity entity);
	
    /**
     * 精确查询 工作日,返回所有的结果，
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    List<WorkdayEntity> queryWorkdayExactByEntityAll(
	    WorkdayEntity entity) ;
    
    /**
     * 根据entity模糊查询 工作日
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    List<WorkdayEntity> queryWorkdayByEntity(
	    WorkdayEntity entity,int start, int limit);
	
    /**
     * 查询queryWorkdayByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    long queryWorkdayByEntityCount(WorkdayEntity entity);
    
    /**
     * 根据entity模糊查询 工作日,返回所有的结果，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:11:36
     */
    List<WorkdayEntity> queryWorkdayByEntityAll(
	    WorkdayEntity entity);
	
}
