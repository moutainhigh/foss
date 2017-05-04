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

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpPscDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPtpPscService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpPscEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpPscDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * 合伙人子公司月报表
 *
 *  * @author foss-youkun-306698
 * @date 2016-3-18 下午3:43:01
 */
public class MvrPtpPscService implements IMvrPtpPscService {
	
	/**
	 * 记录日志 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MvrPtpPscService.class);


	private IMvrPtpPscDao mvrPtpPscDao;
	/**
	 * 合伙人子公司月报表总记录数
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public Long queryMvrParentComCount(MvrPtpPscDto dto) throws SettlementException {
		Long count = 0L;
		try{
			count = this.mvrPtpPscDao.queryMvrParentComCount(dto);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw  new SettlementException("","查询总记录数失败！ "+e.getMessage());
		}
		return count;
	}

	/**
	 * 合伙人子公司月报表集合
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public List<MvrPtpPscEntity> queryMvrPtpPscList(MvrPtpPscDto dto,int offset,int limit) throws SettlementException {
		List<MvrPtpPscEntity> mvrPtpPscEntityList = null;
		try{
			mvrPtpPscEntityList = this.mvrPtpPscDao.queryMvrPtpPscList(dto,offset,limit);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw new SettlementException("","查询失败！ "+e.getMessage());
		}

		return mvrPtpPscEntityList;
	}

	/**
	 * 导出合伙人子公司月报表
	 * @param dto
	 * @return
	 * @throws SettlementException
     */
	public List<MvrPtpPscEntity> exportMvrParentCom(MvrPtpPscDto dto) throws SettlementException {
		List<MvrPtpPscEntity> mvrPtpPscEntityList = null;
		try{
			mvrPtpPscEntityList = this.mvrPtpPscDao.exportMvrParentCom(dto);
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			throw new SettlementException("","导出查询失败！ "+e.getMessage());
		}

		return mvrPtpPscEntityList;
	}


	public void setMvrPtpPscDao(IMvrPtpPscDao mvrPtpPscDao) {
		this.mvrPtpPscDao = mvrPtpPscDao;
	}
}
