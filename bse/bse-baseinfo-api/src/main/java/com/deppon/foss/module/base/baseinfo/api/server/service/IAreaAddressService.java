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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAreaAddressService.java
 * 
 * FILE NAME        	: IAreaAddressService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**   
 * Description:这里写描述<br />
 * @title IAreaAddressManager.java
 * @package com.deppon.crm.module.common.server.manager.impl 
 * @author 毛建强
 * @version 0.1 2012-3-16
 */
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;

/**   
 * @title IAreaAddressManager.java
 * @package com.deppon.crm.module.common.server.manager.impl 
 * @version 0.1 2012-12-01
 */

public interface IAreaAddressService {
	List<AdministrativeRegionsEntity> getCommonCities();
	List<AdministrativeRegionsEntity> getCityByProvince(String provinceId,String cityName);
	List<AdministrativeRegionsEntity> getAreaByCity(String cityId,String areaName);
	List<AdministrativeRegionsEntity> getAllProvinceList();
//	List<AdministrativeRegionsEntity> searchCityByName(String name);
	List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityByName(AdministrativeRegionsEntity c,int limit,int start);
	/**
	 * 查询所有国家
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-9 下午3:20:30
	* @return List<AdministrativeRegionsEntity>
	* @param 
	 */
	List<AdministrativeRegionsEntity> getAllNationList();
	/**
	 * 通过国家id查询省份
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:03:15
	* @return List<AdministrativeRegionsEntity>
	* @param 
	 */
	List<AdministrativeRegionsEntity> getProvinceByNation(String parentId,String proviceName);
	/**
	 * 查询总条数
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 下午2:18:10
	* @return long
	* @param 
	 */
	long queryAdministrativeRegionsByEntityCount(
			AdministrativeRegionsEntity entity);
	/**
	 * 根据编码查询行政区域
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 下午2:35:17
	* @return void
	* @param 
	 */
	AdministrativeRegionsEntity queryRegionByCode(String code);
}
