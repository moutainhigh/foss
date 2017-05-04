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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/dto/WaybillStockStatisticsDto.java
 *  
 *  FILE NAME          :WaybillStockStatisticsDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 查询库存界面统计栏信息
 */
public class WaybillStockStatisticsDto {
	
	/**
	 * 保留3位小数
	 */
	private static final int three = 3;
	
	/**
	 * 运单票数
	 */
	private long waybillQty;
	/**
	 * 库存件数
	 */
	private long stockGoodsQty;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	
	/**
	 * 原始总体积
	 */
	private BigDecimal srcVolumeTotal;

	//初始化构造器
	public WaybillStockStatisticsDto() {
		super();
		this.waybillQty = 0L;
		this.stockGoodsQty = 0L;
		this.weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		this.volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		this.srcVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
	}

	/**
	 * 获取 运单票数.
	 *
	 * @return the 运单票数
	 */
	public long getWaybillQty() {
		return waybillQty;
	}
	
	/**
	 * 设置 运单票数.
	 *
	 * @param waybillQty the new 运单票数
	 */
	public void setWaybillQty(long waybillQty) {
		this.waybillQty = waybillQty;
	}
	
	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public long getStockGoodsQty() {
		return stockGoodsQty;
	}
	
	/**
	 * 设置 库存件数.
	 *
	 * @param stockGoodsQty the new 库存件数
	 */
	public void setStockGoodsQty(long stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	
	/**
	 * 获取原始总体积
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-11
	 * @return
	 */
	public BigDecimal getSrcVolumeTotal() {
		return srcVolumeTotal;
	}

	/**
	 * 设置原始总体积
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-11
	 * @param srcVolumeTotal
	 */
	public void setSrcVolumeTotal(BigDecimal srcVolumeTotal) {
		this.srcVolumeTotal = srcVolumeTotal;
	}
	
	

}