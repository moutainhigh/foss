package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.math.BigDecimal;

public class ResponsibilityInfo {
	// 责任部门，费用承担部门标杆编码
	private String responsibilityDeptCode;
	// 部门承担金额费用
	private BigDecimal responsibilityMoney;

	public String getResponsibilityDeptCode() {
		return responsibilityDeptCode;
	}

	public void setResponsibilityDeptCode(String responsibilityDeptCode) {
		this.responsibilityDeptCode = responsibilityDeptCode;
	}

	public BigDecimal getResponsibilityMoney() {
		return responsibilityMoney;
	}

	public void setResponsibilityMoney(BigDecimal responsibilityMoney) {
		this.responsibilityMoney = responsibilityMoney;
	}
}
