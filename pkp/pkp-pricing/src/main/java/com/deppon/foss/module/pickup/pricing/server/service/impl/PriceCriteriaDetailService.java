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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceCriteriaDetailService.java
 * 
 * FILE NAME        	: PriceCriteriaDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCriteriaDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.google.inject.Inject;

/**
 * 
 * 计价明细service
 * @author DP-Foss-YueHongJie
 * @date 2012-12-25 下午4:15:42
 * @version 1.0
 */
public class PriceCriteriaDetailService implements IPriceCriteriaDetailService{
    /**
     *  区域接口
     */
    @Inject
    IRegionService regionService;
    /**
     * 计价明细DAO
     */
    @Inject
    IPriceCriteriaDetailDao priceCriteriaDetailDao;
    
    /**
     * 设置 计价明细DAO.
     *
     * @param priceCriteriaDetailDao the new 计价明细DAO
     */
    public void setPriceCriteriaDetailDao(IPriceCriteriaDetailDao priceCriteriaDetailDao) {
		this.priceCriteriaDetailDao = priceCriteriaDetailDao;
    }
    /**
     * 设置 区域接口.
     *
     * @param regionService the new 区域接口
     */
    public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
    }
    /**
     * 
     * <p>查询单个计价明细</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:16:03
     * 
     * @param keys
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCriteriaDetailService#selectByPrimaryKeyList(java.util.List)
     */
    @Override
    public List<PriceCriteriaDetailEntity> selectByPrimaryKeyList(
	    List<String> keys) {
	return priceCriteriaDetailDao.selectByPrimaryKeyList(keys);
    }
}