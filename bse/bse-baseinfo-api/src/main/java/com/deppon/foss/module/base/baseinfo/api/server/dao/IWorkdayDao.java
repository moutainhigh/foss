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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IWorkdayDao.java
 * 
 * FILE NAME        	: IWorkdayDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
/**
 *  DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-28 下午5:4:43
 */
public interface IWorkdayDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    WorkdayEntity addWorkday(WorkdayEntity entity);

    /**
     * 根据WORK_MONTH删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    WorkdayEntity deleteWorkday(WorkdayEntity entity);

    /**
     * 根据WORK_MONTH批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    WorkdayEntity deleteWorkdayMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    WorkdayEntity updateWorkday(WorkdayEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据WORK_MONTH 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    WorkdayEntity queryWorkdayByWorkMonth(String code);
	
	
    /**
     * 精确查询
     * 根据多个WORK_MONTH 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     * @see com.deppon.foss.module.base.dict.api.server.dao.IWorkdayDao#queryWorkdayByCode(java.lang.String)
     */
    List<WorkdayEntity> queryWorkdayBatchByWorkMonth(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    List<WorkdayEntity> queryWorkdayExactByEntity(
	    WorkdayEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    long queryWorkdayExactByEntityCount(WorkdayEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    List<WorkdayEntity> queryWorkdayByEntity(WorkdayEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryWorkdayByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    long queryWorkdayByEntityCount(WorkdayEntity entity);
		
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:4:43
     */
    List<WorkdayEntity> queryWorkdayForDownload(WorkdayEntity entity);
		
	
}
