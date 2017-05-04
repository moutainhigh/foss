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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsDetailDto.java
 * 
 * FILE NAME        	: AbandonedGoodsDetailDto.java
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
/**
 * 综合派送    汇总查询出来清单的数据DTO
 * 
 * @author ibm-meiying
 * @date 2013-06-24 下午3:41:41
 */
public class ArriveDeliverTotalDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer totalVotes;//总票数
	private BigDecimal goodsWeightTotal;//总重量（千克）
	private BigDecimal goodsVolumeTotal;//总体积（立方）
	private Integer goodsQtyTotal;//总件数
	private Integer arrivegoodsQtyTotal;// 到达总件数
	private Integer stockgoodsQtyTotal;//库存总件数
	private BigDecimal  prePayAmountTotal;//预付金额（总）
	private BigDecimal  toPayAmountTotal;//到付金额（总）
	private BigDecimal  codAmountTotal;//代收货款（总）
	private BigDecimal allAmountTotal;//收款总额（总）
	public Integer getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(Integer totalVotes) {
		this.totalVotes = totalVotes;
	}
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public Integer getArrivegoodsQtyTotal() {
		return arrivegoodsQtyTotal;
	}
	public void setArrivegoodsQtyTotal(Integer arrivegoodsQtyTotal) {
		this.arrivegoodsQtyTotal = arrivegoodsQtyTotal;
	}
	public Integer getStockgoodsQtyTotal() {
		return stockgoodsQtyTotal;
	}
	public void setStockgoodsQtyTotal(Integer stockgoodsQtyTotal) {
		this.stockgoodsQtyTotal = stockgoodsQtyTotal;
	}
	public BigDecimal getPrePayAmountTotal() {
		return prePayAmountTotal;
	}
	public void setPrePayAmountTotal(BigDecimal prePayAmountTotal) {
		this.prePayAmountTotal = prePayAmountTotal;
	}
	public BigDecimal getToPayAmountTotal() {
		return toPayAmountTotal;
	}
	public void setToPayAmountTotal(BigDecimal toPayAmountTotal) {
		this.toPayAmountTotal = toPayAmountTotal;
	}
	public BigDecimal getCodAmountTotal() {
		return codAmountTotal;
	}
	public void setCodAmountTotal(BigDecimal codAmountTotal) {
		this.codAmountTotal = codAmountTotal;
	}
	public void setAllAmountTotal(BigDecimal allAmountTotal) {
		this.allAmountTotal = allAmountTotal;
	}
	public BigDecimal getAllAmountTotal() {
		return allAmountTotal;
	}

}