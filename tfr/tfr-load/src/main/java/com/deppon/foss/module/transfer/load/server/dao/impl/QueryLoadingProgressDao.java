/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/QueryLoadingProgressDao.java
 *  
 *  FILE NAME          :QueryLoadingProgressDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: QueryLoadingProgressDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IQueryLoadingProgressDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryDeparturePlanDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressResultDto;

/**
 * 查询装车进度dao实现类
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 下午4:46:07
 */
public class QueryLoadingProgressDao extends iBatis3DaoImpl implements
		IQueryLoadingProgressDao {
	private static final String NAMESPACE = "foss.load.loadingprogress.";
	/**
	 * 
	 * 查询装车进度dao
	 * @param queryLoadingProgressConditionDto 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:41:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryLoadingProgressResultDto> queryLoadingProgressInfo(
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto,int limit,int start) {
		//RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryLoadingProgress",	queryLoadingProgressConditionDto);
	}
	/**
	 * 
	 *  根据任务id查询发车计划
	 * @param taskId 任务id
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-27 下午7:48:59
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IQueryLoadingProgressDao#queryDeparturePlan(java.lang.String)
	 */
	@Override
	public QueryDeparturePlanDto queryDeparturePlan(String taskId) {
		return (QueryDeparturePlanDto)getSqlSession().selectOne(NAMESPACE+"queryDeparturePlan", taskId);
	}	
	
	
}