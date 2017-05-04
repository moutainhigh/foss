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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IFinOrgService.java
 * 
 * FILE NAME        	: IFinOrgService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;

/** 
 * 财务组织 数据服务接口
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-7 下午8:44:27
 * @param clientInfo
 * @return 
 */
public interface IFinOrgService {
	
	/**
	 * 插条记录
	 */
	void addFreightRoute(FinancialOrganizationsEntity financialOrganizations);
	
	/**
	 * 更新条记录
	 */
	void updateFreightRoute(FinancialOrganizationsEntity financialOrganizations);
	
	/**
	 * 
	 * 新增或更新记录
	 */
	void saveOrUpdate(FinancialOrganizationsEntity financialOrganizations);
	/**
	 * 删除 
	 * @param entity
	 */
	void delete(FinancialOrganizationsEntity entity);
}