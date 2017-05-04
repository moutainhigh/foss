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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArrivesheetDeliverDto.java
 * 
 * FILE NAME        	: ArrivesheetDeliverDto.java
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
 * 查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
 * @author 097972-foss-dengtingting
 */
public class ArrivesheetDeliverDto implements Serializable {

	private static final long serialVersionUID = 1L;

	// 运单号
	private String waybillNo;
	// 到达联件数
	private Integer arriveSheetGoodsQty;
	// 重量
	private BigDecimal weight;
	// 体积
	private BigDecimal goodsVolumeTotal;
	// 金额
	private BigDecimal totalFee;
	// 返单类别
	private String returnBillType;
	// 缴款开始时间
	private Date operateBeginTime;
	// 缴款结束时间
	private Date operateEndTime;
	// 缴款时间
	private Date operateTime;
	// 到达联状态
	private String status;
	// 车牌号
	private String vehicleNo;
	// 司机编号
	private String driverCode;
	// 到达联是否有效
	private String active;
	/** 是否已有财务单据. */
	private String stlbillGeneratedStatus;
	/**
	 * 派送单状态
	 */
	private String deliverbillStatus;
	
	/**
	 *  是否作废
	 */
	private String destroyed;
	
	/**
	 * 刷卡收入
	 */
	private BigDecimal cardIncome;
	/**
	 * 现金收入
	 */
	private BigDecimal cashIncome;	
	
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
	 * @return the arriveSheetGoodsQty
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * @param arriveSheetGoodsQty the arriveSheetGoodsQty to see
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to see
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to see
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
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
	 * @return the operateBeginTime
	 */
	public Date getOperateBeginTime() {
		return operateBeginTime;
	}

	/**
	 * @param operateBeginTime the operateBeginTime to see
	 */
	public void setOperateBeginTime(Date operateBeginTime) {
		this.operateBeginTime = operateBeginTime;
	}

	/**
	 * @return the operateEndTime
	 */
	public Date getOperateEndTime() {
		return operateEndTime;
	}

	/**
	 * @param operateEndTime the operateEndTime to see
	 */
	public void setOperateEndTime(Date operateEndTime) {
		this.operateEndTime = operateEndTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to see
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
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
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to see
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	public String getStlbillGeneratedStatus() {
		return stlbillGeneratedStatus;
	}

	public void setStlbillGeneratedStatus(String stlbillGeneratedStatus) {
		this.stlbillGeneratedStatus = stlbillGeneratedStatus;
	}

	public String getDeliverbillStatus() {
		return deliverbillStatus;
	}

	public void setDeliverbillStatus(String deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
	}
	
	

}