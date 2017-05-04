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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonMotorcadeService.java
 * 
 * FILE NAME        	: CommonMotorcadeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector
 * FILE    NAME: CommonMotorcadeDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonMotorcadeDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共查询选择器--dao实现.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-11 下午2:24:21
 */
public class CommonMotorcadeService implements ICommonMotorcadeService {

	/** The common motorcade dao. */
	private ICommonMotorcadeDao commonMotorcadeDao;
	private IMotorcadeService motorcadeService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 查询车队.
	 * 
	 * @param entity
	 *            the entity
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-11 下午2:24:21
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao#queryMotorcadeByEntity

(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity,
	 *      int, int)
	 */
	@Override
	public List<MotorcadeEntity> queryMotorcadeByCondition(
			CommonMotorcadeDto dto, int start, int limit) {
		// 检查参数的合法性
		if (null == dto) {
			return null;
		}
		if (StringUtils.equals(FossConstants.YES, dto.getIsFullFleetOrgFlag())) {
			return motorcadeService.queryTopFleetList(dto.getQueryParam());
		}else if (StringUtils.isNotBlank(dto.getTopFleetOrgCode())) {
			//List<String> result = new ArrayList<String>();
			List<String> result = orgAdministrativeInfoComplexService.queryMotorcadeCodeListByOrgCode(dto.getTopFleetOrgCode());
			if(CollectionUtils.isEmpty(result)){
				result.add(dto.getTopFleetOrgCode());
			}
			dto.setMotorcadeCodes(result);
		}else if(CollectionUtils.isNotEmpty(dto.getLoopOrgCodes())){
			List<String> loopOrgCodes = dto.getLoopOrgCodes();
			List<OrgAdministrativeInfoEntity> orgLists = new ArrayList<OrgAdministrativeInfoEntity>();
			for(String LoopOrgCode : loopOrgCodes){				
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(LoopOrgCode);
			orgLists.addAll(orgList);
			}
			if(CollectionUtils.isNotEmpty(orgLists)){
				List<String> subOrgList=new ArrayList<String>();
				for(OrgAdministrativeInfoEntity orgEntity : orgLists){		
					subOrgList.add(orgEntity.getCode());
				}
				dto.setSubOrgList(subOrgList);
			}else{
				dto.setSubOrgList(dto.getLoopOrgCodes());
			}
		}
		return commonMotorcadeDao.queryMotorcadeByCondition(dto, start, limit);
	}

	/**
	 * 查询车队总条数.
	 * 
	 * @param entity
	 *            the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-11 下午2:24:21
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonMotorcadeDao#queryMotorcadeByEntityCount

(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeByConditionCount(CommonMotorcadeDto dto) {
		// 检查参数的合法性
		if (null == dto) {
			return 0;
		}
		if (StringUtils.equals(FossConstants.YES, dto.getIsFullFleetOrgFlag())) {
//			List<MotorcadeEntity> topFleetList = motorcadeService
//					.queryTopFleetList(dto.getQueryParam());
//			if (CollectionUtils.isNotEmpty(topFleetList)) {
//				int n = topFleetList.size();
//				return n;
//			} else {
				return 1;
//			}
		} else if (StringUtils.isNotBlank(dto.getTopFleetOrgCode())) {
		//	List<String> result = new ArrayList<String>();
			List<String> result = orgAdministrativeInfoComplexService.queryMotorcadeCodeListByOrgCode(dto.getTopFleetOrgCode());
			if(CollectionUtils.isEmpty(result)){
				result.add(dto.getTopFleetOrgCode());
			}
			dto.setMotorcadeCodes(result);
		}else if(CollectionUtils.isNotEmpty(dto.getLoopOrgCodes())){
			List<String> loopOrgCodes = dto.getLoopOrgCodes();
			List<OrgAdministrativeInfoEntity> orgLists = new ArrayList<OrgAdministrativeInfoEntity>();
			for(String LoopOrgCode : loopOrgCodes){				
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(LoopOrgCode);
			orgLists.addAll(orgList);
			}
			if(CollectionUtils.isNotEmpty(orgLists)){
				List<String> subOrgList=new ArrayList<String>();
				for(OrgAdministrativeInfoEntity orgEntity : orgLists){
					subOrgList.add(orgEntity.getCode());
				}
				dto.setSubOrgList(subOrgList);
			}else{
				dto.setSubOrgList(dto.getLoopOrgCodes());
			}
		}
		return commonMotorcadeDao.queryMotorcadeByConditionCount(dto);
	}

	/**
	 * setter.
	 * 
	 * @param commonMotorcadeDao
	 *            the new common motorcade dao
	 */
	public void setCommonMotorcadeDao(ICommonMotorcadeDao commonMotorcadeDao) {
		this.commonMotorcadeDao = commonMotorcadeDao;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

}
