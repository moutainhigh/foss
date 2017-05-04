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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/MvrLddQueryService.java
 * 
 * FILE NAME        	: MvrLddQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrWoodenEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrWoodenEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 快递代理月报表查询服务实现.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午9:55:37
 */
public class MvrWoodenEntityService implements IMvrWoodenEntityService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrWoodenEntityService.class);

	/** 数据库接口. */
	private IMvrWoodenEntityDao mvrWoodenEntityDao;
	
	/**
	 * 功能：根据条件查询代打木架信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	@Override
	public List<MvrWoodenEntity> selectByConditions(MvrWoodenDto dto,
			int start, int limit) throws SettlementException {
		LOGGER.info("查询快递代理月报表列表,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		List<MvrWoodenEntity> queryList = mvrWoodenEntityDao.selectMvrWoodenByParam(dto, start, limit);

		LOGGER.info("结束查询快递代理月报表列表");

		return queryList;
	}
	/**
	 * 功能：查询代打木架汇总信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	@Override
	public MvrWoodenDto queryMvrWoodenTotal(MvrWoodenDto dto) {
		LOGGER.info("查询快递代理月报表汇总,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		MvrWoodenDto queryDto = (MvrWoodenDto) mvrWoodenEntityDao.selectMvrWoodenByParamCount(dto);

		LOGGER.info("结束查询快递代理月报表汇总");

		return queryDto;
	}
	
	/**
	 * 功能：查询代打木架汇总信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	@Override
	public List<MvrWoodenEntity> selectByConditions(MvrWoodenDto dto)
			throws SettlementException {
		LOGGER.info("查询快递代理月报表列表,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		List<MvrWoodenEntity> queryList = mvrWoodenEntityDao.selectMvrWoodenByParam(dto);

		LOGGER.info("结束查询快递代理月报表列表");

		return queryList;
	}
	
	public IMvrWoodenEntityDao getMvrWoodenEntityDao() {
		return mvrWoodenEntityDao;
	}

	public void setMvrWoodenEntityDao(IMvrWoodenEntityDao mvrWoodenEntityDao) {
		this.mvrWoodenEntityDao = mvrWoodenEntityDao;
	}
	


}
