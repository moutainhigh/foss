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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonAirportService.java
 * 
 * FILE NAME        	: CommonAirportService.java
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
 * FILE    NAME: CommonAirportService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirportDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirportService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirportDetailVo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 公共查询选择器--机场service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:51:00
 */
public class CommonAirportService implements ICommonAirportService {
	
	/** The common airport dao. */
	private ICommonAirportDao commonAirportDao;
	// 区县service
	/** The area address service. */
	private IAreaAddressService areaAddressService;

	/**
	 * 查询机场.
	 *
	 * @param airport the airport
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:51:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirportService#queryAirportListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity,
	 * int, int)
	 */
	@Override
	public List<AirportDetailVo> queryAirportListByCondition(
			AirportEntity airport, int start, int limit) {
		List<AirportEntity> airportList = commonAirportDao
				.queryAirportListByCondition(airport, start, limit);
		List<AirportDetailVo> vos = new ArrayList<AirportDetailVo>();
		if (CollectionUtils.isNotEmpty(airportList)) {
			AirportDetailVo vo = null;
			for (int i = 0; i < airportList.size(); i++) {
				vo = new AirportDetailVo();
				BeanUtils.copyProperties(airportList.get(i), vo);
				// 设置市名称
				AdministrativeRegionsEntity city = areaAddressService
						.queryRegionByCode(airportList.get(i).getCityCode());
				if (null != city) {
					vo.setCityName(city.getName());
				}
				// 设置区县名称
				AdministrativeRegionsEntity county = areaAddressService
						.queryRegionByCode(airportList.get(i).getCountyCode());
				if (null != county) {
					vo.setCountyName(county.getName());
				}
				vos.add(vo);
			}
		}
		return vos;
	}

	/**
	 * 查询机场总数.
	 *
	 * @param airport the airport
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:51:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirportService#countAirportListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
	 */
	@Override
	public Long countAirportListByCondition(AirportEntity airport) {
		return commonAirportDao.countAirportListByCondition(airport);
	}

	/**
	 * setter.
	 *
	 * @param commonAirportDao the new common airport dao
	 */
	public void setCommonAirportDao(ICommonAirportDao commonAirportDao) {
		this.commonAirportDao = commonAirportDao;
	}

	/**
	 * setter.
	 *
	 * @param areaAddressService the new area address service
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

}
