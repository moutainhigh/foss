package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.List;

public class WaybillComponentDetailEntity {
	//费用构成列表
	   private List<ComponentListEntity> componentList;
	   //运单总票数
	   private int total_count;
	   //运单费用总支出
	   private BigDecimal total_amount;
	   
	public List<ComponentListEntity> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<ComponentListEntity> componentList) {
		this.componentList = componentList;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int totalCount) {
		this.total_count = totalCount;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal totalAmount) {
		this.total_amount = totalAmount;
	}

}
