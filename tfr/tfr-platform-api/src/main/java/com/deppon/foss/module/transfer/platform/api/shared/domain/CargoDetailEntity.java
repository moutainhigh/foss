package com.deppon.foss.module.transfer.platform.api.shared.domain;

public class CargoDetailEntity extends CargoEntity {

	private static final long serialVersionUID = 3943568501387228349L;

	/**
	 * 货量类型
	 */
	private String goodsType;

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

}
