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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonOwnDriverService.java
 * 
 * FILE NAME        	: ICommonOwnDriverService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: OwnDriverService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwnDriverVo;

/**
 * 公司司机service接口定义
 * @author panGuangJun
 * @date 2012-12-3 上午8:22:08
 */
public interface ICommonOwnDriverService {

    /**
     * <p>根据条件有选择的检索出符合条件的“司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author panGuangjun
     * @date 2012-12-03 下午4:24:23
     * @param ownDriver 以“公司司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司司机”实体列表
     * @see
     */
     List<OwnDriverEntity> queryOwnDriverByCondition(OwnDriverEntity ownDriver, int offset, int limit);
    
    /**
     * <p>根据条件来获取公司司机相关的数据实体和其相关联的其他信息的总数</p> 
     * @author panGuangjun
     * @date  下午5:20:05
     * @param driverCode 公司员工工号
     * @return DriverAssociationDto封装了的传送对象
     * @see
     */
     long queryOwnDriverRecordByCondition(OwnDriverEntity ownDriver);
    

    /**
     * <p>根据条件有选择的检索出符合条件的“司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author panGuangjun
     * @date 2012-12-03 下午4:24:23
     * @param ownDriver 以“公司司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司司机”实体列表
     * @see
     */
     List<DriverDto> queryDriverByCondition(OwnDriverVo driverVo, int offset, int limit);
    
    /**
     * <p>根据条件来获取公司司机相关的数据实体和其相关联的其他信息的DTO</p> 
     * @author panGuangjun
     * @date  下午5:20:05
     * @param driverCode 公司员工工号
     * @return DriverAssociationDto封装了的传送对象
     * @see
     */
     long queryDriverRecordByCondition(OwnDriverEntity ownDriver);
}
