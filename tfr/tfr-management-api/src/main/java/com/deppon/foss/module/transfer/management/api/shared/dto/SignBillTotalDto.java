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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/SignBillTotalDto.java
 *  
 *  FILE NAME          :SignBillTotalDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.math.BigDecimal;

/**
 * 签单统计项DTO
 * @author 038300-foss-pengzhen
 * @date 2012-12-26 下午1:40:16
 */
public class SignBillTotalDto {
	/**
	 * 司机数
	 */
	private int totalDrivers;
	/**
	 * 签单票数合计
	 */
	private int totalBills;
	/**
	 * 体积合计
	 */
	private BigDecimal totalVolume;
	/**
	 * 重量合计
	 */
	private BigDecimal totalWeight;
	/**
	 * 接货票数合计
	 */
	private int totalWaybillQty;
	/**
	 * 上楼票数合计
	 */
	private int totalupstairsBillQty;
	/**
	 * 单独接货票数合计
	 */
	private int totalSingleReceiveBillQty;
	
	/**
	 * 获取 司机数.
	 *
	 * @return the 司机数
	 */
	public int getTotalDrivers() {
		return totalDrivers;
	}
	
	/**
	 * 设置 司机数.
	 *
	 * @param totalDrivers the new 司机数
	 */
	public void setTotalDrivers(int totalDrivers) {
		this.totalDrivers = totalDrivers;
	}
	
	/**
	 * 获取 签单票数合计.
	 *
	 * @return the 签单票数合计
	 */
	public int getTotalBills() {
		return totalBills;
	}
	
	/**
	 * 设置 签单票数合计.
	 *
	 */
	public void setTotalBills(int totalBills) {
		this.totalBills = totalBills;
	}
	
	/**
	 * 获取 体积合计.
	 *
	 * @return the 体积合计
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}
	
	/**
	 * 设置 体积合计.
	 *
	 * @param totalVolume the new 体积合计
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}
	
	/**
	 * 获取 重量合计.
	 *
	 * @return the 重量合计
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	
	/**
	 * 设置 重量合计.
	 *
	 * @param totalWeight the new 重量合计
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	/**
	 * 获取 接货票数合计.
	 *
	 * @return the 接货票数合计
	 */
	public int getTotalWaybillQty() {
		return totalWaybillQty;
	}
	
	/**
	 * 设置 接货票数合计.
	 *
	 * @param totalWaybillQty the new 接货票数合计
	 */
	public void setTotalWaybillQty(int totalWaybillQty) {
		this.totalWaybillQty = totalWaybillQty;
	}
	
	/**
	 * 获取 上楼票数合计.
	 *
	 * @return the 上楼票数合计
	 */
	public int getTotalupstairsBillQty() {
		return totalupstairsBillQty;
	}
	
	/**
	 * 设置 上楼票数合计.
	 *
	 * @param totalupstairsBillQty the new 上楼票数合计
	 */
	public void setTotalupstairsBillQty(int totalupstairsBillQty) {
		this.totalupstairsBillQty = totalupstairsBillQty;
	}
	
	/**
	 * 获取 单独接货票数合计.
	 *
	 * @return the 单独接货票数合计
	 */
	public int getTotalSingleReceiveBillQty() {
		return totalSingleReceiveBillQty;
	}
	
	/**
	 * 设置 单独接货票数合计.
	 *
	 * @param totalSingleReceiveBillQty the new 单独接货票数合计
	 */
	public void setTotalSingleReceiveBillQty(int totalSingleReceiveBillQty) {
		this.totalSingleReceiveBillQty = totalSingleReceiveBillQty;
	}
}