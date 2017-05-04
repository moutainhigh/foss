package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.io.Serializable;

public class UpdateInvoiceState implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	//对账单号
	String statementBillNo;
	
	//发票状态
	String invoiceStatus;
	
	//是否修改成功
	String updateResult;
	
	//异常原因
	String errorReason;

	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}	
}
