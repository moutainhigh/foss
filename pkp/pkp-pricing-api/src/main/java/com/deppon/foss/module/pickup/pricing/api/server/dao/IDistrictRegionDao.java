/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPriceRuleDao.java
 * 
 * FILE NAME        	: IPriceRuleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;

public interface IDistrictRegionDao {
	/**
	 * 
	 * @Description: 查询所有行政区域与时效、汽运、空运价格区域关系
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午2:13:39
	 * @param districtCode
	 * @return
	 * @version V1.0
	 */
	List<DistrictRegionEntity> selectByDistrictCode();
	/**
	 * 
	 * @Description: 根据行政区域CODE查询
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午2:13:39
	 * @param districtCode
	 * @return
	 * @version V1.0
	 */
	DistrictRegionEntity selectByDistrictCode(String districtCode);
    /**
     * 
     * @Description: 添加新记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:02
     * @param districtRegionEntity
     * @version V1.0
     */
    void insertDistrictRegion(DistrictRegionEntity districtRegionEntity);
    /**
     * 
     * @Description: 修改记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:09
     * @param districtRegionEntity
     * @version V1.0
     */
    void updateDistrictRegion(DistrictRegionEntity districtRegionEntity);
    /**
     * 
     * @Description: 删除记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:13
     * @param code
     * @version V1.0
     */
    void deleteDistrictRegion(String code);
}