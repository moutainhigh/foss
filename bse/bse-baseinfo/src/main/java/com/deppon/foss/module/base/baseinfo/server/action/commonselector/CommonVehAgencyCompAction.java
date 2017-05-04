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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonVehAgencyCompAction.java
 * 
 * FILE NAME        	: CommonVehAgencyCompAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonVehAgencyCompAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehAgencyCompService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;

/**
 * 公共选择器--代理公司查询ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午10:22:37
 */
public class CommonVehAgencyCompAction extends AbstractAction implements
		IQueryAction {
	// service
	/** The common veh agency comp service. */
	private ICommonVehAgencyCompService commonVehAgencyCompService;
	// search condition
	/** The business partner entity. */
	private BusinessPartnerEntity businessPartnerEntity;
	// result
	/** The business partner entities. */
	private List<BusinessPartnerEntity> businessPartnerEntities;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 8754495125713312283L;

	/**
	 * 查询代理公司.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午10:22:52
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		businessPartnerEntities = commonVehAgencyCompService
				.queryVehicleAgencyCompByCondtion(businessPartnerEntity, limit,
						start);
		setTotalCount(commonVehAgencyCompService
				.countRecordByCondition(businessPartnerEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the business partner entities
	 */
	public List<BusinessPartnerEntity> getBusinessPartnerEntities() {
		return businessPartnerEntities;
	}

	/**
	 * setter.
	 *
	 * @param commonVehAgencyCompService the new common veh agency comp service
	 */
	public void setCommonVehAgencyCompService(
			ICommonVehAgencyCompService commonVehAgencyCompService) {
		this.commonVehAgencyCompService = commonVehAgencyCompService;
	}

	/**
	 * setter.
	 *
	 * @param businessPartnerEntity the new business partner entity
	 */
	public void setBusinessPartnerEntity(
			BusinessPartnerEntity businessPartnerEntity) {
		this.businessPartnerEntity = businessPartnerEntity;
	}

	/**
	 * getter.
	 *
	 * @return the business partner entity
	 */
	public BusinessPartnerEntity getBusinessPartnerEntity() {
		return businessPartnerEntity;
	}

}
