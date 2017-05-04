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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/AreaAddressService.java
 * 
 * FILE NAME        	: AreaAddressService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**   
 * Description:这里写描述<br />
 * @title AreaAddressManager.java
 * @package com.deppon.crm.module.common.server.manager.impl 
 * @author panGuangjun
 * @version 0.1 2012-3-16
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.server.cache.AdministrativeRegionCacheDeal;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * Description:Manager<br />.
 *
 * @title AreaAddressManager.java
 * @package com.deppon.crm.module.common.server.manager.impl
 * @author panGuangjun
 * @version 0.1 2012-3-16
 */

public class AreaAddressService implements IAreaAddressService {
	// 注入service
	/** The administrative regions dao. */
	private IAdministrativeRegionsDao administrativeRegionsDao;

	// 此处不用注入
	/** The administrative region cache deal. */
	private AdministrativeRegionCacheDeal administrativeRegionCacheDeal = new AdministrativeRegionCacheDeal();

	/** The data dictionary value service. */
	IDataDictionaryValueService dataDictionaryValueService;

	/**
	 * Gets the city by province.
	 *
	 * @param provinceId the province id
	 * @return the city by province
	 * @description 获取对应省份的城市
	 * @author panGuangjun
	 * @2012-3-16
	 */
	public List<AdministrativeRegionsEntity> getCityByProvince(String provinceId,String cityName) {
		if (provinceId == null || "".equals(provinceId)) {
			return null;
		}
		List<AdministrativeRegionsEntity> cityList=null;
		
		if(StringUtils.isNotBlank(cityName)){
			//根据城市名称获取省份城市
			cityList=administrativeRegionCacheDeal.getCityByName(cityName,0,Integer.MAX_VALUE);
		}else{
			//根据省份编码获取省份城市
			cityList=administrativeRegionCacheDeal.getCityByProvince(provinceId);
		}
		
		return cityList;
	}

	/**
	 * Gets the area by city.
	 *
	 * @param cityId the city id
	 * @return the area by city
	 * @description 获取对应城市的区域
	 * @author panGuangjun
	 * @2012-3-16
	 */
	public List<AdministrativeRegionsEntity> getAreaByCity(String cityId,String areaName) {
		if (cityId == null || "".equals(cityId)) {
			return null;
		}
		//城市区域列表集合
		List<AdministrativeRegionsEntity> areaList = new ArrayList<AdministrativeRegionsEntity>();
		//城市区域实体
		AdministrativeRegionsEntity areaEntity = new AdministrativeRegionsEntity();
		// areaEntity.setDegree(DictionaryValueConstants.DISTRICT_COUNTY);
		//设置省份编码
		areaEntity.setParentDistrictCode(cityId);
		if(StringUtils.isNotBlank(areaName)){
			areaEntity.setName(areaName);
		}
		List<AdministrativeRegionsEntity> areas = administrativeRegionsDao
				.queryAdministrativeRegionsExactByEntity(areaEntity, 0,
						Integer.MAX_VALUE);
		for (AdministrativeRegionsEntity a : areas) {
			// 将其省份、城市都添加上
			areaList.add(a);
		}
		return areaList;
	}

	/**
	 * Gets the all province list.
	 *
	 * @return the all province list
	 * @description 获取所有省份列表
	 * @author panGuangjun
	 * @2012-3-16
	 */
	public List<AdministrativeRegionsEntity> getAllProvinceList() {
		return administrativeRegionCacheDeal.getAllProvinceList();
	}

	/**
	 * Search province by id.
	 *
	 * @param id the id
	 * @return the list
	 * @description 根据名字查询对应城市
	 * @author panGuangjun
	 * @2012-3-16
	 */
	// public List<AdministrativeRegionsEntity> searchCityByName(String name) {
	// if (name == null || "".equals(name)) {
	// return null;
	// }
	// return administrativeRegionCacheDeal.getCityByName(name);
	// }

