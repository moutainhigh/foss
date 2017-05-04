package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 
 * 代收货款 线上汇款结果DTO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-8 上午11:11:22
 */
public class BillCODOnlineResultDto {

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * 线上汇款结果
	 */
	private BillCODOnlineResultEnum result;

	/**
	 * 失败原因
	 */
	private String failNotes;
	
	/**
	 * 银行账户
	 */
	private String bankAccount;

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return result
	 */
	public BillCODOnlineResultEnum getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(BillCODOnlineResultEnum result) {
		this.result = result;
	}

	/**
	 * @return failNotes
	 */
	public String getFailNotes() {
		return failNotes;
	}

	/**
	 * @param failNotes
	 */
	public void setFailNotes(String failNotes) {
		this.failNotes = failNotes;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}
