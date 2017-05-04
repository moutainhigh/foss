package com.deppon.esb.inteface.domain.fins;

public class FossToFinsRemitCommonResponse {
	/**
	 * 编号
	 */
	private String billNum;
	
	/**
	 * 是否成功（1、成功，0、失败）
	 */
	private String isSuccess;
	/**
	 * 失败原因
	 */
	private String faileReason;
	
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getFaileReason() {
		return faileReason;
	}
	public void setFaileReason(String faileReason) {
		this.faileReason = faileReason;
	}
	

}
