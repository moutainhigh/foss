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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonZoneService.java
 * 
 * FILE NAME        	: ICommonZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
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
public interface ICommonZoneService extends IService {
 
	/**
	 * 根据条件查询接送货大区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:06:59
	 * @return 
	 */
	List<BigZoneEntity> queryBigZoneByCondition(CommonBigZoneDto dto, int limit,int start);
 
	/**
	 * 根据条件查询接送货大区总条数.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:11:56
	 * @return 
	 */
	Long countBigZoneByCodition(CommonBigZoneDto dto);
	/**
	 * 根据条件查询接送货小区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:06:59
	 * @return 
	 */
	List<SmallZoneEntity> querySmallZoneByCondition(CommonSmallZoneDto dto, int limit,int start);
	
	/**
	 * 根据条件查询接送货小区总条数.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:09:23
	 * @return 
	 */
	Long countSmallZoneByCodition(CommonSmallZoneDto dto);

	/**
	 * 根据条件查询接送货大小区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:06:59
	 * @return 
	 */
	List<CommonAllZoneDto> queryAllZoneByCondition(CommonAllZoneDto dto, int limit,int start);
	
	/**
	 * 根据条件查询接送货大小区总条数.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午10:09:23
	 * @return 
	 */
	Long countAllZoneByCodition(CommonAllZoneDto dto);
}
