/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcChangeChargeEntity.java
 * 
 * FILE NAME        	: WaybillRfcChangeChargeEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单变更费用
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:45:13
 */
public class WaybillRfcChangeChargeEntity extends BaseEntity {


    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 5785411896225171767L;

    /**
     *  运单变更明细ID
     */
    private String waybillrfcChangedetailId;

    /**
     *  费用项
     */
    private String chargeItems;

    /**
     *  变更前信息
     */
    private BigDecimal beforeRfcCharge;

    /**
     *  变更后信息
     */
    private BigDecimal afterRfcCharge;
    
    /**
     * 变更明细名称
     */
    private String chargeItemsName;
    
    /**
     * 是否可见
     */
    private String visible;

	
	/**
	 * @return the waybillrfcChangedetailId
	 */
	public String getWaybillrfcChangedetailId() {
		return waybillrfcChangedetailId;
	}

	
	/**
	 * @param waybillrfcChangedetailId the waybillrfcChangedetailId to set
	 */
	public void setWaybillrfcChangedetailId(String waybillrfcChangedetailId) {
		this.waybillrfcChangedetailId = waybillrfcChangedetailId;
	}

	
	/**
	 * @return the chargeItems
	 */
	public String getChargeItems() {
		return chargeItems;
	}

	
	/**
	 * @param chargeItems the chargeItems to set
	 */
	public void setChargeItems(String chargeItems) {
		this.chargeItems = chargeItems;
	}

	
	/**
	 * @return the beforeRfcCharge
	 */
	public BigDecimal getBeforeRfcCharge() {
		return beforeRfcCharge;
	}

	
	/**
	 * @param beforeRfcCharge the beforeRfcCharge to set
	 */
	public void setBeforeRfcCharge(BigDecimal beforeRfcCharge) {
		this.beforeRfcCharge = beforeRfcCharge;
	}

	
	/**
	 * @return the afterRfcCharge
	 */
	public BigDecimal getAfterRfcCharge() {
		return afterRfcCharge;
	}

	
	/**
	 * @param afterRfcCharge the afterRfcCharge to set
	 */
	public void setAfterRfcCharge(BigDecimal afterRfcCharge) {
		this.afterRfcCharge = afterRfcCharge;
	}


	public String getChargeItemsName() {
		return chargeItemsName;
	}


	public void setChargeItemsName(String chargeItemsName) {
		this.chargeItemsName = chargeItemsName;
	}


	public String getVisible() {
		return visible;
	}


	public void setVisible(String visible) {
		this.visible = visible;
	}
}