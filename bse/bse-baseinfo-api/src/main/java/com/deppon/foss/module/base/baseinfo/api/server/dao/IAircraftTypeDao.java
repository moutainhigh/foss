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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAircraftTypeDao.java
 * 
 * FILE NAME        	: IAircraftTypeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
/**
 * 用来操作交互“机型信息”的数据库对应数据访问DAO接口：SUC-785
 * <p style="display:none">modifyaircraftType</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午8:42:17</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午8:42:17
 * @since
 * @version
 */
public interface IAircraftTypeDao {
    
    /**
     * <p>新增一个“机型信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:42:59
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addAircraftType(AircraftTypeEntity aircraftType);

    /**
     * <p>新增一个“机型信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:43:03
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see
     */
     int addAircraftTypeBySelective(AircraftTypeEntity aircraftType);
    
    /**
     * <p>根据“机型信息”记录唯一标识作废一条“机型信息”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:42:55
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see
     */
     int deleteAircraftType(String id);
    
    /**
     * <p>修改一个“机型信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:43:13
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateAircraftTypeBySelective(AircraftTypeEntity aircraftType);

    /**
     * <p>修改一个“机型信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:43:16
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see
     */
     int updateAircraftType(AircraftTypeEntity aircraftType);

    /**
     * <p>根据条件有选择的检索出符合条件的“机型信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:43:10
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return “机型信息”实体
     * @see
     */
     AircraftTypeEntity queryAircraftTypeBySelective(AircraftTypeEntity aircraftType);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 上午8:46:55
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return 符合条件的“机型信息”实体列表
     * @see
     */
     List<AircraftTypeEntity> queryAircraftTypeListBySelective(AircraftTypeEntity aircraftType);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“机型信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return 符合条件的“机型信息”实体记录条数
     * @see
     */
     long queryAircraftTypeCountBySelectiveCondition(AircraftTypeEntity aircraftType);

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 上午8:46:55
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“机型信息”实体列表
     * @see
     */
     List<AircraftTypeEntity> queryAircraftTypeListBySelectiveCondition(AircraftTypeEntity aircraftType, int offset, int limit);
}
