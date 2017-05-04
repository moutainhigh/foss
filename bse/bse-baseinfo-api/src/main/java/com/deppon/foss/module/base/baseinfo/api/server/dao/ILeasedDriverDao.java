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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILeasedDriverDao.java
 * 
 * FILE NAME        	: ILeasedDriverDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
/**
 * 用来操作交互“外请车司机”的数据库对应数据访问DAO接口：SUC-211 
 * <p style="display:none">modifyleasedDriver</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午2:30:09</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午2:30:09
 * @since
 * @version
 */
public interface ILeasedDriverDao {

    /**
     * <p>新增一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param leasedDriver “外请车司机”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedDriver(LeasedDriverEntity leasedDriver);

    /**
     * <p>新增一个“外请车司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param leasedDriver “外请车司机”实体
     * @return 影响记录数
     * @see
     */
     int addLeasedDriverBySelective(LeasedDriverEntity leasedDriver);

    /**
     * <p>根据“外请车司机”记录唯一标识删除（物理删除）一条“外请车司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteLeasedDriver(String id);
    
    /**
     * <p>修改一个“外请车司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param leasedDriver “外请车司机”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedDriverBySelective(LeasedDriverEntity leasedDriver);

    /**
     * <p>修改一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param leasedDriver “外请车司机”实体
     * @return 影响记录数
     * @see
     */
     int updateLeasedDriver(LeasedDriverEntity leasedDriver);
    
    /**
     * <p>根据记录唯一标识查询“外请车司机”唯一激活可用状态实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-10 上午10:17:42
     * @param id 记录唯一标识
     * @return 符合条件的“外请车司机”实体
     * @see
     */
     LeasedDriverEntity queryLeasedDriverBySelective(String id);
    
    /**
     * <p>根据条件有选择的查询“外请车司机”唯一激活可用状态实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-30 下午8:32:32
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体
     * @see
     */
     LeasedDriverEntity queryLeasedDriverBySelective(LeasedDriverEntity leasedDriver);
    
    /**
     * <p>根据条件有选择的统计出符合条件的“外请车司机”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体记录条数
     * @see
     */
     List<LeasedDriverEntity> queryLeasedDriverListBySelective(LeasedDriverEntity leasedDriver);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“外请车司机”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体记录条数
     * @see
     */
     long queryLeasedDriverRecordCountBySelectiveCondition(LeasedDriverEntity leasedDriver);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车司机”实体列表
     * @see
     */
     List<LeasedDriverEntity> queryLeasedDriverListBySelectiveCondition(LeasedDriverEntity leasedDriver, int offset, int limit);
    
    /**
     * <p>提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午5:40:33
     * @param vehicleNo 外请车车牌号
     * @return DriverBaseDto封装了的传送对象集合
     * @throws LeasedDriverException
     * @see
     */
     List<DriverBaseDto> queryLeasedDriverBaseDtosByVehicleNos(String[] vehicleNos);
    
    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午7:04:42
     * @param driverNames 司机姓名集合 
     * @return DriverBaseDto封装的对象集合
     * @throws OwnDriverException
     * @see
     */
     List<DriverBaseDto> queryLeasedDriverBaseDtosByDriverNames(String[] driverNames);
     
      /**
     * 提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
     *
     * @author foss-qiaolifeng
     * @date 2013-7-8 下午3:03:52
     * @param vehicleNo 外请车车牌号
      * @return DriverBaseDto封装了的传送对象集合
      * @throws LeasedDriverException
     * @see
     */
    List<DriverBaseDto> queryLeasedDriverBaseDtosByTruckVehicleNos(String[] vehicleNos);
}
