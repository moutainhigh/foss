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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DeliverbillDetailDto.java
 * 
 * FILE NAME        	: DeliverbillDetailDto.java
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


/**
 * 
 *
 * 派送单明细与派送单DTO
 * @author 043258-
 *				 foss-zhaobin
 * @date 2013-3-16 
 *				 上午10:54:16
 * @since
 * @version
 */
public class DeliverbillDetailDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 车牌号. */
	private String vehicleNo;

	/** 司机工号. */
	private String driverCode;

	/** 到达联编号. */
	private String arrivesheetNo;

	/** 派送单状态. */
	private String status;

	/** 派送单ID. */
	private String deliverbillId;

	/** 最终到达库存部门. */
	private String endStockOrgCode;

	/** 运单号. */
	private String waybillNo;

	/** 派送单ID. */
	private String tSrvDeliverbillId;

	/** 收货人. */
	private String consignee;

	/** 收货人联系方式. */
	private String consigneeContact;

	/** 是否已联系客户. */
	private String isAlreadyContact;

	/** 到付金额. */
	private BigDecimal payAmount;

	/** 确认排单件数. */
	private Integer arrangeGoodsQty;

	/** 库存件数. */
	private Integer inStockGoodsQty;

	/** 到达联件数. */
	private Integer arriveSheetGoodsQty;

	/** 关联的到达联. */
	private ArriveSheetDto arriveSheetDto = new ArriveSheetDto();
	/**
	 * 签收时间
	 */
	private Date signTime;
	/** 派送单号. */
	private String deliverbillNo;
	/** 操作时间(确认时间). */
	private Date operateTime;
	/** 运单件数. */
	private Integer waybillGoodsQty;

	/** 运输方式. */
	private String transportType;

	/** 收货地址. */
	private String consigneeAddress;
	/** 重量. */
	private BigDecimal weight;
	/** 总体积. */
	private BigDecimal volumeTotal;
	/** 提货方式. */
	private String deliverType;
	/**
	 *  包装
	 */
	private String goodsPackage;
	/*库存件数*/
	private Integer stockGoodsQty;
	///*派车类型*/deliver.deliver_type,
	/** 派车类型. */
	private String sendCarType;
	/** 司机姓名. */
	private String driverName;
	/**
	 * 是否快递
	 */
	private String isExpress;

	/**
	 * Instantiates a new deliverbill detail dto.
	 */
	public DeliverbillDetailDto() {

	}

	/**
	 * Instantiates a new deliverbill detail dto.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @param driverCode
	 *            the driver code
	 * @param status
	 *            the status
	 */
	public DeliverbillDetailDto(String vehicleNo, String driverCode,
			String status) {
		this.vehicleNo = vehicleNo;
		this.driverCode = driverCode;
		this.status = status;
	}

	/**
	 * Gets the vehicle no.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicle no.
	 *
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the driver code.
	 *
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the driver code.
	 *
	 * @param driverCode the driverCode to see
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the arrivesheet no.
	 *
	 * @return the arrivesheetNo
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the arrivesheet no.
	 *
	 * @param arrivesheetNo the arrivesheetNo to see
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the deliverbill id.
	 *
	 * @return the deliverbillId
	 */
	public String getDeliverbillId() {
		return deliverbillId;
	}

	/**
	 * Sets the deliverbill id.
	 *
	 * @param deliverbillId the deliverbillId to see
	 */
	public void setDeliverbillId(String deliverbillId) {
		this.deliverbillId = deliverbillId;
	}

	/**
	 * Gets the end stock org code.
	 *
	 * @return the endStockOrgCode
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * Sets the end stock org code.
	 *
	 * @param endStockOrgCode the endStockOrgCode to see
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the consignee.
	 *
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * Sets the consignee.
	 *
	 * @param consignee the new consignee
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the is already contact.
	 *
	 * @return the is already contact
	 */
	public String getIsAlreadyContact() {
		return isAlreadyContact;
	}

	/**
	 * Sets the is already contact.
	 *
	 * @param isAlreadyContact the new is already contact
	 */
	public void setIsAlreadyContact(String isAlreadyContact) {
		this.isAlreadyContact = isAlreadyContact;
	}

	/**
	 * Gets the t srv deliverbill id.
	 *
	 * @return the t srv deliverbill id
	 */
	public String gettSrvDeliverbillId() {
		return tSrvDeliverbillId;
	}

	/**
	 * Sets the t srv deliverbill id.
	 *
	 * @param tSrvDeliverbillId the new t srv deliverbill id
	 */
	public void settSrvDeliverbillId(String tSrvDeliverbillId) {
		this.tSrvDeliverbillId = tSrvDeliverbillId;
	}

	/**
	 * Gets the consignee contact.
	 *
	 * @return the consignee contact
	 */
	public String getConsigneeContact() {
		return consigneeContact;
	}

	/**
	 * Sets the consignee contact.
	 *
	 * @param consigneeContact the new consignee contact
	 */
	public void setConsigneeContact(String consigneeContact) {
		this.consigneeContact = consigneeContact;
	}

	/**
	 * Gets the pay amount.
	 *
	 * @return the pay amount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	/**
	 * Sets the pay amount.
	 *
	 * @param payAmount the new pay amount
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * Gets the arrange goods qty.
	 *
	 * @return the arrange goods qty
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	/**
	 * Sets the arrange goods qty.
	 *
	 * @param arrangeGoodsQty the new arrange goods qty
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	/**
	 * Gets the in stock goods qty.
	 *
	 * @return the in stock goods qty
	 */
	public Integer getInStockGoodsQty() {
		return inStockGoodsQty;
	}

	/**
	 * Sets the in stock goods qty.
	 *
	 * @param inStockGoodsQty the new in stock goods qty
	 */
	public void setInStockGoodsQty(Integer inStockGoodsQty) {
		this.inStockGoodsQty = inStockGoodsQty;
	}

	/**
	 * Gets the arrive sheet dto.
	 *
	 * @return the arrive sheet dto
	 */
	public ArriveSheetDto getArriveSheetDto() {
		return arriveSheetDto;
	}

	/**
	 * Sets the arrive sheet dto.
	 *
	 * @param arriveSheetDto the new arrive sheet dto
	 */
	public void setArriveSheetDto(ArriveSheetDto arriveSheetDto) {
		this.arriveSheetDto = arriveSheetDto;
	}

	/**
	 * Gets the arrive sheet goods qty.
	 *
	 * @return the arrive sheet goods qty
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the arrive sheet goods qty.
	 *
	 * @param arriveSheetGoodsQty the new arrive sheet goods qty
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getWaybillGoodsQty() {
		return waybillGoodsQty;
	}

	public void setWaybillGoodsQty(Integer waybillGoodsQty) {
		this.waybillGoodsQty = waybillGoodsQty;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}

	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getSendCarType() {
		return sendCarType;
	}

	public void setSendCarType(String sendCarType) {
		this.sendCarType = sendCarType;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

}