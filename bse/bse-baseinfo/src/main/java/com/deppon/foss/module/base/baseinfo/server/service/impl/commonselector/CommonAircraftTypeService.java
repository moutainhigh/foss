/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonAircraftTypeService.java
 * 
 * FILE NAME        	: CommonAircraftTypeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonAircraftTypeService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAircraftTypeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAircraftTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;

/**
 * 公共查询选择器--机型service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:50:37
 */
public class CommonAircraftTypeService implements ICommonAircraftTypeService {
	
	/** The common aircraft type dao. */
	private ICommonAircraftTypeDao commonAircraftTypeDao;
	
	/**
	 * 机型查询.
	 *
	 * @param aircraftType the aircraft type
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:50:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAircraftTypeService#queryAircraftTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity, int, int)
	 */
	@Override
	public List<AircraftTypeEntity> queryAircraftTypeListByCondition(
			AircraftTypeEntity aircraftType, int start, int limit) {
		return commonAircraftTypeDao.queryAircraftTypeListByCondition(
				aircraftType, start, limit);
	}

	/**
	 * 总数查询.
	 *
	 * @param aircraftType the aircraft type
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:50:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAircraftTypeService#countAircraftTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
	 */
	@Override
	public Long countAircraftTypeListByCondition(AircraftTypeEntity aircraftType) {
		return commonAircraftTypeDao.countAircraftTypeListByCondition(aircraftType);
	}

	/**
	 * setter.
	 *
	 * @param commonAircraftTypeDao the new common aircraft type dao
	 */
	public void setCommonAircraftTypeDao(
			ICommonAircraftTypeDao commonAircraftTypeDao) {
		this.commonAircraftTypeDao = commonAircraftTypeDao;
	}

}
