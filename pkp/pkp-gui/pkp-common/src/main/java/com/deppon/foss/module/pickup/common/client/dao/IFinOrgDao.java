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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IFinOrgDao.java
 * 
 * FILE NAME        	: IFinOrgDao.java
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
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;

/**
 * @author 105089-foss-yangtong
 *
 */
/**
 * 
 * 走货路径数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-yangtong,date:2012-10-16 下午7:53:59, </p>
 * @author foss-yangtong
 * @date 2012-10-16 下午7:53:59
 * @since
 * @version
 */
public interface IFinOrgDao {
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addFinancialOrganizations(FinancialOrganizationsEntity financialOrganizations);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateFinancialOrganizations(FinancialOrganizationsEntity financialOrganizations);

	/**
	 * 删除 
	 * @param entity
	 */
	void delete(FinancialOrganizationsEntity entity);

}