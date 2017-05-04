package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;

public class BudgetEntity implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	//预算金额
	private String amount;
	//部门标杆编码
	private String deptStandCode;
	//费用期间
	private String month;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDeptStandCode() {
		return deptStandCode;
	}
	public void setDeptStandCode(String deptStandCode) {
		this.deptStandCode = deptStandCode;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
