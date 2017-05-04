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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ModifyBillWriteoffResultDto.java
 * 
 * FILE NAME        	: ModifyBillWriteoffResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 更改单返回dto
 * @author 026113-foss-linwensheng
 *
 */
public class ModifyBillWriteoffResultDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8298697590255789231L;

	// 更改ID
	private String waybillChangeId;

	// 运单号
	private String waybillNumber;

	// 更改内容
	private String changeItems;

	// 更改依据
	private String changeReason;
	
	//更改类型
	private String rfcType;

	// 更改前到付
	private BigDecimal oldToPayAmount;

	// 更改后到付
	private BigDecimal newToPayAmount;

	// 更改前预付
	private BigDecimal oldPrePayAmount;

	// 更改后预付
	private BigDecimal newPrePayAmount;

	// 核销状态
	private String writeoffStatus;
	
	//核销人姓名
	private String writeOffEmpName;
	
	// 核销时间
	private Date writeOffTime;

	// 起草部门
	private String darftOrgName;

	// 起草人
	private String darfter;
	
	//备注
	private String writeOffNote;
	
	
	
	//打印次数
	private String printCounts;
	
	
	//---------快递新增
	//产品类型
	private String productType;

	//运单开单时间
	private Date createTime;
	
	//更改单发起时间
	private Date draftTime;
	
	//更改单受理时间
	private Date rfcOperateTime;
	
	//运单出库时间
	private Date signTime;
	//更改类型
	private String rfcSource;
	//更改后的送货费
	private BigDecimal deliveryGoodsFeeNew;
	//更改前的送货费
	private BigDecimal deliveryGoodsFeeOld;
	//更改后的发货客户编码
	private String deliveryCustomerCodeNew;
	//更改前的发货客户编码
	private String deliveryCustomerCodeOld;
	//更改后的包装费
	private BigDecimal packageFeeNew;
	//更改后的包装费
	private BigDecimal packageFeeOld;
	//更改后的运输性质
	private String productCodeNew;
	//更改前的运输性质
	private String productCodeOld;
	
	public String getProductCodeNew() {
		return productCodeNew;
	}

	public void setProductCodeNew(String productCodeNew) {
		this.productCodeNew = productCodeNew;
	}

	public String getProductCodeOld() {
		return productCodeOld;
	}

	public void setProductCodeOld(String productCodeOld) {
		this.productCodeOld = productCodeOld;
	}

	public BigDecimal getDeliveryGoodsFeeNew() {
		return deliveryGoodsFeeNew;
	}

	public void setDeliveryGoodsFeeNew(BigDecimal deliveryGoodsFeeNew) {
		this.deliveryGoodsFeeNew = deliveryGoodsFeeNew;
	}

	public BigDecimal getDeliveryGoodsFeeOld() {
		return deliveryGoodsFeeOld;
	}

	public void setDeliveryGoodsFeeOld(BigDecimal deliveryGoodsFeeOld) {
		this.deliveryGoodsFeeOld = deliveryGoodsFeeOld;
	}

	public String getDeliveryCustomerCodeNew() {
		return deliveryCustomerCodeNew;
	}

	public void setDeliveryCustomerCodeNew(String deliveryCustomerCodeNew) {
		this.deliveryCustomerCodeNew = deliveryCustomerCodeNew;
	}

	public String getDeliveryCustomerCodeOld() {
		return deliveryCustomerCodeOld;
	}

	public void setDeliveryCustomerCodeOld(String deliveryCustomerCodeOld) {
		this.deliveryCustomerCodeOld = deliveryCustomerCodeOld;
	}

	public BigDecimal getPackageFeeNew() {
		return packageFeeNew;
	}

	public void setPackageFeeNew(BigDecimal packageFeeNew) {
		this.packageFeeNew = packageFeeNew;
	}

	public BigDecimal getPackageFeeOld() {
		return packageFeeOld;
	}

	public void setPackageFeeOld(BigDecimal packageFeeOld) {
		this.packageFeeOld = packageFeeOld;
	}

	public String getRfcSource() {
		return rfcSource;
	}

	public void setRfcSource(String rfcSource) {
		this.rfcSource = rfcSource;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

	public String getWaybillChangeId() {
		return waybillChangeId;
	}

	public void setWaybillChangeId(String waybillChangeId) {
		this.waybillChangeId = waybillChangeId;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public String getChangeItems() {
		return changeItems;
	}

	public void setChangeItems(String changeItems) {
		this.changeItems = changeItems;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public BigDecimal getOldToPayAmount() {
		return oldToPayAmount;
	}

	public void setOldToPayAmount(BigDecimal oldToPayAmount) {
		this.oldToPayAmount = oldToPayAmount;
	}

	public BigDecimal getNewToPayAmount() {
		return newToPayAmount;
	}

	public void setNewToPayAmount(BigDecimal newToPayAmount) {
		this.newToPayAmount = newToPayAmount;
	}

	public BigDecimal getOldPrePayAmount() {
		return oldPrePayAmount;
	}

	public void setOldPrePayAmount(BigDecimal oldPrePayAmount) {
		this.oldPrePayAmount = oldPrePayAmount;
	}

	public BigDecimal getNewPrePayAmount() {
		return newPrePayAmount;
	}

	public void setNewPrePayAmount(BigDecimal newPrePayAmount) {
		this.newPrePayAmount = newPrePayAmount;
	}

	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	public String getDarftOrgName() {
		return darftOrgName;
	}

	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	public String getDarfter() {
		return darfter;
	}

	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrintCounts() {
		return printCounts;
	}

	public void setPrintCounts(String printCounts) {
		this.printCounts = printCounts;
	}

	public String getRfcType() {
		return rfcType;
	}

	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	public Date getRfcOperateTime() {
		return rfcOperateTime;
	}

	public void setRfcOperateTime(Date rfcOperateTime) {
		this.rfcOperateTime = rfcOperateTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getWriteOffTime() {
		return writeOffTime;
	}

	public void setWriteOffTime(Date writeOffTime) {
		this.writeOffTime = writeOffTime;
	}

	public String getWriteOffEmpName() {
		return writeOffEmpName;
	}

	public void setWriteOffEmpName(String writeOffEmpName) {
		this.writeOffEmpName = writeOffEmpName;
	}


	

}