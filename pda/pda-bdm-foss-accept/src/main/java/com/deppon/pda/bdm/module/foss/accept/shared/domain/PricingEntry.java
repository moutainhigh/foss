package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class PricingEntry implements Serializable {

	private static final long serialVersionUID = -6261517760376611111L;
	// 计价条目编码
	private String pricingEntryCode;
	// 原始费用
	private double originnalCost;
	// 子类型
	private String subType;
	//木架体积
	private double woodenVolume;

	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	public double getOriginnalCost() {
		return originnalCost;
	}

	public void setOriginnalCost(double originnalCost) {
		this.originnalCost = originnalCost;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public double getWoodenVolume() {
		return woodenVolume;
	}

	public void setWoodenVolume(double woodenVolume) {
		this.woodenVolume = woodenVolume;
	}

}
