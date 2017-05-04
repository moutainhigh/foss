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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/StayHandoverDto.java
 * 
 * FILE NAME        	: StayHandoverDto.java
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

/**
 * 交接dto
 * @author foss-meiying
 * @date 2012-11-28 上午11:41:39
 * @since
 * @version
 */
public class StayHandoverDto implements Serializable {
	private static final long serialVersionUID = 5372198774604775803L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;
	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 到达联件数
	 */
	private Integer arriveSheetGoodsQty;
	/**
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/***
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 是否已生成交接
	 */
    private String handoverStatus;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 是否是快递
     */
    private String isExpress;
    /**
     * 是否扩大查询时间
     */
    private String isExpandTime;

	public String getIsExpandTime() {
		return isExpandTime;
	}

	public void setIsExpandTime(String isExpandTime) {
		this.isExpandTime = isExpandTime;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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

	/**
	 * Gets the 货物总件数.
	 *
	 * @return the 货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the 货物总件数.
	 *
	 * @param goodsQtyTotal the new 货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the 货物总重量.
	 *
	 * @return the 货物总重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * Sets the 货物总重量.
	 *
	 * @param goodsWeightTotal the new 货物总重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * Gets the 货物总体积.
	 *
	 * @return the 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the 货物总体积.
	 *
	 * @param goodsVolumeTotal the new 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the 到达联件数.
	 *
	 * @return the 到达联件数
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 到达联件数.
	 *
	 * @param arriveSheetGoodsQty the new 到达联件数
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * Gets the 签收件数.
	 *
	 * @return the 签收件数
	 */
	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}

	/**
	 * Sets the 签收件数.
	 *
	 * @param signGoodsQty the new 签收件数
	 */
	public void setSignGoodsQty(Integer signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	/**
	 * Gets the * 司机工号.
	 *
	 * @return the * 司机工号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the * 司机工号.
	 *
	 * @param driverCode the new * 司机工号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 是否已生成交接.
	 *
	 * @return the 是否已生成交接
	 */
	public String getHandoverStatus() {
		return handoverStatus;
	}

	/**
	 * Sets the 是否已生成交接.
	 *
	 * @param handoverStatus the new 是否已生成交接
	 */
	public void setHandoverStatus(String handoverStatus) {
		this.handoverStatus = handoverStatus;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}


}