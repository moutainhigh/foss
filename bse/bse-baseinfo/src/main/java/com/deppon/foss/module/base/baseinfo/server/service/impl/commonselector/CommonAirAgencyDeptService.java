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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonAirAgencyDeptService.java
 * 
 * FILE NAME        	: CommonAirAgencyDeptService.java
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
 * FILE    NAME: CommonAirAgencyDeptService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 公共选择器--空运代理网点查询service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 下午1:59:34
 */
public class CommonAirAgencyDeptService implements ICommonAirAgencyDeptService {
	
	/** The common agency dept dao. */
	private ICommonAgencyDeptDao commonAgencyDeptDao;
	
	/**
	 *  行政区域 Service接口
	 */
	 private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 空运代理网点查询.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午1:59:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyDeptService#queryAgencyDeptsByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity,
	 * int, int)
	 */
	@Override
	public List<OuterBranchEntity> queryAgencyDeptsByCondition(
			OuterBranchEntity entity, int limit, int start) {
		if (null == entity) {
			entity = new OuterBranchEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
		List<OuterBranchEntity> outerBranchList  = commonAgencyDeptDao.queryAgencyDeptsByCondition(entity, limit,start);
		if(CollectionUtils.isNotEmpty(outerBranchList)){
    		for(OuterBranchEntity outEntity : outerBranchList){
    			outEntity.setProvName(administrativeRegionsService.gainDistrictNameByCode(outEntity.getProvCode())); 
    			outEntity.setCityName(administrativeRegionsService.gainDistrictNameByCode(outEntity.getCityCode()));
    			outEntity.setCountyName(administrativeRegionsService.gainDistrictNameByCode(outEntity.getCountyCode()));
    		}
		}
		return outerBranchList;
	}

	/**
	 * 查询空运代理网点总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午1:59:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyDeptService#countRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
	 */
	@Override
	public Long countRecordByCondition(OuterBranchEntity entity) {
		if (null == entity) {
			entity = new OuterBranchEntity();
		}
		entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
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

	
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
}
