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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ModifyBillWriteoffDto.java
 * 
 * FILE NAME        	: ModifyBillWriteoffDto.java
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
import java.util.List;

/**
 * 更改单DTO
 * 
 * @author 026113-foss-linwensheng
 * 
 */
/**
 * @author Administrator
 *
 */
public class ModifyBillWriteoffDto implements Serializable {

	/**
	 * 更改单序列号
	 */
	private static final long serialVersionUID = -5450085240043617968L;

	// 更改单受理开始日期
	private Date acceptStartDate;

	// 更改单受理结速日期
	private Date acceptEndDate;

	// 大区
	private String largeArea;

	// 小区
	private String smallArea;

	// 核销状态
	private String writeoffStatus;

	// 运单号集合
	private List<String> waybillNumbers;

	// 起草部门编码
	private String darftOrgCode;
	
	
	private String  empCode;
	
	//运单开单时间
	private Date createTime;
	
	//更改单发起时间
	private Date draftTime;
	
	//更改单受理时间
	private Date rfcOperateTime;
	
	//运单出库时间
	private Date signTime;
	
	//大区编码
	private String largeAreaCode;
	//内部要求  、外部要求
	private String changeType;
	
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getLargeAreaCode() {
		return largeAreaCode;
	}

	public void setLargeAreaCode(String largeAreaCode) {
		this.largeAreaCode = largeAreaCode;
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

	/**
	 * 起草部门集合
	 */
	private List<String> darftOrgCodeList;

	// 查询状态：查询已受理
	private String status;

	// 备注
	private String writeOffNote;
	
	//变更金额
	private BigDecimal changeAmount;
	
	//--------------快递新增属性
	/**
	 * 产品类型
	 */
	private List<String> productType;


	
	public List<String> getProductType() {
		return productType;
	}

	public void setProductType(List<String> productType) {
		this.productType = productType;
	}

	public List<String> getWaybillNumbers() {
		return waybillNumbers;
	}

	public void setWaybillNumbers(List<String> waybillNumbers) {
		this.waybillNumbers = waybillNumbers;
	}



	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public Date getAcceptStartDate() {
		return acceptStartDate;
	}

	public void setAcceptStartDate(Date acceptStartDate) {
		this.acceptStartDate = acceptStartDate;
	}

	public Date getAcceptEndDate() {
		return acceptEndDate;
	}

	public void setAcceptEndDate(Date acceptEndDate) {
		this.acceptEndDate = acceptEndDate;
	}

	public String getLargeArea() {
		return largeArea;
	}

	public void setLargeArea(String largeArea) {
		this.largeArea = largeArea;
	}

	public String getSmallArea() {
		return smallArea;
	}

	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}

	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getDarftOrgCodeList() {
		return darftOrgCodeList;
	}

	public void setDarftOrgCodeList(List<String> darftOrgCodeList) {
		this.darftOrgCodeList = darftOrgCodeList;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
}