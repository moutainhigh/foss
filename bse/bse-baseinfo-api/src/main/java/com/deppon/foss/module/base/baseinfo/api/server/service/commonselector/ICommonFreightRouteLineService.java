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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonFreightRouteLineService.java
 * 
 * FILE NAME        	: ICommonFreightRouteLineService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonFreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonReightRouteLineCondition;
/**
 * 走货路径线路Service
 * @author 078823-foss-panGuangjun
 * @date 2012-12-7  下午6:12:16 
 * @version 1.0
 */
public interface ICommonFreightRouteLineService {
    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路</p> 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-7  下午6:20:14 
     * * @param freightRouteLine:查询条件 主要根据状态和名称进行查询
     * @return List<FreightRouteLineEntity>
     */
    List<CommonFreightRouteLineDto> queryFreightRouteLinesByCondtion(CommonReightRouteLineCondition freightRouteLine,int start,int limit);
    /**
     * 
     * <p>查询特定走货路径下的所有走货路径线路</p> 
     * @author 078823-foss-panGuangjun
     * @date 2012-12-7  下午6:20:14 
     * @param freightRouteLine:查询条件 主要根据状态和名称进行查询
     * @return long
     */
    Long countFreightRouteLinesByCondtion(CommonReightRouteLineCondition freightRouteLine);
}
