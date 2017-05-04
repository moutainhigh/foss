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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IDiscountOrgDao.java
 * 
 * FILE NAME        	: IDiscountOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;


public interface IDiscountOrgDao {
	/**
	 * 
	 * @Description: 根据主键删除
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:04:55
	 * @param id
	 * @return
	 * @version V1.0
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
     * @Description: 根据计费规则删除
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:05:37
     * @param id
     * @return
     * @version V1.0
     */
    int deleteByPriceValuationId(String id);
    /**
     * 
     * @Description: 插入数据
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:06:25
     * @param record
     * @return
     * @version V1.0
     */
    int insertSelective(DiscountOrgEntity record);
    /**
     * 
     * @Description: 根据主键查询
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:06:45
     * @param id
     * @return
     * @version V1.0
     */
    DiscountOrgEntity selectByPrimaryKey(String id);
    /**
     * 
     * @Description: 根据主键修改
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:07:05
     * @param record
     * @return
     * @version V1.0
     */
    int updateByPrimaryKeySelective(DiscountOrgEntity record);
    /**
     * 
     * @Description: 根据条件查询
     * @author FOSSDP-sz
     * @date 2013-3-8 上午10:07:53
     * @param record
     * @return
     * @version V1.0
     */
    List<DiscountOrgEntity> selectByCondition(DiscountOrgEntity record);
}