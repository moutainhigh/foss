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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/SignVo.java
 * 
 * FILE NAME        	: SignVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/***
 * 签收出库Vo
 * @author foss-meiying
 * @date 2012-10-18 下午1:58:49
 * @since
 * @version
 */
public class SignVo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6283343887546011644L;
	private DopSignEntity dopEntity;
	
	public DopSignEntity getDopEntity() {
		return dopEntity;
	}

	public void setDopEntity(DopSignEntity dopEntity) {
		this.dopEntity = dopEntity;
	}

	/**
	 *  签收出库查询条件dto
	 */
	private SignDto signDto;
	/**
	 *  结算 签收确认收入服务使用DTO
	 */
	private LineSignDto lineSignDto;
	/**
	 * 订单号
	 */
	private String orderNo;
	//是否家装反签收
	private boolean rfcFlag;
	/**
	 * 子母件的dto
	 */
	private CzmSignDto czmSignDto;

	/**
	 * Gets the 签收出库查询条件dto.
	 *
	 * @return the 签收出库查询条件dto
	 */
	public SignDto getSignDto() {
		return signDto;
	}

	/**
	 * Sets the 签收出库查询条件dto.
	 *
	 * @param signDto the new 签收出库查询条件dto
	 */
	public void setSignDto(SignDto signDto) {
		this.signDto = signDto;
	}

	/**
	 * Gets the 结算 签收确认收入服务使用DTO.
	 *
	 * @return the 结算 签收确认收入服务使用DTO
	 */
	public LineSignDto getLineSignDto() {
		return lineSignDto;
	}

	/**
	 * Sets the 结算 签收确认收入服务使用DTO.
	 *
	 * @param lineSignDto the new 结算 签收确认收入服务使用DTO
	 */
	public void setLineSignDto(LineSignDto lineSignDto) {
		this.lineSignDto = lineSignDto;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 子母件dto
	 * @return czmSignDto
	 */
	public CzmSignDto getCzmSignDto() {
		return czmSignDto;
	}
	/**
	 * 子母件dto
	 * @return czmSignDto
	 */
	public void setCzmSignDto(CzmSignDto czmSignDto) {
		this.czmSignDto = czmSignDto;
	}
	
	public boolean getRfcFlag() {
		return rfcFlag;
	}

	public void setRfcFlag(boolean rfcFlag) {
		this.rfcFlag = rfcFlag;
	}
}