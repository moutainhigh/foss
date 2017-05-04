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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonSalesDeptAccountantAction.java
 * 
 * FILE NAME        	: CommonSalesDeptAccountantAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonAirAgencyCompanyAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSalesDeptAccountantService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;

/**
 * 公共选择器--区域会计ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午10:32:34
 */
public class CommonSalesDeptAccountantAction extends AbstractAction implements
		IQueryAction {
	// service
	/** The common sales dept accountant service. */
	private ICommonSalesDeptAccountantService  commonSalesDeptAccountantService;
	// search condition
	/** The accountant entity. */
	private SalesDeptAccountantEntity accountantEntity;
	// result
	/** The accountant entities. */
	private List<SalesDeptAccountantEntity> accountantEntities;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -3383631039625626863L;

	/**
	 * 空运代理公司查询.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午10:32:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		accountantEntities = commonSalesDeptAccountantService
				.querySalesDeptAccountantGroup(accountantEntity, limit,
						start);
		setTotalCount(commonSalesDeptAccountantService
				.queryRecordCount(accountantEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the accountant entities
	 */
	public List<SalesDeptAccountantEntity> getAccountantEntities() {
		return accountantEntities;
	}

	/**
	 * setter.
	 *
	 * @param commonSalesDeptAccountantService the new common sales dept accountant service
	 */
	public void setCommonSalesDeptAccountantService(
			ICommonSalesDeptAccountantService commonSalesDeptAccountantService) {
		this.commonSalesDeptAccountantService = commonSalesDeptAccountantService;
	}

	/**
	 * setter.
	 *
	 * @param accountantEntity the new accountant entity
	 */
	public void setAccountantEntity(SalesDeptAccountantEntity accountantEntity) {
		this.accountantEntity = accountantEntity;
	}

	/**
	 * getter.
	 *
	 * @return the accountant entity
	 */
	public SalesDeptAccountantEntity getAccountantEntity() {
		return accountantEntity;
	}


}