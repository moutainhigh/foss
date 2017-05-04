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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/AssignLoadTaskDao.java
 *  
 *  FILE NAME          :AssignLoadTaskDao.java
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
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadManagerExceptionDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadManagerExceptionEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskEntityDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskExceptionDto;

/**
 * 查询异常装车任务Dao
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月29日
 */
public class LoadManagerExceptionDao extends iBatis3DaoImpl implements ILoadManagerExceptionDao{
	private static final String NAMESPACE = "tfr.load.exception.";

	/**
	 * 查询装车任务异常记录数
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	@Override
	public Long queryLoadManagerExceptionCount(LoadManagerExceptionEntity loadManagerExceptionEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getExceptionCount", loadManagerExceptionEntity);
	}
	
	/**
	 * 查询装车任务异常
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月30日
	 * @param loadManagerExceptionEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadTaskExceptionDto> queryLoadManagerException(LoadManagerExceptionEntity loadManagerExceptionEntity,int limit,int start){
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "getExceptionList", loadManagerExceptionEntity,rowBounds);
	}

	/**
	 * 查询扫描件数
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月7日
	 * @param taskNo
	 * @return
	 */
	@Override
	public int queryScanQtyCount(String taskNo) {
		
		Object returnCount = this.getSqlSession().selectOne(NAMESPACE + "queryScanQtyCount", taskNo);
		if (returnCount == null || returnCount == "") {
			return 0;
		} else {
			return (Integer)returnCount;
		}
	}

	/**
	 * 查询装车实体
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月7日
	 * @param taskNo
	 * @return
	 */
	@Override
	public LoadTaskEntityDto queryLoadManagerEntity(String taskNo) {
		return (LoadTaskEntityDto) this.getSqlSession().selectOne(NAMESPACE + "queryLoadTaskEntityByTaskNo", taskNo);
	}
}