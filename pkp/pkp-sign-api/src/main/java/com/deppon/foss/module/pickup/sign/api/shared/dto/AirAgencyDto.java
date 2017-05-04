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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirAgencyDto.java
 * 
 * FILE NAME        	: AirAgencyDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * AirAgencyDto
 * @author foss-meiying
 * @date 2013-1-12 上午10:09:57
 * @since
 * @version
 */
public class AirAgencyDto implements Serializable  {
	//序列
	private static final long serialVersionUID = -6522770559854851487L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 最终配载部门（判断是否为本部门）
	 */
	private String lastLoadOrgCode;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 外发单号
	 */
	private String externalBillNo;
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;	
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 最终配载部门（判断是否为本部门）.
	 *
	 * @return the 最终配载部门（判断是否为本部门）
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * Sets the 最终配载部门（判断是否为本部门）.
	 *
	 * @param lastLoadOrgCode the new 最终配载部门（判断是否为本部门）
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * Gets the 外发单号.
	 *
	 * @return the 外发单号
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	/**
	 * Sets the 外发单号.
	 *
	 * @param externalBillNo the new 外发单号
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}
	
	
}