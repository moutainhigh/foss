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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILeasedVehicleDao.java
 * 
 * FILE NAME        	: ILeasedVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
/**
 * 用来操作交互“外请车（厢式车、挂车、拖头）”的数据库对应数据访问DAO接口：SUC-44、SUC-103、SUC-608
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 下午6:02:29</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 下午6:02:29
 * @since
 * @version
 */
public interface ILeasedVehicleDao {
    
    /**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:27
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedVehicle(LeasedTruckEntity leasedTruck);

    /**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:25
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedVehicleBySelective(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录唯一标识删除（物理删除）一条“外请车（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:30
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteLeasedVehicle(String id);

    /**
     * <p>根据ID集合批量作废（逻辑删除）“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-23 下午3:37:52
     * @param ids ID集合
     * @param active 启用或禁用
     * @param modifyUser 修改人
     * @return 影响记录数
     * @see
     */
     int deleteLeasedVehicleActiveStatusByBatchIds(String[] ids, String active, String modifyUser);
    
    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:20
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedVehicleBySelective(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:18
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedVehicle(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>根据记录唯一标识检索出符合条件的“外请车（厢式车、挂车、拖头）”唯一实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:58:51
     * @param id 记录唯一标识
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @see
     */
     LeasedTruckEntity queryLeasedVehicleBySelective(String id);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:58:51
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @see
     */
     LeasedTruckEntity queryLeasedVehicleBySelective(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @see
     */
     List<LeasedTruckEntity> queryLeasedVehicleListBySelective(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“外请车（厢式车、挂车、拖头）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午10:15:40
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
     * @see
     */
     long queryLeasedVehicleRecordCountBySelectiveCondition(LeasedTruckEntity leasedTruck);

    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @see
     */
     List<LeasedTruckEntity> queryLeasedVehicleListBySelectiveCondition(LeasedTruckEntity leasedTruck, int offset, int limit);
    
     /**
      * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
      * @author 189284-ZhangXu
      * @Date 2015-3-5  下午3:59:11
      * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
      * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
      * @see
      */
      List<LeasedTruckEntity> queryLeasedVehicleListBySelectiveCondition(LeasedTruckEntity leasedTruck);
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午3:56:03
     * @param vehicleNos 车牌号集合
     * @return VehicleBaseDto封装了传送对象集合
     * @see
     */
     List<VehicleBaseDto> queryLeasedVehicleBaseDtosByVehicleNos(String[] vehicleNos);
    
    /**
     * <p>提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleTypeCode 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param openVehicle 是否敞篷车（Y/N）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @see
     */
     List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status);
     /**
      * <p>（分页模糊）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
      * @author 100847-foss-GaoPeng
      * @date 2012-11-6 下午6:06:22
      * @param vehicleNo 车牌号
      * @param vehicleTypeCode 车型编码
      * @param driverName 司机姓名
      * @param driverPhone 司机电话
      * @param openVehicle 是否敞篷车（Y/N）
      * @param status Y:可用；N:不可用
      * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
      * @see
      */
     List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status, int offset, int limit);
     /**
      * <p>（分页模糊）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p>
      * @author 100847-foss-GaoPeng
      * @date 2012-10-24 上午10:15:40
      * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
      * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
      * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
      */
     long countQueryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status);
     
     /**
      * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
      * @author 310854
      * @date 2016-5-15
      */
     List<LeasedTruckEntity> queryLeasedServiceDateList(LeasedTruckEntity leasedTruck, int offset, int limit);
     
     /**
      * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
      * @author 310854
      * @date 2016-5-152012-10-24 上午10:15:40
      * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
      * @return 符合
      */
     long queryLeasedServiceDateList(LeasedTruckEntity leasedTruck);
     
     /**
      * 外清车【外请厢式车】绑定车队
      * @author 310854
      * @date 2016-5-16
      */
     int addLeasedServiceDateTream(List<LeasedTruckEntity> list);
     
     /**
      * 外清车【外请厢式车】释放车队
      * @author 310854
      * @date 2016-5-16
      */
     int deleteLeasedServiceDateTream(Map<String,Object> map);
     
     /**
      * 结算使用，通过车牌号获取司机电话
      * @author 310854
      * @date 2016-7-14
      */
     String queryLeasedVehicleDriverByVehicleNo(String vehicleNo);
     
     /**
      * 通过外请车车牌号获取服务车队
      * @param vehicleNo
      * @return
      */
     public LeasedTruckEntity queryLeasedVehicleTeam (String vehicleNo);
}
