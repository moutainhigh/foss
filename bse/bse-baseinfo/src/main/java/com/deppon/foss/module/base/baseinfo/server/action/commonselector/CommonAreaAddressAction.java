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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonAreaAddressAction.java
 * 
 * FILE NAME        	: CommonAreaAddressAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonAdministrativeRegionsVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * Description:查询省市区Action<br />.
 *
 * @title AreaAddressAction.java
 * @package com.deppon.crm.module.common.server.action
 * @author xiaoqiang
 * @version 0.1 2012-3-15
 */

public class CommonAreaAddressAction extends AbstractAction {
	
	/** The Constant serialVersionUID. @serial */
	private static final long serialVersionUID = -2827822795551126345L;
	// 注入Manager
	/** The area address service. */
	private IAreaAddressService areaAddressService;

	/** The city vo. */
	private CommonAdministrativeRegionsVo cityVo = new CommonAdministrativeRegionsVo();

	/**
	 * Description:获取所有国家<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-3-19
	 */
	@JSON
	public String searchAllNationList() {
		cityVo.setNationList(areaAddressService.getAllNationList());
		return returnSuccess();
	}

	/**
	 * Description:获取所有省份<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-3-19
	 */
	@JSON
	public String searchAllProvinceList() {
		cityVo.setProvinceList(areaAddressService.getAllProvinceList());
		return returnSuccess();
	}

	/**
	 * Description:根据国家id查询对应省份<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryProvince() {
		if (!StringUtil.isEmpty(cityVo.getParentId())) {
			cityVo.setProvinceList(this.areaAddressService
					.getProvinceByNation(cityVo.getParentId(),cityVo.getName()));
		} else {
			AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
			entity.setDegree(DictionaryValueConstants.DISTRICT_PROVINCE);
			entity.setName(cityVo.getName());
			entity.setActive(cityVo.getActive());
			cityVo.setProvinceList(this.areaAddressService
					.getAdministrativeRegionsEntityByName(entity, start, limit));
			setTotalCount(areaAddressService.queryAdministrativeRegionsByEntityCount(entity));
		}
		return returnSuccess();
	}

	/**
	 * Description:根据id查询对应城市<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryCity() {
		if (!StringUtil.isEmpty(cityVo.getParentId())) {
			cityVo.setCityList(this.areaAddressService.getCityByProvince(cityVo
					.getParentId(),cityVo.getName()));
		} else{
			AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
			entity.setDegree(DictionaryValueConstants.DISTRICT_CITY);
			entity.setName(cityVo.getName());
			entity.setActive(cityVo.getActive());
			cityVo.setCityList(this.areaAddressService
					.getAdministrativeRegionsEntityByName(entity, start, limit));
			setTotalCount(areaAddressService.queryAdministrativeRegionsByEntityCount(entity));
		}
		return returnSuccess();
	}

	/**
	 * Description:根据id查询对应城市<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryArea() {
		if (!StringUtil.isEmpty(cityVo.getParentId())) {
			cityVo.setAreaList(this.areaAddressService.getAreaByCity(cityVo
					.getParentId(),cityVo.getName()));
		} else{
			AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
			entity.setDegree(DictionaryValueConstants.DISTRICT_COUNTY);
			entity.setName(cityVo.getName());
			entity.setActive(cityVo.getActive());
			cityVo.setAreaList(this.areaAddressService
					.getAdministrativeRegionsEntityByName(entity, start, limit));
			setTotalCount(areaAddressService.queryAdministrativeRegionsByEntityCount(entity));
		}
		return returnSuccess();
	}

	/**
	 * Description:根据名称查询对应区域<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryProvinceByName() {
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(DictionaryValueConstants.DISTRICT_PROVINCE);
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		cityVo.setProvinceList(this.areaAddressService
				.getAdministrativeRegionsEntityByName(entity, start, limit));
		setTotalCount(areaAddressService
				.queryAdministrativeRegionsByEntityCount(entity));
		return returnSuccess();
	}

	/**
	 * Description:根据名称查询对应行政区域<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryReginByName() {
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setName(cityVo.getName());
		entity.setDegree(cityVo.getDegree() );
		entity.setActive(cityVo.getActive());
		cityVo.setCityList(this.areaAddressService
				.getAdministrativeRegionsEntityByName(entity, start, limit));
		setTotalCount(areaAddressService
				.queryAdministrativeRegionsByEntityCount(entity));
		return returnSuccess();
	}

	/**
	 * Description:根据名称查询对应区域<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryCityByName() {
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(DictionaryValueConstants.DISTRICT_CITY);
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		cityVo.setCityList(this.areaAddressService
				.getAdministrativeRegionsEntityByName(entity, start, limit));
		setTotalCount(areaAddressService
				.queryAdministrativeRegionsByEntityCount(entity));
		return returnSuccess();
	}

	/**
	 * Description:根据id查询对应区域<br />.
	 *
	 * @return the string
	 * @author panGuangjun
	 * @version 0.1 2012-11-30
	 */
	@JSON
	public String queryAreaByName() {
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(DictionaryValueConstants.DISTRICT_COUNTY);
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		entity.setParentDistrictCode(cityVo.getParentId());
		cityVo.setAreaList(this.areaAddressService
				.getAdministrativeRegionsEntityByName(entity, start, limit));
		setTotalCount(areaAddressService
				.queryAdministrativeRegionsByEntityCount(entity));
		return returnSuccess();
	}

	/**
	 * Sets the area address service.
	 *
	 * @param areaAddressService the new area address service
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}

	/**
	 * Gets the city vo.
	 *
	 * @return the city vo
	 */
	public CommonAdministrativeRegionsVo getCityVo() {
		return cityVo;
	}

	/**
	 * Sets the city vo.
	 *
	 * @param cityVo the new city vo
	 */
	public void setCityVo(CommonAdministrativeRegionsVo cityVo) {
		this.cityVo = cityVo;
	}
	/**
	 * 根据省份查询城市targetList = administrativeRegionsDao.queryAdministrativeRegionsByEntity(c, start, limit);
	 * @return
	 */
	private IAdministrativeRegionsDao administrativeRegionsDao;
	
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}
	/**
	 * 根据省份查询城市
	 * @return
	 */
	@JSON
	public String searchCityByProvince(){
		
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(DictionaryValueConstants.DISTRICT_CITY);
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		entity.setParentDistrictCode(cityVo.getParentId());
		List<AdministrativeRegionsEntity> list =this.administrativeRegionsDao.queryAdministrativeRegionsByEntity(entity, start, limit);
		cityVo.setCityList(list);
		setTotalCount((long)this.administrativeRegionsDao.queryAdministrativeRegionsByEntity(entity, 0, Integer.MAX_VALUE).size());
		return returnSuccess();
	}
	
	/**
	 * 根据城市查询区县
	 * @return
	 */
	@JSON
	public String searchAreaByCity(){
		
		AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
		entity.setDegree(DictionaryValueConstants.DISTRICT_COUNTY);
		entity.setName(cityVo.getName());
		entity.setActive(cityVo.getActive());
		entity.setParentDistrictCode(cityVo.getParentId());
		List<AdministrativeRegionsEntity> list =this.administrativeRegionsDao.queryAdministrativeRegionsByEntity(entity, start, limit);
		cityVo.setAreaList(list);
		setTotalCount((long)this.administrativeRegionsDao.queryAdministrativeRegionsByEntity(entity, 0, Integer.MAX_VALUE).size());
		return returnSuccess();
	}
}
