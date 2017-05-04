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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IOwnVehicleDao.java
 * 
 * FILE NAME        	: IOwnVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
/**
 * 用来操作交互“公司车辆”的数据库对应数据访问DAO接口：SUC-785
 * <p style="display:none">modifyownTruck</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午2:31:52</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午2:31:52
 * @since
 * @version
 */
public interface IOwnVehicleDao {
    
    /**
     * <p>新增一个“公司车辆”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:32:03
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see
     */
     int addOwnVehicle(OwnTruckEntity ownTruck);

    /**
     * <p>新增一个“公司车辆”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:32:01
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see
     */
     int addOwnVehicleBySelective(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据“公司车辆”记录唯一标识作废（物理删除）一条“公司车辆”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:32:05
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteOwnVehicle(String id);

    /**
     * <p>修改一个“公司车辆”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:57
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see
     */
     int updateOwnVehicleBySelective(OwnTruckEntity ownTruck);

    /**
     * <p>修改一个“公司车辆”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:54
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see
     */
     int updateOwnVehicle(OwnTruckEntity ownTruck);
     
     /**
      * <p>作废公司车辆</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-5-29 下午8:46:52
      * @param ownTruck “公司车辆”实体
      * @return
      * @see
      */
     int deleteOwnVehicleByVehicleNo(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据“公司车辆”记录唯一标识查询出一条“公司车辆”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:59
     * @param id 记录唯一标识
     * @return “公司车辆”实体
     * @see
     */
     OwnTruckEntity queryOwnVehicleBySelective(String id);
    
    /**
     * <p>根据条件有选择的查询一个“公司车辆”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-26 下午4:28:06
     * @param ownTruck 以“公司车辆”实体为参数
     * @return “公司车辆”实体
     * @see
     */
     OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param ownTruck 以“外请车厢式车”实体承载的条件参数实体
     * @return 符合条件的“外请车厢式车”实体列表
     * @see
     */
     List<OwnTruckEntity> queryOwnVehicleListBySelective(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“公司车辆”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午11:35:56
     * @param ownTruck 以“公司车辆”实体承载的条件参数实体
     * @return 符合条件的“公司车辆”实体记录条数
     * @see
     */
     long queryOwnVehicleCountBySelectiveCondition(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param ownTruck 以“外请车厢式车”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车厢式车”实体列表
     * @see
     */
     List<OwnTruckEntity> queryOwnVehicleListBySelectiveCondition(OwnTruckEntity ownTruck, int offset, int limit);
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:44:23
     * @param vehicleNos 车牌号集合
     * @param orgId 部门编号
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @param excludeVehicleType 排除的车辆类型
     * @return VehicleBaseDto封装了传送对象集合
     * @see
     */
     List<VehicleBaseDto> queryOwnVehicleBaseDtoListByVehicleNos(
	    List<String> vehicleNos, String orgId, String vehicleTypeCode, String status, int offset, int limit, String excludeVehicleType);
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合记录总数</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:44:23
     * @param vehicleNos 车牌号集合
     * @param orgId 部门编号
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态
     * @return
     * @see
     */
     Long queryOwnVehicleBaseDtoCountByVehicleNos(
	    List<String> vehicleNos, String orgId, String vehicleTypeCode, String status);
}
