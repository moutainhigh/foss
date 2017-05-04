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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/AdministrativeRegionCacheProvider.java
 * 
 * FILE NAME        	: AdministrativeRegionCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CityCacheProvider.java
 * @package com.deppon.crm.module.common.server.cache 
 * @author 078823-foss-panGuangjun
 * @version 0.1 2012-3-15
 */
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.cache.provider.IBatchCacheProvider;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * <p>
 * Description:城市、省份缓存<br />
 * </p>
 * 
 * @title CityCacheProvider.java
 * @package com.deppon.crm.module.common.server.cache
 * @author 078823-foss-panGuangjun
 * @version 0.1 2012-3-15
 */
public class AdministrativeRegionCacheProvider implements
		IBatchCacheProvider<String, Object> {
	// 注入省份dao
	private IAdministrativeRegionsDao administrativeRegionsDao;

	@Override
	public Date getLastModifyTime() {
		return administrativeRegionsDao.getLastModifyTime();
	}

	/**
	 * @description 设置缓存放入哪些数据
	 * @author 078823-foss-panGuangjun
	 * @2012-3-23
	 * @return 缓存的map
	 */
	public Map<String, Object> get() {
		List<AdministrativeRegionsEntity> provinceList = new ArrayList<AdministrativeRegionsEntity>();
		List<AdministrativeRegionsEntity> cityList = new ArrayList<AdministrativeRegionsEntity>();
		List<AdministrativeRegionsEntity> nationList = new ArrayList<AdministrativeRegionsEntity>();
		List<AdministrativeRegionsEntity> countyList = new ArrayList<AdministrativeRegionsEntity>();
		Map<String, Object> map = new HashMap<String, Object>();

		AdministrativeRegionsEntity searchEntity = new AdministrativeRegionsEntity();
		searchEntity.setActive(FossConstants.ACTIVE);
		List<AdministrativeRegionsEntity> regionList = administrativeRegionsDao
				.queryAdministrativeRegionsExactByEntity(searchEntity, 0,
						Integer.MAX_VALUE);

		if (CollectionUtils.isNotEmpty(regionList)) {
			for (int i = 0; i < regionList.size(); i++) {
				if (DictionaryValueConstants.DISTRICT_NATION.equals(regionList
						.get(i).getDegree())) {
					nationList.add(regionList.get(i));
				} else if (DictionaryValueConstants.DISTRICT_PROVINCE
						.equals(regionList.get(i).getDegree())) {
					provinceList.add(regionList.get(i));
				} else if (DictionaryValueConstants.DISTRICT_CITY
						.equals(regionList.get(i).getDegree())) {
					cityList.add(regionList.get(i));
				} else if (DictionaryValueConstants.DISTRICT_COUNTY
						.equals(regionList.get(i).getDegree())) {
					countyList.add(regionList.get(i));
				}
			}
		}
		map.put("region", regionList);
		return map;
	}

	public IAdministrativeRegionsDao getAdministrativeRegionsDao() {
		return administrativeRegionsDao;
	}

	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}

}
