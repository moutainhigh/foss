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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IVehicleUnavilableDao.java
 * 
 * FILE NAME        	: IVehicleUnavilableDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
/**
 * 用来操作交互“公司车停车计划”的数据库对应数据访问DAO接口：无SUC
 * <p style="display:none">modifyvehicleUnavilable</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午9:56:10</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午9:56:10
 * @since
 * @version
 */
public interface IVehicleUnavilableDao {

    /**
     * <p>新增一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:57:46
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addVehicleUnavilable(VehicleUnavilableEntity vehicleUnavilable);

    /**
     * <p>新增一个“公司车停车计划”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:57:58
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addVehicleUnavilableBySelective(VehicleUnavilableEntity vehicleUnavilable);
    
    /**
     * <p>根据“公司车停车计划”记录唯一标识作废一条“公司车停车计划”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:57:17
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see
     */
     int deleteVehicleUnavilable(String id);

    /**
     * <p>修改一个“公司车停车计划”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:58:46
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateVehicleUnavilableBySelective(VehicleUnavilableEntity vehicleUnavilable);

    /**
     * <p>修改一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:58:49
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateVehicleUnavilable(VehicleUnavilableEntity vehicleUnavilable);
    
    /**
     * <p>根据“公司车停车计划”记录唯一标识查询出一条“公司车停车计划”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:58:01
     * @param id 记录唯一标识
     * @return “公司车停车计划”实体
     * @see
     */
     VehicleUnavilableEntity queryVehicleUnavilable(String id);

    /**
     * <p>根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午9:58:43
     * @param vehicleUnavilable 以“外请车厢式车”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车厢式车”实体列表
     * @see
     */
     List<VehicleUnavilableEntity> queryVehicleUnavilableListBySelectiveCondition(VehicleUnavilableEntity vehicleUnavilable, int offset, int limit);
}
