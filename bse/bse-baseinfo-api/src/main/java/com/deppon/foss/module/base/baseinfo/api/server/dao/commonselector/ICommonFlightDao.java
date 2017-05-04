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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonFlightDao.java
 * 
 * FILE NAME        	: ICommonFlightDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
 
/**
 * 公共查询组件--“航班信息”的数据库对应数据访问DAO接口
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-8 下午2:44:33
 */
public interface ICommonFlightDao {

    /**
     * 根据传入对象查询符合条件所有航班信息 
     * @author 101911-foss-zhouChunlai
    * @date 2013-1-8 下午2:44:44
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
    List<FlightEntity> queryFlightListByCondition(FlightDto dto,int limit,int start);
    
   
    /**
     * 统计总记录数 
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-8 下午2:53:10
     * @return 
     */
    Long countFlightListByCondition(FlightDto dto);
}
