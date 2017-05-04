package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;

public class ComponentListEntity {
	//费用构成名称
    private String component_name;
    //费用总支出
    private BigDecimal amount;
    
	public String getComponent_name() {
		return component_name;
	}
	public void setComponent_name(String componentName) {
		this.component_name = componentName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
