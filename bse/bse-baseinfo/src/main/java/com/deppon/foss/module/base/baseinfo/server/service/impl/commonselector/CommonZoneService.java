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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonZoneService.java
 * 
 * FILE NAME        	: CommonZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonSmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonAllZoneDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonBigZoneDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSmallZoneDto;

/**
 * 公共查询选择器--集中接送货大小区service.
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:57:30
 */
public class CommonZoneService implements ICommonZoneService {

	/**
	 * 集中接送货大区Dao
	 */
	private ICommonBigZoneDao commonBigZoneDao;

	/**
	 * 集中接送货小区Dao
	 */
	private ICommonSmallZoneDao commonSmallZoneDao;

	/**
	 * 集中接送货大小区Dao
	 */
	private ICommonAllZoneDao commonAllZoneDao;
	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	

	/**
	 * 根据条件查询接送货大区.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:06:59
	 * @return
	 */
	@Override
	public List<BigZoneEntity> queryBigZoneByCondition(CommonBigZoneDto dto,
			int limit, int start) {
		return commonBigZoneDao.queryBigZoneByCondition(dto, limit, start);
	}

	/**
	 * 根据条件查询接送货大区总条数.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:11:56
	 * @return
	 */
	@Override
	public Long countBigZoneByCodition(CommonBigZoneDto dto) {
		return commonBigZoneDao.queryRecordCount(dto);
	}

	/**
	 * 根据条件查询接送货小区.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:06:59
	 * @return
	 */
	@Override
	public List<SmallZoneEntity> querySmallZoneByCondition(
			CommonSmallZoneDto dto, int limit, int start) {
	//	List<String> codes = new ArrayList<String>();
		//如果传入组织编码，就查询组织下所有子组织
		if(CollectionUtils.isNotEmpty(dto.getParentOrgCodes())){
			List<String> parentOrgCodes = dto.getParentOrgCodes();
			List<OrgAdministrativeInfoEntity> orgLists = new ArrayList<OrgAdministrativeInfoEntity>();
			for(String parentOrgCode : parentOrgCodes){				
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(parentOrgCode);
			orgLists.addAll(orgList);
			}
			if(CollectionUtils.isNotEmpty(orgLists)){
				List<String> subOrgList=new ArrayList<String>();
				for(OrgAdministrativeInfoEntity orgEntity : orgLists){		
					subOrgList.add(orgEntity.getCode());
				}
				dto.setOrgCodes(subOrgList);
			}else{
				dto.setOrgCodes(dto.getParentOrgCodes());
			}							
		}		
		return commonSmallZoneDao.querySmallZoneByCondition(dto, limit, start);
	}

	/**
	 * 根据条件查询接送货小区总条数.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:09:23
	 * @return
	 */
	@Override
	public Long countSmallZoneByCodition(CommonSmallZoneDto dto) {
	//	List<String> codes = new ArrayList<String>();
		//如果传入组织编码，就查询组织下所有子组织
		if(CollectionUtils.isNotEmpty(dto.getParentOrgCodes())){
			List<String> parentOrgCodes = dto.getParentOrgCodes();
			List<OrgAdministrativeInfoEntity> orgLists = new ArrayList<OrgAdministrativeInfoEntity>();
			for(String parentOrgCode : parentOrgCodes){				
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(parentOrgCode);
			orgLists.addAll(orgList);
			}
			if(CollectionUtils.isNotEmpty(orgLists)){
				List<String> subOrgList=new ArrayList<String>();
				for(OrgAdministrativeInfoEntity orgEntity : orgLists){		
					subOrgList.add(orgEntity.getCode());
				}
				dto.setOrgCodes(subOrgList);
			}else{
				dto.setOrgCodes(dto.getParentOrgCodes());
			}							
		}		
		return commonSmallZoneDao.queryRecordCount(dto);
	}

	

	/**
	 * 根据条件查询接送货大小区.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:06:59
	 * @return
	 */
	@Override
	public List<CommonAllZoneDto> queryAllZoneByCondition(CommonAllZoneDto dto,
			int limit, int start) {
		return commonAllZoneDao.queryAllZoneByCondition(dto, limit, start);
	}

	/**
	 * 根据条件查询接送货大小区总条数.
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-12 上午10:09:23
	 * @return
	 */
	@Override
	public Long countAllZoneByCodition(CommonAllZoneDto dto) {
		return commonAllZoneDao.queryRecordCount(dto);
	}

	public ICommonBigZoneDao getCommonBigZoneDao() {
		return commonBigZoneDao;
	}

	public void setCommonBigZoneDao(ICommonBigZoneDao commonBigZoneDao) {
		this.commonBigZoneDao = commonBigZoneDao;
	}

	public ICommonSmallZoneDao getCommonSmallZoneDao() {
		return commonSmallZoneDao;
	}

	public void setCommonSmallZoneDao(ICommonSmallZoneDao commonSmallZoneDao) {
		this.commonSmallZoneDao = commonSmallZoneDao;
	}

	
	public ICommonAllZoneDao getCommonAllZoneDao() {
		return commonAllZoneDao;
	}

	
	public void setCommonAllZoneDao(ICommonAllZoneDao commonAllZoneDao) {
		this.commonAllZoneDao = commonAllZoneDao;
	}
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}
