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
 *  FILE PATH          :/UploadingEdiDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * edi dto 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-26 下午1:36:35
 */
public class UploadingEdiDto implements Serializable{

	private static final long serialVersionUID = -278975351041075588L;

	/** 运单号 */
	private String wayBillNo;
	
	/** 到达时间  */
	private Date arriveTime;
	
	/** 签收情况 1、送货上门，2、自提，3、中转自提，4、中转送货 */
	private String deliveryType;
	
	/** 签收人 */
	private String signer;
	
	/** 签收时间  */
	private Date signTime;
	
	/** 送货时间 */
	private Date deliveryTime;
	
	/** 实收代收货款  */
	private BigDecimal realRefund;
	
	/** 实收到付运费 */
	private BigDecimal arriveCharge;
	
	/** 实收到付款 */
	private BigDecimal realArriveMoney;
	
	/** 代理网点名称 */
	private String ladingStation;
	
	/** 代理网点编号 */
	private String ladingStationNumber;
	
	/** 出港航班 */
	private String outBoundFlight;
	
	/** 出港日期 */
	private Date departureTime;
	
	/**  签收状态(1、正常签收，2、未签收，3、异常签收，4、汽运外发，5、空运代理) */
	private String signState;
	
	/** 收货日期 */
	private Date receiptTime;
	
	/** 收货人 */
	private String consignee;
	
	/** 件数 */
	private Integer piece;
	
	/** 重量 */
	private BigDecimal weight;
	
	/** 制单人：运单创建人 */
	private String orderCreator;
	/**
	 * 运输性质
	 */
	private String productCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 签收情况 1、送货上门，2、自提，3、中转自提，4、中转送货.
	 *
	 * @return the 签收情况 1、送货上门，2、自提，3、中转自提，4、中转送货
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * 设置 签收情况 1、送货上门，2、自提，3、中转自提，4、中转送货.
	 *
	 * @param deliveryType the new 签收情况 1、送货上门，2、自提，3、中转自提，4、中转送货
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * 获取 签收人.
	 *
	 * @return the 签收人
	 */
	public String getSigner() {
		return signer;
	}

	/**
	 * 设置 签收人.
	 *
	 * @param signer the new 签收人
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}

	/**
	 * 获取 签收时间.
	 *
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * 设置 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取 送货时间.
	 *
	 * @return the 送货时间
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * 设置 送货时间.
	 *
	 * @param deliveryTime the new 送货时间
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * 获取 实收代收货款.
	 *
	 * @return the 实收代收货款
	 */
	public BigDecimal getRealRefund() {
		return realRefund;
	}

	/**
	 * 设置 实收代收货款.
	 *
	 * @param realRefund the new 实收代收货款
	 */
	public void setRealRefund(BigDecimal realRefund) {
		this.realRefund = realRefund;
	}

	/**
	 * 获取 实收到付运费.
	 *
	 * @return the 实收到付运费
	 */
	public BigDecimal getArriveCharge() {
		return arriveCharge;
	}

	/**
	 * 设置 实收到付运费.
	 *
	 * @param arriveCharge the new 实收到付运费
	 */
	public void setArriveCharge(BigDecimal arriveCharge) {
		this.arriveCharge = arriveCharge;
	}

	/**
	 * 获取 实收到付款.
	 *
	 * @return the 实收到付款
	 */
	public BigDecimal getRealArriveMoney() {
		return realArriveMoney;
	}

	/**
	 * 设置 实收到付款.
	 *
	 * @param realArriveMoney the new 实收到付款
	 */
	public void setRealArriveMoney(BigDecimal realArriveMoney) {
		this.realArriveMoney = realArriveMoney;
	}

	/**
	 * 获取 代理网点名称.
	 *
	 * @return the 代理网点名称
	 */
	public String getLadingStation() {
		return ladingStation;
	}

	/**
	 * 设置 代理网点名称.
	 *
	 * @param ladingStation the new 代理网点名称
	 */
	public void setLadingStation(String ladingStation) {
		this.ladingStation = ladingStation;
	}

	/**
	 * 获取 代理网点编号.
	 *
	 * @return the 代理网点编号
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * 设置 代理网点编号.
	 *
	 * @param ladingStationNumber the new 代理网点编号
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * 获取 出港航班.
	 *
	 * @return the 出港航班
	 */
	public String getOutBoundFlight() {
		return outBoundFlight;
	}

	/**
	 * 设置 出港航班.
	 *
	 * @param outBoundFlight the new 出港航班
	 */
	public void setOutBoundFlight(String outBoundFlight) {
		this.outBoundFlight = outBoundFlight;
	}

	/**
	 * 获取 出港日期.
	 *
	 * @return the 出港日期
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * 设置 出港日期.
	 *
	 * @param departureTime the new 出港日期
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * 获取 签收状态(1、正常签收，2、未签收，3、异常签收，4、汽运外发，5、空运代理).
	 *
	 * @return the 签收状态(1、正常签收，2、未签收，3、异常签收，4、汽运外发，5、空运代理)
	 */
	public String getSignState() {
		return signState;
	}

	/**
	 * 设置 签收状态(1、正常签收，2、未签收，3、异常签收，4、汽运外发，5、空运代理).
	 *
	 * @param signState the new 签收状态(1、正常签收，2、未签收，3、异常签收，4、汽运外发，5、空运代理)
	 */
	public void setSignState(String signState) {
		this.signState = signState;
	}

	/**
	 * 获取 收货日期.
	 *
	 * @return the 收货日期
	 */
	public Date getReceiptTime() {
		return receiptTime;
	}

	/**
	 * 设置 收货日期.
	 *
	 * @param receiptTime the new 收货日期
	 */
	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	/**
	 * 获取 收货人.
	 *
	 * @return the 收货人
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置 收货人.
	 *
	 * @param consignee the new 收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public Integer getPiece() {
		return piece;
	}

	/**
	 * 设置 件数.
	 *
	 * @param piece the new 件数
	 */
	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 制单人：运单创建人.
	 *
	 * @return the 制单人：运单创建人
	 */
	public String getOrderCreator() {
		return orderCreator;
	}

	/**
	 * 设置 制单人：运单创建人.
	 *
	 * @param orderCreator the new 制单人：运单创建人
	 */
	public void setOrderCreator(String orderCreator) {
		this.orderCreator = orderCreator;
	}
	
}