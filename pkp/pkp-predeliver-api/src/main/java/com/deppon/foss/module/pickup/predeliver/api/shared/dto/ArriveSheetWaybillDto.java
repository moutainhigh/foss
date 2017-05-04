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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArriveSheetWaybillDto.java
 * 
 * FILE NAME        	: ArriveSheetWaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 根据运单查询到达联DTO
 * @author 097972-foss-dengtingting
 * @date 2012-10-25 上午8:44:59
 */
public class ArriveSheetWaybillDto implements Serializable {
	private static final long serialVersionUID = 1L;

	// 到达未出库件数
	private Integer arriveNotoutGoodsQty;

	// 运单号
	private String waybillNo;

	// 到达件数
	private Integer arriveGoodsQty;

	// 已生成到达联件数
	private Integer generateGoodsQty;

	// 提货方式
	private String receiveMethod;

	// 交接单号
	private String handoverNo;

	// 收货客户名称
	private String receiveCustomerName;

	// 收货客户手机
	private String receiveCustomerMobilephone;

	// 收货客户电话
	private String receiveCustomerPhone;
	
	// 收货客户联系人名称
	private String receiveCustomerContact;

	// 预派送单号
	private String deliverbillId;

	// 货物名称
	private String goodsName;

	// 收货具体地址
	private String receiveCustomerAddress;
	// 目的站
	private String targetOrgCode;

	private String returnBillType; // 返单类别

	private String waybillrfcStatus; // 更改单状态

	private Integer goodsQtyTotal; // 开单总件数

	private String isPrinted; // 是否打印
	
	/**
	 * 创建开始时间
	 */
	private Date inStockTimeFrom;
	/**
	 * 创建结束时间
	 */
	private Date inStockTimeTo;

	// 运单是否有效
	private String active;

	private String orgCode;

	// 开单付款方式
	private String paidMethod;

	// 更改单状态
	private List<String> waybillRfcStatus;
	
	//是否付款
	private String isPay;
	
	//是否为本部门库存
	private String isSelfFlg;
	
	/**
	 * 选择的运单号列表
	 */
	private String[] arrayWaybillNos;
	
	// 打印时间
	private Date printTime;
	// 打印部门名称
	private String printOrgName;
	// 打印人编码
	private String printUserName;
	/**
	 * 打印次数
	 */
	private Integer printtimes; 
	
	// 更改单状态
	private List<String> arriveSheetStatus;
	/**
	 *  是否作废
	 */
	private String destroyed;
	private String lastLoadOrgCode;
	// 库存部门
	private String endStockOrgCode;
	// 库区
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}

	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}

	private Integer isPrintedStatus;
	//运输类型
	private String transportType;
	/**
	 * 到达时间起
	 */
	private Date arriveStartTime;
	/**
	 * 到达时间止
	 */
	private Date arriveEndTime;
	

	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	
	/**
	 * @return isSelfFlg : return the property isSelfFlg.
	 */
	public String getIsSelfFlg() {
		return isSelfFlg;
	}

	/**
	 * @param isSelfFlg : set the property isSelfFlg.
	 */
	public void setIsSelfFlg(String isSelfFlg) {
		this.isSelfFlg = isSelfFlg;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the handoverNo
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * @param handoverNo the handoverNo to see
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod the receiveMethod to see
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the deliverbillId
	 */
	public String getDeliverbillId() {
		return deliverbillId;
	}

	/**
	 * @param deliverbillId the deliverbillId to see
	 */
	public void setDeliverbillId(String deliverbillId) {
		this.deliverbillId = deliverbillId;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * @param receiveCustomerAddress the receiveCustomerAddress to see
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType the returnBillType to see
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * @return the waybillrfcStatus
	 */
	public String getWaybillrfcStatus() {
		return waybillrfcStatus;
	}

	/**
	 * @param waybillrfcStatus the waybillrfcStatus to see
	 */
	public void setWaybillrfcStatus(String waybillrfcStatus) {
		this.waybillrfcStatus = waybillrfcStatus;
	}

	/**
	 * @return the arriveNotoutGoodsQty
	 */
	public Integer getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}

	/**
	 * @param arriveNotoutGoodsQty the arriveNotoutGoodsQty to see
	 */
	public void setArriveNotoutGoodsQty(Integer arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

	/**
	 * @return the arriveGoodsQty
	 */
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	/**
	 * @param arriveGoodsQty the arriveGoodsQty to see
	 */
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	/**
	 * @return the generateGoodsQty
	 */
	public Integer getGenerateGoodsQty() {
		return generateGoodsQty;
	}

	/**
	 * @param generateGoodsQty the generateGoodsQty to see
	 */
	public void setGenerateGoodsQty(Integer generateGoodsQty) {
		this.generateGoodsQty = generateGoodsQty;
	}

	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode the targetOrgCode to see
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the isPrinted
	 */
	public String getIsPrinted() {
		return isPrinted;
	}

	/**
	 * @param isPrinted the isPrinted to see
	 */
	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to see
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod the paidMethod to see
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the waybillRfcStatus
	 */
	public List<String> getWaybillRfcStatus() {
		return waybillRfcStatus;
	}

	/**
	 * @param waybillRfcStatus the waybillRfcStatus to see
	 */
	public void setWaybillRfcStatus(List<String> waybillRfcStatus) {
		this.waybillRfcStatus = waybillRfcStatus;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	/**
	 * @return receiveCustomerContact : return the property receiveCustomerContact.
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact : set the property receiveCustomerContact.
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String[] getArrayWaybillNos() {
		return arrayWaybillNos;
	}

	public void setArrayWaybillNos(String[] arrayWaybillNos) {
		this.arrayWaybillNos = arrayWaybillNos;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public String getPrintOrgName() {
		return printOrgName;
	}

	public void setPrintOrgName(String printOrgName) {
		this.printOrgName = printOrgName;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getArriveSheetStatus() {
		return arriveSheetStatus;
	}

	public void setArriveSheetStatus(List<String> arriveSheetStatus) {
		this.arriveSheetStatus = arriveSheetStatus;
	}

	public String getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	public String getPrintUserName() {
		return printUserName;
	}

	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}

	public Date getInStockTimeFrom() {
		return inStockTimeFrom;
	}

	public void setInStockTimeFrom(Date inStockTimeFrom) {
		this.inStockTimeFrom = inStockTimeFrom;
	}

	public Date getInStockTimeTo() {
		return inStockTimeTo;
	}

	public void setInStockTimeTo(Date inStockTimeTo) {
		this.inStockTimeTo = inStockTimeTo;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public Integer getIsPrintedStatus() {
		return isPrintedStatus;
	}

	public void setIsPrintedStatus(Integer isPrintedStatus) {
		this.isPrintedStatus = isPrintedStatus;
	}

	public Date getArriveStartTime() {
		return arriveStartTime;
	}

	public void setArriveStartTime(Date arriveStartTime) {
		this.arriveStartTime = arriveStartTime;
	}

	public Date getArriveEndTime() {
		return arriveEndTime;
	}

	public void setArriveEndTime(Date arriveEndTime) {
		this.arriveEndTime = arriveEndTime;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
}