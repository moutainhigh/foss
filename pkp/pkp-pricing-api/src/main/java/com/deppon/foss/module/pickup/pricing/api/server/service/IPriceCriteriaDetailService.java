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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceCriteriaDetailService.java
 * 
 * FILE NAME        	: IPriceCriteriaDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;

/**
 * 
 * (定义计价方式明细实现接口)
 * @author 岳洪杰
 * @date 2012-10-13 上午11:47:22
 * @since
 * @version
 */
public interface IPriceCriteriaDetailService extends IService{
    
    /**
     * 
     * <p>
     * (根据主键集合ID查询相关计价明细实体信息)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-14 下午5:49:17
     * @param keys
     * @return
     * @see
     */
    List<PriceCriteriaDetailEntity> selectByPrimaryKeyList(List<String> keys);
}