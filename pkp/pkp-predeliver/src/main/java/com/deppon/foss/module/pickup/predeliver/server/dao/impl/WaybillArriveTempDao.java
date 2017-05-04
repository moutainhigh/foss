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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/WaybillArriveTempDao.java
 * 
 * FILE NAME        	: WaybillArriveTempDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWaybillArriveTempDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyJZEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * @功能： 运单通知DAO实现类
 * @author ibm-wangfei
 * @date 2012-10-11 15:21:39
 * @version 0.1
 */
public class WaybillArriveTempDao extends iBatis3DaoImpl implements IWaybillArriveTempDao {
	// 运单通知实体命名空间
	private static final String VEHICLE_ARRIVE_TEMP_NAMESPACE = "foss.pickup.vehicle.arrive.temp.";
	// 运单通知INSERT
	private static final String CALL_PROCEDURES = "callProcedures";

	/**
	 * 
	 * 执行存储过程CalculateWaybillArriveInfo
	 * @author ibm-wangfei
	 * @date Nov 20, 2012 3:18:20 PM
	 */
	@Override
	public void callCalculateWaybillArriveInfo() {
		String jobId = UUIDUtils.getUUID();
		this.getSqlSession().selectOne(VEHICLE_ARRIVE_TEMP_NAMESPACE + CALL_PROCEDURES, jobId);
	}
	
	/**
	 * 
	 * 查询符合条件的记录数量
	 * 
	 * @param vehicleArriveNotifyEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 3:59:54 PM
	 */
	@Override
	public Long queryVehicleArriveNotifyCount(VehicleArriveNotifyEntity vehicleArriveNotifyEntity) {
		return (Long) this.getSqlSession().selectOne(VEHICLE_ARRIVE_TEMP_NAMESPACE + "queryVehicleArriveNotifyCount",  vehicleArriveNotifyEntity);
	}
	/**
	 * 
	 * 查询符合条件的记录数量
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 3:59:54 PM
	 */
	@Override
	public Long querySalesDepartmentCount(String truckTaskDetailId) {
		return (Long) this.getSqlSession().selectOne(VEHICLE_ARRIVE_TEMP_NAMESPACE + "querySalesDepartmentCount",  truckTaskDetailId);
	}

	@Override
	public int updateByParam(VehicleArriveNotifyEntity vehicleArriveNotifyEntity) {
		return this.getSqlSession().update(VEHICLE_ARRIVE_TEMP_NAMESPACE + "updateByParam", vehicleArriveNotifyEntity);
	}
	/**
	 * job执行，更新车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午9:38:41
	 */
	@Override
	public int updateJZWaybillByParam(VehicleArriveNotifyJZEntity vehicleArriveNotifyJZEntity) {
		return this.getSqlSession().update(VEHICLE_ARRIVE_TEMP_NAMESPACE + "updateJZWaybillByParam", vehicleArriveNotifyJZEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleArriveNotifyEntity> queryVehicleArriveNotify(VehicleArriveNotifyEntity vehicleArriveNotifyEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", vehicleArriveNotifyEntity.getStatus());
		map.put("jobId", vehicleArriveNotifyEntity.getJobId());
		map.put("rowNum", vehicleArriveNotifyEntity.getRowNum());
		return this.getSqlSession().selectList(VEHICLE_ARRIVE_TEMP_NAMESPACE + "queryVehicleArriveNotify", map);
	}
	/**
	 * 查询符合条件的车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午9:46:29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleArriveNotifyJZEntity> queryVehicleArriveNotifyJZ(VehicleArriveNotifyJZEntity vehicleArriveNotifyJZEntity){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", vehicleArriveNotifyJZEntity.getStatus());
		map.put("jobId", vehicleArriveNotifyJZEntity.getJobId());
		map.put("rowNum", vehicleArriveNotifyJZEntity.getRowNum());
		return this.getSqlSession().selectList(VEHICLE_ARRIVE_TEMP_NAMESPACE + "queryVehicleArriveNotifyJZ", map);
	}
	
	@Override
	public int deleteByPrimaryKey(String jobId) {
		return getSqlSession().delete(VEHICLE_ARRIVE_TEMP_NAMESPACE + "deleteByPrimaryKey", jobId);
	}
	/**
	 * 根据id删除车辆到达临时家装表中的卸车任务
	 * @author 243921-FOSS-zhangtingting
	 * @date 2016-01-06 上午10:04:52
	 */
	@Override
	public int deleteJZWaybillByPrimaryKey(String id) {
		return getSqlSession().delete(VEHICLE_ARRIVE_TEMP_NAMESPACE + "deleteJZWaybillByPrimaryKey", id);
	}
	
	/**
	 * 
	 * 查询符合条件的运单
	 * 
	 * @param jobId
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 4:17:34 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotifyCustomerDto> queryAutoWaybillInfo(String truckTaskDetailId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("truckTaskDetailId", truckTaskDetailId);
		return this.getSqlSession().selectList(VEHICLE_ARRIVE_TEMP_NAMESPACE + "queryAutoWaybillInfo", map);
	}

	/**
	 * 按车辆任务ID查询符合条件的运单给dop推送到货信息
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-03 上午10:08:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArrivalGoodsWaybillDto> queryAutoWaybillInfoForSendDop(Map<String,Object>
			map) {
		return this.getSqlSession().selectList(VEHICLE_ARRIVE_TEMP_NAMESPACE + "queryAutoWaybillInfoForSendDop", map);
	}
	
}