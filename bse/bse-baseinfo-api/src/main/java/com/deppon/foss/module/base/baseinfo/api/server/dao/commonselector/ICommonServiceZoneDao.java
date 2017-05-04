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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonServiceZoneDao.java
 * 
 * FILE NAME        	: ICommonServiceZoneDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ServiceZoneDto;
/**
 * 公共选择器--接送货大小区DAO接口
 * @author 078823-foss-panGuangJun
 * @date 2012-10-12 上午8:42:17
 */
public interface ICommonServiceZoneDao {
    /**
     * <p>根据条件有选择的检索出符合条件的大小区实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-28 下午8:46:55
     * @param aircraftType 以“大小区”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“大小区”实体列表
     */
     List<ServiceZoneDto> queryServiceZoneListByCondition(SmallZoneEntity smallZoneEntity, int offset, int limit);
    /**
     * <p>根据条件有选择的检索出符合条件的“大小区”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-28 下午8:46:55
     * @param aircraftType 以“大小区”实体承载的条件参数实体
     * @return 符合条件的“大小区”总数
     */
    Long  countServiceZoneListByCondition(SmallZoneEntity smallZoneEntity);
}
