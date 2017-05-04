package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.util.Date;

/**
 * 
 * 退代收货款实体
 * @author foss-pengzhen
 * @date 2013-4-1 下午1:37:30
 * @since
 * @version
 */
public class DvrReturnCodEntity {
	
	/**
	 * id
	 */
    private String id;

    /**
     * 付款日期
     */
    private Date paymentDate;

    /**
     * 付款所属期间
     */
    private String period;

    /**
     * 应付部门编码
     */
    private String payableOrgCode;

    /**
     * 应付部门名称
     */
    private String payableOrgName;

    /**
     * 标杆编码
     */
    private String unifiedCode;

    /**
     * 应付子公司编码
     */
    private String payableComCode;

    /**
     * 应付子公司名称
     */
    private String payableComName;

    /**
     * 退款部门（固定值）
     */
    private String returnOrg;

    /**
     * 退款子公司（固定值）
     */
    private String returnComOrg;

    /**
     * 业务类型
     */
    private String productCode;

    /**
     * 银行账户
     */
    private String bankAccount;

    /**
     * 付款途径
     */
    private String refundPath;

    /**
     * 退款金额
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
	 * @return  the unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}

	
	/**
	 * @param unifiedCode the unifiedCode to set
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
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
	 * @return  the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * @return  the bankAccount
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

    
}