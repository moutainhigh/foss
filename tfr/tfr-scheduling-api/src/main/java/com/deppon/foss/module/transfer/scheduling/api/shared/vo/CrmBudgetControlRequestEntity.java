package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CrmBudgetControlRequestEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//标识字段，0：占用预算占用	1：释放预算	2：查询预算
	private String flag;
	//请求时间
	private String applyDate;
	//理赔ID
	private String claimID;
	//申请人工号
	private String empCode;
	//申请人部门标杆编码
	private String applyDeptStandCode;
	//预算总金额
	private String totalAmount;
	
	private List<BudgetEntity> budgetEntitys;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getClaimID() {
		return claimID;
	}
	public void setClaimID(String claimID) {
		this.claimID = claimID;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}      
	public List<BudgetEntity> getBudgetEntitys() {
		return budgetEntitys;
	}
	public void setBudgetEntitys(List<BudgetEntity> budgetEntitys) {
		this.budgetEntitys = budgetEntitys;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getApplyDeptStandCode() {
		return applyDeptStandCode;
	}
	public void setApplyDeptStandCode(String applyDeptStandCode) {
		this.applyDeptStandCode = applyDeptStandCode;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	
}
