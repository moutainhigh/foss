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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonMotorcadeDistrictService.java
 * 
 * FILE NAME        	: CommonMotorcadeDistrictService.java
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
 * FILE    NAME: CommonMotorcadeServeDistrictService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDistrictDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;

/**
 * 公共选择器--车队对应行政区域service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:19:39
 */
public class CommonMotorcadeDistrictService implements
		ICommonMotorcadeDistrictService {
	
	/** The common motorcade district dao. */
	private ICommonMotorcadeDistrictDao commonMotorcadeDistrictDao;
	
	/**
	 * 查询车队对应的行政区域.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:19:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeDistrictService#queryMotorcadeServeDistrictByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity, int, int)
	 */
	@Override
	public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictByCondition(
			MotorcadeServeDistrictEntity entity, int start, int limit) {
		entity.setActive("Y");
		return commonMotorcadeDistrictDao
				.queryMotorcadeServeDistrictByCondition(entity, start, limit);
	}

	/**
	 * 查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:19:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeDistrictService#queryMotorcadeServeDistricByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity)
	 */
	@Override
	public long queryMotorcadeServeDistricByCondition(
			MotorcadeServeDistrictEntity entity) {
		entity.setActive("Y");
		return commonMotorcadeDistrictDao
				.queryMotorcadeServeDistricByCondition(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonMotorcadeDistrictDao the new common motorcade district dao
	 */
	public void setCommonMotorcadeDistrictDao(
			ICommonMotorcadeDistrictDao commonMotorcadeDistrictDao) {
		this.commonMotorcadeDistrictDao = commonMotorcadeDistrictDao;
	}



}
