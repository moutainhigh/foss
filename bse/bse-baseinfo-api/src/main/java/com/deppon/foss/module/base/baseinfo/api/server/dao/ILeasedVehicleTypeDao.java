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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILeasedVehicleTypeDao.java
 * 
 * FILE NAME        	: ILeasedVehicleTypeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
/**
 * 用来操作交互“车辆车型”的数据库对应数据访问DAO接口：SUC-109
 * <p style="display:none">modifyvehicleType</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午3:55:37</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午3:55:37
 * @since
 * @version
 */
public interface ILeasedVehicleTypeDao {
    
    /**
     * <p>新增一个“车辆车型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:48
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedVehicleType(VehicleTypeEntity vehicleType);

    /**
     * <p>新增一个“车辆车型”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:46
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType);
    
    /**
     * <p>根据“车辆车型”记录唯一标识删除（物理删除）一条“车辆车型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:50
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteLeasedVehicleType(String id);
    
    /**
     * <p>修改一个“车辆车型”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:43
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType);
    
    /**
     * <p>根据“车辆车型”记录ID集合作废（逻辑删除）多条“车辆车型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-23 下午5:52:27
     * @param modifyUser 修改人
     * @param active 启用或者禁用
     * @param ids 唯一标识集合
     * @return 影响记录数
     * @see
     */
     int updateLeasedVehicleTypeActiveStatusByBatchIds(String modifyUser, String active, List<String> ids);
    
    /**
     * <p>修改一个“车辆车型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:40
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedVehicleType(VehicleTypeEntity vehicleType);
    
    /**
     * <p>获取当前系统中此对象最大的序列号</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-12 上午11:10:24
     * @param vehicleType 车辆类型（厢式车/挂车）
     * @param vehicleSort 公司车或外请车
     * @return 当前最大的序列号
     * @see
     */
     BigDecimal queryLeasedVehicleTypeMaxSeq(String vehicleType, String vehicleSort);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:45
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @return “车辆车型”实体
     * @see
     */
     VehicleTypeEntity queryLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:37:46
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @return 符合条件的“车辆车型”实体列表
     * @see 
     */
     List<VehicleTypeEntity> queryLeasedVehicleTypeListBySelective(VehicleTypeEntity vehicleType);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“车辆车型”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param leasedDriver 以“车辆车型”实体承载的条件参数实体
     * @return 符合条件的“车辆车型”实体记录条数
     * @see
     */
     long queryLeasedVehicleTypeRecordCountBySelectiveCondition(VehicleTypeEntity vehicleType);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:37:46
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆车型”实体列表
     * @see 
     */
     List<VehicleTypeEntity> queryLeasedVehicleTypeListBySelectiveCondition(VehicleTypeEntity vehicleType,int offset,int limit);
     /** 
      * 查找是否存在序列号
      * @author 101911-foss-zhouChunlai
      * @date 2013-4-20 上午9:37:15
      */
     long queryLeasedVehicleSeqBySeq(BigDecimal seq);
}
