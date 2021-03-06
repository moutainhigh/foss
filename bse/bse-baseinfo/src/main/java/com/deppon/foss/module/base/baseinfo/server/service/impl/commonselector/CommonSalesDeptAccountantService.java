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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonSalesDeptAccountantService.java
 * 
 * FILE NAME        	: CommonSalesDeptAccountantService.java
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
 * FILE    NAME: CommonSalesDeptAccountantService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSalesDeptAccountantService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;

/**
 * 公共查询选择器--区域会计查询service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:52:44
 */
public class CommonSalesDeptAccountantService implements
		ICommonSalesDeptAccountantService {
	
	/** The common sales dept accountant dao. */
	private ICommonSalesDeptAccountantDao commonSalesDeptAccountantDao;
	
	/**
	 * 区域会计总数查询.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSalesDeptAccountantService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
	 */
	@Override
	public Long queryRecordCount(SalesDeptAccountantEntity entity) {
		return commonSalesDeptAccountantDao.queryRecordCount(entity);
	}

	/**
	 * 区域会计查询.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSalesDeptAccountantService#querySalesDeptAccountantGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity, int, int)
	 */
	@Override
	public List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
			SalesDeptAccountantEntity entity, int start, int limit) {
		return commonSalesDeptAccountantDao.querySalesDeptAccountantGroup(entity, start,limit);
	}

	/**
	 * setter.
	 *
	 * @param commonSalesDeptAccountantDao the new common sales dept accountant dao
	 */
	public void setCommonSalesDeptAccountantDao(
			ICommonSalesDeptAccountantDao commonSalesDeptAccountantDao) {
		this.commonSalesDeptAccountantDao = commonSalesDeptAccountantDao;
	}

}
