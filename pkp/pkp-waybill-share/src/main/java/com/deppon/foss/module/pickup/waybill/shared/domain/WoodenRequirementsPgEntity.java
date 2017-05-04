package com.deppon.foss.module.pickup.waybill.shared.domain;


public class WoodenRequirementsPgEntity extends WoodenRequirementsEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3512612375424863296L;
	/**
	 * 非包装部分货物尺寸 
	 */
	private String noPackGoodsSize;

	public String getNoPackGoodsSize() {
		return noPackGoodsSize;
	}

	public void setNoPackGoodsSize(String noPackGoodsSize) {
		this.noPackGoodsSize = noPackGoodsSize;
	}
}