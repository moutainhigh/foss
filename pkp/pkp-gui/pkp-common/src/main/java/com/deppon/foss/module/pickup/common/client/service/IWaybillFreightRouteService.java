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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IWaybillFreightRouteService.java
 * 
 * FILE NAME        	: IWaybillFreightRouteService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.dto.OrgInfoDto;

/**
 * 
 * GUI查询走货路径相关服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-28 下午2:00:16,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-28 下午2:00:16
 * @since
 * @version
 */
public interface IWaybillFreightRouteService extends IService {

	/**
	 * 
	 * <p>查询配载部门和最终配载部门信息(本地查询)</p> 
	 * @author foss-sunrui
	 * @date 2012-12-28 下午2:00:10
	 * @param pickupCentralized	是否集中接送货
	 * @param orginalOrganizationCode 出发营业部
	 * @param destinationOrganizationCode 到达营业部
	 * @param productCode 运输性质
	 * @return
	 * @see
	 */
	OrgInfoDto queryLodeDepartmentInfoLocal(boolean pickupCentralized, String orginalOrganizationCode,
			String destinationOrganizationCode, String productCode);
	
	/**
	 * 
	 * <p>查询配载部门和最终配载部门信息(在线查询)</p> 
	 * @author foss-sunrui
	 * @date 2012-12-28 下午2:22:50
	 * @param pickupCentralized	是否集中接送货
	 * @param orginalOrganizationCode 出发营业部
	 * @param destinationOrganizationCode 到达营业部
	 * @param productCode 运输性质
	 * @return
	 * @see
	 */
	OrgInfoDto queryLodeDepartmentInfoOnline(boolean pickupCentralized, String orginalOrganizationCode,
			String destinationOrganizationCode, String productCode);

	/**
	 * 
	 * <p>查询营业部的默认外场</p> 
	 * @author foss-sunrui
	 * @date 2012-12-28 下午2:00:13
	 * @param saleCode 营业部
	 * @param productCode 运输性质
	 * @return
	 * @see
	 */
	OutfieldEntity queryDefaultTransDept(String saleCode, String productCode);
	
	/**
	 * 
	 * <p>查询营业部的默认外场</p> 
	 * @author foss-sunrui
	 * @date 2012-12-28 下午2:00:13
	 * @param saleCode 营业部
	 * @param productCode 运输性质
	 * @param isSaleTrans 是否驻地营业部
	 * @return
	 * @see
	 */
	String queryDefaultTransCodeDept(SaleDepartmentEntity saleDepartmentEntity, String productCode, boolean isSaleTrans);
}