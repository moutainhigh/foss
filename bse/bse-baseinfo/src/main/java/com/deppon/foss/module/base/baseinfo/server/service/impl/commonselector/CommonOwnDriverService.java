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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonOwnDriverService.java
 * 
 * FILE NAME        	: CommonOwnDriverService.java
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
 * FILE    NAME: CommonOwnDriverService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwnDriverVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共选择器--公司车辆查询service.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:54:10
 */
public class CommonOwnDriverService implements ICommonOwnDriverService {
	
	/** The common own driver dao. */
	private ICommonOwnDriverDao commonOwnDriverDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IOrgAdministrativeInfoService OrgAdministrativeInfoService;
	
	/**
	 * 根据条件查询车辆信息.
	 *
	 * @param ownDriver the own driver
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:54:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService#queryOwnDriverByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)
	 */
	@Override
	public List<OwnDriverEntity> queryOwnDriverByCondition(
			OwnDriverEntity ownDriver, int offset, int limit) {
		if (null == ownDriver ) {
			return null;
		}	
		if (StringUtil.isNotBlank(ownDriver.getParentOrgCode())) {
			if (StringUtils.equals(FossConstants.YES, ownDriver.getWaybillFlag())) {
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(ownDriver.getParentOrgCode(),ownDriver.getFleetType());
				ownDriver.setSubOrgCodeList(orgList);
			} else {
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(ownDriver
								.getParentOrgCode());
				if (CollectionUtils.isNotEmpty(orgList)) {
					List<String> subOrgCodeList = new ArrayList<String>();
					for (OrgAdministrativeInfoEntity orgEntity : orgList) {
						subOrgCodeList.add(orgEntity.getCode());
					}
					ownDriver.setSubOrgCodeList(subOrgCodeList);
				}
			}
			if(CollectionUtils.isEmpty(ownDriver.getSubOrgCodeList())){
				List<String> parentOrgCodeList= new ArrayList<String>();
				parentOrgCodeList.add(ownDriver.getParentOrgCode());
				ownDriver.setSubOrgCodeList(parentOrgCodeList);
			}
		}
		List<OwnDriverEntity>  ownDriverEntitys = commonOwnDriverDao.queryOwnDriverListBySelectiveCondition(ownDriver, offset, limit);
		List<OwnDriverEntity> ownDriverEntityList = new ArrayList<OwnDriverEntity>();
		for(OwnDriverEntity ownDriverEntity : ownDriverEntitys){
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = OrgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(ownDriverEntity.getOrgId());
			/**
			 * 2015.5.4 修复的以前的bug  如果orgid为空查询出来的orgAdministrativeInfoEntity为空 会出现空指针异常
			 * 189284
			 */
			if(orgAdministrativeInfoEntity!=null&&StringUtils.isNotBlank(orgAdministrativeInfoEntity.getName())){
			ownDriverEntity.setOrgName(orgAdministrativeInfoEntity.getName());
			ownDriverEntityList.add(ownDriverEntity);
			}
		}
		return  ownDriverEntityList;
	}

	/**
	 * 根据条件查询总条数.
	 *
	 * @param ownDriver the own driver
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:54:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService#queryOwnDriverRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
	 */
	@Override
	public long queryOwnDriverRecordByCondition(OwnDriverEntity ownDriver) {
		if (null == ownDriver ) {
			return 0;
		}	
		if (StringUtil.isNotBlank(ownDriver.getParentOrgCode())) {
			if (StringUtils.equals(FossConstants.YES, ownDriver.getWaybillFlag())) {
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(ownDriver.getParentOrgCode(),ownDriver.getFleetType());
				ownDriver.setSubOrgCodeList(orgList);
			} else {
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(ownDriver
								.getParentOrgCode());
				if (CollectionUtils.isNotEmpty(orgList)) {
					List<String> subOrgCodeList = new ArrayList<String>();
					for (OrgAdministrativeInfoEntity orgEntity : orgList) {
						subOrgCodeList.add(orgEntity.getCode());
					}
					ownDriver.setSubOrgCodeList(subOrgCodeList);
				}
			}
			if(CollectionUtils.isEmpty(ownDriver.getSubOrgCodeList())){
				List<String> parentOrgCodeList= new ArrayList<String>();
				parentOrgCodeList.add(ownDriver.getParentOrgCode());
				ownDriver.setSubOrgCodeList(parentOrgCodeList);
			}
		}
		
		return commonOwnDriverDao.queryOwnDriverRecordByCondition(ownDriver);
	}

