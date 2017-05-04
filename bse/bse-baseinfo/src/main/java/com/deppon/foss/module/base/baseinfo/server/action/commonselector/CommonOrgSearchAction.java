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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonOrgSearchAction.java
 * 
 * FILE NAME        	: CommonOrgSearchAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonOrgSearchAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrgVo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 公共组件查询 ACTION.
 *
 * @author panGuangJun
 * @date 2012-11-28 上午10:59:27
 */
public class CommonOrgSearchAction extends AbstractAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -6427731385678430305L;
	// 前台传值
	/** The common org vo. */
	private CommonOrgVo commonOrgVo = new CommonOrgVo();
	// service
	/** The common org service. */
	private ICommonOrgService commonOrgService;
	// service
	/** The org administrative info complex service. */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	// service
	/** The org administrative info service. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * Gets the common org vo.
	 *
	 * @return the common org vo
	 */
	public CommonOrgVo getCommonOrgVo() {
		return commonOrgVo;
	}

	/**
	 * Sets the common org service.
	 *
	 * @param commonOrgService the new common org service
	 */
	public void setCommonOrgService(ICommonOrgService commonOrgService) {
		this.commonOrgService = commonOrgService;
	}

	/**
	 * Seacrh org administation.
	 *
	 * @return the string
	 * @description 公共组件--查询
	 * @author panGuangJun
	 * @date 2012-11-28 上午11:25:14
	 */
	public String seacrhOrgAdministation() {
		commonOrgVo.setLimit(limit);
		commonOrgVo.setStart(start);
		List<CommonOrgEntity> orgList = commonOrgService
				.searchOrgByCondition(commonOrgVo);
		totalCount = commonOrgService.countOrgByCondition(commonOrgVo);
		commonOrgVo.setCommonOrgEntityList(orgList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

	/**
	 * Seacrh org by param.
	 *
	 * @return the string
	 * @description 公共组件--通过名称/拼音/编码查询
	 * @author panGuangJun
	 * @date 2012-11-28 上午11:25:14
	 */
	public String seacrhOrgByParam() {
		commonOrgVo.setLimit(limit);
		commonOrgVo.setStart(start);
		List<CommonOrgEntity> orgList = commonOrgService.searchOrgByParam(commonOrgVo);
		totalCount = commonOrgService.countOrgByParam(commonOrgVo);
		commonOrgVo.setCommonOrgEntityList(orgList);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

	/**
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:27:26
	 * @return
	 * @see
	 */
	public String queryOrgByParam(){
		try {	
			List<CommonOrgEntity> orgList = commonOrgService.queryOrgByParam(commonOrgVo.getDto(),start,limit);
			totalCount = commonOrgService.queryOrgByParamCount(commonOrgVo.getDto());
			commonOrgVo.setCommonOrgEntityList(orgList);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 查询所有大区.
	 *
	 * @return String
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-14 下午3:14:18
	 */
	@JSON
	public String searchAllBigRegion() {
		OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
		BeanUtils.copyProperties(commonOrgVo, entity);
		List<OrgAdministrativeInfoEntity> administras = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByEntity(entity, start, limit);
		
		List<CommonOrgEntity> orgList = new ArrayList<CommonOrgEntity>();
		if (CollectionUtils.isNotEmpty(administras)) {
			CommonOrgEntity entity1 =null;
			for (int i = 0; i < administras.size(); i++) {
				entity1 = new CommonOrgEntity();
				BeanUtils.copyProperties(administras.get(i), entity1);
				orgList.add(entity1);
			}
		}
		commonOrgVo.setCommonOrgEntityList(orgList);
		setTotalCount(orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByEntityCount(entity));
		return returnSuccess();
	}

	/**
	 * 根据组织id查询子组织.
	 *
	 * @return String
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-14 下午2:55:02
	 */
	@JSON
	public String searchOrgDeptByParentCode() {
		List<OrgAdministrativeInfoEntity> administras = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoUpDown(commonOrgVo.getCode(), false);
		List<CommonOrgEntity> orgList = new ArrayList<CommonOrgEntity>();
		if (CollectionUtils.isNotEmpty(administras)) {
			CommonOrgEntity entity = null;
			for (int i = 0; i < administras.size(); i++) {
				entity=new CommonOrgEntity();
				BeanUtils.copyProperties(administras.get(i), entity);
				orgList.add(entity);
			}
		}
		commonOrgVo.setCommonOrgEntityList(orgList);
		return returnSuccess();
	}

	/**
	 * Sets the common org vo.
	 *
	 * @param commonOrgVo the new common org vo
	 */
	public void setCommonOrgVo(CommonOrgVo commonOrgVo) {
		this.commonOrgVo = commonOrgVo;
	}

	/**
	 * setter.
	 *
	 * @param orgAdministrativeInfoComplexService the new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * setter.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}
