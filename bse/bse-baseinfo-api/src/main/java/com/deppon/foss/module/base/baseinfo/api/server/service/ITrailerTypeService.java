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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ITrailerTypeService.java
 * 
 * FILE NAME        	: ITrailerTypeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleForTrailerTypeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.TrailerTypeException;
/**
 * 用来操作交互“挂车类型”的数据库对应“数据字典”数据访问Service接口：无SUC，提供给LMS同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-5 上午9:39:17</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-5 上午9:39:17
 * @since
 * @version
 */
public interface ITrailerTypeService extends IService {
    
    /**
     * <p>新增一个“LMS挂车类型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:32:53
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addTrailerType(VehicleForTrailerTypeDto vehicleForTrailerTypeDto) throws TrailerTypeException;
    
    /**
     * <p>根据“LMS挂车类型”记录唯一标识作废（逻辑删除）一条“LMS挂车类型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:32:57
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteTrailerType(VehicleForTrailerTypeDto vehicleForTrailerTypeDto) throws TrailerTypeException;
    
    /**
     * <p>修改一个“LMS挂车类型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:01
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateTrailerType(VehicleForTrailerTypeDto vehicleForTrailerTypeDto) throws TrailerTypeException;
}
