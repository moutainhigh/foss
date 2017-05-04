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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonPlatformService.java
 * 
 * FILE NAME        	: CommonPlatformService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl
 * FILE    NAME: CommonPlatformService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPlatformDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPlatformService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;

/**
 * 公共选择器--月台查询service.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午9:30:38
 */
public class CommonPlatformService implements ICommonPlatformService {
	
	/** The platform dao. */
	//private IPlatformDao platformDao;
	private ICommonPlatformDao commonPlatformDao;
	private OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 根据条件查询月台总数.
	 *
	 * @param plat the plat
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:30:38
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPlatformService#countPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
	 */
	@Override
	public long countPlatformListByCondition(PlatformEntity plat) {
		if(null == plat){
			return 0;
		}
		List<String> orgCodeList = null;
		if(StringUtils.isNotBlank(plat.getOrganizationCode())){
			orgCodeList = new ArrayList<String>();
			orgCodeList.add(plat.getOrganizationCode());
			List<String> bizTypeList = new ArrayList<String>();
			bizTypeList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(plat.getOrganizationCode(),bizTypeList);
			if(null != orgInfo){
				orgCodeList.add(orgInfo.getCode());
			}
			plat.setOrgCodeList(orgCodeList);
		}
		return commonPlatformDao.countPlatformListByCondition(plat);
	}

	/**
	 * 根据条件查询月台信息.
	 *
	 * @param plat the plat
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:30:38
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPlatformService#queryPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity, int, int)
	 */
	@Override
	public List<PlatformEntity> queryPlatformListByCondition(
			PlatformEntity plat, int start, int limit) {
		if(null == plat){
			return null;
		}
		List<String> orgCodeList = null;
		if(StringUtils.isNotBlank(plat.getOrganizationCode())){
			orgCodeList = new ArrayList<String>();
			orgCodeList.add(plat.getOrganizationCode());
			List<String> bizTypeList = new ArrayList<String>();
			bizTypeList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(plat.getOrganizationCode(),bizTypeList);
			if(null != orgInfo){
				orgCodeList.add(orgInfo.getCode());
			}
			plat.setOrgCodeList(orgCodeList);
		}
		
		return commonPlatformDao.queryPlatformListByCondition(plat, start, limit);
	}

	/**
	 * Sets the platform dao.
	 *
	 * @param platformDao the new platform dao
	 */
	/*public void setPlatformDao(IPlatformDao platformDao) {
		this.platformDao = platformDao;
	}*/

	public void setCommonPlatformDao(ICommonPlatformDao commonPlatformDao) {
		this.commonPlatformDao = commonPlatformDao;
	}

	
	public OrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	
	public void setOrgAdministrativeInfoComplexService(
			OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

}
