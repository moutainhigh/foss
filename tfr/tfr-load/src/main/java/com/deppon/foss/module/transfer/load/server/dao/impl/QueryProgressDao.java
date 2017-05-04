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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/QueryProgressDao.java
 *  
 *  FILE NAME          :QueryProgressDao.java
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
 * FILE    NAME: QueryProgressDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IQueryProgressDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressMapperResultDto;

/**
 * 月台调用
 * @author 046130-foss-xuduowei
 * @date 2012-11-27 下午2:10:52
 */
public class QueryProgressDao extends iBatis3DaoImpl implements
		IQueryProgressDao {
	
	private static final String NAMESPACE = "foss.platform.queryprogress.";
	/** 
	 * 给月台查询是调用显示车辆的装车或卸车进度
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-27 下午2:10:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IQueryProgressDao#queryProgressResult(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryProgressMapperResultDto> queryLoadProgressResult(
			Map<String, Object> queryMap) {
		return getSqlSession().selectList(NAMESPACE+"queryLoadProgressResult", queryMap);
	}
	/**
	 * 
	 * 给月台查询是调用显示车辆的装车或卸车进度
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 上午9:10:27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryProgressMapperResultDto> queryUnloadProgressResult(
			Map<String, Object> queryMap) {
		return getSqlSession().selectList(NAMESPACE+"queryUnloadProgressResult", queryMap);
	}
	
}