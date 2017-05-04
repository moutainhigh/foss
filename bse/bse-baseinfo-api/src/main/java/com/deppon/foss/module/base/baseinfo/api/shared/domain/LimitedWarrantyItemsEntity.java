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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LimitedWarrantyItemsEntity.java
 * 
 * FILE NAME        	: LimitedWarrantyItemsEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 限保物品
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class LimitedWarrantyItemsEntity extends BaseEntity {

    private static final long serialVersionUID = -3967231351861398828L;

    /**
     * 数据版本号
     */
    private Long versionNo;

    /**
     * 虚拟编码
     */
    private String virtualCode;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 限保金额
     */
    private BigDecimal limitedAmount;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private String active;

    public Long getVersionNo() {
	return versionNo;
    }

    public void setVersionNo(Long versionNo) {
	this.versionNo = versionNo;
    }

    public String getVirtualCode() {
	return virtualCode;
    }

    public void setVirtualCode(String virtualCode) {
	this.virtualCode = virtualCode;
    }

    public String getGoodsName() {
	return goodsName;
    }

    public void setGoodsName(String goodsName) {
	this.goodsName = goodsName;
    }

    public BigDecimal getLimitedAmount() {
	return limitedAmount;
    }

    public void setLimitedAmount(BigDecimal limitedAmount) {
	this.limitedAmount = limitedAmount;
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }

    public String getActive() {
	return active;
    }

    public void setActive(String active) {
	this.active = active;
    }
}
