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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/MvrRfdEntityService.java
 * 
 * FILE NAME        	: MvrRfdEntityService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDhkEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDhkEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款月报表Service.
 *
 * @author guxinhua
 * @date 2013-3-6 下午2:22:28
 */
public class MvrDhkEntityService implements IMvrDhkEntityService {

	private final static Logger LOGGER = LogManager.getLogger(MvrDhkEntityService.class);
	
	/** 代汇款月报表Dao. */
	private IMvrDhkEntityDao mvrDhkEntityDao;

	/**
	 * 查询代汇款月报表.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author guxinhua
	 * @date 2013-3-6 下午2:23:13
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto,
	 * int, int)
	 */
	@Override
	public List<MvrDhkEntity> selectByConditions(MvrDhkDto dto, int start,
			int limit)  throws SettlementException {
		if(null == dto){
			//内部错误，代汇款月报表DTO参数为空
			throw new SettlementException("内部错误，代汇款月报表DTO参数为空！");
		}
		LOGGER.info("查询代汇款月报表 Start：" + dto.getPeriod());
		
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询代汇款月报表
		List<MvrDhkEntity> list = mvrDhkEntityDao.selectByConditions(dto, start, limit);
		
		LOGGER.info("查询代汇款月报表 End.");
		
		return list;
	}

	/**
	 * 查询代汇款月报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd entity
	 * @author Administrator
	 * @date 2013-3-6 下午2:23:29
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public MvrDhkDto selectTotalByConditions(MvrDhkDto dto)  throws SettlementException {
		if(null == dto){
			//内部错误，代汇款月报表DTO参数为空
			throw new SettlementException("内部错误，代汇款月报表DTO参数为空！");
		}
		LOGGER.info("查询代汇款月报表合计 Start：" + dto.getPeriod());
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询代汇款月报表合计
		MvrDhkDto mvrRfdDto = mvrDhkEntityDao.selectTotalByConditions(dto);

		LOGGER.info("查询代汇款月报表合计 End.");
		return mvrRfdDto;
	}

	/**
	 * 验证MvrRfdDto
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 下午2:27:54
	 */
	private void validationMvrRfdDto(MvrDhkDto dto){
		
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}

	}

	/**
	 * @param mvrDhkEntityDao the mvrDhkEntityDao to set
	 */
	public void setMvrDhkEntityDao(IMvrDhkEntityDao mvrDhkEntityDao) {
		this.mvrDhkEntityDao = mvrDhkEntityDao;
	}
 
}
