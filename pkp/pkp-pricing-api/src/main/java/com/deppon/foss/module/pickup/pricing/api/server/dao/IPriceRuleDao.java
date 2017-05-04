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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;

public interface IPriceRuleDao {
    
	/**
     * @Description: 根据条件查询计价规则实体集合
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:38:11
     * @param code
     * @return
     * @version V1.0
     */
    List<PriceRuleEntity> selectPriceRuleByCondition(PriceRuleEntity record);
    /**
     * @Description: 根据计价规则CODE与适用时间查询计价规则实体
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:38:11
     * @param code
     * @param billDate
     * @return
     * @version V1.0
     */
    PriceRuleEntity queryPriceRuleByCode(String code, Date billDate);
    /**
     * @Description: 根据计价规则CODE查询计价规则实体集合
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:38:11
     * @param code
     * @return
     * @version V1.0
     */
    List<PriceRuleEntity> queryPriceRuleByCode(String code);
}