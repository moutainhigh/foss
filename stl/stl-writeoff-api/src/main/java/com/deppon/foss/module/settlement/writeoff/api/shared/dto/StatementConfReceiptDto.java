package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;

/**
 * 对账单回执Dto类
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-2 下午5:38:16
 */
public class StatementConfReceiptDto implements Serializable {

	/*
	 * × 对账单回执Dto类序列号
	 */
	private static final long serialVersionUID = 6221729221094274128L;

	/**
	 * 对账单回执
	 */
	private StatementConfReceiptEntity statementConfReceiptEntity;

	/**
	 * 对账单ID
	 */
	private String statementBillId;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	
	/**
	 * @return statementConfReceiptEntity
	 */
	public StatementConfReceiptEntity getStatementConfReceiptEntity() {
		return statementConfReceiptEntity;
	}

	
	/**
	 * @param statementConfReceiptEntity
	 */
	public void setStatementConfReceiptEntity(
			StatementConfReceiptEntity statementConfReceiptEntity) {
		this.statementConfReceiptEntity = statementConfReceiptEntity;
	}

	
	/**
	 * @return statementBillId
	 */
	public String getStatementBillId() {
		return statementBillId;
	}

	
	/**
	 * @param statementBillId
	 */
	public void setStatementBillId(String statementBillId) {
		this.statementBillId = statementBillId;
	}

	
	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	
	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}


}
