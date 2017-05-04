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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/WayBillOtherForBseDto.java
 * 
 * FILE NAME        	: WayBillOtherForBseDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * WayBillOtherForBSEDto
 * @author ibm-wangfei
 * @date Dec 24, 2012 5:00:34 PM
 */
public class WayBillOtherForBseDto implements Serializable {
	private static final long serialVersionUID = -5760989116217799382L;
	/**
	 * 开单时间
	 */
	private Date billTime;
	/**
	 * 预计派送/提货时间
	 */
	private Date preCustomerPickupTime;
	/**
	 * 开单组织
	 */
	
	
	
	private  String actualPath;
	
	public String getActualPath() {
		return actualPath;
	}

	public void setActualPath(String actualPath) {
		this.actualPath = actualPath;
	}

	private String createOrgCode;
	/**
	 * 干线走货路径 -- 配载线路virtual code
	 */
	private String loadLineCode;
	/**
	 * 到达部门 -- 提货网点
	 */
	private String customerPickupOrgCode;
	/**
	 * 仓储费
	 */
	private BigDecimal storageCharge;
	/**
	 * 运单状态
	 */
	private String waybillStatus;
	/**
	 * 预付费保密
	 */
	private String secretPrepaid;
	
	
	/**
	 * @return the billTime
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime the billTime to see
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to see
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the loadLineCode
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * @param loadLineCode the loadLineCode to see
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to see
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return the storageCharge
	 */
	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	/**
	 * @param storageCharge the storageCharge to see
	 */
	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the preCustomerPickupTime
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * @param preCustomerPickupTime the preCustomerPickupTime to see
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public String getSecretPrepaid() {
		return secretPrepaid;
	}

	public void setSecretPrepaid(String secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}
}