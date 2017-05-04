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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PdaReceiveGoodsDto.java
 * 
 * FILE NAME        	: PdaReceiveGoodsDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * 查询PDA接货记录，提供给结算使用
 * @title PdaReceiveGoodsDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto 
 * @author suyujun
 * @version 0.1 2012-12-12
 */
public class PdaReceiveGoodsDto implements Serializable {
	private static final long serialVersionUID = 4016808789526393468L;
	/**
	 * 运单号、重量、件数、体积、现付金额、是否有签收单、付款类型
	 */
	private String waybillNo;
	private BigDecimal weight;
	private Integer pieces;
	private BigDecimal volume;
	private BigDecimal payAmount;
	private String isHaveSignBill;
	private String queryDate;
	private String vehicleNo;
	private String paidMethod;
	/**
	 * 刷卡收入
	 */
	private BigDecimal cardIncome;
	

	public String getPaidMethod() {
		return paidMethod;
	}
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	/**
	 * 现金收入
	 */
	private BigDecimal cashIncome;	
	/**
	 * 运单类型，传统运单为空，电子运单为EWAYBILL
	 * */
	private String waybillType;
	
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public BigDecimal getCardIncome() {
		return cardIncome;
	}
	public void setCardIncome(BigDecimal cardIncome) {
		this.cardIncome = cardIncome;
	}
	public BigDecimal getCashIncome() {
		return cashIncome;
	}
	public void setCashIncome(BigDecimal cashIncome) {
		this.cashIncome = cashIncome;
	}
	/**
	 * @return waybillNo : set the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : return the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return weight : set the property weight.
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight : return the property weight.
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return pieces : set the property pieces.
	 */
	public Integer getPieces() {
		return pieces;
	}
	/**
	 * @param pieces : return the property pieces.
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return volume : set the property volume.
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * @param volume : return the property volume.
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * @return payAmount : set the property payAmount.
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	/**
	 * @param payAmount : return the property payAmount.
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * @return isHaveSignBill : set the property isHaveSignBill.
	 */
	public String getIsHaveSignBill() {
		return isHaveSignBill;
	}
	/**
	 * @param isHaveSignBill : return the property isHaveSignBill.
	 */
	public void setIsHaveSignBill(String isHaveSignBill) {
		this.isHaveSignBill = isHaveSignBill;
	}
	/**
	 * @return queryDate : set the property queryDate.
	 */
	public String getQueryDate() {
		return queryDate;
	}
	/**
	 * @param queryDate : return the property queryDate.
	 */
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	/**
	 * @return vehicleNo : set the property vehicleNo.
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo : return the property vehicleNo.
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
}