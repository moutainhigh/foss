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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillStockStatusDto.java
 * 
 * FILE NAME        	: WaybillStockStatusDto.java
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
 * FILE    NAME: WaybillStockStatusDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 运单库存状态
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-29 下午2:38:23
 */
public class WaybillStockStatusDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6683245916402044232L;

	/**
	 * -始发库存部门编码
	 */
	private String startStockOrgCode;

	/**
	 * 最终库存部门编码
	 */
	private String endStockOrgCode;

	/**
	 * 当前库存部门编码
	 */
	private String currentStockOrgCode;

	/**
	 * 有无出库记录（Y有 N无）
	 */
	private String stockRecord;

	/**
	 * 所属库存状态（开单库存、中转库存、到达库存等）
	 */
	private String result;

	/**
	 * @return the startStockOrgCode
	 */
	public String getStartStockOrgCode() {
		return startStockOrgCode;
	}

	/**
	 * @param startStockOrgCode
	 *            the startStockOrgCode to set
	 */
	public void setStartStockOrgCode(String startStockOrgCode) {
		this.startStockOrgCode = startStockOrgCode;
	}

	/**
	 * @return the endStockOrgCode
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * @param endStockOrgCode
	 *            the endStockOrgCode to set
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * @return the currentStockOrgCode
	 */
	public String getCurrentStockOrgCode() {
		return currentStockOrgCode;
	}

	/**
	 * @param currentStockOrgCode
	 *            the currentStockOrgCode to set
	 */
	public void setCurrentStockOrgCode(String currentStockOrgCode) {
		this.currentStockOrgCode = currentStockOrgCode;
	}

	/**
	 * @return the stockRecord
	 */
	public String getStockRecord() {
		return stockRecord;
	}

	/**
	 * @param stockRecord
	 *            the stockRecord to set
	 */
	public void setStockRecord(String stockRecord) {
		this.stockRecord = stockRecord;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

}