/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * PROJECT NAME	: stl-closing
 *
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/CRMPayableBillService.java
 *
 * FILE NAME        	: CRMPayableBillService.java
 *
 * AUTHOR			: FOSS结算系统开发组
 *
 * HOME PAGE		: http://www.deppon.com
 *
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpRpsDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpRpsService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpsEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpsDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * 合伙人奖罚特殊月报表
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public class MvrPtpRpsService implements IMvrPtpRpsService{
	
	/**
	 * 记录日志 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MvrPtpRpsService.class);

	private IMvrPtpRpsDao mvrPtpRpsDao;


	public Long queryMvrPtpRpsCount(MvrPtpRpsDto dto) {
		Long count = 0L;
		try{
			count = this.mvrPtpRpsDao.queryMvrPtpRpsCount(dto);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw new SettlementException("查询总记录数失败！ " +e.getMessage());
		}
		return count;
	}

	public List<MvrPtpRpsEntity> queryMvrPtpRpsEntityList(MvrPtpRpsDto dto, int offset, int limit) {
		List<MvrPtpRpsEntity> mvrPtpRpsEntityList = null;
		try{
			mvrPtpRpsEntityList = mvrPtpRpsDao.queryMvrPtpRpsEntityList(dto,offset,limit);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw new SettlementException("查询失败！ " +e.getMessage());
		}
		return mvrPtpRpsEntityList;
	}

	/**
	 * 导出合伙人奖罚特殊报表
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public List<MvrPtpRpsEntity> exportMvrPtpPsc(MvrPtpRpsDto dto) throws SettlementException {
		List<MvrPtpRpsEntity> mvrPtpRpsEntityList = null;
		try{
			mvrPtpRpsEntityList = mvrPtpRpsDao.exportMvrPtpPsc(dto);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw new SettlementException("查询失败！ " +e.getMessage());
		}
		return mvrPtpRpsEntityList;
	}


	public void setMvrPtpRpsDao(IMvrPtpRpsDao mvrPtpRpsDao) {
		this.mvrPtpRpsDao = mvrPtpRpsDao;
	}
}
