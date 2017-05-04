package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 代收货款综合查询条件VO.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午2:58:08
 */
public class CODCompositeQueryDto {

	/** 查询类别(按单号，还是日期). */
	private String queryType;
	
	/** 用户编码. */
	private String userCode;

	/** 运单单号集合. */
	private List<String> waybillNos;

	/** 运单单号. */
	private String waybillNo;

	/** 是否有效. */
	private String active;

	/** 代收货款业务类型. */
	private String codType;

	/** 应付类别. */
	private String payableType;

	/** 付款状态. */
	private String status;

	/** 起始开单时间. */
	private Date inceptBizDate;

	/** 结束开单时间. */
	private Date endBizDate;

	/** 起始签收日期. */
	private Date inceptSignDate;

	/** 结束签收日期. */
	private Date endSignDate;

	/** 起始付款日期. */
	private Date inceptPaymentDate;

	/** 结束签收日期. */
	private Date endPaymentDate;
	
	/**
	 * 汇款成功日期
	 */
	private Date inceptRefundSuccessDate;

	/**
	 * 汇款成功日期
	 */
	private Date endRefundSuccessDate;

	/** 应付部门编码. */
	private String payableOrgCode;
	
	/** 当前登录部门编码. */
	private String currentOrgCode;

	/** 收款人信息. */
	private String payeeName;

	/** 发货人. */
	private String consignee;

	/** 起始代收货款金额. */
	private BigDecimal inceptCodAmount;

	/** 结束代收货款金额. */
	private BigDecimal endCodAmount;

	/** 支付银行. */
	private String bank;
	
	/**
	 * 银行
	 */
	private List<String> bankList;

	/** 付款批次号. */
	private String batchNumber;

	/** 支付路径. */
	private String refundPath;

	/** 当前代收货款记录Id. */
	private String currentCodId;
	
	/**
	 * 核销类型
	 */
	private String writeoffType;
	
	/**
	 * 是否红单
	 */
	private String isRedBack;
	
	/**
	 * 运输性质
	 */
	private List<String> productCodeList;
	
	/**
	 * 合并编号
	 */
	private String mergeCode;

	/**
	 * Gets the query type.
	 *
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * Sets the query type.
	 *
	 * @param queryType the new query type
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * Gets the waybill nos.
	 *
	 * @return waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * Sets the waybill nos.
	 *
	 * @param waybillNos the new waybill nos
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the active.
	 *
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the cod type.
	 *
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * Sets the cod type.
	 *
	 * @param codType the new cod type
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * Gets the payable type.
	 *
	 * @return payableType
	 */
	public String getPayableType() {
		return payableType;
	}

	/**
	 * Sets the payable type.
	 *
	 * @param payableType the new payable type
	 */
	public void setPayableType(String payableType) {
		this.payableType = payableType;
	}

	/**
	 * Gets the status.
	 *
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the incept biz date.
	 *
	 * @return inceptBizDate
	 */
	public Date getInceptBizDate() {
		return inceptBizDate;
	}

	/**
	 * Sets the incept biz date.
	 *
	 * @param inceptBizDate the new incept biz date
	 */
	public void setInceptBizDate(Date inceptBizDate) {
		this.inceptBizDate = inceptBizDate;
	}

	/**
	 * Gets the end biz date.
	 *
	 * @return endBizDate
	 */
	public Date getEndBizDate() {
		return endBizDate;
	}

	/**
	 * Sets the end biz date.
	 *
	 * @param endBizDate the new end biz date
	 */
	public void setEndBizDate(Date endBizDate) {
		this.endBizDate = endBizDate;
	}

	/**
	 * Gets the incept sign date.
	 *
	 * @return inceptSignDate
	 */
	public Date getInceptSignDate() {
		return inceptSignDate;
	}

	/**
	 * Sets the incept sign date.
	 *
	 * @param inceptSignDate the new incept sign date
	 */
	public void setInceptSignDate(Date inceptSignDate) {
		this.inceptSignDate = inceptSignDate;
	}

	/**
	 * Gets the end sign date.
	 *
	 * @return endSignDate
	 */
	public Date getEndSignDate() {
		return endSignDate;
	}

