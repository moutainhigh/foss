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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonAirAgencyCompanyService.java
 * 
 * FILE NAME        	: CommonAirAgencyCompanyService.java
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
 * FILE    NAME: CommonAirAgencyCompanyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 公共查询组件--查询空运代理公司.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午9:37:42
 */
public class CommonAirAgencyCompanyService implements
		ICommonAirAgencyCompanyService {
	
	/** The common agency company dao. */
	ICommonAgencyCompanyDao commonAgencyCompanyDao;

	/**
	 * 查询空运代理公司.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午9:37:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyCompanyService#queryAirAgencyCompanysByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity,
	 * int, int)
	 */
	@Override
	public List<BusinessPartnerEntity> queryAirAgencyCompanysByCondtion(
			BusinessPartnerEntity entity, int limit, int start) {
		if (null == entity) {
			entity = new BusinessPartnerEntity();
		}
		entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
		return commonAgencyCompanyDao.queryAgencyCompanysByCondtion(entity,
				limit, start);
	}

	/**
	 * 查询空运代理公司总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午9:37:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgencyCompanyService#countRecordByCondtion(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
	 */
	@Override
	public Long countRecordByCondtion(BusinessPartnerEntity entity) {
		if (null == entity) {
			entity = new BusinessPartnerEntity();
		}
		entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_KY);
		return commonAgencyCompanyDao.countRecordByCondition(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonAgencyCompanyDao the new common agency company dao
	 */
	public void setCommonAgencyCompanyDao(
			ICommonAgencyCompanyDao commonAgencyCompanyDao) {
		this.commonAgencyCompanyDao = commonAgencyCompanyDao;
	}

}