	/**
	 * 
	 * Description:根据id查询对应城市<br />
	 * 
	 * @author panGuangjun
	 * @version 0.1 2012-3-27
	 * @param 城市id
	 * @return
	 */
	public List<AdministrativeRegionsEntity> searchProvinceById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		return administrativeRegionCacheDeal.searchProvinceById(id);
	}

	/**
	 * Description:根据id查询对应城市<br />.
	 *
	 * @param id the id
	 * @return the list
	 * @author panGuangjun
	 * @version 0.1 2012-3-27
	 */
	public List<AdministrativeRegionsEntity> searchCityById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		return administrativeRegionCacheDeal.searchCityById(id);
	}

	/**
	 * Search area by id.
	 *
	 * @param id the id
	 * @return the list
	 * @description 根据id查询对应区域
	 * @author panGuangjun
	 * @2012-11-30
	 */
	public List<AdministrativeRegionsEntity> searchAreaById(String id) {
		if (id == null || "".equals(id)) {
			return null;
		}
		//行政区域列表集合
		List<AdministrativeRegionsEntity> areaList = new ArrayList<AdministrativeRegionsEntity>();
		//行政区域实体
		AdministrativeRegionsEntity area = new AdministrativeRegionsEntity();
		//设置区域编码
		area.setCode(id);
		//获取区域列表
		List<AdministrativeRegionsEntity> areas = administrativeRegionsDao
				.queryAdministrativeRegionsExactByEntity(area, 0,
						Integer.MAX_VALUE);
		for (AdministrativeRegionsEntity a : areas) {
			areaList.add(a);
		}
		return areaList;
	}

	/**
	 * <p>
	 * 根据传入省份和城市名称查询对应城市.
	 *
	 * @param c the c
	 * @param start the start
	 * @param limit the limit
	 * @return the administrative regions entity by name
	 * @author Administrator
	 * @date 2012-9-24 下午4:18:48
	 * @see com.deppon.foss.module.commonselector.server.service.IAreaAddressService#getCityByNameAndProvince()
	 */
	@Override
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityByName(
			AdministrativeRegionsEntity c, int start, int limit) {
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		if (null == c) {
			return targetList;
		}
		// 判断行政级别是省
		if (DictionaryValueConstants.DISTRICT_PROVINCE.equals(c.getDegree())) {
			targetList = administrativeRegionCacheDeal.getProvinceByName(
					c.getName(), start, limit);
			// 行政级别为城市
		} else if (DictionaryValueConstants.DISTRICT_CITY.equals(c.getDegree())) {
			targetList = administrativeRegionCacheDeal.getCityByName(
					c.getName(), start, limit);
			// 行政级别为县
		} else if (DictionaryValueConstants.DISTRICT_COUNTY.equals(c
				.getDegree())) {
			targetList = administrativeRegionsDao
					.queryAdministrativeRegionsByEntity(c, start, limit);
		} else {
			// 当不传行政级别时
			targetList = administrativeRegionsDao
					.queryAdministrativeRegionsByEntity(c, start, limit);
			// 通过数据字段转换行政区域的code为name
			List<DataDictionaryValueEntity> values = dataDictionaryValueService
					.queryDataDictionaryValueByTermsCode(DictionaryConstants.DISTRICT_DEGREE);
			if (CollectionUtils.isNotEmpty(targetList)
					&& CollectionUtils.isNotEmpty(values)) {
				setTargetList(targetList, values);
			}
		}

		return targetList;
	}

	private void setTargetList(List<AdministrativeRegionsEntity> targetList,
			List<DataDictionaryValueEntity> values) {
		for (int i = 0; i < targetList.size(); i++) {
			for (int j = 0; j < values.size(); j++) {
				if (!StringUtil.isEmpty(targetList.get(i).getDegree())
						&& targetList.get(i).getDegree()
								.equals(values.get(j).getValueCode())) {
					targetList.get(i).setDegreeName(
							values.get(j).getValueName());
				}
			}
		}
	}

	/**
	 * 得到常用城市.
	 *
	 * @return the common cities
	 * @author panGuangJun
	 * @date 2012-11-29 下午4:07:36
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService#getCommonCities()
	 */
	@Override
	public List<AdministrativeRegionsEntity> getCommonCities() {
		return null;
	}

	/**
	 * Sets the administrative regions dao.
	 *
	 * @param administrativeRegionsDao the new administrative regions dao
	 */
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}

	/**
	 * 查询所有国家.
	 *
	 * @return the all nation list
	 * @author 078823-foss-panGuangJun
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService#getAllNationList()
	 */
	@Override
	public List<AdministrativeRegionsEntity> getAllNationList() {
		//返回所有国家列表信息
		return administrativeRegionCacheDeal.getAllNationList();
	}

	/**
	 * 通过国家code查询对应的省份.
	 *
	 * @param parentId the parent id
	 * @return the province by nation
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:03:54
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService#getProvinceByNation(java.lang.String)
	 */
	@Override
	public List<AdministrativeRegionsEntity> getProvinceByNation(String parentId,String proviceName) {
		//当国家code为空则直接返回
		if (parentId == null || "".equals(parentId)) {
			return null;
		}
		List<AdministrativeRegionsEntity> proviceList=null;
		//判断省份名称是否为空，不会空则按名称查询;为空则按国家查询所以省份
		if(StringUtils.isNotBlank(proviceName)){
			proviceList= administrativeRegionCacheDeal.getProvinceByName(proviceName, 0, Integer.MAX_VALUE);
		}else {
			proviceList = administrativeRegionCacheDeal.getProvinceByNation(parentId);
		}
		return proviceList;
	}

	/** 
	 * 根据条件查询所以记录条数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 下午5:38:56
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService#queryAdministrativeRegionsByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
	 */
	@Override
	public long queryAdministrativeRegionsByEntityCount(
			AdministrativeRegionsEntity entity) {
		Long count = 0L;
		if(null == entity){
			return count;
		}
		//获取区域级别
		String name = entity.getName();
		//区域级别为国家
		//313353 sonar优化
		if (DictionaryValueConstants.DISTRICT_NATION.equals(entity.getDegree())
				|| DictionaryValueConstants.DISTRICT_PROVINCE.equals(entity.getDegree())) {
			count = administrativeRegionCacheDeal
					.countProvinceByName(entity.getName());
		//区域级别为省份
//		}else if (DictionaryValueConstants.DISTRICT_PROVINCE.equals(entity
//				.getDegree())) {
//			count = administrativeRegionCacheDeal
//					.countProvinceByName(entity.getName());
		//区域级别为城市
		} else if (DictionaryValueConstants.DISTRICT_CITY.equals(entity
				.getDegree())) {
			count = administrativeRegionCacheDeal.countCityByName(entity
					.getName());
		//区域级别为县
		} else if (DictionaryValueConstants.DISTRICT_COUNTY.equals(entity
				.getDegree())) {
			count = administrativeRegionsDao
					.queryAdministrativeRegionsByEntityCount(entity);
		} else {
			//未设置域级别时按条件进行查询
			AdministrativeRegionsEntity c = new AdministrativeRegionsEntity();
			//设置区域名称
			entity.setName(name);
			//根据条件获取区域信息
			count = administrativeRegionsDao.queryAdministrativeRegionsByEntityCount(c);
		}
		return count;
	}

	/**
	 * setter.
	 *
	 * @param dataDictionaryValueService the new data dictionary value service
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * 根据编码查询行政区域.
	 *
	 * @param code the code
	 * @return the administrative regions entity
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 下午2:35:48
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService#queryRegionByCode(java.lang.String)
	 */
	@Override
	public AdministrativeRegionsEntity queryRegionByCode(String code) {
		return administrativeRegionCacheDeal.searchRegionByCode(code);
	}	
}
