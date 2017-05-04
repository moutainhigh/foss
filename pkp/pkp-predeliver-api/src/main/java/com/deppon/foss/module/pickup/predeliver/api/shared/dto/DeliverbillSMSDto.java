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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DeliverbillDto.java
 * 
 * FILE NAME        	: DeliverbillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 派送单短信Dto.
 * 
 * @author 
 * @date 
 * @since
 * @version
 */
public class DeliverbillSMSDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 车辆车牌号. */
	private String vehicleNo;
	
	/** 司机电话号码. */
	private String driverTel;

	/** 派送单编号. */
	private String deliverbillNo;

	/** 派送单状态. */
	private String status;
	
	/** 总票数. */
	private String totalGoodsQty;

	/** 特殊标记货物票数. */
	private String specialAddressNum;
	
	/** 总件数. */
	private String waybillDoodsQty;
	
	/** 总到付金额. */
	private BigDecimal payAmountTotal;
	
	/** 送货日期. */
	private String DeliveryDate;
	
	/** 装载率. */
	private String loadingRate;
	
	
	/** 操作人编码. */
	private String operatorNo;
	
	/** 操作人. */
	private String operator;
	
	/** 操作部门编码. */
	private String operateOrgCode;
	
	/** 操作部门. */
	private String operateOrgName;
	
	/** 通知类型. */
	private String noticeType;
	
	/** 接收人姓名. */
	private String consignee;
	
	/** 接收人手机号. */
	private String mobile;
	
	/** 操作时间. */
	private Date operateTime;
	
	/** 模块名称. */
	private String moduleName;
	
	/** 短信内容  . */
	private String noticeContent;
	
	/** 运单号. */
	private String waybillNo;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

//	public String getDriverName() {
//		return driverName;
//	}
//
//	public void setDriverName(String driverName) {
//		this.driverName = driverName;
//	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalGoodsQty() {
		return totalGoodsQty;
	}

	public void setTotalGoodsQty(String totalGoodsQty) {
		this.totalGoodsQty = totalGoodsQty;
	}

	public BigDecimal getPayAmountTotal() {
		return payAmountTotal;
	}

	public void setPayAmountTotal(BigDecimal payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}

	public String getLoadingRate() {
		return loadingRate;
	}

	public void setLoadingRate(String loadingRate) {
		this.loadingRate = loadingRate;
	}

//	public String getCreateUserName() {
//		return createUserName;
//	}
//
//	public void setCreateUserName(String createUserName) {
//		this.createUserName = createUserName;
//	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getSpecialAddressNum() {
		return specialAddressNum;
	}

	public void setSpecialAddressNum(String specialAddressNum) {
		this.specialAddressNum = specialAddressNum;
	}

	public String getWaybillDoodsQty() {
		return waybillDoodsQty;
	}

	public void setWaybillDoodsQty(String waybillDoodsQty) {
		this.waybillDoodsQty = waybillDoodsQty;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	
}