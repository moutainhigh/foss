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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonAirlinesService.java
 * 
 * FILE NAME        	: ICommonAirlinesService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
/**
 * 公共查询组件--“航空公司”的数据库对应数据访问Service接口
 * @author 078823-foss-panGuangjun
 * @date 2012-12-7 上午8:50:06
 * @since
 * @version
 */
public interface ICommonAirlinesService {

    /**
     * 根据传入对象查询符合条件所有航空公司信息 
     * @author 078823-foss-panGuangjun
     * @date 2012-10-15 上午10:55:00
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */
    List<AirlinesEntity> queryAirlines(AirlinesEntity entity,int start,int limit);
    
    /**
     * 统计总记录数 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-7 上午10:55:00
     * @param entity 航空公司实体
     * @return Long
     */
    Long queryRecordCount(AirlinesEntity entity);
}
