/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/FinOrgService.java
 * 
 * FILE NAME        	: FinOrgService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.pickup.common.client.dao.IFinOrgDao;
import com.deppon.foss.module.pickup.common.client.service.IFinOrgService;
import com.google.inject.Inject;

/** 
 * 财务组织 数据服务类
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-7 下午8:44:27
 * @param clientInfo
 * @return 
 */
public class FinOrgService implements IFinOrgService {
	
	@Inject
	IFinOrgDao finOrgDao;

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional 
	@Override
	public void addFreightRoute(
			FinancialOrganizationsEntity financialOrganizations) {
		finOrgDao.addFinancialOrganizations(financialOrganizations);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateFreightRoute(
			FinancialOrganizationsEntity financialOrganizations) {
		finOrgDao.updateFinancialOrganizations(financialOrganizations);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(FinancialOrganizationsEntity financialOrganizations) {
		if(!finOrgDao.addFinancialOrganizations(financialOrganizations)){
			finOrgDao.updateFinancialOrganizations(financialOrganizations); 
		}

	}

	/**
	 * 删除 
	 * @param entity
	 */
	public void delete(FinancialOrganizationsEntity entity) {
		finOrgDao.delete(entity);
	}

}