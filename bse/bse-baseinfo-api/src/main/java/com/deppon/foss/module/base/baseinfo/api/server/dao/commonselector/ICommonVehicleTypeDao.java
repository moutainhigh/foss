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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonVehicleTypeDao.java
 * 
 * FILE NAME        	: ICommonVehicleTypeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;

/**
 * 公共选择器--车辆车型DAO接口
 * 
 * @author 078823-foss-panGuangjun
 * @date 2012-12-10 下午3:55:37
 */
public interface ICommonVehicleTypeDao {

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-10 上午10:37:46
	 * @param vehicleType
	 *            以“车辆车型”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“车辆车型”实体列表
	 */
	List<VehicleTypeEntity> queryVehicleTypesByCondition(
			VehicleTypeEntity vehicleType, int offset, int limit);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangjun
	 * @date 2012-12-10 上午10:37:46
	 * @param vehicleType
	 *            以“车辆车型”实体承载的条件参数实体
	 * @return 符合条件的“车辆车型”总条数
	 */
	Long countVehicleTypeByCondition(VehicleTypeEntity vehicleType);
}
