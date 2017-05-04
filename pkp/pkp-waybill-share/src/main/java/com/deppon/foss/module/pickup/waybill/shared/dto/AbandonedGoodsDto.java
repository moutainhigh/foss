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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/AbandonedGoodsDto.java
 * 
 * FILE NAME        	: AbandonedGoodsDto.java
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

/**
 * 
 * <p>
 * 弃货数据
 * </p>
 * 
 * @title AbandonedGoodsDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto
 * @author suyujun
 * @version 0.1 2012-12-14
 */
public class AbandonedGoodsDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * status
	 */
	private String status;

	/**
	 * deliverCustomerMobile
	 */
	private String deliverCustomerMobile;

	/**
	 * respectiveRegional
	 */
	private String respectiveRegional;

	/**
	 * goodsName
	 */
	private String goodsName;

	/**
	 * v
	 */
	private String deliverCustomerContact;

	/**
	 * receiveOrgCode
	 */
	private String receiveOrgCode;

	/**
	 * lastLoadOrgCode
	 */
	private String lastLoadOrgCode;

	/**
	 * storageOrgCode
	 */
	private String storageOrgCode;

	/**
	 * codAmount
	 */
	private Integer codAmount;

	/**
	 * insuranceAmount
	 */
	private BigDecimal insuranceAmount;

	/**
	 * prePayAmount
	 */
	private BigDecimal prePayAmount;

	/**
	 * toPayamount
	 */
	private BigDecimal toPayamount;

	/**
	 * goodsType
	 */
	private String goodsType;

	/**
	 * abandoneGoodsStatus
	 */
	private String abandoneGoodsStatus;

	/**
	 * storageDay
	 */
	private Integer storageDay;

	/**
	 * notes
	 */
	private String notes;

	/**
	 * weight
	 */
	private BigDecimal weight;

	/**
	 * volume
	 */
	private BigDecimal volume;

	/**
	 * pieces
	 */
	private Integer pieces;

	/**
	 * deliverCostomerPhone
	 */
	private String deliverCostomerPhone;

	/**
	 * 展示字段
	 */
	/**
	 * waybillNo
	 */
	private String waybillNo;

	/**
	 * preTreatPerson
	 */
	private String preTreatPerson;

	/**
	 * receiverName
	 */
	private String receiverName;

	/**
	 * receiverTel
	 */
	private String receiverTel;

	/**
	 * treatTime
	 */
	private String treatTime;

	/**
	 * @return id : set property id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            : return property id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return status : set property status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            : return property status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return deliverCustomerMobile : set property deliverCustomerMobile.
	 */
	public String getDeliverCustomerMobile() {
		return deliverCustomerMobile;
	}

	/**
	 * @param deliverCustomerMobile
	 *            : return property deliverCustomerMobile.
	 */
	public void setDeliverCustomerMobile(String deliverCustomerMobile) {
		this.deliverCustomerMobile = deliverCustomerMobile;
	}

	/**
	 * @return respectiveRegional : set property respectiveRegional.
	 */
	public String getRespectiveRegional() {
		return respectiveRegional;
	}

	/**
	 * @param respectiveRegional
	 *            : return property respectiveRegional.
	 */
	public void setRespectiveRegional(String respectiveRegional) {
		this.respectiveRegional = respectiveRegional;
	}

	/**
	 * @return goodsName : set property goodsName.
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            : return property goodsName.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return deliverCustomerContact : set property deliverCustomerContact.
	 */
	public String getDeliverCustomerContact() {
		return deliverCustomerContact;
	}

	/**
	 * @param deliverCustomerContact
	 *            : return property deliverCustomerContact.
	 */
	public void setDeliverCustomerContact(String deliverCustomerContact) {
		this.deliverCustomerContact = deliverCustomerContact;
	}

	/**
	 * @return receiveOrgCode : set property receiveOrgCode.
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode
	 *            : return property receiveOrgCode.
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return lastLoadOrgCode : set property lastLoadOrgCode.
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode
	 *            : return property lastLoadOrgCode.
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return storageOrgCode : set property storageOrgCode.
	 */
	public String getStorageOrgCode() {
		return storageOrgCode;
	}

	/**
	 * @param storageOrgCode
	 *            : return property storageOrgCode.
	 */
	public void setStorageOrgCode(String storageOrgCode) {
		this.storageOrgCode = storageOrgCode;
	}

	/**
	 * @return codAmount : set property codAmount.
	 */
	public Integer getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 *            : return property codAmount.
	 */
	public void setCodAmount(Integer codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return insuranceAmount : set property insuranceAmount.
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount
	 *            : return property insuranceAmount.
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return prePayAmount : set property prePayAmount.
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount
	 *            : return property prePayAmount.
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return toPayamount : set property toPayamount.
	 */
	public BigDecimal getToPayamount() {
		return toPayamount;
	}

	/**
	 * @param toPayamount
	 *            : return property toPayamount.
	 */
	public void setToPayamount(BigDecimal toPayamount) {
		this.toPayamount = toPayamount;
	}

	/**
	 * @return goodsType : set property goodsType.
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType
	 *            : return property goodsType.
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return abandoneGoodsStatus : set property abandoneGoodsStatus.
	 */
	public String getAbandoneGoodsStatus() {
		return abandoneGoodsStatus;
	}

	/**
	 * @param abandoneGoodsStatus
	 *            : return property abandoneGoodsStatus.
	 */
	public void setAbandoneGoodsStatus(String abandoneGoodsStatus) {
		this.abandoneGoodsStatus = abandoneGoodsStatus;
	}

	/**
	 * @return storageDay : set property storageDay.
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay
	 *            : return property storageDay.
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return notes : set property notes.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            : return property notes.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return weight : set property weight.
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            : return property weight.
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return volume : set property volume.
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            : return property volume.
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return pieces : set property pieces.
	 */
	public Integer getPieces() {
		return pieces;
	}

	/**
	 * @param pieces
	 *            : return property pieces.
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return deliverCostomerPhone : set property deliverCostomerPhone.
	 */
	public String getDeliverCostomerPhone() {
		return deliverCostomerPhone;
	}

	/**
	 * @param deliverCostomerPhone
	 *            : return property deliverCostomerPhone.
	 */
	public void setDeliverCostomerPhone(String deliverCostomerPhone) {
		this.deliverCostomerPhone = deliverCostomerPhone;
	}

	/**
	 * @return waybillNo : set property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            : return property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return preTreatPerson : set property preTreatPerson.
	 */
	public String getPreTreatPerson() {
		return preTreatPerson;
	}

	/**
	 * @param preTreatPerson
	 *            : return property preTreatPerson.
	 */
	public void setPreTreatPerson(String preTreatPerson) {
		this.preTreatPerson = preTreatPerson;
	}

	/**
	 * @return receiverName : set property receiverName.
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName
	 *            : return property receiverName.
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @return receiverTel : set property receiverTel.
	 */
	public String getReceiverTel() {
		return receiverTel;
	}

	/**
	 * @param receiverTel
	 *            : return property receiverTel.
	 */
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	/**
	 * @return treatTime : set property treatTime.
	 */
	public String getTreatTime() {
		return treatTime;
	}

	/**
	 * @param treatTime
	 *            : return property treatTime.
	 */
	public void setTreatTime(String treatTime) {
		this.treatTime = treatTime;
	}

}