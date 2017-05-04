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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonVehAgencyDeptService.java
 * 
 * FILE NAME        	: CommonVehAgencyDeptService.java
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
 * FILE    NAME: CommonVehAgencyDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 公共选择器--偏线代理网点查询service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 下午2:03:10
 */
public class CommonVehAgencyDeptService implements ICommonVehAgencyDeptService {
	
	/** The common agency dept dao. */
	private ICommonAgencyDeptDao commonAgencyDeptDao;
	private IAreaAddressService areaAddressService;
	
	/**
	 * 偏线代理网点查询.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午2:03:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehAgencyDeptService#queryAgencyDeptsByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
	 */
	@Override
	public List<OuterBranchEntity> queryAgencyDeptsByCondition(
			OuterBranchEntity entity, int limit, int start) {
		if (null == entity) {
			entity = new OuterBranchEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
		List<OuterBranchEntity> outerBranchEntitys = commonAgencyDeptDao.queryAgencyDeptsByCondition(entity, limit,
				start);
		for(int i=0;i<outerBranchEntitys.size();i++){
			// 设置省名称
			if(StringUtils.isBlank(outerBranchEntitys.get(i).getProvName())){
			AdministrativeRegionsEntity pro = areaAddressService.queryRegionByCode(
					outerBranchEntitys.get(i).getProvCode());
			if (null!=pro) {
				outerBranchEntitys.get(i).setProvName(pro.getName());
			}
			}
			// 设置城市名称
			if(StringUtils.isBlank(outerBranchEntitys.get(i).getCityName())){
			AdministrativeRegionsEntity city =  areaAddressService.queryRegionByCode(
					outerBranchEntitys.get(i).getCityCode());
			if (null!=city) {
				outerBranchEntitys.get(i).setCityName(city.getName());
				}
			}
			// 设置区县名称
			if(StringUtils.isBlank(outerBranchEntitys.get(i).getCountyName())){
				AdministrativeRegionsEntity county =  areaAddressService.queryRegionByCode(
					outerBranchEntitys.get(i).getCountyCode());
				if (null!=county) {
					outerBranchEntitys.get(i).setCountyName(county.getName());
						}
				}
		}
		return outerBranchEntitys;
	}

	/**
	 * 查询偏线代理网点总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午2:03:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehAgencyDeptService#countRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
	 */
	@Override
	public Long countRecordByCondition(OuterBranchEntity entity) {
		if (null == entity) {
			entity = new OuterBranchEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
		return commonAgencyDeptDao.countRecordByCondition(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonAgencyDeptDao the new common agency dept dao
	 */
	public void setCommonAgencyDeptDao(ICommonAgencyDeptDao commonAgencyDeptDao) {
		this.commonAgencyDeptDao = commonAgencyDeptDao;
	}

	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

}
