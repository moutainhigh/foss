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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonOwnVehicleDao.java
 * 
 * FILE NAME        	: ICommonOwnVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;

/**
 * 公共查询选择器--公司车辆DAO接口
 * @author 078823-foss-panGuangjun
 * @date 2012-12-10 下午2:31:52
 */
public interface ICommonOwnVehicleDao {

	/**
	 * <p>
	 * 根据条件有选择的统计出符合条件的“公司车辆”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-10-24 上午11:35:56
	 * @param ownTruck
	 *            以“公司车辆”实体承载的条件参数实体
	 * @return 符合条件的“公司车辆”实体记录条数
	 * @see
	 */
	 long queryOwnVehicleRecordCountBySelectiveCondition(
			OwnTruckEntity ownTruck);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-10 下午2:31:24
	 * @param ownTruck
	 *            以“外请车厢式车”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“外请车厢式车”实体列表
	 * @see
	 */
	 List<OwnTruckEntity> queryOwnVehicleListBySelectiveCondition(
			OwnTruckEntity ownTruck, int offset, int limit);

	/**
	 * <p>
	 * 根据条件有选择的统计出符合条件的“公司车辆”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-10-24 上午11:35:56
	 * @param ownTruck
	 *            以“公司车辆”实体承载的条件参数实体
	 * @return 符合条件的“公司车辆”实体记录条数
	 * @see
	 */
	 long queryVehicleRecordCountBySelectiveCondition(
			OwnTruckEntity ownTruck);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-10 下午2:31:24
	 * @param ownTruck
	 *            以“车厢式车”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“车厢式车”实体列表
	 * @see
	 */
	 List<TruckDto> queryVehicleListBySelectiveCondition(
			OwnTruckEntity ownTruck, int offset, int limit);
}
