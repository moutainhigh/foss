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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonLeasedDriverService.java
 * 
 * FILE NAME        	: CommonLeasedDriverService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl
 * FILE    NAME: CommonLeasedDriverService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;

/**
 * 公共选择器--外请司机查询service.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午9:02:34
 */
public class CommonLeasedDriverService implements ICommonLeasedDriverService {
	
	/** The leased driver dao. */
	private ILeasedDriverDao leasedDriverDao;
	
	/**
	 * 根据条件查询外请司机.
	 *
	 * @param leasedDriver the leased driver
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:04:17
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedDriverService#queryLeasedDriverListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity, int, int)
	 */
	@Override
	public List<LeasedDriverEntity> queryLeasedDriverListByCondition(
			LeasedDriverEntity leasedDriver, int start, int limit) {
		return leasedDriverDao.queryLeasedDriverListBySelectiveCondition(leasedDriver, start, limit);
	}

	/**
	 * 根据条件查询符合条件的外请司机总条数.
	 *
	 * @param leasedDriver the leased driver
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:04:17
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedDriverService#countLeasedDriverListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
	 */
	@Override
	public long countLeasedDriverListByCondition(
			LeasedDriverEntity leasedDriver) {
		return leasedDriverDao.queryLeasedDriverRecordCountBySelectiveCondition(leasedDriver);
	}

	/**
	 * Sets the leased driver dao.
	 *
	 * @param leasedDriverDao the new leased driver dao
	 */
	public void setLeasedDriverDao(ILeasedDriverDao leasedDriverDao) {
		this.leasedDriverDao = leasedDriverDao;
	}

}
