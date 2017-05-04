package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RepaymentDisableApplicationEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**id*/
	private String id;
	/**还款单号*/
	private String repaymentNo;
	/**客户编码*/
	private String customerCode;
	/**客户名称*/
	private String customerName;
	/**申请作废小区编码*/
	private String applyParentOrgCode;
	/**申请作废小区名称*/
	private String applyParentOrgName;
	/**申请作废大区编码*/
	private String applyAreaCode;
	/**申请作废大区名称*/
	private String applyAreaName;
	/**申请作废部门编码*/
	private String applyOrgCode;
	/**申请作废部门名称*/
	private String applyOrgName;
	/**申请作废人编码*/
	private String applyUserCode;
	/**申请作废人名称*/
	private String applyUserName;
	/**申请作废时间*/
	private Date applyTime;
	/**申请作废金额*/
	private BigDecimal amount;
	/**付款方式*/
	private String paymentType;
	/**作废原因*/
	private String disableReason;
	/**审核人编码*/
	private String approveUserCode;
	/**审核人名称*/
	private String approveUserName;
	/**审核人部门编码*/
	private String approveOrgCode;
	/**审核人部门名称*/
	private String approveOrgName;
	/**审核时间*/
	private Date approveTime;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date modifyTime;
	/**审核状态*/
	private String approveStatus;
	/**有效*/
	private String active;
	/**初始化*/
	private String isInit;
	/***/
	private String isAllDisable;
	/***/
	private String disableNote;
	private BigDecimal repaymentAmount;
	private int disableNum;
	
	/**实际还款金额*/
	private BigDecimal trueAmount;
	/**汇款编号*/
	private String oaPaymentNo;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}
	/**
	 * @param repaymentNo the repaymentNo to set
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the applyParentOrgCode
	 */
	public String getApplyParentOrgCode() {
		return applyParentOrgCode;
	}
	/**
	 * @param applyParentOrgCode the applyParentOrgCode to set
	 */
	public void setApplyParentOrgCode(String applyParentOrgCode) {
		this.applyParentOrgCode = applyParentOrgCode;
	}
	/**
	 * @return the applyParentOrgName
	 */
	public String getApplyParentOrgName() {
		return applyParentOrgName;
	}
	/**
	 * @param applyParentOrgName the applyParentOrgName to set
	 */
	public void setApplyParentOrgName(String applyParentOrgName) {
		this.applyParentOrgName = applyParentOrgName;
	}
	/**
	 * @return the applyAreaCode
	 */
	public String getApplyAreaCode() {
		return applyAreaCode;
	}
	/**
	 * @param applyAreaCode the applyAreaCode to set
	 */
	public void setApplyAreaCode(String applyAreaCode) {
		this.applyAreaCode = applyAreaCode;
	}
	/**
	 * @return the applyAreaName
	 */
	public String getApplyAreaName() {
		return applyAreaName;
	}
	/**
	 * @param applyAreaName the applyAreaName to set
	 */
	public void setApplyAreaName(String applyAreaName) {
		this.applyAreaName = applyAreaName;
	}
	/**
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	/**
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	/**
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	/**
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	/**
	 * @return the applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}
	/**
	 * @param applyUserCode the applyUserCode to set
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}
	/**
	 * @return the applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}
	/**
	 * @param applyUserName the applyUserName to set
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	/**
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return the disableReason
	 */
	public String getDisableReason() {
		return disableReason;
	}
	/**
	 * @param disableReason the disableReason to set
	 */
	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}
	/**
	 * @return the approveUserCode
	 */
	public String getApproveUserCode() {
		return approveUserCode;
	}
	/**
	 * @param approveUserCode the approveUserCode to set
	 */
	public void setApproveUserCode(String approveUserCode) {
		this.approveUserCode = approveUserCode;
	}
	/**
	 * @return the approveUserName
	 */
	public String getApproveUserName() {
		return approveUserName;
	}
	/**
	 * @param approveUserName the approveUserName to set
	 */
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	/**
	 * @return the approveOrgCode
	 */
	public String getApproveOrgCode() {
		return approveOrgCode;
	}
	/**
	 * @param approveOrgCode the approveOrgCode to set
	 */
	public void setApproveOrgCode(String approveOrgCode) {
		this.approveOrgCode = approveOrgCode;
	}
	/**
	 * @return the approveOrgName
	 */
	public String getApproveOrgName() {
		return approveOrgName;
	}
	/**
	 * @param approveOrgName the approveOrgName to set
	 */
	public void setApproveOrgName(String approveOrgName) {
		this.approveOrgName = approveOrgName;
	}
	/**
	 * @return the approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}
	/**
	 * @param approveTime the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return the approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}
	/**
	 * @param approveStatus the approveStatus to set
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the isInit
	 */
	public String getIsInit() {
		return isInit;
	}
	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	/**
	 * @return the isAllDisable
	 */
	public String getIsAllDisable() {
		return isAllDisable;
	}
	/**
	 * @param isAllDisable the isAllDisable to set
	 */
	public void setIsAllDisable(String isAllDisable) {
		this.isAllDisable = isAllDisable;
	}
	/**
	 * @return the disableNote
	 */
	public String getDisableNote() {
		return disableNote;
	}
	/**
	 * @param disableNote the disableNote to set
	 */
	public void setDisableNote(String disableNote) {
		this.disableNote = disableNote;
	}
	/**
	 * @return the repaymentAmount
	 */
	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}
	/**
	 * @param repaymentAmount the repaymentAmount to set
	 */
	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	/**
	 * @return the disableNum
	 */
	public int getDisableNum() {
		return disableNum;
	}
	/**
	 * @param disableNum the disableNum to set
	 */
	public void setDisableNum(int disableNum) {
		this.disableNum = disableNum;
	}
	/**
	 * @return the trueAmount
	 */
	public BigDecimal getTrueAmount() {
		return trueAmount;
	}
	/**
	 * @param trueAmount the trueAmount to set
	 */
	public void setTrueAmount(BigDecimal trueAmount) {
		this.trueAmount = trueAmount;
	}
	/**
	 * @return the oaPaymentNo
	 */
	public String getOaPaymentNo() {
		return oaPaymentNo;
	}
	/**
	 * @param oaPaymentNo the oaPaymentNo to set
	 */
	public void setOaPaymentNo(String oaPaymentNo) {
		this.oaPaymentNo = oaPaymentNo;
	}
}
