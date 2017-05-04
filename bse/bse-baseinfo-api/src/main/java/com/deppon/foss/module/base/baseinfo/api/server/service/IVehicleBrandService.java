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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IVehicleBrandService.java
 * 
 * FILE NAME        	: IVehicleBrandService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleBrandException;
/**
 * 用来操作交互“车辆品牌”的数据库对应数据访问Service接口：无SUC,由LMS调用同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-6 上午9:19:54</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-6 上午9:19:54
 * @since
 * @version
 */
public interface IVehicleBrandService extends IService {
    
    /**
     * <p>新增一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:09
     * @param vehicleBrand “车辆品牌”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @see
     */
     int addVehicleBrand(VehicleBrandEntity vehicleBrand, String createUser, boolean ignoreNull) throws VehicleBrandException;
    
    /**
     * <p>根据“车辆品牌”记录唯一标识作废（逻辑删除）一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:12
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteVehicleBrand(List<String> ids, String modifyUser) throws VehicleBrandException;
    
    /**
     * <p>修改一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:15
     * @param vehicleBrand “车辆品牌”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int updateVehicleBrand(VehicleBrandEntity vehicleBrand, String modifyUser,  boolean ignoreNull) throws VehicleBrandException;
    
    /**
     * <p>根据“车辆品牌”编码查询出一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 上午10:11:17
     * @param brandCode 品牌编码
     * @return “车辆品牌”实体
     * @see
     */
     VehicleBrandEntity queryVehicleBrandByBrandCode(String brandCode) throws VehicleBrandException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 上午9:34:59
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆品牌”实体列表
     * @throws VehicleBrandException
     * @see
     */
     List<VehicleBrandEntity> queryVehicleBrandBySelectiveCondition(VehicleBrandEntity vehicleBrand, int offset, int limit) throws VehicleBrandException;
}
