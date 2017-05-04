package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 代收货款综合查询DTO 用户结果集返回
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午3:23:32
 */
public class CODCompositeGridDto {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 代收货款类别
	 */
	private String codType;

	/**
	 * 代收货款应付部门
	 */
	private String payableOrgName;

	/**
	 * 代收货款应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 运单单号
	 */
	private String waybillNo;

	/**
	 * 代收货款金额
	 */
	private BigDecimal codAmount;

	/**
	 * 核销部分金额
	 */
	private BigDecimal verifyAmount;

	/**
	 * 应付金额
	 */
	private BigDecimal payeeAmount;
	
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;

	/**
	 * 付款批次号
	 */
	private String batchNumber;

	/**
	 * 发货客户
	 */
	private String consignee;

	/**
	 * 收款人编码
	 */
	private String payeeCode;

	/**
	 * 收款人姓名
	 */
	private String payeeName;

	/**
	 * 收款人帐号
	 */
	private String payeeAccount;

	/**
	 * 开户行
	 */
	private String bank;

	/**
	 * 开单日期
	 */
	private Date businessDate;

	/**
	 * 签收时间
	 */
	private Date signDate;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 状态
	 */
	private String status;


	/**
	 * 资金部退款申请时间
	 */
	private Date tusyorgRfdApptime;

	/**
	 * 付款成功时间
	 */
	private Date refundSuccessTime;

	/**
	 * 汇款失败原因
	 */
	private String remittanceFailNotes;
	
	/**
	 * 汇款导出人
	 */
	private String codExportName;
	
	/**
	 * 汇款导出时间
	 */
	private Date codExportTime;
	
	/**
	 * 批次号
	 */
	private String batchNo;
	
	/**
	 * 代收批次状态：已发送、银企审核通过、银企审核不通过
	 */
	private String codBatchStatus;

	/**
	 * 银企退回失败原因
	 */
	private String failNotes;
	
	
	/**
	 * 支行名称
	 */
	private String bankBranchName;
	
	/**
	 * 运输性质
	 */
	private String productCode;

	/** 收银确认时间*/
	private Date cashConfirmTime;
	
	/**
	 * 合并编号
	 */
	private String mergeCode;
	
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}

	/**
	 * @param payableOrgName
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	/**
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * @param payableOrgCode
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

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
	 * @return codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return payeeAmount
	 */
	public BigDecimal getPayeeAmount() {
		return payeeAmount;
	}

	/**
	 * @param payeeAmount
	 */
	public void setPayeeAmount(BigDecimal payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

	/**
	 * @return batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * @param consignee
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * @return payeeCode
	 */
	public String getPayeeCode() {
		return payeeCode;
	}

	/**
	 * @param payeeCode
	 */
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	/**
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return payeeAccount
	 */
	public String getPayeeAccount() {
		return payeeAccount;
	}

	/**
	 * @param payeeAccount
	 */
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	/**
	 * @return bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return tusyorgRfdApptime
	 */
	public Date getTusyorgRfdApptime() {
		return tusyorgRfdApptime;
	}

	/**
	 * @param tusyorgRfdApptime
	 */
	public void setTusyorgRfdApptime(Date tusyorgRfdApptime) {
		this.tusyorgRfdApptime = tusyorgRfdApptime;
	}

	/**
	 * @return refundSuccessTime
	 */
	public Date getRefundSuccessTime() {
		return refundSuccessTime;
	}

	/**
	 * @param refundSuccessTime
	 */
	public void setRefundSuccessTime(Date refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}

	/**
	 * @return remittanceFailNotes
	 */
	public String getRemittanceFailNotes() {
		return remittanceFailNotes;
	}

	/**
	 * @param remittanceFailNotes
	 */
	public void setRemittanceFailNotes(String remittanceFailNotes) {
		this.remittanceFailNotes = remittanceFailNotes;
	}

	
	/**
	 * @return  the codExportName
	 */
	public String getCodExportName() {
		return codExportName;
	}

	
	/**
	 * @param codExportName the codExportName to set
	 */
	public void setCodExportName(String codExportName) {
		this.codExportName = codExportName;
	}

	
	/**
	 * @return  the codExportTime
	 */
	public Date getCodExportTime() {
		return codExportTime;
	}

	
	/**
	 * @param codExportTime the codExportTime to set
	 */
	public void setCodExportTime(Date codExportTime) {
		this.codExportTime = codExportTime;
	}

	/**
	 * @return codBatchStatus
	 */
	public String getCodBatchStatus() {
		return codBatchStatus;
	}

	/**
	 * @param codBatchStatus
	 */
	public void setCodBatchStatus(String codBatchStatus) {
		this.codBatchStatus = codBatchStatus;
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
	 * @return bankBranchName
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @param bankBranchName
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime the cashConfirmTime to set
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return the mergeCode
	 */
	public String getMergeCode() {
		return mergeCode;
	}

	/**
	 * @param mergeCode the mergeCode to set
	 */
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}

	/**
	 * @return the codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee the codFee to set
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}
	
}
