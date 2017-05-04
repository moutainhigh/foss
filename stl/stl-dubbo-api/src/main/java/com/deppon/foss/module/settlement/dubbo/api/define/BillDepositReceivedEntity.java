package com.deppon.foss.module.settlement.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 预收单
 * 
 * @author dp-liqin
 * @date 2012-10-11 下午5:22:47
 * @since
 * @version
 */
public class BillDepositReceivedEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8109014982933154249L;

	/**
	 * 预收单号
	 */
	private String depositReceivedNo;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
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
	 * 录入部门编码
	 */
	private String createOrgCode;

	/**
	 * 录入部门名称
	 */
	private String createOrgName;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;

	/**
	 * 收款部门所属子公司编码
	 */
	private String collectionCompanyCode;

	/**
	 * 收款部门所属子公司名称
	 */
	private String collectionCompanyName;

	/**
	 * 预收部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 预收部门名称
	 */
	private String generatingOrgName;

	/**
	 * 预收部门所属子公司编码
	 */
	private String generatingCompanyCode;

	/**
	 * 预收部门所属子公司名称
	 */
	private String generatingCompanyName;

	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 退款状态
	 */
	private String refundStatus;

	/**
	 * 汇款编号
	 */
	private String remitNo;

	/**
	 * 汇款人名称
	 */
	private String remiterName;

	/**
	 * 汇款所属部门编码
	 */
	private String remitOrgCode;

	/**
	 * 汇款所属部门名称
	 */
	private String remitOrgName;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 制单人工号
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 审核人工号
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 修改人工号
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;


	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 付款金额
	 */
	private BigDecimal paymentAmount;

	/**
	 * 付款备注
	 */
	private String paymentNotes;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 付款单号
	 */
	private String paymentNo;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;
    /**
     * 是否统一结算
     */
    private String unifiedSettlement;
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    /**
     * 客户类型
     */
    private String customerType;
    

    public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getUnifiedSettlement() {
        return unifiedSettlement;
    }

    public void setUnifiedSettlement(String unifiedSettlement) {
        this.unifiedSettlement = unifiedSettlement;
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

    /**
	 * @return depositReceivedNo
	 */
	public String getDepositReceivedNo() {
		return depositReceivedNo;
	}

	/**
	 * @param depositReceivedNo
	 */
	public void setDepositReceivedNo(String depositReceivedNo) {
		this.depositReceivedNo = depositReceivedNo;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	/**
	 * @return collectionCompanyCode
	 */
	public String getCollectionCompanyCode() {
		return collectionCompanyCode;
	}

	/**
	 * @param collectionCompanyCode
	 */
	public void setCollectionCompanyCode(String collectionCompanyCode) {
		this.collectionCompanyCode = collectionCompanyCode;
	}

	/**
	 * @return collectionCompanyName
	 */
	public String getCollectionCompanyName() {
		return collectionCompanyName;
	}

	/**
	 * @param collectionCompanyName
	 */
	public void setCollectionCompanyName(String collectionCompanyName) {
		this.collectionCompanyName = collectionCompanyName;
	}

	/**
	 * @return generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return generatingCompanyCode
	 */
	public String getGeneratingCompanyCode() {
		return generatingCompanyCode;
	}

	/**
	 * @param generatingCompanyCode
	 */
	public void setGeneratingCompanyCode(String generatingCompanyCode) {
		this.generatingCompanyCode = generatingCompanyCode;
	}

	/**
	 * @return generatingCompanyName
	 */
	public String getGeneratingCompanyName() {
		return generatingCompanyName;
	}

	/**
	 * @param generatingCompanyName
	 */
	public void setGeneratingCompanyName(String generatingCompanyName) {
		this.generatingCompanyName = generatingCompanyName;
	}

	/**
	 * @return workflowNo
	 */
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * @param workflowNo
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	/**
	 * @return refundStatus
	 */
	public String getRefundStatus() {
		return refundStatus;
	}

	/**
	 * @param refundStatus
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * @return remitNo
	 */
	public String getRemitNo() {
		return remitNo;
	}

	/**
	 * @param remitNo
	 */
	public void setRemitNo(String remitNo) {
		this.remitNo = remitNo;
	}

	/**
	 * @return remiterName
	 */
	public String getRemiterName() {
		return remiterName;
	}

	/**
	 * @param remiterName
	 */
	public void setRemiterName(String remiterName) {
		this.remiterName = remiterName;
	}

	/**
	 * @return remitOrgCode
	 */
	public String getRemitOrgCode() {
		return remitOrgCode;
	}

	/**
	 * @param remitOrgCode
	 */
	public void setRemitOrgCode(String remitOrgCode) {
		this.remitOrgCode = remitOrgCode;
	}

	/**
	 * @return remitOrgName
	 */
	public String getRemitOrgName() {
		return remitOrgName;
	}

	/**
	 * @param remitOrgName
	 */
	public void setRemitOrgName(String remitOrgName) {
		this.remitOrgName = remitOrgName;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
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
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
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
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
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

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return paymentAmount
	 */
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 */
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return paymentNotes
	 */
	public String getPaymentNotes() {
		return paymentNotes;
	}

	/**
	 * @param paymentNotes
	 */
	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	/**
	 * @return transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * @return the invoiceMark
	 */
	public String getInvoiceMark() {
		return invoiceMark;
	}

	/**
	 * @param invoiceMark the invoiceMark to set
	 */
	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	@Override
	public String toString() {
		return "BillDepositReceivedEntity [depositReceivedNo=" + depositReceivedNo + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", currencyCode=" + currencyCode + ", amount=" + amount
				+ ", verifyAmount=" + verifyAmount + ", unverifyAmount=" + unverifyAmount + ", createOrgCode="
				+ createOrgCode + ", createOrgName=" + createOrgName + ", collectionOrgCode=" + collectionOrgCode
				+ ", collectionOrgName=" + collectionOrgName + ", collectionCompanyCode=" + collectionCompanyCode
				+ ", collectionCompanyName=" + collectionCompanyName + ", generatingOrgCode=" + generatingOrgCode
				+ ", generatingOrgName=" + generatingOrgName + ", generatingCompanyCode=" + generatingCompanyCode
				+ ", generatingCompanyName=" + generatingCompanyName + ", workflowNo=" + workflowNo + ", refundStatus="
				+ refundStatus + ", remitNo=" + remitNo + ", remiterName=" + remiterName + ", remitOrgCode="
				+ remitOrgCode + ", remitOrgName=" + remitOrgName + ", billType=" + billType + ", status=" + status
				+ ", active=" + active + ", isRedBack=" + isRedBack + ", paymentType=" + paymentType
				+ ", createUserCode=" + createUserCode + ", createUserName=" + createUserName + ", auditUserCode="
				+ auditUserCode + ", auditUserName=" + auditUserName + ", modifyUserCode=" + modifyUserCode
				+ ", modifyUserName=" + modifyUserName + ", cashConfirmUserCode=" + cashConfirmUserCode
				+ ", cashConfirmUserName=" + cashConfirmUserName + ", businessDate=" + businessDate + ", accountDate="
				+ accountDate + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", cashConfirmTime="
				+ cashConfirmTime + ", isInit=" + isInit + ", statementBillNo=" + statementBillNo + ", notes=" + notes
				+ ", versionNo=" + versionNo + ", paymentAmount=" + paymentAmount + ", paymentNotes=" + paymentNotes
				+ ", transportType=" + transportType + ", paymentNo=" + paymentNo + ", invoiceMark=" + invoiceMark
				+ ", unifiedSettlement=" + unifiedSettlement + ", contractOrgCode=" + contractOrgCode
				+ ", contractOrgName=" + contractOrgName + ", customerType=" + customerType + ", getCreateUser()="
				+ getCreateUser() + ", getModifyUser()=" + getModifyUser() + ", getId()=" + getId()
				+ ", getCreateDate()=" + getCreateDate() + ", getModifyDate()=" + getModifyDate() + "]";
	}
	
}
