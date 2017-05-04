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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArriveSheetWaybillAddPropertyDto.java
 * 
 * FILE NAME        	: ArriveSheetWaybillAddPropertyDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 运单信息 与查询到达联详细信息Dto
 * @author 097972-foss-dengtingting
 * @date 2012-12-25 下午2:22:35
 */
public class ArriveSheetWaybillAddPropertyDto extends WaybillEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	//发货网点电话
	private String receiveDepTelephone;
	
	//提货网点电话
	private String customerPickupTelephone;
	
	//到达联制单人
	private String arriveSheetCreateUserCode;
	
	//到达联制时间
	private Date arriveSheetCreateDate;
	
	//到达联制单部门
	private String deptName;
	
	/*
	*省份
    */	
    private String customerPickupProvCode;
	/*
	*新增提货网点对应的城市的CODE
    */	
    private String customerPickupCityCode;
	
	//未出库件数
	private Integer arriveNotoutGoodsQty;
	//结清状态
	private String settleStatus;
	//保管费
	private BigDecimal storageCharge;
	// 发货网点地址
	private String receiveOrgAddress;
	// 收货网点地址
	private String customerPickupOrgAddress;
	
	// 收货客户是否大客户
	private String receiveBigCustomer;
	// 发货客户是否大客户
	private String deliveryBigCustomer;
	 
	// 运单费用明细
    private List<WaybillChargeDtlPrintDto> waybillChargeDtlPrintDtos;
    
    //开单人
    private String billCreateUserName;
    //开单时间
    private Date billCreateTime;
    //打印次数
    private Integer printNum;   
    //订单来源
    private String orderChannel;
    /**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;

    
	public String getBillCreateUserName() {
		return billCreateUserName;
	}

	public void setBillCreateUserName(String billCreateUserName) {
		this.billCreateUserName = billCreateUserName;
	}

	public Date getBillCreateTime() {
		return billCreateTime;
	}

	public void setBillCreateTime(Date billCreateTime) {
		this.billCreateTime = billCreateTime;
	}

	public Integer getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
    
	/**
	 * Gets the settleStatus.
	 * 
	 * @return the settleStatus
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	/**
	 * Sets the settleStatus.
	 * 
	 * @param settleStatus the settleStatus to see
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	/**
	 * Gets the waybillChargeDtlPrintDtos.
	 * 
	 * @return the waybillChargeDtlPrintDtos
	 */
	public List<WaybillChargeDtlPrintDto> getWaybillChargeDtlPrintDtos() {
		return waybillChargeDtlPrintDtos;
	}

	/**
	 * Sets the waybillChargeDtlPrintDtos.
	 * 
	 * @param waybillChargeDtlPrintDtos the waybillChargeDtlPrintDtos to see
	 */
	public void setWaybillChargeDtlPrintDtos(List<WaybillChargeDtlPrintDto> waybillChargeDtlPrintDtos) {
		this.waybillChargeDtlPrintDtos = waybillChargeDtlPrintDtos;
	}

	/**
	 * Gets the receiveDepTelephone.
	 * 
	 * @return the receiveDepTelephone
	 */
	public String getReceiveDepTelephone() {
		return receiveDepTelephone;
	}

	/**
	 * Sets the receiveDepTelephone.
	 * 
	 * @param receiveDepTelephone the receiveDepTelephone to see
	 */
	public void setReceiveDepTelephone(String receiveDepTelephone) {
		this.receiveDepTelephone = receiveDepTelephone;
	}

	/**
	 * Gets the customerPickupTelephone.
	 * 
	 * @return customerPickupTelephone : return the property
	 *         customerPickupTelephone.
	 */
	public String getCustomerPickupTelephone() {
		return customerPickupTelephone;
	}

	/**
	 * Sets the customerPickupTelephone.
	 * 
	 * @param customerPickupTelephone : set the property
	 *            customerPickupTelephone.
	 */
	public void setCustomerPickupTelephone(String customerPickupTelephone) {
		this.customerPickupTelephone = customerPickupTelephone;
	}

	/**
	 * Gets the arriveSheetCreateUserCode.
	 * 
	 * @return arriveSheetCreateUserCode : return the property
	 *         arriveSheetCreateUserCode.
	 */
	public String getArriveSheetCreateUserCode() {
		return arriveSheetCreateUserCode;
	}

	/**
	 * Sets the arriveSheetCreateUserCode.
	 * 
	 * @param arriveSheetCreateUserCode : set the property
	 *            arriveSheetCreateUserCode.
	 */
	public void setArriveSheetCreateUserCode(String arriveSheetCreateUserCode) {
		this.arriveSheetCreateUserCode = arriveSheetCreateUserCode;
	}

	/**
	 * Gets the arriveSheetCreateDate.
	 * 
	 * @return arriveSheetCreateDate : return the property
	 *         arriveSheetCreateDate.
	 */
	public Date getArriveSheetCreateDate() {
		return arriveSheetCreateDate;
	}

	/**
	 * Sets the arriveSheetCreateDate.
	 * 
	 * @param arriveSheetCreateDate : set the property arriveSheetCreateDate.
	 */
	public void setArriveSheetCreateDate(Date arriveSheetCreateDate) {
		this.arriveSheetCreateDate = arriveSheetCreateDate;
	}

	/**
	 * Gets the deptName.
	 * 
	 * @return deptName : return the property deptName.
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the deptName.
	 * 
	 * @param deptName : set the property deptName.
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the arriveNotoutGoodsQty.
	 * 
	 * @return arriveNotoutGoodsQty : return the property arriveNotoutGoodsQty.
	 */
	public Integer getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}

	/**
	 * Sets the arriveNotoutGoodsQty.
	 * 
	 * @param arriveNotoutGoodsQty : set the property arriveNotoutGoodsQty.
	 */
	public void setArriveNotoutGoodsQty(Integer arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

	/**
	 * Gets the customerPickupProvCode.
	 * 
	 * @return the customerPickupProvCode
	 */
	public String getCustomerPickupProvCode() {
		return customerPickupProvCode;
	}

	/**
	 * Sets the customerPickupProvCode.
	 * 
	 * @param customerPickupProvCode the customerPickupProvCode to see
	 */
	public void setCustomerPickupProvCode(String customerPickupProvCode) {
		this.customerPickupProvCode = customerPickupProvCode;
	}

	public String getReceiveOrgAddress() {
		return receiveOrgAddress;
	}

	public void setReceiveOrgAddress(String receiveOrgAddress) {
		this.receiveOrgAddress = receiveOrgAddress;
	}

	public String getCustomerPickupOrgAddress() {
		return customerPickupOrgAddress;
	}

	public void setCustomerPickupOrgAddress(String customerPickupOrgAddress) {
		this.customerPickupOrgAddress = customerPickupOrgAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}


	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	public String getCustomerPickupCityCode() {
		return customerPickupCityCode;
	}

	public void setCustomerPickupCityCode(String customerPickupCityCode) {
		this.customerPickupCityCode = customerPickupCityCode;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	
	
}