	/**
	 * setter.
	 *
	 * @param commonOwnDriverDao the new common own driver dao
	 */
	public void setCommonOwnDriverDao(ICommonOwnDriverDao commonOwnDriverDao) {
		this.commonOwnDriverDao = commonOwnDriverDao;
	}

	/**
	 * 查询所有司机（公司司机，外请司机）.
	 *
	 * @param ownDriver the own driver
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:14:06
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService#queryDriverByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)
	 */
	@Override
	public List<DriverDto> queryDriverByCondition(OwnDriverVo driverVo,
			int offset, int limit) {
		if (null == driverVo || null == driverVo.getDriverEntity()) {
			return null;
		}
		// 如果查询条件为空则返回空
		OwnDriverEntity entity = driverVo.getDriverEntity();
		if (StringUtil.isNotBlank(entity.getParentOrgCode())) {
			if (StringUtils.equals(FossConstants.YES, entity.getWaybillFlag())) {
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(entity.getParentOrgCode(),entity.getFleetType());
				entity.setSubOrgCodeList(orgList);
			} else {
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(entity
								.getParentOrgCode());
				if (CollectionUtils.isNotEmpty(orgList)) {
					List<String> subOrgCodeList = new ArrayList<String>();
					for (OrgAdministrativeInfoEntity orgEntity : orgList) {
						subOrgCodeList.add(orgEntity.getCode());
					}
					entity.setSubOrgCodeList(subOrgCodeList);
				}
			}
			if(CollectionUtils.isEmpty(entity.getSubOrgCodeList())){
				List<String> parentOrgCodeList= new ArrayList<String>();
				parentOrgCodeList.add(entity.getParentOrgCode());
				entity.setSubOrgCodeList(parentOrgCodeList);
			}
		}
		
		List<DriverDto> driverDtos=commonOwnDriverDao.queryDriverListBySelectiveCondition(entity,
				offset, limit);
		List<DriverDto> driverDtoList = new ArrayList<DriverDto>();
		for(DriverDto driverDto : driverDtos){
			if("公司司机".equals(driverDto.getDriverType())){
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = OrgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(driverDto.getOrgId());
				if(null !=orgAdministrativeInfoEntity){
					driverDto.setOrgName(orgAdministrativeInfoEntity.getName());
				}else{
					continue;
				}
				
			}
			driverDtoList.add(driverDto);
		}
		
		return driverDtoList;
	}

	/**
	 * 查询所有司机（公司司机，外请司机）总数.
	 *
	 * @param ownDriver the own driver
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:14:06
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnDriverService#queryDriverRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
	 */
	@Override
	public long queryDriverRecordByCondition(OwnDriverEntity ownDriver) {
		if (null == ownDriver ) {
			return 0;
		}	
		if (StringUtil.isNotBlank(ownDriver.getParentOrgCode())) {
			if (StringUtils.equals(FossConstants.YES, ownDriver.getWaybillFlag())) {
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(ownDriver.getParentOrgCode(),ownDriver.getFleetType());
				ownDriver.setSubOrgCodeList(orgList);
			} else {
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(ownDriver
								.getParentOrgCode());
				if (CollectionUtils.isNotEmpty(orgList)) {
					List<String> subOrgCodeList = new ArrayList<String>();
					for (OrgAdministrativeInfoEntity orgEntity : orgList) {
						subOrgCodeList.add(orgEntity.getCode());
					}
					ownDriver.setSubOrgCodeList(subOrgCodeList);
				}
			}
			if(CollectionUtils.isEmpty(ownDriver.getSubOrgCodeList())){
				List<String> parentOrgCodeList= new ArrayList<String>();
				parentOrgCodeList.add(ownDriver.getParentOrgCode());
				ownDriver.setSubOrgCodeList(parentOrgCodeList);
			}
		}
		
		return commonOwnDriverDao.queryDriverRecordByCondition(ownDriver);
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return OrgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		OrgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	
}
