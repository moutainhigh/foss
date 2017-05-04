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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonMotorcadeSalesAreaService.java
 * 
 * FILE NAME        	: CommonMotorcadeSalesAreaService.java
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
 * FILE    NAME: CommonMotorcadeSalesAreaService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSalesAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSalesAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;

/**
 * 公共选择器--车队对应营业区service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:17:16
 */
public class CommonMotorcadeSalesAreaService implements
		ICommonMotorcadeSalesAreaService {
	
	/** The common motorcade sales area dao. */
	private ICommonMotorcadeSalesAreaDao commonMotorcadeSalesAreaDao;
	
	/**
	 * 查询车队对应的营业区.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:17:16
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSalesAreaService#queryMotorcadeServeSalesAreaByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity, int, int)
	 */
	@Override
	public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByCondtion(
			MotorcadeServeSalesAreaEntity entity, int start, int limit) {
		entity.setActive("Y");
		return commonMotorcadeSalesAreaDao.queryMotorcadeServeSalesAreaByCondtion(entity, start, limit);
	}

	/**
	 * 查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:17:16
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSalesAreaService#queryMotorcadeServeSalesAreaByCondtionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity)
	 */
	@Override
	public long queryMotorcadeServeSalesAreaByCondtionCount(
			MotorcadeServeSalesAreaEntity entity) {
		entity.setActive("Y");
		return commonMotorcadeSalesAreaDao
				.queryMotorcadeServeSalesAreaByCondtionCount(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonMotorcadeSalesAreaDao the new common motorcade sales area dao
	 */
	public void setCommonMotorcadeSalesAreaDao(
			ICommonMotorcadeSalesAreaDao commonMotorcadeSalesAreaDao) {
		this.commonMotorcadeSalesAreaDao = commonMotorcadeSalesAreaDao;
	}


}
