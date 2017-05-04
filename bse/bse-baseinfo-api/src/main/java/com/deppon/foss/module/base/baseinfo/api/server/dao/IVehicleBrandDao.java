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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IVehicleBrandDao.java
 * 
 * FILE NAME        	: IVehicleBrandDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity;
/**
 * 用来操作交互“车辆品牌”的数据库对应数据访问DAO接口：无SUC,由LMS调用同步
 * <p style="display:none">modifyvehicleBrand</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午3:54:32</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午3:54:32
 * @since
 * @version
 */
public interface IVehicleBrandDao {

    /**
     * <p>新增一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:48
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see
     */
     int addVehicleBrand(VehicleBrandEntity vehicleBrand);

    /**
     * <p>新增一个“车辆品牌”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:45
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see
     */
     int addVehicleBrandBySelective(VehicleBrandEntity vehicleBrand);
    
    /**
     * <p>根据“车辆品牌”记录唯一标识作废一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:50
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteVehicleBrand(String id);
    
    /**
     * <p>修改一个“车辆品牌”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:39
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see
     */
     int updateVehicleBrandBySelective(VehicleBrandEntity vehicleBrand);

    /**
     * <p>修改一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:37
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see
     */
     int updateVehicleBrand(VehicleBrandEntity vehicleBrand);

    /**
     * <p>根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:41
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @return “车辆品牌”实体
     * @see
     */
     VehicleBrandEntity queryVehicleBrandBySelective(VehicleBrandEntity vehicleBrand);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:22:42
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @return 符合条件的“车辆品牌”实体列表
     * @see
     */
     List<VehicleBrandEntity> queryVehicleBrandListBySelective(VehicleBrandEntity vehicleBrand);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:22:42
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆品牌”实体列表
     * @see
     */
     List<VehicleBrandEntity> queryVehicleBrandBySelectiveCondition(VehicleBrandEntity vehicleBrand, int offset, int limit);
}
