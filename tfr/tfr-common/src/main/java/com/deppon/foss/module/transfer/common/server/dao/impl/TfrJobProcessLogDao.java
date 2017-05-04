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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/dao/impl/TfrJobProcessLogDao.java
 *  
 *  FILE NAME          :TfrJobProcessLogDao.java
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
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessLogDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessLogDto;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * @author 038300-foss-pengzhen
 *
 */
public class TfrJobProcessLogDao extends iBatis3DaoImpl implements
		ITfrJobProcessLogDao {

	@Override
	public void addJobProcessLog(TfrJobProcessLogEntity jobProcessLogEntity) {
		jobProcessLogEntity.setId(UUIDUtils.getUUID());

		this.getSqlSession().insert(
				"foss.tfr.TfrJobProcessLogDao.addJobProcessLog",
				jobProcessLogEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrJobProcessLogEntity> selectJobProcessLogList(
			TfrJobProcessLogDto jobProcessLogDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				"foss.tfr.TfrJobProcessLogDao.selectJobProcessLogList",
				jobProcessLogDto, rowBounds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessLogDao
	 * #selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public TfrJobProcessLogEntity selectByPrimaryKey(String id) {
		return (TfrJobProcessLogEntity) this.getSqlSession().selectOne(
				"foss.tfr.TfrJobProcessLogDao.selectByPrimaryKey", id);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessLogDao#selectJobProcessLogCount(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobProcessLogDto)
	 */
	@Override
	public Long selectJobProcessLogCount(TfrJobProcessLogDto jobProcessLogDto) {
		return (Long) this.getSqlSession().selectOne(
				"foss.tfr.TfrJobProcessLogDao.selectJobProcessLogCount", jobProcessLogDto);
	}

}