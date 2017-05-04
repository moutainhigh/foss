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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CouponInfoDto.java
 * 
 * FILE NAME        	: CouponInfoDto.java
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
 * 优惠劵效验请求实体
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-15 下午4:36:49,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-15 下午4:36:49
 * @since
 * @version
 */
public class CouponInfoDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -726246716535045131L;

	// 优惠劵编码
	private String couponNumber;

	// 是否使用
	private boolean isUsed;

	// 运单信息实体
	private CouponWaybillInfoDto waybillInfo;
	private BigDecimal couponFree; 
    //优惠券优惠费用归集类型   仅供图片开单使用
    private String couponRankType;
   //优惠券备注   仅供图片开单使用
    private String remark;

     
	public BigDecimal getCouponFree() {
		return couponFree;
	}

	public void setCouponFree(BigDecimal couponFree) {
		this.couponFree = couponFree;
	}

	public String getCouponRankType() {
		return couponRankType;
	}

	public void setCouponRankType(String couponRankType) {
		this.couponRankType = couponRankType;
	}

	/**
	 * @return the couponNumber .
	 */
	public String getCouponNumber() {
		return couponNumber;
	}

	/**
	 * @param couponNumber
	 *            the couponNumber to set.
	 */
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	/**
	 * @return the isUsed .
	 */
	public boolean isUsed() {
		return isUsed;
	}

	/**
	 * @param isUsed
	 *            the isUsed to set.
	 */
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * @return the waybillInfo .
	 */
	public CouponWaybillInfoDto getWaybillInfo() {
		return waybillInfo;
	}

	/**
	 * @param waybillInfo
	 *            the waybillInfo to set.
	 */
	public void setWaybillInfo(CouponWaybillInfoDto waybillInfo) {
		this.waybillInfo = waybillInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}