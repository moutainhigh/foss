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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/service/ISynchronousOwnVehicleService.java
 * 
 * FILE NAME        	: ISynchronousOwnVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
/**
 * 用来同步LMS“公司车辆（厢式车、挂车、拖头）”到FOSS系统的抽取Service接口：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-29 下午5:02:10</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-29 下午5:02:10
 * @since
 * @version
 */
public interface ISynchronousOwnVehicleService extends IService {

    /**
     * <p>从LMS同步一个“公司车辆和停车计划信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:15
     * @param ownTruck “公司车辆”实体
     * @param vehicleUnavilable 停车计划
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException
     * @see
     */
    public int synchronousOwnVehiclePlanByLMS(OwnTruckEntity ownTruck, VehicleUnavilableEntity vehicleUnavilable) throws OwnVehicleException;
    
    /**
     * <p>新增一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:37
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @see
     */
    public int addOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException;
    
    /**
     * <p>根据“公司车辆（厢式车、挂车、拖头）”记录唯一标识作废（逻辑删除）一条“公司车辆（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:40
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @see
     */
    public int deleteOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException;
    
    /**
     * <p>修改一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:43
     * @param ownTruck “公司车辆（厢式车、挂车、拖头）”实体
     * @return 1：成功；-1：失败
     * @see
     */
    public int updateOwnVehicle(OwnTruckEntity ownTruck) throws OwnVehicleException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头）”单个实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午9:28:20
     * @param ownTruck 以“公司车辆（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“公司车辆（厢式车、挂车、拖头）”实体
     * @throws OwnVehicleException
     * @see
     */
    public OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity ownTruck) throws OwnVehicleException;
}
