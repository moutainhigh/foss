package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

public class CrmBudgetControlResponseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//理赔预算值
	private String budget;
	//是否成功0：失败，1：成功
	private int isSucceed;
	//失败原因
	private String failure;

	
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public int getIsSucceed() {
		return isSucceed;
	}
	public void setIsSucceed(int isSucceed) {
		this.isSucceed = isSucceed;
	}
	
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}
	@Override
	public String toString() {
		return "CrmBudgetControlResponseEntity [budget=" + budget
				+ ", isSucceed=" + isSucceed + ", failure=" + failure + "]";
	}
	
	
	
}
