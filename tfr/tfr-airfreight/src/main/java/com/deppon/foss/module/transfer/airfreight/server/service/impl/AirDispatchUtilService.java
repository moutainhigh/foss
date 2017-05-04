/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirDispatchUtilService.java
 *  
 *  FILE NAME          :AirDispatchUtilService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;

/**
 * 查询空运总调的公共service
 * @author foss-liuxue(for IBM)
 * @date 2013-1-30 上午9:35:05
 */
public class AirDispatchUtilService implements IAirDispatchUtilService {
	
	/**
	 * 线路service，用于查询空运总调
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 查询空运总调
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-30 上午9:43:31
	 */
	@Override
	public OrgAdministrativeInfoEntity queryAirDispatchDept(String currentDeptCode){
		//获取空运总调
		List<String> list = new ArrayList<String>();
		list.add(BizTypeConstants.ORG_AIR_DISPATCH);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentDeptCode, list);
		if(null == orgAdministrativeInfoEntity){
			IOrgAdministrativeInfoService orgAdministrativeInfoService = (IOrgAdministrativeInfoService) WebApplicationContextHolder
					.getWebApplicationContext().getBean(
							"orgAdministrativeInfoService");
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentDeptCode);
		}
		return orgAdministrativeInfoEntity;
	}
	
}