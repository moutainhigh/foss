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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmWaybillServiceDto.java
 * 
 * FILE NAME        	: CrmWaybillServiceDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCostInfo;

/**
 * 
 * <p>
 * CRM系统查询运单信息Dto
 * </p>
 * 
 * @title CrmWaybillServiceDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class CrmWaybillServiceDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5277884441841343541L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 运输类型
	 */
	private String tranType;
	/**
	 * 发货人
	 */
	private String sender;
	/**
	 * 发货人电话
	 */
	private String senderPhone;
	/**
	 * 发货人手机
	 */
	private String senderMobile;
	/**
	 * 出发部门
	 */
	private String departure;
	/**
	 * 发货人地址
	 */
	private String senderAddress;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 收货人电话
	 */
	private String consigneePhone;
	/**
	 *收货人手机
	 */
	private String consigneeMobile;
	/**
	 * 目的地
	 */
	private String destination;
	/**
	 * 收货人地址
	 */
	private String consigneeAddress;
	/**
	 * 货物名字
	 */
	private String goodName;
	/**
	 * 件数
	 */
	private int pieces;
	/**
	 * 重量
	 */
	private Float weight;
	/**
	 * 体积
	 */
	private Float cubage;
	/**
	 * 总费用
	 */
	private BigDecimal totalCharge;
	/**
	 * 支付方式
	 */
	private String payment;
	/**
	 * 预付金额
	 */
	private BigDecimal preCharge;
	/**
	 * 到付金额
	 */
	private BigDecimal arriveCharge;
	/**
	 * 返单类型
	 */
	private String refundType;
	/**
	 * 返单金额
	 */
	private BigDecimal refund;
	/**
	 * 返单金额
	 */
	private BigDecimal refundFee;
	/**
	 * 派送类型
	 */
	private String deliveryType;
	/**
	 *代理费用
	 */
	private BigDecimal consignCharge;
	/**
	 * 派送费
	 */
	private BigDecimal deliveryCharge;
	/**
	 * 签收回单费用
	 */
	private BigDecimal signBackCharge;
	/**
	 * 送货费
	 */
	private BigDecimal pickCharge;
	/**
	 * 装卸费率
	 */
	private BigDecimal laborRebate;
	/**
	 * 公布价
	 */
	private BigDecimal publishCharge;
	/**
	 * 出发部门名称
	 */
	private String departureDeptName;
	/**
	 * 出发部门编码
	 */
	private String departureDeptNumber;
	/**
	 * 出发部门地址
	 */
	private String departureDeptAddr;
	private String departureDeptPhone;
	private String departureDeptFax;
	private String ladingStationName;
	private String ladingStationNumber;
	private String ladingStationAddr;
	private String ladingStationPhone;
	private String ladingStationFax;
	private String isSigned;
	private String isNormalSigned;
	private String signRecorderId;
	private Date signedDate;
	private String signedDesc;
	private String orderNumber;
	private BigDecimal insuranceValue;
	private BigDecimal insurance;
	private String packing;
	private String orderState;
	private BigDecimal otherPayment;
	private String tranDesc;
	private String senderNumber;
	private String consigneeNumber;
	private String isClear;
	private String signBackType;
	private String stowageType;
	private String transNotice;
	private Date sendTime;
	private String receiveDeptName;
	private String receiveDeptNumber;
	private String stowageDept;
	private String senderCityCode;
	private String senderCityName;
	private String senderProvinceCode;
	private String senderProvinceName;
	private String consigneeCityCode;
	private String consigneeCityName;
	private String consigneeProvinceCode;
	private String consigneeProvinceName;
	private String isDoorToDoorPick;
	private String isSmsNotice;
	private String signBillBackWay;
	private BigDecimal packingFee;
	private BigDecimal otherFee;
	private BigDecimal transportFee;
	private String returnBillType;
	private String paidMethod;
	private String transProperties;
	private String waybillStatus;
	private transient List<WaybillCostInfo> waybillCostInfos;
	
	//发货城市名称
	private String departureCityName;
	//收货城市名称
	private String destinationCityName;
	//预计到达时间
	private Date predictArriveTime;
	
	//收货地址备注
	private String receiveCustomerAddressNote;
	
	//发货地址备注
	private String deliveryCustomerAddressNote;
	
	public String getDepartureCityName() {
		return departureCityName;
	}
	public void setDepartureCityName(String departureCityName) {
		this.departureCityName = departureCityName;
	}
	public String getDestinationCityName() {
		return destinationCityName;
	}
	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}
	public Date getPredictArriveTime() {
		return predictArriveTime;
	}
	public void setPredictArriveTime(Date predictArriveTime) {
		this.predictArriveTime = predictArriveTime;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the tranType
	 */
	public String getTranType() {
		return tranType;
	}
	/**
	 * @param tranType the tranType to set
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the senderPhone
	 */
	public String getSenderPhone() {
		return senderPhone;
	}
	/**
	 * @param senderPhone the senderPhone to set
	 */
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	/**
	 * @return the senderMobile
	 */
	public String getSenderMobile() {
		return senderMobile;
	}
	/**
	 * @param senderMobile the senderMobile to set
	 */
	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}
	/**
	 * @return the departure
	 */
	public String getDeparture() {
		return departure;
	}
	/**
	 * @param departure the departure to set
	 */
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	/**
	 * @return the senderAddress
	 */
	public String getSenderAddress() {
		return senderAddress;
	}
	/**
	 * @param senderAddress the senderAddress to set
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}
	/**
	 * @param consignee the consignee to set
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	/**
	 * @return the consigneePhone
	 */
	public String getConsigneePhone() {
		return consigneePhone;
	}
	/**
	 * @param consigneePhone the consigneePhone to set
	 */
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	/**
	 * @return the consigneeMobile
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	/**
	 * @param consigneeMobile the consigneeMobile to set
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	/**
	 * @param consigneeAddress the consigneeAddress to set
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	/**
	 * @return the goodName
	 */
	public String getGoodName() {
		return goodName;
	}
	/**
	 * @param goodName the goodName to set
	 */
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	/**
	 * @return the pieces
	 */
	public int getPieces() {
		return pieces;
	}
	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return the weight
	 */
	public Float getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	/**
	 * @return the cubage
	 */
	public Float getCubage() {
		return cubage;
	}
	/**
	 * @param cubage the cubage to set
	 */
	public void setCubage(Float cubage) {
		this.cubage = cubage;
	}
	/**
	 * @return the totalCharge
	 */
	public BigDecimal getTotalCharge() {
		return totalCharge;
	}
	/**
	 * @param totalCharge the totalCharge to set
	 */
	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}
	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * @return the preCharge
	 */
	public BigDecimal getPreCharge() {
		return preCharge;
	}
	/**
	 * @param preCharge the preCharge to set
	 */
	public void setPreCharge(BigDecimal preCharge) {
		this.preCharge = preCharge;
	}
	/**
	 * @return the arriveCharge
	 */
	public BigDecimal getArriveCharge() {
		return arriveCharge;
	}
	/**
	 * @param arriveCharge the arriveCharge to set
	 */
	public void setArriveCharge(BigDecimal arriveCharge) {
		this.arriveCharge = arriveCharge;
	}
	/**
	 * @return the refundType
	 */
	public String getRefundType() {
		return refundType;
	}
	/**
	 * @param refundType the refundType to set
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	/**
	 * @return the refund
	 */
	public BigDecimal getRefund() {
		return refund;
	}
	/**
	 * @param refund the refund to set
	 */
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}
	/**
	 * @return the refundFee
	 */
	public BigDecimal getRefundFee() {
		return refundFee;
	}
	/**
	 * @param refundFee the refundFee to set
	 */
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}
	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	/**
	 * @return the consignCharge
	 */
	public BigDecimal getConsignCharge() {
		return consignCharge;
	}
	/**
	 * @param consignCharge the consignCharge to set
	 */
	public void setConsignCharge(BigDecimal consignCharge) {
		this.consignCharge = consignCharge;
	}
	/**
	 * @return the deliveryCharge
	 */
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}
	/**
	 * @param deliveryCharge the deliveryCharge to set
	 */
	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	/**
	 * @return the signBackCharge
	 */
	public BigDecimal getSignBackCharge() {
		return signBackCharge;
	}
	/**
	 * @param signBackCharge the signBackCharge to set
	 */
	public void setSignBackCharge(BigDecimal signBackCharge) {
		this.signBackCharge = signBackCharge;
	}
	/**
	 * @return the pickCharge
	 */
	public BigDecimal getPickCharge() {
		return pickCharge;
	}
	/**
	 * @param pickCharge the pickCharge to set
	 */
	public void setPickCharge(BigDecimal pickCharge) {
		this.pickCharge = pickCharge;
	}
	/**
	 * @return the laborRebate
	 */
	public BigDecimal getLaborRebate() {
		return laborRebate;
	}
	/**
	 * @param laborRebate the laborRebate to set
	 */
	public void setLaborRebate(BigDecimal laborRebate) {
		this.laborRebate = laborRebate;
	}
	/**
	 * @return the publishCharge
	 */
	public BigDecimal getPublishCharge() {
		return publishCharge;
	}
	/**
	 * @param publishCharge the publishCharge to set
	 */
	public void setPublishCharge(BigDecimal publishCharge) {
		this.publishCharge = publishCharge;
	}
	/**
	 * @return the departureDeptName
	 */
	public String getDepartureDeptName() {
		return departureDeptName;
	}
	/**
	 * @param departureDeptName the departureDeptName to set
	 */
	public void setDepartureDeptName(String departureDeptName) {
		this.departureDeptName = departureDeptName;
	}
	/**
	 * @return the departureDeptNumber
	 */
	public String getDepartureDeptNumber() {
		return departureDeptNumber;
	}
	/**
	 * @param departureDeptNumber the departureDeptNumber to set
	 */
	public void setDepartureDeptNumber(String departureDeptNumber) {
		this.departureDeptNumber = departureDeptNumber;
	}
	/**
	 * @return the departureDeptAddr
	 */
	public String getDepartureDeptAddr() {
		return departureDeptAddr;
	}
	/**
	 * @param departureDeptAddr the departureDeptAddr to set
	 */
	public void setDepartureDeptAddr(String departureDeptAddr) {
		this.departureDeptAddr = departureDeptAddr;
	}
	/**
	 * @return the departureDeptPhone
	 */
	public String getDepartureDeptPhone() {
		return departureDeptPhone;
	}
	/**
	 * @param departureDeptPhone the departureDeptPhone to set
	 */
	public void setDepartureDeptPhone(String departureDeptPhone) {
		this.departureDeptPhone = departureDeptPhone;
	}
	/**
	 * @return the departureDeptFax
	 */
	public String getDepartureDeptFax() {
		return departureDeptFax;
	}
	/**
	 * @param departureDeptFax the departureDeptFax to set
	 */
	public void setDepartureDeptFax(String departureDeptFax) {
		this.departureDeptFax = departureDeptFax;
	}
	/**
	 * @return the ladingStationName
	 */
	public String getLadingStationName() {
		return ladingStationName;
	}
	/**
	 * @param ladingStationName the ladingStationName to set
	 */
	public void setLadingStationName(String ladingStationName) {
		this.ladingStationName = ladingStationName;
	}
	/**
	 * @return the ladingStationNumber
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}
	/**
	 * @param ladingStationNumber the ladingStationNumber to set
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}
	/**
	 * @return the ladingStationAddr
	 */
	public String getLadingStationAddr() {
		return ladingStationAddr;
	}
	/**
	 * @param ladingStationAddr the ladingStationAddr to set
	 */
	public void setLadingStationAddr(String ladingStationAddr) {
		this.ladingStationAddr = ladingStationAddr;
	}
	/**
	 * @return the ladingStationPhone
	 */
	public String getLadingStationPhone() {
		return ladingStationPhone;
	}
	/**
	 * @param ladingStationPhone the ladingStationPhone to set
	 */
	public void setLadingStationPhone(String ladingStationPhone) {
		this.ladingStationPhone = ladingStationPhone;
	}
	/**
	 * @return the ladingStationFax
	 */
	public String getLadingStationFax() {
		return ladingStationFax;
	}
	/**
	 * @param ladingStationFax the ladingStationFax to set
	 */
	public void setLadingStationFax(String ladingStationFax) {
		this.ladingStationFax = ladingStationFax;
	}
	/**
	 * @return the isSigned
	 */
	public String getIsSigned() {
		return isSigned;
	}
	/**
	 * @param isSigned the isSigned to set
	 */
	public void setIsSigned(String isSigned) {
		this.isSigned = isSigned;
	}
	/**
	 * @return the isNormalSigned
	 */
	public String getIsNormalSigned() {
		return isNormalSigned;
	}
	/**
	 * @param isNormalSigned the isNormalSigned to set
	 */
	public void setIsNormalSigned(String isNormalSigned) {
		this.isNormalSigned = isNormalSigned;
	}
	/**
	 * @return the signRecorderId
	 */
	public String getSignRecorderId() {
		return signRecorderId;
	}
	/**
	 * @param signRecorderId the signRecorderId to set
	 */
	public void setSignRecorderId(String signRecorderId) {
		this.signRecorderId = signRecorderId;
	}
	/**
	 * @return the signedDate
	 */
	public Date getSignedDate() {
		return signedDate;
	}
	/**
	 * @param signedDate the signedDate to set
	 */
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}
	/**
	 * @return the signedDesc
	 */
	public String getSignedDesc() {
		return signedDesc;
	}
	/**
	 * @param signedDesc the signedDesc to set
	 */
	public void setSignedDesc(String signedDesc) {
		this.signedDesc = signedDesc;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the insuranceValue
	 */
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}
	/**
	 * @param insuranceValue the insuranceValue to set
	 */
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	/**
	 * @return the insurance
	 */
	public BigDecimal getInsurance() {
		return insurance;
	}
	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	/**
	 * @return the packing
	 */
	public String getPacking() {
		return packing;
	}
	/**
	 * @param packing the packing to set
	 */
	public void setPacking(String packing) {
		this.packing = packing;
	}
	/**
	 * @return the orderState
	 */
	public String getOrderState() {
		return orderState;
	}
	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	/**
	 * @return the otherPayment
	 */
	public BigDecimal getOtherPayment() {
		return otherPayment;
	}
	/**
	 * @param otherPayment the otherPayment to set
	 */
	public void setOtherPayment(BigDecimal otherPayment) {
		this.otherPayment = otherPayment;
	}
	/**
	 * @return the tranDesc
	 */
	public String getTranDesc() {
		return tranDesc;
	}
	/**
	 * @param tranDesc the tranDesc to set
	 */
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}
	/**
	 * @return the senderNumber
	 */
	public String getSenderNumber() {
		return senderNumber;
	}
	/**
	 * @param senderNumber the senderNumber to set
	 */
	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}
	/**
	 * @return the consigneeNumber
	 */
	public String getConsigneeNumber() {
		return consigneeNumber;
	}
	/**
	 * @param consigneeNumber the consigneeNumber to set
	 */
	public void setConsigneeNumber(String consigneeNumber) {
		this.consigneeNumber = consigneeNumber;
	}
	/**
	 * @return the isClear
	 */
	public String getIsClear() {
		return isClear;
	}
	/**
	 * @param isClear the isClear to set
	 */
	public void setIsClear(String isClear) {
		this.isClear = isClear;
	}
	/**
	 * @return the signBackType
	 */
	public String getSignBackType() {
		return signBackType;
	}
	/**
	 * @param signBackType the signBackType to set
	 */
	public void setSignBackType(String signBackType) {
		this.signBackType = signBackType;
	}
	/**
	 * @return the stowageType
	 */
	public String getStowageType() {
		return stowageType;
	}
	/**
	 * @param stowageType the stowageType to set
	 */
	public void setStowageType(String stowageType) {
		this.stowageType = stowageType;
	}
	/**
	 * @return the transNotice
	 */
	public String getTransNotice() {
		return transNotice;
	}
	/**
	 * @param transNotice the transNotice to set
	 */
	public void setTransNotice(String transNotice) {
		this.transNotice = transNotice;
	}
	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * @return the receiveDeptName
	 */
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	/**
	 * @param receiveDeptName the receiveDeptName to set
	 */
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	/**
	 * @return the receiveDeptNumber
	 */
	public String getReceiveDeptNumber() {
		return receiveDeptNumber;
	}
	/**
	 * @param receiveDeptNumber the receiveDeptNumber to set
	 */
	public void setReceiveDeptNumber(String receiveDeptNumber) {
		this.receiveDeptNumber = receiveDeptNumber;
	}
	/**
	 * @return the stowageDept
	 */
	public String getStowageDept() {
		return stowageDept;
	}
	/**
	 * @param stowageDept the stowageDept to set
	 */
	public void setStowageDept(String stowageDept) {
		this.stowageDept = stowageDept;
	}
	/**
	 * @return the senderCityCode
	 */
	public String getSenderCityCode() {
		return senderCityCode;
	}
	/**
	 * @param senderCityCode the senderCityCode to set
	 */
	public void setSenderCityCode(String senderCityCode) {
		this.senderCityCode = senderCityCode;
	}
	/**
	 * @return the senderCityName
	 */
	public String getSenderCityName() {
		return senderCityName;
	}
	/**
	 * @param senderCityName the senderCityName to set
	 */
	public void setSenderCityName(String senderCityName) {
		this.senderCityName = senderCityName;
	}
	/**
	 * @return the senderProvinceCode
	 */
	public String getSenderProvinceCode() {
		return senderProvinceCode;
	}
	/**
	 * @param senderProvinceCode the senderProvinceCode to set
	 */
	public void setSenderProvinceCode(String senderProvinceCode) {
		this.senderProvinceCode = senderProvinceCode;
	}
	/**
	 * @return the senderProvinceName
	 */
	public String getSenderProvinceName() {
		return senderProvinceName;
	}
	/**
	 * @param senderProvinceName the senderProvinceName to set
	 */
	public void setSenderProvinceName(String senderProvinceName) {
		this.senderProvinceName = senderProvinceName;
	}
	/**
	 * @return the consigneeCityCode
	 */
	public String getConsigneeCityCode() {
		return consigneeCityCode;
	}
	/**
	 * @param consigneeCityCode the consigneeCityCode to set
	 */
	public void setConsigneeCityCode(String consigneeCityCode) {
		this.consigneeCityCode = consigneeCityCode;
	}
	/**
	 * @return the consigneeCityName
	 */
	public String getConsigneeCityName() {
		return consigneeCityName;
	}
	/**
	 * @param consigneeCityName the consigneeCityName to set
	 */
	public void setConsigneeCityName(String consigneeCityName) {
		this.consigneeCityName = consigneeCityName;
	}
	/**
	 * @return the consigneeProvinceCode
	 */
	public String getConsigneeProvinceCode() {
		return consigneeProvinceCode;
	}
	/**
	 * @param consigneeProvinceCode the consigneeProvinceCode to set
	 */
	public void setConsigneeProvinceCode(String consigneeProvinceCode) {
		this.consigneeProvinceCode = consigneeProvinceCode;
	}
	/**
	 * @return the consigneeProvinceName
	 */
	public String getConsigneeProvinceName() {
		return consigneeProvinceName;
	}
	/**
	 * @param consigneeProvinceName the consigneeProvinceName to set
	 */
	public void setConsigneeProvinceName(String consigneeProvinceName) {
		this.consigneeProvinceName = consigneeProvinceName;
	}
	/**
	 * @return the isDoorToDoorPick
	 */
	public String getIsDoorToDoorPick() {
		return isDoorToDoorPick;
	}
	/**
	 * @param isDoorToDoorPick the isDoorToDoorPick to set
	 */
	public void setIsDoorToDoorPick(String isDoorToDoorPick) {
		this.isDoorToDoorPick = isDoorToDoorPick;
	}
	/**
	 * @return the isSmsNotice
	 */
	public String getIsSmsNotice() {
		return isSmsNotice;
	}
	/**
	 * @param isSmsNotice the isSmsNotice to set
	 */
	public void setIsSmsNotice(String isSmsNotice) {
		this.isSmsNotice = isSmsNotice;
	}
	/**
	 * @return the signBillBackWay
	 */
	public String getSignBillBackWay() {
		return signBillBackWay;
	}
	/**
	 * @param signBillBackWay the signBillBackWay to set
	 */
	public void setSignBillBackWay(String signBillBackWay) {
		this.signBillBackWay = signBillBackWay;
	}
	/**
	 * @return the packingFee
	 */
	public BigDecimal getPackingFee() {
		return packingFee;
	}
	/**
	 * @param packingFee the packingFee to set
	 */
	public void setPackingFee(BigDecimal packingFee) {
		this.packingFee = packingFee;
	}
	/**
	 * @return the otherFee
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	/**
	 * @param otherFee the otherFee to set
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	/**
	 * @return the transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	/**
	 * @param transportFee the transportFee to set
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	/**
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}
	/**
	 * @param returnBillType the returnBillType to set
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	/**
	 * @return the paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}
	/**
	 * @param paidMethod the paidMethod to set
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	/**
	 * @return the transProperties
	 */
	public String getTransProperties() {
		return transProperties;
	}
	/**
	 * @param transProperties the transProperties to set
	 */
	public void setTransProperties(String transProperties) {
		this.transProperties = transProperties;
	}
	/**
	 * @return the waybillStatus
	 */
	public String getWaybillStatus() {
		return waybillStatus;
	}
	/**
	 * @param waybillStatus the waybillStatus to set
	 */
	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
	/**
	 * @return the waybillCostInfos
	 */
	public List<WaybillCostInfo> getWaybillCostInfos() {
		return waybillCostInfos;
	}
	/**
	 * @param waybillCostInfos the waybillCostInfos to set
	 */
	public void setWaybillCostInfos(List<WaybillCostInfo> waybillCostInfos) {
		this.waybillCostInfos = waybillCostInfos;
	}
	
	/**
	  * 是否电子发票
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-10-21下午14:12
	  */
	private String isElectronicInvoice;
	
	/**
	 * 发票手机号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:12
	 */
	private String invoiceMobilePhone;
	
	/**
	 * 发票发送邮箱
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:23
	 */
	private String email;

	public String getIsElectronicInvoice() {
		return isElectronicInvoice;
	}

	public void setIsElectronicInvoice(String isElectronicInvoice) {
		this.isElectronicInvoice = isElectronicInvoice;
	}

	public String getInvoiceMobilePhone() {
		return invoiceMobilePhone;
	}

	public void setInvoiceMobilePhone(String invoiceMobilePhone) {
		this.invoiceMobilePhone = invoiceMobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}
	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}
}