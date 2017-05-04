package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file UnBillingEntity.java
 * @description 未开单录入
 * @author ChenLiang
 * @created 2013-1-8 下午3:35:00
 * @version 1.0
 */
public class UnBillingEntity extends ScanMsgEntity {

	private static final long serialVersionUID = 3950393785828590776L;

	/**
	 * 价格
	 */
	private double amount;
	/**
	 * 类型
	 */
	private String transType;
	/**
	 * 编码
	 */
	private String truckCode;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

}
