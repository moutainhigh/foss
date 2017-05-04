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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceRuleService.java
 * 
 * FILE NAME        	: IPriceRuleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;

/**
 * 
 * @Description: 计价规则服务
 * IPriceRuleService.java Create on 2013-2-2 上午11:25:24
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public interface IPriceRuleService extends IService{
    
    /**
     * @Description: 根据计价规则entryCode返回计价规则实体
     * @author FOSSDP-sz
     * @date 2013-2-2 上午11:27:01
     * @param entryCode
     * @param billDate
     * @return
     * @version V1.0
     */
    PriceRuleEntity getPriceRuleByCache(String ruleCode, Date billDate);
    /**
     * 
     * @Description: 刷新计价规则
     * @author FOSSDP-sz
     * @date 2013-2-22 上午10:57:25
     * @param ruleCode
     * @version V1.0
     */
    void refreshPriceRuleCache(String ruleCode);
}