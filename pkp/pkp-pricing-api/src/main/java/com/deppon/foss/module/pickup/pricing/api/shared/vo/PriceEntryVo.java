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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/GoodVo.java
 * 
 * FILE NAME        	: GoodVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;

/**
 * 
 * 计价条目vo
 * @author DP-Foss-YueHongJie
 * @date 2013-3-29 下午5:44:46
 * @version 1.0
 */
public class PriceEntryVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4418748013249748409L;
    /**
     * 计价条目
     */
    private PriceEntity priceEntity;
    /**
     * 计价条目LIST
     */
    private List<PriceEntity> priceEntryList;
    /**
     * 删除IDS
     */
    private List<String> priceEntryIds;
    
    /**
     * 获取 计价条目.
     *
     * @return the 计价条目
     */
    public PriceEntity getPriceEntity() {
        return priceEntity;
    }
    
    /**
     * 设置 计价条目.
     *
     * @param priceEntity the new 计价条目
     */
    public void setPriceEntity(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
    }
    
 
    
    /**
     * 获取 计价条目LIST.
     *
     * @return the 计价条目LIST
     */
    public List<PriceEntity> getPriceEntryList() {
        return priceEntryList;
    }

    
    /**
     * 设置 计价条目LIST.
     *
     * @param priceEntryList the new 计价条目LIST
     */
    public void setPriceEntryList(List<PriceEntity> priceEntryList) {
        this.priceEntryList = priceEntryList;
    }

    /**
     * 获取 删除IDS.
     *
     * @return the 删除IDS
     */
    public List<String> getPriceEntryIds() {
        return priceEntryIds;
    }
    
    /**
     * 设置 删除IDS.
     *
     * @param priceEntryIds the new 删除IDS
     */
    public void setPriceEntryIds(List<String> priceEntryIds) {
        this.priceEntryIds = priceEntryIds;
    }
}