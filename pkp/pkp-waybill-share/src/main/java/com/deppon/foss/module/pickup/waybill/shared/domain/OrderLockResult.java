package com.deppon.foss.module.pickup.waybill.shared.domain;

public class OrderLockResult {
	  private String deptCode;
	  private String resultState;
	  private String failCause;
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getResultState() {
		return resultState;
	}
	public void setResultState(String resultState) {
		this.resultState = resultState;
	}
	public String getFailCause() {
		return failCause;
	}
	public void setFailCause(String failCause) {
		this.failCause = failCause;
	}
	  
}
