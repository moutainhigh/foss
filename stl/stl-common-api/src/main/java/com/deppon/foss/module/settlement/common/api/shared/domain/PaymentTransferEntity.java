package com.deppon.foss.module.settlement.common.api.shared.domain;

public class PaymentTransferEntity {

	/**
	 * 转报销工作流号
	 */
	private String workflowNo;
	
	/**
	 * 批次号
	 */
	private String batchNo;
	
	/**
	 * 是否成功，1成功0失败
	 */
	private String isSuccess;
	
	/**
	 * 备注
	 */
	private String reason;

	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
