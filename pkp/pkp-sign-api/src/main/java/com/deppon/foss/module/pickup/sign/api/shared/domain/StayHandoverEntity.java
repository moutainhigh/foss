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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/StayHandoverEntity.java
 * 
 * FILE NAME        	: StayHandoverEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 交接类
 * @author foss-meiying
 * @date 2012-11-9 下午6:34:36
 * @since
 * @version
 */
public class StayHandoverEntity extends BaseEntity {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交接任务号
	 */
	private String handovertaskNo;
	/**
	 * 交接类型
	 */
	private String handoverType;
	/**
	 *  车牌号
	 */
	private String vehicleNo;
	/**
	 *  总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 *  总重量
	 */
	private BigDecimal weightTotal;
	/**
	 *  总体积
	 */
	private BigDecimal volumeTotal;
	/** 
	 * 卡货票数
	 */
	private Integer fastWaybillQty;
	/**
	 *  创建人
	 */
	private String createUserName;
	/**
	 *  创建人编码
	 */
	private String createUserCode;
	/** 
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *  状态
	 */
	private String assignState;
	/**
	 *  到达ID
	 */
	private String truckArriveId;
	/** 
	 * 到达时间
	 */
	private Date actualArriveTime;
	/**
	 * 驻地外场
	 */
	private String transferCenter;
	/**
	 * 空运票数
	 */
	private Integer afWaybillQty;
	/**
	 * 空运体积
	 */
    private BigDecimal afWaybillVolume;
    /**
     * 空运重量
     */
    private BigDecimal afWaybillWeight;
    /**
     * 卡货票数
     */
    private Integer flfWaybillQty;
    /**
     * 卡货体积
     */
    private BigDecimal flfWaybillVolume;
    /**
     * 卡货重量
     */
    private BigDecimal flfWaybillWeight;
    /**
     * 城运票数
     */
    private Integer fsfWaybillQty;
    /**
     * 城运体积
     */
    private BigDecimal fsfWaybillVolume;
    /**
     * 城运重量
     */
    private BigDecimal fsfWaybillWeight;
    
	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	/**
	 * Gets the 交接任务号.
	 *
	 * @return the 交接任务号
	 */
	public String getHandovertaskNo() {
		return handovertaskNo;
	}

	/**
	 * Sets the 交接任务号.
	 *
	 * @param handovertaskNo the new 交接任务号
	 */
	public void setHandovertaskNo(String handovertaskNo) {
		this.handovertaskNo = handovertaskNo;
	}

	/**
	 * Gets the 交接类型.
	 *
	 * @return the 交接类型
	 */
	public String getHandoverType() {
		return handoverType;
	}

	/**
	 * Sets the 交接类型.
	 *
	 * @param handoverType the new 交接类型
	 */
	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
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
	 * Gets the 总件数.
	 *
	 * @return the 总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the 总件数.
	 *
	 * @param goodsQtyTotal the new 总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * Sets the 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * Gets the 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * Sets the 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * Gets the 卡货票数.
	 *
	 * @return the 卡货票数
	 */
	public Integer getFastWaybillQty() {
		return fastWaybillQty;
	}

	/**
	 * Sets the 卡货票数.
	 *
	 * @param fastWaybillQty the new 卡货票数
	 */
	public void setFastWaybillQty(Integer fastWaybillQty) {
		this.fastWaybillQty = fastWaybillQty;
	}

	/**
	 * Gets the 创建人.
	 *
	 * @return the 创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the 创建人.
	 *
	 * @param createUserName the new 创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the 创建人编码.
	 *
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the 创建人编码.
	 *
	 * @param createUserCode the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public String getAssignState() {
		return assignState;
	}

	/**
	 * Sets the 状态.
	 *
	 * @param assignState the new 状态
	 */
	public void setAssignState(String assignState) {
		this.assignState = assignState;
	}

	/**
	 * Gets the 到达ID.
	 *
	 * @return the 到达ID
	 */
	public String getTruckArriveId() {
		return truckArriveId;
	}

	/**
	 * Sets the 到达ID.
	 *
	 * @param truckArriveId the new 到达ID
	 */
	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}

	/**
	 * Gets the 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	/**
	 * Sets the 到达时间.
	 *
	 * @param actualArriveTime the new 到达时间
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	public Integer getAfWaybillQty() {
		return afWaybillQty;
	}

	public void setAfWaybillQty(Integer afWaybillQty) {
		this.afWaybillQty = afWaybillQty;
	}

	public BigDecimal getAfWaybillVolume() {
		return afWaybillVolume;
	}

	public void setAfWaybillVolume(BigDecimal afWaybillVolume) {
		this.afWaybillVolume = afWaybillVolume;
	}

	public BigDecimal getAfWaybillWeight() {
		return afWaybillWeight;
	}

	public void setAfWaybillWeight(BigDecimal afWaybillWeight) {
		this.afWaybillWeight = afWaybillWeight;
	}

	public Integer getFlfWaybillQty() {
		return flfWaybillQty;
	}

	public void setFlfWaybillQty(Integer flfWaybillQty) {
		this.flfWaybillQty = flfWaybillQty;
	}

	public BigDecimal getFlfWaybillVolume() {
		return flfWaybillVolume;
	}

	public void setFlfWaybillVolume(BigDecimal flfWaybillVolume) {
		this.flfWaybillVolume = flfWaybillVolume;
	}

	public BigDecimal getFlfWaybillWeight() {
		return flfWaybillWeight;
	}

	public void setFlfWaybillWeight(BigDecimal flfWaybillWeight) {
		this.flfWaybillWeight = flfWaybillWeight;
	}

	public Integer getFsfWaybillQty() {
		return fsfWaybillQty;
	}

	public void setFsfWaybillQty(Integer fsfWaybillQty) {
		this.fsfWaybillQty = fsfWaybillQty;
	}

	public BigDecimal getFsfWaybillVolume() {
		return fsfWaybillVolume;
	}

	public void setFsfWaybillVolume(BigDecimal fsfWaybillVolume) {
		this.fsfWaybillVolume = fsfWaybillVolume;
	}

	public BigDecimal getFsfWaybillWeight() {
		return fsfWaybillWeight;
	}

	public void setFsfWaybillWeight(BigDecimal fsfWaybillWeight) {
		this.fsfWaybillWeight = fsfWaybillWeight;
	}
	
}