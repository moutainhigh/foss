package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.util.Date;

/**
 * 
 * 退代收货款明细实体
 * @author foss-pengzhen
 * @date 2013-4-10 下午3:29:22
 * @since
 * @version
 */
public class DvdReturnCodEntity {
	/**
	 * id
	 */
    private String id;

    /**
	 * 期间
	 */
    private String period;

    /**
	 * 运单号
	 */
    private String waybillNo;
    
    /**
     * 运输性质
     */
    private String productCode;

    /**
	 * 应付部门编码
	 */
    private String payableOrgCode;

    /**
	 * 应付部门名称
	 */
    private String payableOrgName;

    /**
	 * 应付子公司编码
	 */
    private String payableComCode;

    /**
	 * 应付子公司名称
	 */
    private String payableComName;

    /**
	 * 代收货款类型
	 */
    private String codType;

    /**
	 * 发货客户编码
	 */
    private String customerCode;

    /**
	 * 客户类型
	 */
    private String customerType;

    /**
	 * 发货客户名称/代理名称
	 */
    private String customerName;

    /**
	 * 收款人
	 */
    private String payeeName;

    /**
	 * 收款人银行帐号
	 */
    private String payeeAccount;

    /**
	 * 开户行编码
	 */
    private String bankHqCode;

    /**
	 * 开户行名称
	 */
    private String bankHqName;

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
	 * 付款日期
	 */
    private Date paymentDate;

    /**
	 * 退款部门（固定值）
	 */
    private String returnOrg;

    /**
	 * 退款子公司（固定值）
	 */
    private String returnComOrg;

    /**
	 * 退款路径
	 */
    private String refundPath;
    
    /**
     * 银行账号
     */
    private String comAccount;

    /**
	 * 应退金额
	 */
    private Long codAmount;

	
	/**
	 * @return  the id
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
	 * @return  the period
	 */
	public String getPeriod() {
		return period;
	}

	
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	
	/**
	 * @param payableOrgCode the payableOrgCode to set
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	
	/**
	 * @return  the payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}

	
	/**
	 * @param payableOrgName the payableOrgName to set
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

	
	/**
	 * @return  the payableComCode
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	
	/**
	 * @param payableComCode the payableComCode to set
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

	
	/**
	 * @return  the payableComName
	 */
	public String getPayableComName() {
		return payableComName;
	}

	
	/**
	 * @param payableComName the payableComName to set
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}

	
	/**
	 * @return  the codType
	 */
	public String getCodType() {
		return codType;
	}

	
	/**
	 * @param codType the codType to set
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	
	/**
	 * @return  the customerCode
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
	 * @return  the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	/**
	 * @return  the customerName
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
	 * @return  the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	
	/**
	 * @param payeeName the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	
	/**
	 * @return  the payeeAccount
	 */
	public String getPayeeAccount() {
		return payeeAccount;
	}

	
	/**
	 * @param payeeAccount the payeeAccount to set
	 */
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	
	/**
	 * @return  the bankHqCode
	 */
	public String getBankHqCode() {
		return bankHqCode;
	}

	
	/**
	 * @param bankHqCode the bankHqCode to set
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}

	
	/**
	 * @return  the bankHqName
	 */
	public String getBankHqName() {
		return bankHqName;
	}

	
	/**
	 * @param bankHqName the bankHqName to set
	 */
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}

	
	/**
	 * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	
	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	
	/**
	 * @return  the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	
	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	
	/**
	 * @return  the signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	
	/**
	 * @param signDate the signDate to set
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	
	/**
	 * @return  the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	
	/**
	 * @return  the returnOrg
	 */
	public String getReturnOrg() {
		return returnOrg;
	}

	
	/**
	 * @param returnOrg the returnOrg to set
	 */
	public void setReturnOrg(String returnOrg) {
		this.returnOrg = returnOrg;
	}

	
	/**
	 * @return  the returnComOrg
	 */
	public String getReturnComOrg() {
		return returnComOrg;
	}

	
	/**
	 * @param returnComOrg the returnComOrg to set
	 */
	public void setReturnComOrg(String returnComOrg) {
		this.returnComOrg = returnComOrg;
	}

	
	/**
	 * @return  the refundPath
	 */
	public String getRefundPath() {
		return refundPath;
	}

	
	/**
	 * @param refundPath the refundPath to set
	 */
	public void setRefundPath(String refundPath) {
		this.refundPath = refundPath;
	}

	
	/**
	 * @return  the codAmount
	 */
	public Long getCodAmount() {
		return codAmount;
	}

	
	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Long codAmount) {
		this.codAmount = codAmount;
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
	 * @return the comAccount
	 */
	public String getComAccount() {
		return comAccount;
	}


	/**
	 * @param comAccount the comAccount to set
	 */
	public void setComAccount(String comAccount) {
		this.comAccount = comAccount;
	}

}