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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IVehicleActualSituationEntityDao.java
 * 
 * FILE NAME        	: IVehicleActualSituationEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;



/**
 * 车辆车载信息DAO接口
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午11:37:51
 */
public interface IVehicleActualSituationEntityDao {

    /**
     * 
     * 添加车辆实况记录
     * @param 
     * @author 038590-foss-wanghui
     * @date 2012-12-6 上午11:05:08
     */
    int addVehicleSituation(VehicleActualSituationEntity record);

    /**
	 * 
	 * 查询
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 4:08:47 PM
	 */
    VehicleActualSituationEntity queryByVehicleNo(String vehicleNo);
    
    /**
     * 
     * 根据车牌号修改车辆剩余重量和剩余体积
     * @param 
     * @author 038590-foss-wanghui
     * @date 2012-11-3 下午1:48:46
     */
    int updateWVByVehicleNo(VehicleActualSituationEntity record);
    
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
	int updateWV2EmptyByVehicleNo(VehicleActualSituationEntity actualSituationEntities);
	
	/**
	 * 
	 * 根据车牌号增加车载信息（载重、载空和接送货票数）
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:21:35
	 */
	int addWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity);
	
	/**
	 * 
	 * 删除车辆实况
	 * @param vehicleNo 车牌号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-27 下午3:17:02
	 */
	int deleteVehicleSituation(String vehicleNo);
}