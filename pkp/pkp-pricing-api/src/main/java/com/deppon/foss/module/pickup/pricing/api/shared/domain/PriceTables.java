package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class PriceTables extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4593155257625997724L;
	/**
	 * 分页查询结果集
	 */
	private List<PriceTableEntity> priceTableEntitys;
	/**
	 * 总数 
	 */
	private int total;
	public List<PriceTableEntity> getPriceTableEntitys() {
		return priceTableEntitys;
	}
	public void setPriceTableEntitys(List<PriceTableEntity> priceTableEntitys) {
		this.priceTableEntitys = priceTableEntitys;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
