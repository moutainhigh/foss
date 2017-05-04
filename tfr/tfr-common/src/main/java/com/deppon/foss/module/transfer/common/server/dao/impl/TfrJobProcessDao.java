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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/dao/impl/TfrJobProcessDao.java
 *  
 *  FILE NAME          :TfrJobProcessDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessDto;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * @author 038300-foss-pengzhen
 *
 */
public class TfrJobProcessDao extends iBatis3DaoImpl implements ITfrJobProcessDao{

	@SuppressWarnings("unchecked")
	@Override
	public TfrJobProcessEntity queryJobProcessByIndex(String bizCode, int threadNo, int threadCount) {
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		jobProcess.setBizCode(bizCode);
		jobProcess.setThreadNo(threadNo);
		jobProcess.setThreadCount(threadCount);
		
		List<TfrJobProcessEntity> list = this.getSqlSession().selectList("foss.tfr.TfrJobProcessDao.queryJobProcessByIndex", jobProcess);
		
		if(list.size() == 1){
			return list.get(0);
		}else{
			return new TfrJobProcessEntity();
		}
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TfrJobProcessEntity queryJobProcessByIndexNoLock(String bizCode, int threadNo, int threadCount) {
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		jobProcess.setBizCode(bizCode);
		jobProcess.setThreadNo(threadNo);
		jobProcess.setThreadCount(threadCount);
		
		List<TfrJobProcessEntity> list = this.getSqlSession().selectList("foss.tfr.TfrJobProcessDao.queryJobProcessByIndexNoLock", jobProcess);
		
		if(list.size() == 1){
			return list.get(0);
		}else{
			return new TfrJobProcessEntity();
		}
	}

	@Override
	public void addJobProcessEntity(TfrJobProcessEntity jobProcess) {
		jobProcess.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.tfr.TfrJobProcessDao.addJobProcessEntity", jobProcess);
	}

	@Override
	public void updateJobProcessEntity(TfrJobProcessEntity jobProcess) {
		this.getSqlSession().update("foss.tfr.TfrJobProcessDao.updateJobProcessEntity", jobProcess);
	}
	/**
	 * 
	 */
	@Override
	public void updateByPrimaryKeySelective(TfrJobProcessEntity jobProcess) {
		if(null != jobProcess && StringUtils.isNotEmpty(jobProcess.getId())){
			this.getSqlSession().update("foss.tfr.TfrJobProcessDao.updateByPrimaryKeySelective", jobProcess);
		}
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public TfrJobProcessEntity selectByPrimaryKey(String id) {
		
		return (TfrJobProcessEntity) this.getSqlSession().selectOne(
				"foss.tfr.TfrJobProcessDao.selectByPrimaryKey", id);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao#selectJobProcessList(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TfrJobProcessEntity> selectJobProcessList(
			TfrJobProcessDto jobProcess,int start,int limit) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				"foss.tfr.TfrJobProcessDao.selectJobProcessList",jobProcess,rowBounds);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao#selectJobProcessCount(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessDto)
	 */
	@Override
	public Long selectJobProcessCount(TfrJobProcessDto jobProcess) {
		return (Long) this.getSqlSession().selectOne(
				"foss.tfr.TfrJobProcessDao.selectJobProcessCount", jobProcess);
	}
}