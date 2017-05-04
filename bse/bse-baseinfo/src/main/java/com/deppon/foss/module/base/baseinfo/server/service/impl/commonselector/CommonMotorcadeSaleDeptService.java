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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonMotorcadeSaleDeptService.java
 * 
 * FILE NAME        	: CommonMotorcadeSaleDeptService.java
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
 * FILE    NAME: CommonMotorcadeSaleDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeSaleDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSaleDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共查询--车队对应营业部service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:15:22
 */
public class CommonMotorcadeSaleDeptService implements
		ICommonMotorcadeSaleDeptService {
	
	/** The common motorcade sale dept dao. */
	private ICommonMotorcadeSaleDeptDao commonMotorcadeSaleDeptDao;

	/**
	 * 查询车队对应营业部.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:15:22
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSaleDeptService#queryMotorcadeSalesDeptByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity,
	 * int, int)
	 */
	@Override
	public List<SalesMotorcadeEntity> queryMotorcadeSalesDeptByCondition(
			SalesMotorcadeEntity entity, int start, int limit) {
		entity.setActive(FossConstants.ACTIVE);
		return commonMotorcadeSaleDeptDao
				.queryMotorcadeSalesDeptByCondition(entity, start, limit);
	}

	/**
	 * 查询总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:15:22
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSaleDeptService#queryMotorcadeSalesDeptByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeSalesDeptByConditionCount(
			SalesMotorcadeEntity entity) {
		entity.setActive("Y");
		return commonMotorcadeSaleDeptDao
				.queryMotorcadeSalesDeptByConditionCount(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonMotorcadeSaleDeptDao the new common motorcade sale dept dao
	 */
	public void setCommonMotorcadeSaleDeptDao(
			ICommonMotorcadeSaleDeptDao commonMotorcadeSaleDeptDao) {
		this.commonMotorcadeSaleDeptDao = commonMotorcadeSaleDeptDao;
	}


}
