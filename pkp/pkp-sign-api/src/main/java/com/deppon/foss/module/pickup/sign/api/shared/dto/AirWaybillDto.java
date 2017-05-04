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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirWaybillDto.java
 * 
 * FILE NAME        	: AirWaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提供给EDI的空运运单信息DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-25 下午9:10:22
 */
public class AirWaybillDto implements Serializable {
	private static final long serialVersionUID = 6824773151338819096L;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 送货情况
	 */
	private String deliveryType;
	/**
	 * 签收人
	 */
	private String signer;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 送货时间
	 */
	private Date deliveryTime;
	/**
	 * 实收代收货款
	 */
	private BigDecimal realRefund;
	/**
	 * 实收到付运费
	 */
	private BigDecimal arriveCharge;
	/**
	 * 实收到付款
	 */
	private BigDecimal realArriveMoney;
	/**
	 * 代理网点
	 */
	private String ladingStation;
	/**
	 * 代理网点编号
	 */
	private String ladingStationNumber;
	/**
	 * 出港航班
	 */
	private String outBoundFlight;
	/**
	 * 出港日期
	 */
	private Date departureTime;
	/**
	 * 签收状态
	 */
	private String signState;
	/**
	 * 收货日期
	 */
	private Date receiptTime;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 件数
	 */
	private Integer piece;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 制单人
	 */
	private String orderCreator;
	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * Gets the 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * Gets the 送货情况.
	 *
	 * @return the 送货情况
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * Sets the 送货情况.
	 *
	 * @param deliveryType the new 送货情况
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * Gets the 签收人.
	 *
	 * @return the 签收人
	 */
	public String getSigner() {
		return signer;
	}

	/**
	 * Sets the 签收人.
	 *
	 * @param signer the new 签收人
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}

	/**
	 * Gets the 签收时间.
	 *
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * Sets the 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * Gets the 送货时间.
	 *
	 * @return the 送货时间
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * Sets the 送货时间.
	 *
	 * @param deliveryTime the new 送货时间
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * Gets the 实收代收货款.
	 *
	 * @return the 实收代收货款
	 */
	public BigDecimal getRealRefund() {
		return realRefund;
	}

	/**
	 * Sets the 实收代收货款.
	 *
	 * @param realRefund the new 实收代收货款
	 */
	public void setRealRefund(BigDecimal realRefund) {
		this.realRefund = realRefund;
	}

	/**
	 * Gets the 实收到付运费.
	 *
	 * @return the 实收到付运费
	 */
	public BigDecimal getArriveCharge() {
		return arriveCharge;
	}

	/**
	 * Sets the 实收到付运费.
	 *
	 * @param arriveCharge the new 实收到付运费
	 */
	public void setArriveCharge(BigDecimal arriveCharge) {
		this.arriveCharge = arriveCharge;
	}

	/**
	 * Gets the 实收到付款.
	 *
	 * @return the 实收到付款
	 */
	public BigDecimal getRealArriveMoney() {
		return realArriveMoney;
	}

	/**
	 * Sets the 实收到付款.
	 *
	 * @param realArriveMoney the new 实收到付款
	 */
	public void setRealArriveMoney(BigDecimal realArriveMoney) {
		this.realArriveMoney = realArriveMoney;
	}

	/**
	 * Gets the 代理网点.
	 *
	 * @return the 代理网点
	 */
	public String getLadingStation() {
		return ladingStation;
	}

	/**
	 * Sets the 代理网点.
	 *
	 * @param ladingStation the new 代理网点
	 */
	public void setLadingStation(String ladingStation) {
		this.ladingStation = ladingStation;
	}

	/**
	 * Gets the 代理网点编号.
	 *
	 * @return the 代理网点编号
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * Sets the 代理网点编号.
	 *
	 * @param ladingStationNumber the new 代理网点编号
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * Gets the 出港航班.
	 *
	 * @return the 出港航班
	 */
	public String getOutBoundFlight() {
		return outBoundFlight;
	}

	/**
	 * Sets the 出港航班.
	 *
	 * @param outBoundFlight the new 出港航班
	 */
	public void setOutBoundFlight(String outBoundFlight) {
		this.outBoundFlight = outBoundFlight;
	}

	/**
	 * Gets the 出港日期.
	 *
	 * @return the 出港日期
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * Sets the 出港日期.
	 *
	 * @param departureTime the new 出港日期
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * Gets the 签收状态.
	 *
	 * @return the 签收状态
	 */
	public String getSignState() {
		return signState;
	}

	/**
	 * Sets the 签收状态.
	 *
	 * @param signState the new 签收状态
	 */
	public void setSignState(String signState) {
		this.signState = signState;
	}

	/**
	 * Gets the 收货日期.
	 *
	 * @return the 收货日期
	 */
	public Date getReceiptTime() {
		return receiptTime;
	}

	/**
	 * Sets the 收货日期.
	 *
	 * @param receiptTime the new 收货日期
	 */
	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	/**
	 * Gets the 收货人.
	 *
	 * @return the 收货人
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * Sets the 收货人.
	 *
	 * @param consignee the new 收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getPiece() {
		return piece;
	}

	/**
	 * Sets the 件数.
	 *
	 * @param piece the new 件数
	 */
	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	/**
	 * Gets the 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the 制单人.
	 *
	 * @return the 制单人
	 */
	public String getOrderCreator() {
		return orderCreator;
	}

	/**
	 * Sets the 制单人.
	 *
	 * @param orderCreator the new 制单人
	 */
	public void setOrderCreator(String orderCreator) {
		this.orderCreator = orderCreator;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}