	/**
	 * Sets the end sign date.
	 *
	 * @param endSignDate the new end sign date
	 */
	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}

	/**
	 * Gets the incept payment date.
	 *
	 * @return inceptPaymentDate
	 */
	public Date getInceptPaymentDate() {
		return inceptPaymentDate;
	}

	/**
	 * Sets the incept payment date.
	 *
	 * @param inceptPaymentDate the new incept payment date
	 */
	public void setInceptPaymentDate(Date inceptPaymentDate) {
		this.inceptPaymentDate = inceptPaymentDate;
	}

	/**
	 * Gets the end payment date.
	 *
	 * @return endPaymentDate
	 */
	public Date getEndPaymentDate() {
		return endPaymentDate;
	}

	/**
	 * Sets the end payment date.
	 *
	 * @param endPaymentDate the new end payment date
	 */
	public void setEndPaymentDate(Date endPaymentDate) {
		this.endPaymentDate = endPaymentDate;
	}

	/**
	 * Gets the payable org code.
	 *
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * Sets the payable org code.
	 *
	 * @param payableOrgCode the new payable org code
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * Gets the payee name.
	 *
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * Sets the payee name.
	 *
	 * @param payeeName the new payee name
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * Gets the consignee.
	 *
	 * @return consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * Sets the consignee.
	 *
	 * @param consignee the new consignee
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the incept cod amount.
	 *
	 * @return inceptCodAmount
	 */
	public BigDecimal getInceptCodAmount() {
		return inceptCodAmount;
	}

	/**
	 * Sets the incept cod amount.
	 *
	 * @param inceptCodAmount the new incept cod amount
	 */
	public void setInceptCodAmount(BigDecimal inceptCodAmount) {
		this.inceptCodAmount = inceptCodAmount;
	}

	/**
	 * Gets the end cod amount.
	 *
	 * @return endCodAmount
	 */
	public BigDecimal getEndCodAmount() {
		return endCodAmount;
	}

	/**
	 * Sets the end cod amount.
	 *
	 * @param endCodAmount the new end cod amount
	 */
	public void setEndCodAmount(BigDecimal endCodAmount) {
		this.endCodAmount = endCodAmount;
	}

	/**
	 * Gets the bank.
	 *
	 * @return bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * Sets the bank.
	 *
	 * @param bank the new bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * Gets the batch number.
	 *
	 * @return batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * Sets the batch number.
	 *
	 * @param batchNumber the new batch number
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * Gets the refund path.
	 *
	 * @return refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	/**
	 * Sets the refund path.
	 *
	 * @param refundPath the new refund path
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	/**
	 * Gets the current cod id.
	 *
	 * @return currentCodId
	 */
	public String getCurrentCodId() {
		return currentCodId;
	}

	/**
	 * Sets the current cod id.
	 *
	 * @param currentCodId the new current cod id
	 */
	public void setCurrentCodId(String currentCodId) {
		this.currentCodId = currentCodId;
	}

	/**
	 * Gets the user code.
	 *
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * Sets the user code.
	 *
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
	/**
	 * @return  the writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	
	/**
	 * @param writeoffType the writeoffType to set
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

	
	/**
	 * @return  the isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param isRedBack the isRedBack to set
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return bankList
	 */
	public List<String> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList
	 */
	public void setBankList(List<String> bankList) {
		this.bankList = bankList;
	}

	/**
	 * @return currentOrgCode
	 */
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	/**
	 * @param currentOrgCode
	 */
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
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
	 * @return the inceptRefundSuccessDate
	 */
	public Date getInceptRefundSuccessDate() {
		return inceptRefundSuccessDate;
	}

	/**
	 * @param inceptRefundSuccessDate the inceptRefundSuccessDate to set
	 */
	public void setInceptRefundSuccessDate(Date inceptRefundSuccessDate) {
		this.inceptRefundSuccessDate = inceptRefundSuccessDate;
	}

	/**
	 * @return the endRefundSuccessDate
	 */
	public Date getEndRefundSuccessDate() {
		return endRefundSuccessDate;
	}

	/**
	 * @param endRefundSuccessDate the endRefundSuccessDate to set
	 */
	public void setEndRefundSuccessDate(Date endRefundSuccessDate) {
		this.endRefundSuccessDate = endRefundSuccessDate;
	}
	
}
