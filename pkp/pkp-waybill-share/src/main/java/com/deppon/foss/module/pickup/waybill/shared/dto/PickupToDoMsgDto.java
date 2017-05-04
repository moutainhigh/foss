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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PickupToDoMsgDto.java
 * 
 * FILE NAME        	: PickupToDoMsgDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 定时提醒
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-18 下午10:15:34,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-18 下午10:15:34
 * @since
 * @version
 */
public class PickupToDoMsgDto implements Serializable {

	private static final long serialVersionUID = 1321670314476588187L;

	private String businessType;

	private String receiveRoleCode;

	private String receiveOrgCode;

	private int noDealNum;

	private String productCode;

	private String noDealData;
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getReceiveRoleCode() {
		return receiveRoleCode;
	}

	public void setReceiveRoleCode(String receiveRoleCode) {
		this.receiveRoleCode = receiveRoleCode;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public int getNoDealNum() {
		return noDealNum;
	}

	public void setNoDealNum(int noDealNum) {
		this.noDealNum = noDealNum;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getNoDealData() {
		return noDealData;
	}

	public void setNoDealData(String noDealData) {
		this.noDealData = noDealData;
	}

}