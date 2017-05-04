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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/DeliverbillDetailEntity.java
 * 
 * FILE NAME        	: DeliverbillDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 
 *
 * 派送单明细
 * @author 043258-
 *				 foss-zhaobin
 * @date 2013-3-16 
 *				 上午10:51:41
 * @since
 * @version
 */
public class DeliverbillDetailEntity extends BaseEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -670445093701486053L;

	/** The id. */
	private String id;

	/** 派送单ID. */
	private String tSrvDeliverbillId;

	/** 规定兑现时间. */
	private Date cashTime;
	
	/** 序号. */
	private Integer serialNo;

	/** 到达联编号. */
	private String arrivesheetNo;

	/** 运单号. */
	private String waybillNo;

	/** 排单状态. */
	private String arrangeStatus;

	/** 预排单件数. */
	private Integer preArrangeGoodsQty;

	/** 确认排单件数. */
	private Integer arrangeGoodsQty;

	/** 重量. */
	private BigDecimal weight;

	/** 尺寸. */
	private String dimension;

	/** 货物名称. */
	private String goodsName;

	/** 运单件数. */
	private Integer waybillGoodsQty;

	/** 运输方式. */
	private String transportType;

	/** 到达时间. */
	private Date arriveTime;

	/** 收货人. */
	private String consignee;

	/** 收货人联系方式. */
	private String consigneeContact;

	/** 收货地址. */
	private String consigneeAddress;

	/** 送货要求. */
	private String deliverRequire;

	/** 货物状态. */
	private String goodsStatus;

	/** 是否异常. */
	private String isException;

	/** 是否已联系客户. */
	private String isAlreadyContact;

	/** 预计送货时间. */
	private Date estimatedDeliverTime;

	/** 是否需要发票. */
	private String isNeedInvoice;

	/** 付款方式. */
	private String paymentType;
	
	/**
	 * 派送方式
	 */
	private String receiveMethod;

	/** 备注. */
	private String notes;

	/** 提货方式. */
	private String deliverType;

	/** 总体积. */
	private BigDecimal goodsVolumeTotal;

	/** 到付金额. */
	private BigDecimal payAmount;

	/** 卡货标志. */
	private Short fastWaybillFlag;

	// 包装
	/** The goods package. */
	private String goodsPackage;
	// 签收单状态
	/** The single sign status. */
	private String singleSignStatus;
	// 货物信息
	/** The goods info. */
	private String goodsInfo;
	// 客户信息
	/** The consignee info. */
	private String consigneeInfo;

	/** The stock good qty. */
	private Integer stockGoodQty;
	
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/** 货币类型. */
	private String currencyCode;

	/** 返单类型. */
	private String returnBillType;
	
	/** 更改状态. */
	private String waybillrfcStatus;
	/**
	 *  是否作废
	 */
	private String destroyed;
	/**
	 * 是否已采集
	 */
	private String isCollect;
	
	/**
	 * 送货日期,时间段,开始时间点-结束时间点
	 */
	private String deliveryTime;
	
	/**
	 * 送货日期
	 */
	private String preDeliverDate;
	/**
	 * 时间段
	 */
	private String deliveryTimeInterval;
	/**
	 * 开始时间点
	 */
	private String deliveryTimeStart;
	/**
	 * 结束时间点
	 */
	private String deliveryTimeOver;
	
	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	/**
	 * 行政区域 DMANA-4290
	 */
	private String districtName;
	
	/**
	 * 送货日期(通知)
	 */
	private Date deliverDate;
	
	/**
	 * 交单id
	 */
	private String srvDeliverHandoverbillId;
	
	/**
	 * 建议送货时间
	 */
	private String recommendedDeliveryTime;
	
	/**
	 * 调度排序序号
	 */
	private Integer dispatchSortSerialNo; 
	/**
	 * 打印次数
	 */
	private Integer printtimes;

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

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
	
	public String getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 *
	 * @return the id
	 * @author ibm-wangfei
	 * @date Dec 25, 2012 10:03:36 AM
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/**
	 *
	 * @param id the new id
	 * @author ibm-wangfei
	 * @date Dec 25, 2012 10:03:36 AM
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the t srv deliverbill id.
	 *
	 * @return the tSrvDeliverbillId
	 */
	public String gettSrvDeliverbillId() {
		return tSrvDeliverbillId;
	}

	/**
	 * Sets the t srv deliverbill id.
	 *
	 * @param tSrvDeliverbillId the tSrvDeliverbillId to see
	 */
	public void settSrvDeliverbillId(String tSrvDeliverbillId) {
		this.tSrvDeliverbillId = tSrvDeliverbillId;
	}

	/**
	 * Gets the serial no.
	 *
	 * @return the serialNo
	 */
	public Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
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
	 * Gets the arrange status.
	 *
	 * @return the arrangeStatus
	 */
	public String getArrangeStatus() {
		return arrangeStatus;
	}

	/**
	 * Sets the arrange status.
	 *
	 * @param arrangeStatus the arrangeStatus to see
	 */
	public void setArrangeStatus(String arrangeStatus) {
		this.arrangeStatus = arrangeStatus;
	}

	/**
	 * Gets the pre arrange goods qty.
	 *
	 * @return the preArrangeGoodsQty
	 */
	public Integer getPreArrangeGoodsQty() {
		return preArrangeGoodsQty;
	}

	/**
	 * Sets the pre arrange goods qty.
	 *
	 * @param preArrangeGoodsQty the preArrangeGoodsQty to see
	 */
	public void setPreArrangeGoodsQty(Integer preArrangeGoodsQty) {
		this.preArrangeGoodsQty = preArrangeGoodsQty;
	}

	/**
	 * Gets the arrange goods qty.
	 *
	 * @return the arrangeGoodsQty
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	/**
	 * Sets the arrange goods qty.
	 *
	 * @param arrangeGoodsQty the arrangeGoodsQty to see
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the weight to see
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the dimension.
	 *
	 * @return the dimension
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * Sets the dimension.
	 *
	 * @param dimension the dimension to see
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	/**
	 * Gets the goods name.
	 *
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the goods name.
	 *
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the waybill goods qty.
	 *
	 * @return the waybillGoodsQty
	 */
	public Integer getWaybillGoodsQty() {
		return waybillGoodsQty;
	}

	/**
	 * Sets the waybill goods qty.
	 *
	 * @param waybillGoodsQty the waybillGoodsQty to see
	 */
	public void setWaybillGoodsQty(Integer waybillGoodsQty) {
		this.waybillGoodsQty = waybillGoodsQty;
	}

	/**
	 * Gets the transport type.
	 *
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * Sets the transport type.
	 *
	 * @param transportType the transportType to see
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * Gets the arrive time.
	 *
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the arrive time.
	 *
	 * @param arriveTime the arriveTime to see
	 */
	@DateFormat
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
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
	 * @param consignee the consignee to see
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the consignee contact.
	 *
	 * @return the consigneeContact
	 */
	public String getConsigneeContact() {
		return consigneeContact;
	}

	/**
	 * Sets the consignee contact.
	 *
	 * @param consigneeContact the consigneeContact to see
	 */
	public void setConsigneeContact(String consigneeContact) {
		this.consigneeContact = consigneeContact;
	}

	/**
	 * Gets the consignee address.
	 *
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * Sets the consignee address.
	 *
	 * @param consigneeAddress the consigneeAddress to see
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	/**
	 * Gets the deliver require.
	 *
	 * @return the deliverRequire
	 */
	public String getDeliverRequire() {
		return deliverRequire;
	}

	/**
	 * Sets the deliver require.
	 *
	 * @param deliverRequire the deliverRequire to see
	 */
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	/**
	 * Gets the goods status.
	 *
	 * @return the goodsStatus
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * Sets the goods status.
	 *
	 * @param goodsStatus the goodsStatus to see
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	/**
	 * Gets the is exception.
	 *
	 * @return the isException
	 */
	public String getIsException() {
		return isException;
	}

	/**
	 * Sets the is exception.
	 *
	 * @param isException the isException to see
	 */
	public void setIsException(String isException) {
		this.isException = isException;
	}

	/**
	 * Gets the is already contact.
	 *
	 * @return the isAlreadyContact
	 */
	public String getIsAlreadyContact() {
		return isAlreadyContact;
	}

	/**
	 * Sets the is already contact.
	 *
	 * @param isAlreadyContact the isAlreadyContact to see
	 */
	public void setIsAlreadyContact(String isAlreadyContact) {
		this.isAlreadyContact = isAlreadyContact;
	}

	/**
	 * Gets the estimated deliver time.
	 *
	 * @return the estimatedDeliverTime
	 */
	@DateFormat
	public Date getEstimatedDeliverTime() {
		return estimatedDeliverTime;
	}

	/**
	 * Sets the estimated deliver time.
	 *
	 * @param estimatedDeliverTime the estimatedDeliverTime to see
	 */
	@DateFormat
	public void setEstimatedDeliverTime(Date estimatedDeliverTime) {
		this.estimatedDeliverTime = estimatedDeliverTime;
	}

	/**
	 * Gets the is need invoice.
	 *
	 * @return the isNeedInvoice
	 */
	public String getIsNeedInvoice() {
		return isNeedInvoice;
	}

	/**
	 * Sets the is need invoice.
	 *
	 * @param isNeedInvoice the isNeedInvoice to see
	 */
	public void setIsNeedInvoice(String isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	/**
	 * Gets the payment type.
	 *
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType the paymentType to see
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the deliver type.
	 *
	 * @return the deliverType
	 */
	public String getDeliverType() {
		return deliverType;
	}

	/**
	 * Sets the deliver type.
	 *
	 * @param deliverType the deliverType to see
	 */
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	/**
	 * Gets the goods volume total.
	 *
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the pay amount.
	 *
	 * @return the payAmount
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	/**
	 * Sets the pay amount.
	 *
	 * @param payAmount the payAmount to see
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * Gets the fast waybill flag.
	 *
	 * @return the fastWaybillFlag
	 */
	public Short getFastWaybillFlag() {
		return fastWaybillFlag;
	}

	/**
	 * Sets the fast waybill flag.
	 *
	 * @param fastWaybillFlag the fastWaybillFlag to see
	 */
	public void setFastWaybillFlag(Short fastWaybillFlag) {
		this.fastWaybillFlag = fastWaybillFlag;
	}

	/**
	 * Gets the stock good qty.
	 *
	 * @return the stockGoodQty
	 */
	public Integer getStockGoodQty() {
		return stockGoodQty;
	}

	/**
	 * Sets the stock good qty.
	 *
	 * @param stockGoodQty the stockGoodQty to see
	 */
	public void setStockGoodQty(Integer stockGoodQty) {
		this.stockGoodQty = stockGoodQty;
	}

	/**
	 * Gets the goods package.
	 *
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the goods package.
	 *
	 * @param goodsPackage the goodsPackage to see
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the single sign status.
	 *
	 * @return the singleSignStatus
	 */
	public String getSingleSignStatus() {
		return singleSignStatus;
	}

	/**
	 * Sets the single sign status.
	 *
	 * @param singleSignStatus the singleSignStatus to see
	 */
	public void setSingleSignStatus(String singleSignStatus) {
		this.singleSignStatus = singleSignStatus;
	}

	/**
	 * Gets the goods info.
	 *
	 * @return the goodsInfo
	 */
	public String getGoodsInfo() {
		return goodsInfo;
	}

	/**
	 * Sets the goods info.
	 *
	 * @param goodsInfo the goodsInfo to see
	 */
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	/**
	 * Gets the consignee info.
	 *
	 * @return the consigneeInfo
	 */
	public String getConsigneeInfo() {
		return consigneeInfo;
	}

	/**
	 * Sets the consignee info.
	 *
	 * @param consigneeInfo the consigneeInfo to see
	 */
	public void setConsigneeInfo(String consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the currencyCode to see
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the return bill type.
	 *
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * Sets the return bill type.
	 *
	 * @param returnBillType the returnBillType to see
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * Gets the waybillrfc status.
	 *
	 * @return the waybillrfc status
	 */
	public String getWaybillrfcStatus()
	{
		return waybillrfcStatus;
	}

	/**
	 * Sets the waybillrfc status.
	 *
	 * @param waybillrfcStatus the new waybillrfc status
	 */
	public void setWaybillrfcStatus(String waybillrfcStatus)
	{
		this.waybillrfcStatus = waybillrfcStatus;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getPreDeliverDate() {
		return preDeliverDate;
	}

	public void setPreDeliverDate(String preDeliverDate) {
		this.preDeliverDate = preDeliverDate;
	}

	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	public void setDeliveryTimeOver(String deliveryTimeOver) {
		this.deliveryTimeOver = deliveryTimeOver;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getSrvDeliverHandoverbillId() {
		return srvDeliverHandoverbillId;
	}

	public void setSrvDeliverHandoverbillId(String srvDeliverHandoverbillId) {
		this.srvDeliverHandoverbillId = srvDeliverHandoverbillId;
	}

	public String getRecommendedDeliveryTime() {
		return recommendedDeliveryTime;
	}

	public void setRecommendedDeliveryTime(String recommendedDeliveryTime) {
		this.recommendedDeliveryTime = recommendedDeliveryTime;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getDispatchSortSerialNo() {
		return dispatchSortSerialNo;
	}

	public void setDispatchSortSerialNo(Integer dispatchSortSerialNo) {
		this.dispatchSortSerialNo = dispatchSortSerialNo;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public Date getCashTime() {
		return cashTime;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}


	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}


}