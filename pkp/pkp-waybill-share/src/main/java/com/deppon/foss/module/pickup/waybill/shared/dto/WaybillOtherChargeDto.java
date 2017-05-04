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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillOtherChargeDto.java
 * 
 * FILE NAME        	: WaybillOtherChargeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: chargeDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 运单费用明细DTO，包括其它费用
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-15 上午11:37:07
 */
public class WaybillOtherChargeDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 5995944840051739150L;

	// 其它费用ID
	private String id;

	// 费用编码
	private String code;

	// 金额
	private String amount;

	// 费用名称
	private String chargeName;

	// 归集类别
	private String type;

	// 描述
	private String descrition;

	// 上限
	private String upperLimit;

	// 下限
	private String lowerLimit;

	// 是否可修改
	private String isUpdate;

	// 是否可删除
	private String isDelete;

	public String getCode() {
		return code;
	}

	/**
	 * @return the id .
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the amount .
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the chargeName .
	 */
	public String getChargeName() {
		return chargeName;
	}

	/**
	 * @param chargeName
	 *            the chargeName to set.
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * @return the type .
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the descrition .
	 */
	public String getDescrition() {
		return descrition;
	}

	/**
	 * @param descrition
	 *            the descrition to set.
	 */
	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	/**
	 * @return the upperLimit .
	 */
	public String getUpperLimit() {
		return upperLimit;
	}

	/**
	 * @param upperLimit
	 *            the upperLimit to set.
	 */
	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	/**
	 * @return the lowerLimit .
	 */
	public String getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * @param lowerLimit
	 *            the lowerLimit to set.
	 */
	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * @return the isUpdate .
	 */
	public String getIsUpdate() {
		return isUpdate;
	}

	/**
	 * @param isUpdate
	 *            the isUpdate to set.
	 */
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * @return the isDelete .
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set.
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @param code
	 *            the code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

}