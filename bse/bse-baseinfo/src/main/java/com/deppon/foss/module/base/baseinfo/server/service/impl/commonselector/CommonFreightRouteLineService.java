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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonFreightRouteLineService.java
 * 
 * FILE NAME        	: CommonFreightRouteLineService.java
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
 * FILE    NAME: CommonFreightRouteLineService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonFreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonReightRouteLineCondition;

/**
 * 公共查询选择器--线路站点查询service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:52:27
 */
public class CommonFreightRouteLineService implements
		ICommonFreightRouteLineService {
	
	/** The common freight route line dao. */
	private ICommonFreightRouteLineDao commonFreightRouteLineDao;

	/**
	 * 线路站点查询.
	 *
	 * @param freightRouteLine the freight route line
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:52:27
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFreightRouteLineService#queryFreightRouteLinesByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto,
	 * int, int)
	 */
	@Override
	public List<CommonFreightRouteLineDto> queryFreightRouteLinesByCondtion(
			CommonReightRouteLineCondition freightRouteLine, int start, int limit) {
		if (null==freightRouteLine) {
			freightRouteLine=new CommonReightRouteLineCondition();
		}
		return commonFreightRouteLineDao.queryFreightRouteLinesByCondtion(
				freightRouteLine, start, limit);
	}

	/**
	 * 线路站点总数查询.
	 *
	 * @param freightRouteLine the freight route line
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:52:27
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonFreightRouteLineService#countFreightRouteLinesByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto)
	 */
	@Override
	public Long countFreightRouteLinesByCondtion(
			CommonReightRouteLineCondition freightRouteLine) {
		return commonFreightRouteLineDao
				.countFreightRouteLinesByCondtion(freightRouteLine);
	}

	/**
	 * setter.
	 *
	 * @param commonFreightRouteLineDao the new common freight route line dao
	 */
	public void setCommonFreightRouteLineDao(
			ICommonFreightRouteLineDao commonFreightRouteLineDao) {
		this.commonFreightRouteLineDao = commonFreightRouteLineDao;
	}

}
