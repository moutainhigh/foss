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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IVehicleActualSituationManageService.java
 * 
 * FILE NAME        	: IVehicleActualSituationManageService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleRegionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;


/**
 * 车辆车载信息管理服务
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午11:39:10
 */
public interface IVehicleActualSituationManageService extends IService {
	
	/**
	 * 
	 * 添加车辆实况实体
	 * @param situationEntity 实体 -- vehicleNo 车牌号  remainingWeight 车辆载重  remainingVolume 车辆净空  createTime
	 * @author 038590-foss-wanghui
	 * @date 2012-12-6 上午11:06:24
	 */
	int addVehicleSituation(VehicleActualSituationEntity situationEntity);
	
	/**
	 * 
	 * 通过车牌号查询车载信息
	 * @param vehicleNo 车牌号
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 上午11:40:21
	 */
	VehicleActualSituationEntity queryByVehicleNo(String vehicleNo);
	
	/**
	 * 
	 * 根据车牌号修改车辆剩余重量和剩余体积
	 * @param vehicleActualSituationEntity 车辆实际情况实体
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 下午1:54:39
	 */
	boolean updateWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity);
	
	/**
	 * 
	 * 根据已有自有车查询出车辆的实际情况
	 * @param ownVehicleRegionDtos 已查询出的自有车List
	 * @author 038590-foss-wanghui
	 * @date 2012-11-9 下午5:50:59
	 */
	List<VehicleActualSituationEntity> queryListByVehicleNo(List<OwnVehicleRegionDto> ownVehicleRegionDtos);
	
	/**
	 * 
	 * 按分页查询车辆实况表
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-17 下午6:10:31
	 */
	List<VehicleActualSituationEntity> queryByPage(int start, int limit);
	
	
	/**
	 * 
	 * 根据车牌号清空载重和载空
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 上午8:41:31
	 */
	int updateWV2EmptyByVehicleNo(VehicleActualSituationEntity actualSituationEntity);
	
	/**
	 * 
	 * 根据车牌号增加车载信息
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:20:38
	 */
	boolean addWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity);
}