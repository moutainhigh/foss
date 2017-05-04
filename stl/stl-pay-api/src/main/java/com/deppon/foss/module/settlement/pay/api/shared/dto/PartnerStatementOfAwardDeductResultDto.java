package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-5 下午1:57:03    
 */
public class PartnerStatementOfAwardDeductResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3289038428720056407L;

	/**
	 * 来源单号
	 */
	private String sourceBillNo;
	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * 是否成功
	 */
	private boolean result;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the deductResult
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * @param deductResult the deductResult to set
	 */
	public void setResult(boolean deductResult) {
		this.result = deductResult;
	}

	/**
	 * @return the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}
}
