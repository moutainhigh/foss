/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirWaybillDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
* @description FOSS推送航空正单明细信息至OPP
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月18日 上午11:05:28
 */
public class OPPNeedAirWaybillDetailEntity implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6695534601262490629L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 航空正单Id
	 */
	private String airWaybillId;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单方式
	 */
	private String makeWaybillWay;

	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 尺寸
	 */
	private String measurement;
	/**
	 * 毛重
	 */
	private BigDecimal grossWeight;
	/**
	 * 计费重量
	 */
	private BigDecimal  billingWeight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 件数
	 */
	private Integer goodsQty;

	/**
	 * 品名
	 */
	private String goodsName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;
	/**
	 * 到付费
	 */
	private BigDecimal arrivalFee;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 代收款
	 */
	private BigDecimal collectionFee;
	/**
	 * 收货人电话	
	 */
	private String receiverContactPhone;
	/**
	 * 收货地址
	 */
	private String receiverAddress;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	
	/**
	 * 备注
	 */
	private String notes;

	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	
	
	
	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirWaybillId() {
		return airWaybillId;
	}

	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}

	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getMakeWaybillWay() {
		return makeWaybillWay;
	}

	public void setMakeWaybillWay(String makeWaybillWay) {
		this.makeWaybillWay = makeWaybillWay;
	}

	public String getArrvRegionName() {
		return arrvRegionName;
	}

	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getBillingWeight() {
		return billingWeight;
	}

	public void setBillingWeight(BigDecimal billingWeight) {
		this.billingWeight = billingWeight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}


	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	public BigDecimal getArrivalFee() {
		return arrivalFee;
	}

	public void setArrivalFee(BigDecimal arrivalFee) {
		this.arrivalFee = arrivalFee;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public BigDecimal getCollectionFee() {
		return collectionFee;
	}

	public void setCollectionFee(BigDecimal collectionFee) {
		this.collectionFee = collectionFee;
	}

	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}

	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


   
}