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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonSmallZoneService.java
 * 
 * FILE NAME        	: ICommonSmallZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ServiceZoneDto;

/**
 * 集中接送货大、小区service接口
 * @author panGuangjun
 * @date 2012-12-03 下午5:45:30
 * @since
 * @version
 */
public interface ICommonSmallZoneService extends IService {
    /**
     * 根据传入对象查询符合条件所有集中接送货大、小区信息 
     * @author panGuangjun
     * @date 2012-12-03 下午5:45:30
     * @param entity 集中接送货小区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<ServiceZoneDto> queryServiceZones(SmallZoneEntity entity,int start,int limit);
    
    /**
     * 统计总记录数 
     * @author panGuangjun
     * @date 2012-12-03 下午5:45:30
     * @param entity 集中接送货小区实体
     * @return
     * @see
     */
     Long queryServiceRecordCount(SmallZoneEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有集中接送货小区信息 
     * @author panGuangjun
     * @date 2012-12-03 下午5:45:30
     * @param entity 集中接送货小区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<SmallZoneEntity> querySmallZones(SmallZoneEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author panGuangjun
     * @date 2012-12-03 下午5:45:30
     * @param entity 集中接送货小区实体
     * @return
     * @see
     */
     Long queryRecordCount(SmallZoneEntity entity);
    
}
