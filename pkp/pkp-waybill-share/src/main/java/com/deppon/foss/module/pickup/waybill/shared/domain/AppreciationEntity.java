package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 费用-图片开单推送app
 */
public class AppreciationEntity implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	
	private static final long serialVersionUID = 7643763292938821350L;

	// 增值项名字
	private String appreciationName;
	// 金额
	private BigDecimal appreciationCost;
	public String getAppreciationName() {
		return appreciationName;
	}
	public void setAppreciationName(String appreciationName) {
		this.appreciationName = appreciationName;
	}
	public BigDecimal getAppreciationCost() {
		return appreciationCost;
	}
	public void setAppreciationCost(BigDecimal appreciationCost) {
		this.appreciationCost = appreciationCost;
	}
}
