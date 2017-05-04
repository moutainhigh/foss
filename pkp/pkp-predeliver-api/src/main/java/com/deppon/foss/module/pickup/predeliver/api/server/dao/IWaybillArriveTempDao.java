/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IWaybillArriveTempDao.java
 * 
 * FILE NAME        	: IWaybillArriveTempDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyJZEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;


/**
 * 
 * 运单到达数量、到达时间临时表DAO
 * @author ibm-wangfei
 * @date Nov 20, 2012 3:02:55 PM
 */
public interface IWaybillArriveTempDao {

	/**
	 * 
	 * 执行存储过程CalculateWaybillArriveInfo
	 * @author ibm-wangfei
	 * @date Nov 20, 2012 3:18:20 PM
	 */
	void callCalculateWaybillArriveInfo();

	int updateByParam(VehicleArriveNotifyEntity vehicleArriveNotifyEntity);
	/**
	 * job执行，更新车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午9:35:58
	 */
	int updateJZWaybillByParam(VehicleArriveNotifyJZEntity vehicleArriveNotifyJZEntity);

	Long queryVehicleArriveNotifyCount(VehicleArriveNotifyEntity vehicleArriveNotifyEntity);

	List<VehicleArriveNotifyEntity> queryVehicleArriveNotify(VehicleArriveNotifyEntity vehicleArriveNotifyEntity);
	/**
	 * 查询符合条件的车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午9:45:23
	 */
	List<VehicleArriveNotifyJZEntity> queryVehicleArriveNotifyJZ(VehicleArriveNotifyJZEntity vehicleArriveNotifyJZEntity);

	int deleteByPrimaryKey(String id);
	/**
	 * 根据id删除车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午10:04:35
	 */
	int deleteJZWaybillByPrimaryKey(String id);

	List<NotifyCustomerDto> queryAutoWaybillInfo(String truckTaskDetailId);

	Long querySalesDepartmentCount(String truckTaskDetailId);
	/**
	 * 按车辆任务ID查询符合条件的运单给dop推送到货信息
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-03 上午10:02:12
	 */
	List<ArrivalGoodsWaybillDto> queryAutoWaybillInfoForSendDop(Map<String, Object> map);
}