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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CouponInfoResultDto.java
 * 
 * FILE NAME        	: CouponInfoResultDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 优惠劵验证结果
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-15 下午4:41:42, </p>
 * @author foss-sunrui
 * @date 2012-11-15 下午4:41:42
 * @since
 * @version
 */
public class CouponInfoResultDto implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 6585430175285195778L;
    //是否能够使用
    private boolean isCanUse;
    //优惠金额
    private BigDecimal couponAmount;
    //不能使用的原因
    private String canNotUseReason;

	/**
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
    //优惠券或折扣抵扣类型
    private String deductibleType;
	
	/**
	 * @return the isCanUse .
	 */
	public boolean isCanUse() {
		return isCanUse;
	}
	
	/**
	 *@param isCanUse the isCanUse to set.
	 */
	public void setCanUse(boolean isCanUse) {
		this.isCanUse = isCanUse;
	}
	
	/**
	 * @return the couponAmount .
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}
	
	/**
	 *@param couponAmount the couponAmount to set.
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}
	
	/**
	 * @return the canNotUseReason .
	 */
	public String getCanNotUseReason() {
		return canNotUseReason;
	}
	
	/**
	 *@param canNotUseReason the canNotUseReason to set.
	 */
	public void setCanNotUseReason(String canNotUseReason) {
		this.canNotUseReason = canNotUseReason;
	}

	public String getDeductibleType() {
		return deductibleType;
	}

	public void setDeductibleType(String deductibleType) {
		this.deductibleType = deductibleType;
	}
	
}