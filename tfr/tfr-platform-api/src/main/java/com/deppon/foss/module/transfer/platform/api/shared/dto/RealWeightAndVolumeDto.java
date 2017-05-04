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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/RealWeightAndVolumeDto.java
 * 
 *  FILE NAME     :RealWeightAndVolumeDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  货量预测DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class RealWeightAndVolumeDto implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -2470702017293793261L;
	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 总体积
	 */
	private BigDecimal totalVolume;
	/**
	 * 总票数
	 */
	private int waybillQtyTotal;
	/**
	 * 总件数
	 */
	private int goodsQtyTotal;

	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param totalWeight the new 总重量
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	/**
	 * 设置 总体积.
	 *
	 * @param totalVolume the new 总体积
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	/**
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public int getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	/**
	 * 设置 总票数.
	 *
	 * @param waybillQtyTotal the new 总票数
	 */
	public void setWaybillQtyTotal(int waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置 总件数.
	 *
	 * @param goodsQtyTotal the new 总件数
	 */
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

}