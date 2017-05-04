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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillChangeDto.java
 * 
 * FILE NAME        	: WaybillChangeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;

/**
 * 
 * TODO（描述类的职责）
 * @author 102246-foss-shaohongliang
 * @date 2012-10-30 下午2:35:30
 */
public class WaybillChangeDto {
	//更改ID
	private String waybillChangeId;
	//运单号
	private String waybillNumber;
	//更改内容
	private String changeItems;
	//更改原因
	private String changeReason;
	//更改前到付
	private BigDecimal oldToPayAmount;
	//更改后到付
	private BigDecimal newToPayAmount;
	//更改前预付
	private BigDecimal oldPrePayAmount;
	//更改后预付
	private BigDecimal newPrePayAmount;
	//核销状态
	private String writeoffStatus;
    // 起草部门
    private String darftOrgName;
    // 起草人
    private String darfter;
	/**
	 * @return waybillChangeId : set the property waybillChangeId.
	 */
	public String getWaybillChangeId() {
		return waybillChangeId;
	}
	/**
	 * @param waybillChangeId : return the property waybillChangeId.
	 */
	public void setWaybillChangeId(String waybillChangeId) {
		this.waybillChangeId = waybillChangeId;
	}
	/**
	 * @return waybillNumber : set the property waybillNumber.
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * @param waybillNumber : return the property waybillNumber.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * @return changeItems : set the property changeItems.
	 */
	public String getChangeItems() {
		return changeItems;
	}
	/**
	 * @param changeItems : return the property changeItems.
	 */
	public void setChangeItems(String changeItems) {
		this.changeItems = changeItems;
	}
	/**
	 * @return changeReason : set the property changeReason.
	 */
	public String getChangeReason() {
		return changeReason;
	}
	/**
	 * @param changeReason : return the property changeReason.
	 */
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	/**
	 * @return oldToPayAmount : set the property oldToPayAmount.
	 */
	public BigDecimal getOldToPayAmount() {
		return oldToPayAmount;
	}
	/**
	 * @param oldToPayAmount : return the property oldToPayAmount.
	 */
	public void setOldToPayAmount(BigDecimal oldToPayAmount) {
		this.oldToPayAmount = oldToPayAmount;
	}
	/**
	 * @return newToPayAmount : set the property newToPayAmount.
	 */
	public BigDecimal getNewToPayAmount() {
		return newToPayAmount;
	}
	/**
	 * @param newToPayAmount : return the property newToPayAmount.
	 */
	public void setNewToPayAmount(BigDecimal newToPayAmount) {
		this.newToPayAmount = newToPayAmount;
	}
	/**
	 * @return oldPrePayAmount : set the property oldPrePayAmount.
	 */
	public BigDecimal getOldPrePayAmount() {
		return oldPrePayAmount;
	}
	/**
	 * @param oldPrePayAmount : return the property oldPrePayAmount.
	 */
	public void setOldPrePayAmount(BigDecimal oldPrePayAmount) {
		this.oldPrePayAmount = oldPrePayAmount;
	}
	/**
	 * @return newPrePayAmount : set the property newPrePayAmount.
	 */
	public BigDecimal getNewPrePayAmount() {
		return newPrePayAmount;
	}
	/**
	 * @param newPrePayAmount : return the property newPrePayAmount.
	 */
	public void setNewPrePayAmount(BigDecimal newPrePayAmount) {
		this.newPrePayAmount = newPrePayAmount;
	}
	/**
	 * @return writeoffStatus : set the property writeoffStatus.
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}
	/**
	 * @param writeoffStatus : return the property writeoffStatus.
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}
	/**
	 * @return darftOrgName : set the property darftOrgName.
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}
	/**
	 * @param darftOrgName : return the property darftOrgName.
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}
	/**
	 * @return darfter : set the property darfter.
	 */
	public String getDarfter() {
		return darfter;
	}
	/**
	 * @param darfter : return the property darfter.
	 */
	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}
}