/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: GoodsStateDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;

/**
 * 
 * @author dp-duyi
 * @date 2013-4-10 上午10:43:33
 */
public class GoodsStateDto extends StockEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6367536528575546683L;
	private String goodsState;
	private int stockQty;
	public String getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
}
