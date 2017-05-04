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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAirportDao.java
 * 
 * FILE NAME        	: IAirportDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
/**
 * 用来操作交互“机场信息”的数据库对应数据访问DAO接口：SUC-52
 * <p style="display:none">modifyairport</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 上午8:53:06</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 上午8:53:06
 * @since
 * @version
 */
public interface IAirportDao {

    /**
     * <p>新增一个“机场信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:10
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see
     */
     int addAirport(AirportEntity airport);

    /**
     * <p>新增一个“机场信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:12
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see
     */
     int addAirportBySelective(AirportEntity airport);

    /**
     * <p>根据“机场信息”记录唯一标识作废（物理删除）一条“机场信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:08
     * @param id id 记录唯一标识
     * @return 影响记录数
     * @see
     */
     int deleteAirport(String id);

    /**
     * <p>修改一个“机场信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:17
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see
     */
     int updateAirportBySelective(AirportEntity airport);

    /**
     * <p>修改一个“机场信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:19
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see
     */
     int updateAirport(AirportEntity airport);
    

    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:14
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return “机场信息”实体
     * @see
     */
     AirportEntity queryAirportBySelective(AirportEntity airport);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return 符合条件的“机场信息”实体列表
     * @see
     */
     List<AirportEntity> queryAirportListBySelective(AirportEntity airport);
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“机场信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return 符合条件的“机场信息”实体记录条数
     * @see
     */
     long queryAirportCountBySelectiveCondition(AirportEntity airport);
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“机场信息”实体列表
     * @see
     */
     List<AirportEntity> queryAirportListBySelectiveCondition(AirportEntity airport, int offset, int limit);
}
