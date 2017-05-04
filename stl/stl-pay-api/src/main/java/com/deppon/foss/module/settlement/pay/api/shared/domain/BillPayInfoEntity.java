package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 应付单和付款单信息实体
 * @author foss-0302346-Jiang Xun
 */
public class BillPayInfoEntity implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3594714458588997965L;
	
	/**
	 * 应付单id
	 */
	private String id;

	/**
	 * 应付单号
	 */
	private String payableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单ID
	 */
	private String waybillId;

	/**
	 * 付款单号（付款反写）
	 */
	private String paymentNo;

	/**
	 * 系统生成方式
	 */
	private String createType;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 应付部门名称
	 */
	private String payableOrgName;


	/**
	 * 客户编码/代理编码
	 */
	private String customerCode;

	/**
	 * 客户名称/代理名称
	 */
	private String customerName;

	/**
	 * 应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
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
	 * 币种
	 */
	private String currencyCode;


	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 签收日期
	 */
	private Date signDate;


	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 支付状态（付款反写该状态）
	 */
	private String payStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 审批状态
	 */
	private String approveStatus;

	/**
	 * 付款备注
	 */
	private String paymentNotes;// PAYMENT_NOTES

	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;// PAYMENT_AMOUNT
	
	/**
	 * 最后付款时间
	 */
	private Date lastPaymentTime;
	
	/**
	 * 支付类别
	 */
	private String paymentCategories;
	
	/**
	 * 是否核销
	 */
	private String isWriteoff;
	
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    
	/**
	 * 付款单id
	 */
	private String pId;
	
	/**
	 * 付款金额
	 */
	private BigDecimal pAmount;
	
	/**
	 * 付款单记账日期
	 */
	private Date pAccountDate;
	
	/**
	 * 付款部门编码
	 */
	private String paymentOrgCode;
	
	/**
	 * 付款部门名称
	 */
	private String paymentOrgName;
	
	/**
	 * 汇款状态
	 */
	private String remitStatus;
	
	/**
	 * 付款方式
	 */
	private String paymentType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getWaybillId() {
		return waybillId;
	}

	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getSourceBillType() {
		return sourceBillType;
	}

	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	public String getPayableOrgName() {
		return payableOrgName;
	}

	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsRedBack() {
		return isRedBack;
	}

	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public Short getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getPaymentNotes() {
		return paymentNotes;
	}

	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

	public String getPaymentCategories() {
		return paymentCategories;
	}

	public void setPaymentCategories(String paymentCategories) {
		this.paymentCategories = paymentCategories;
	}

	public String getIsWriteoff() {
		return isWriteoff;
	}

	public void setIsWriteoff(String isWriteoff) {
		this.isWriteoff = isWriteoff;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public BigDecimal getpAmount() {
		return pAmount;
	}

	public void setpAmount(BigDecimal pAmount) {
		this.pAmount = pAmount;
	}

	public Date getpAccountDate() {
		return pAccountDate;
	}

	public void setpAccountDate(Date pAccountDate) {
		this.pAccountDate = pAccountDate;
	}

	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	public String getPaymentOrgName() {
		return paymentOrgName;
	}

	public void setPaymentOrgName(String paymentOrgName) {
		this.paymentOrgName = paymentOrgName;
	}

	public String getRemitStatus() {
		return remitStatus;
	}

	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
