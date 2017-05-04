package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class DiscountProgramEntity implements Serializable {
	
	private static final long serialVersionUID = 4211283206658964323L;
	// 折扣方案类型
	private String discountProgramType;
	// 折扣率
	private double discountRate;
	// 减免的费用
	private double reduceFee;
	public String getDiscountProgramType() {
		return discountProgramType;
	}
	public void setDiscountProgramType(String discountProgramType) {
		this.discountProgramType = discountProgramType;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public double getReduceFee() {
		return reduceFee;
	}
	public void setReduceFee(double reduceFee) {
		this.reduceFee = reduceFee;
	}

}
