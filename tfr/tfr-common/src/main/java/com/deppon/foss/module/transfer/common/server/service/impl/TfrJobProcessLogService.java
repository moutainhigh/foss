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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/TfrJobProcessLogService.java
 *  
 *  FILE NAME          :TfrJobProcessLogService.java
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
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessLogDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessLogService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo;

/**
 * JOB日志查询
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月2日 10:00:55
 */
public class TfrJobProcessLogService implements ITfrJobProcessLogService {

	
	/**
	 * DAO
	 */
	private ITfrJobProcessLogDao tfrJobProcessLogDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.
	 * ITfrJobProcessLogService
	 * #getJobProcessLogsList(com.deppon.foss.module.transfer
	 * .common.api.shared.vo.TfrJobProcessLogVo)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TfrJobProcessLogEntity> getJobProcessLogsList(TfrJobProcessLogVo vo,int start,int limit) {
		return tfrJobProcessLogDao.selectJobProcessLogList(vo.getJobProcessLogDto(), start, limit);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessLogService#getJobProcessLogByID(com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo)
	 */
	@Override
	public TfrJobProcessLogEntity getJobProcessLogByID(TfrJobProcessLogVo vo) {
		return tfrJobProcessLogDao.selectByPrimaryKey(vo.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessLogService#getJobProcessLogscount(com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo)
	 */
	@Override
	public Long getJobProcessLogscount(TfrJobProcessLogVo vo) {
		return tfrJobProcessLogDao.selectJobProcessLogCount(vo.getJobProcessLogDto());
	}


	
	/**************************************getter AND setter***********************************/
	
	/**
	 * @return the tfrJobProcessLogDao
	 */
	public ITfrJobProcessLogDao getTfrJobProcessLogDao() {
		return tfrJobProcessLogDao;
	}

	/**
	 * @param tfrJobProcessLogDao the tfrJobProcessLogDao to set
	 */
	public void setTfrJobProcessLogDao(ITfrJobProcessLogDao tfrJobProcessLogDao) {
		this.tfrJobProcessLogDao = tfrJobProcessLogDao;
	}

}
