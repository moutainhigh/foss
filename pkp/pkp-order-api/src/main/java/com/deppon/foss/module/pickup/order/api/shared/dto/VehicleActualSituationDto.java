/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/VehicleActualSituationDto.java
 * 
 * FILE NAME        	: VehicleActualSituationDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 车辆实况DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-30 下午5:23:02
 */
public class VehicleActualSituationDto implements Serializable {

	private static final long serialVersionUID = 1197232002306368501L;
	/** 
	 * id
	 */
	private String id;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 剩余重量
	 */
	private BigDecimal remainingWeight;
	/** 
	 * 剩余体积
	 */
	private BigDecimal remainingVolume;
	/** 
	 * 已接票数
	 */
	private Integer alreadyPickupGoodsQty;
	/** 
	 * 未接票数
	 */
	private Integer nonePickupGoodsQty;
	/** 
	 * 已送票数
	 */
	private Integer alreadyDeliverGoodsQty;
	/** 
	 * 未送票数
	 */
	private Integer noneDeliverGoodsQty;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the remainingWeight
	 */
	public BigDecimal getRemainingWeight() {
		return remainingWeight;
	}

	/**
	 * @param remainingWeight the remainingWeight to set
	 */
	public void setRemainingWeight(BigDecimal remainingWeight) {
		this.remainingWeight = remainingWeight;
	}

	/**
	 * @return the remainingVolume
	 */
	public BigDecimal getRemainingVolume() {
		return remainingVolume;
	}

	/**
	 * @param remainingVolume the remainingVolume to set
	 */
	public void setRemainingVolume(BigDecimal remainingVolume) {
		this.remainingVolume = remainingVolume;
	}

	/**
	 * @return the alreadyPickupGoodsQty
	 */
	public Integer getAlreadyPickupGoodsQty() {
		return alreadyPickupGoodsQty;
	}

	/**
	 * @param alreadyPickupGoodsQty the alreadyPickupGoodsQty to set
	 */
	public void setAlreadyPickupGoodsQty(Integer alreadyPickupGoodsQty) {
		this.alreadyPickupGoodsQty = alreadyPickupGoodsQty;
	}

	/**
	 * @return the nonePickupGoodsQty
	 */
	public Integer getNonePickupGoodsQty() {
		return nonePickupGoodsQty;
	}

	/**
	 * @param nonePickupGoodsQty the nonePickupGoodsQty to set
	 */
	public void setNonePickupGoodsQty(Integer nonePickupGoodsQty) {
		this.nonePickupGoodsQty = nonePickupGoodsQty;
	}

	/**
	 * @return the alreadyDeliverGoodsQty
	 */
	public Integer getAlreadyDeliverGoodsQty() {
		return alreadyDeliverGoodsQty;
	}

	/**
	 * @param alreadyDeliverGoodsQty the alreadyDeliverGoodsQty to set
	 */
	public void setAlreadyDeliverGoodsQty(Integer alreadyDeliverGoodsQty) {
		this.alreadyDeliverGoodsQty = alreadyDeliverGoodsQty;
	}

	/**
	 * @return the noneDeliverGoodsQty
	 */
	public Integer getNoneDeliverGoodsQty() {
		return noneDeliverGoodsQty;
	}

	/**
	 * @param noneDeliverGoodsQty the noneDeliverGoodsQty to set
	 */
	public void setNoneDeliverGoodsQty(Integer noneDeliverGoodsQty) {
		this.noneDeliverGoodsQty = noneDeliverGoodsQty;
	}
}