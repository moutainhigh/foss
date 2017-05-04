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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IFlightDao.java
 * 
 * FILE NAME        	: IFlightDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
/**
 * 用来操作交互“航班信息”的数据库对应数据访问DAO接口：SUC-785 
 * <p style="display:none">modifyflight</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午11:52:34</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午11:52:34
 * @since
 * @version
 */
public interface IFlightDao {

    /**
     * <p>新增一个“航班信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:39
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see
     */
     int addFlight(FlightEntity flight);

    /**
     * <p>新增一个“航班信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:42
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see
     */
     int addFlightBySelective(FlightEntity flight);

    /**
     * <p>根据“航班信息”记录唯一标识作废（物理删除）一条“航班信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:36
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteFlight(String id);

    /**
     * <p>修改一个“航班信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:47
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see
     */
     int updateFlightBySelective(FlightEntity flight);

    /**
     * <p>修改一个“航班信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:49
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see
     */
     int updateFlight(FlightEntity flight);

    /**
     * <p>根据条件有选择的检索出符合条件的“航班信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:45
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return “航班信息”实体
     * @see
     */
     FlightEntity queryFlightBySelective(FlightEntity flight);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航班信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return 符合条件的“航班信息”实体列表
     * @see
     */
     List<FlightEntity> queryFlightListBySelective(FlightEntity flight);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“航班信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return 符合条件的“航班信息”实体记录条数
     * @see
     */
     long queryFlightCountBySelectiveCondition(FlightEntity flight);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航班信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航班信息”实体列表
     * @see
     */
     List<FlightEntity> queryFlightListBySelectiveCondition(FlightEntity flight, int offset, int limit);
}
