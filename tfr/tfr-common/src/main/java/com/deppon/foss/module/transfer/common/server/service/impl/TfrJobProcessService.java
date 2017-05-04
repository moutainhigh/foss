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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/TfrJobProcessService.java
 *  
 *  FILE NAME          :TfrJobProcessService.java
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
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo;

/**
 * JOB日志查询
 * 
 * @author foss-yuyongxiang
 * @date 2013年5月13日 09:08:19
 */
public class TfrJobProcessService implements ITfrJobProcessService {

	/**
	 * DAO
	 */
	private ITfrJobProcessDao tfrJobProcessDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.
	 * ITfrJobProcessService #getJobProcesssList(com.deppon.foss.module.transfer
	 * .common.api.shared.vo.TfrJobProcessVo)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TfrJobProcessEntity> getJobProcessList(TfrJobProcessLogVo vo,
			int start, int limit) {
		return tfrJobProcessDao.selectJobProcessList(vo.getJobProcessDto(),
				start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.
	 * ITfrJobProcessService
	 * #getJobProcessByID(com.deppon.foss.module.transfer.common
	 * .api.shared.vo.TfrJobProcessVo)
	 */
	@Override
	public TfrJobProcessEntity getJobProcessByID(TfrJobProcessLogVo vo) {
		return tfrJobProcessDao.selectByPrimaryKey(vo.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.
	 * ITfrJobProcessService
	 * #getJobProcessscount(com.deppon.foss.module.transfer.
	 * common.api.shared.vo.TfrJobProcessVo)
	 */
	@Override
	public Long getJobProcesscount(TfrJobProcessLogVo vo) {
		return tfrJobProcessDao.selectJobProcessCount(vo.getJobProcessDto());
	}

	/************************************** getter AND setter ***********************************/

	/**
	 * @return the tfrJobProcessDao
	 */
	public ITfrJobProcessDao getTfrJobProcessDao() {
		return tfrJobProcessDao;
	}

	/**
	 * @param tfrJobProcessDao
	 *            the tfrJobProcessDao to set
	 */
	public void setTfrJobProcessDao(ITfrJobProcessDao tfrJobProcessDao) {
		this.tfrJobProcessDao = tfrJobProcessDao;
	}

}
