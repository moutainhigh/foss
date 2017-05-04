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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/QueryUnloadingProgressDao.java
 *  
 *  FILE NAME          :QueryUnloadingProgressDao.java
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
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.dao.impl
 * FILE    NAME: QueryUnloadingProgressDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryBillInfoResultDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;

/**
 * 查询卸车进度dao实现类
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 下午3:34:38
 */
public class QueryUnloadingProgressDao extends iBatis3DaoImpl implements
		IQueryUnloadingProgressDao {
	private static final String NAMESPACE = "foss.unload.unloadingprogress.";
	/** 
	 * 查询卸车进度信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:34:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao#queryUnloadingProgressInfo(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUnloadingProgressResultDto> queryUnloadingProgressInfo(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto,int limit,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryUnloadingProgress", 
				queryUnloadingProgressConditionDto,
				rowBounds);
	}
	
	/** 
	 * 查询卸车进度信息
	 * @author 134019
	 * @date 2013年7月15日 19:10:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao#queryUnloadingProgressInfoCount(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto)
	 */
	@Override
	public Long queryUnloadingProgressInfoCount(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryUnloadingProgressCount", 
				queryUnloadingProgressConditionDto);
	}
	
	
	/**
	 * 
	 * 查询单据信息
	 * @param map{id:卸车任务id，cancel：取消状态}
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-13 下午4:52:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryBillInfoResultDto> queryBillInfo(Map<String, String> map) {
		
		return getSqlSession().selectList(NAMESPACE+"queryBillInfoResult", map);
	}

	
	
}