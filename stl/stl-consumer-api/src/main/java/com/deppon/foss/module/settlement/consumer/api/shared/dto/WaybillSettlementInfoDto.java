package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运单财务信息dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 上午8:17:42
 * @since
 * @version
 */
public class WaybillSettlementInfoDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4075061097757209011L;

	/*************************************** 代收货款 ********************************************/
	/**
	 * 实收代收货款
	 */
	private BigDecimal codFee;

	/**
	 * 应付金额
	 */
	private BigDecimal payableAmount;

	/**
	 * 核销金额
	 */
	private BigDecimal writeoffAmount;

	/**
	 * 可退金额
	 */
	private BigDecimal retreatAmount;

	/**
	 * 退款状态
	 */
	private String codRefundStatus;

	/**
	 * 代收货款退款说明
	 */
	private String codRefundNotes;

	/********************************************** 装卸费 ********************************/
	/**
	 * 装卸费退款类型
	 */
	private String sfRefundType;

	/**
	 * 装卸费退款金额
	 */
	private BigDecimal sfRefundAmount;

	/**
	 * 装卸费退款状态
	 */
	private String sfRecundStatus;

	/**
	 * 装卸费退款说明
	 */
	private String sfRefundNotes;

	/************************************* 运单发票信息 **************************/
	/**
	 * 始发开票金额
	 */
	private BigDecimal origInvoiceAmount;

	/**
	 * 到达开票金额
	 */
	private BigDecimal destInvoiceAmount;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 描述
	 */
	private String notes;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * @return the codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 *            the codFee to set
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return the payableAmount
	 */
	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @param payableAmount
	 *            the payableAmount to set
	 */
	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	/**
	 * @return the writeoffAmount
	 */
	public BigDecimal getWriteoffAmount() {
		return writeoffAmount;
	}

	/**
	 * @param writeoffAmount
	 *            the writeoffAmount to set
	 */
	public void setWriteoffAmount(BigDecimal writeoffAmount) {
		this.writeoffAmount = writeoffAmount;
	}

	/**
	 * @return the retreatAmount
	 */
	public BigDecimal getRetreatAmount() {
		return retreatAmount;
	}

	/**
	 * @param retreatAmount
	 *            the retreatAmount to set
	 */
	public void setRetreatAmount(BigDecimal retreatAmount) {
		this.retreatAmount = retreatAmount;
	}

	/**
	 * @return the codRefundStatus
	 */
	public String getCodRefundStatus() {
		return codRefundStatus;
	}

	/**
	 * @param codRefundStatus
	 *            the codRefundStatus to set
	 */
	public void setCodRefundStatus(String codRefundStatus) {
		this.codRefundStatus = codRefundStatus;
	}

	/**
	 * @return the codRefundNotes
	 */
	public String getCodRefundNotes() {
		return codRefundNotes;
	}

	/**
	 * @param codRefundNotes
	 *            the codRefundNotes to set
	 */
	public void setCodRefundNotes(String codRefundNotes) {
		this.codRefundNotes = codRefundNotes;
	}

	/**
	 * @return the sfRefundType
	 */
	public String getSfRefundType() {
		return sfRefundType;
	}

	/**
	 * @param sfRefundType
	 *            the sfRefundType to set
	 */
	public void setSfRefundType(String sfRefundType) {
		this.sfRefundType = sfRefundType;
	}

	/**
	 * @return the sfRefundAmount
	 */
	public BigDecimal getSfRefundAmount() {
		return sfRefundAmount;
	}

	/**
	 * @param sfRefundAmount
	 *            the sfRefundAmount to set
	 */
	public void setSfRefundAmount(BigDecimal sfRefundAmount) {
		this.sfRefundAmount = sfRefundAmount;
	}

	/**
	 * @return the sfRecundStatus
	 */
	public String getSfRecundStatus() {
		return sfRecundStatus;
	}

	/**
	 * @param sfRecundStatus
	 *            the sfRecundStatus to set
	 */
	public void setSfRecundStatus(String sfRecundStatus) {
		this.sfRecundStatus = sfRecundStatus;
	}

	/**
	 * @return the sfRefundNotes
	 */
	public String getSfRefundNotes() {
		return sfRefundNotes;
	}

	/**
	 * @param sfRefundNotes
	 *            the sfRefundNotes to set
	 */
	public void setSfRefundNotes(String sfRefundNotes) {
		this.sfRefundNotes = sfRefundNotes;
	}

	/**
	 * @return the origInvoiceAmount
	 */
	public BigDecimal getOrigInvoiceAmount() {
		return origInvoiceAmount;
	}

	/**
	 * @param origInvoiceAmount
	 *            the origInvoiceAmount to set
	 */
	public void setOrigInvoiceAmount(BigDecimal origInvoiceAmount) {
		this.origInvoiceAmount = origInvoiceAmount;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the destInvoiceAmount
	 */
	public BigDecimal getDestInvoiceAmount() {
		return destInvoiceAmount;
	}

	/**
	 * @param destInvoiceAmount
	 *            the destInvoiceAmount to set
	 */
	public void setDestInvoiceAmount(BigDecimal destInvoiceAmount) {
		this.destInvoiceAmount = destInvoiceAmount;
	}

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 *            the verifyAmount to set
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return the unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 *            the unverifyAmount to set
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
