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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonSaleDepartmentService.java
 * 
 * FILE NAME        	: CommonSaleDepartmentService.java
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
 * FILE    NAME: CommonSaleDepartmentService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 公共查询--营业部查询service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:12:48
 */
public class CommonSaleDepartmentService implements
		ICommonSaleDepartmentService {
	
	/** The common sale department dao. */
	private ICommonSaleDepartmentDao commonSaleDepartmentDao;

	/**
	 * 查询营业部.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:12:48
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService#querySaleDepartmentExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity,
	 * int, int)
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		entity.setActive("Y");
		return commonSaleDepartmentDao.querySaleDepartmentExactByEntity(entity,
				start, limit);
	}

	/**
	 * 查询营业部条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:12:48
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService#querySaleDepartmentExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	public long querySaleDepartmentExactByEntityCount(
			SaleDepartmentEntity entity) {
		entity.setActive("Y");
		return commonSaleDepartmentDao
				.querySaleDepartmentExactByEntityCount(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonSaleDepartmentDao the new common sale department dao
	 */
	public void setCommonSaleDepartmentDao(
			ICommonSaleDepartmentDao commonSaleDepartmentDao) {
		this.commonSaleDepartmentDao = commonSaleDepartmentDao;
	}

	/**
     * 精确查询
     * 查询不包括虚拟营业部和快递点部的营业部
     *
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
	@Override
	public List<SaleDepartmentEntity> querySaleDeptFilterExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		List<SaleDepartmentEntity> result = commonSaleDepartmentDao.querySaleDeptFilterExactByEntity(entity, start, limit);
		return result;
	}

	/**
     * 精确查询-查询总条数，用于分页
     * 查询不包括虚拟营业部和快递点部的营业部总条数
     * 
     * @author 313353-foss-qiupeng
     * @date 2016-10-9 上午9:20:31
     */
	@Override
	public long querySaleDeptFilterExactByEntityCount(
			SaleDepartmentEntity entity) {
		return commonSaleDepartmentDao.querySaleDeptFilterExactByEntityCount(entity);
	}

}
