package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 小票实体
 * @author dp-zengbinwen
 * @date 2012-10-10 下午1:45:16
 */
public class OtherRevenueEntity extends BaseEntity {

	/**
     * 序列号
     */
	private static final long serialVersionUID = 8787993015789974771L;

    /**
     * 小票单号
     */
    private String otherRevenueNo;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 收入部门编码
     */
    private String generatingOrgCode;

    /**
     * 收入部门名称
     */
    private String generatingOrgName;

    /**
     * 收入公司编码
     */
    private String generatingComCode;

    /**
     * 收入公司名称
     */
    private String generatingComName;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户电话
     */
    private String customerPhone;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     * 收款方式
     */
    private String paymentType;

    /**
     * 收入类别
     */
    private String incomeCategories;

    /**
     * 金额
     *  BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
     */
    private BigDecimal amount;

    /**
     * 业务日期
     */
    private Date businessDate;

    /**
     * 版本号
     */
    private Short versionNo;

    /**
     * 是否有效
     */
    private String active;

    /**
     * 是否红单
     */
    private String isRedBack;

    /**
     * 制单人编码
     */
    private String createUserCode;

    /**
     * 制单人名称
     */
    private String createUserName;

    /**
     * 制单部门编码
     */
    private String createOrgCode;

    /**
     * 制单部门名称
     */
    private String createOrgName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人编码
     */
    private String modifyUserCode;

    /**
     * 修改人名称
     */
    private String modifyUserName;

    /**
     * 是否作废
     */
    private String isDisable;

    /**
     * 作废人编码
     */
    private String disableUserCode;

    /**
     * 作废人名称
     */
    private String disableUserName;

    /**
     * 作废时间
     */
    private Date disableTime;

    /**
     * 备注
     */
    private String notes;
    
    /**
     * 币种类型
     */
    private String currencyCode;
    
    /**
     * 产品类型
     */
    private String productCode;

	/**
	 * 发票标记
	 */
	private String invoiceMark;
	/**
	 * 发票类别
	 */
	private String invoiceCategory;
	
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
	 * 银联交易流水号
	 *
	 * （此字段数据库不存在，后台关联的对应现金收款单）
	 */
	private String posBatchNo;

	public String getPosBatchNo() {
		return posBatchNo;
	}

	public void setPosBatchNo(String posBatchNo) {
		this.posBatchNo = posBatchNo;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	/**
	 * @return otherRevenueNo
	 */
	public String getOtherRevenueNo() {
		return otherRevenueNo;
	}

	/**
	 * @param  otherRevenueNo  
	 */
	public void setOtherRevenueNo(String otherRevenueNo) {
		this.otherRevenueNo = otherRevenueNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param  generatingOrgCode  
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
	 * @param  generatingOrgName  
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return generatingComCode
	 */
	public String getGeneratingComCode() {
		return generatingComCode;
	}

	/**
	 * @param  generatingComCode  
	 */
	public void setGeneratingComCode(String generatingComCode) {
		this.generatingComCode = generatingComCode;
	}

	/**
	 * @return generatingComName
	 */
	public String getGeneratingComName() {
		return generatingComName;
	}

	/**
	 * @param  generatingComName  
	 */
	public void setGeneratingComName(String generatingComName) {
		this.generatingComName = generatingComName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param  customerCode  
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
	 * @param  customerName  
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param  customerPhone  
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param  customerType  
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param  paymentType  
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return incomeCategories
	 */
	public String getIncomeCategories() {
		return incomeCategories;
	}

	/**
	 * @param  incomeCategories  
	 */
	public void setIncomeCategories(String incomeCategories) {
		this.incomeCategories = incomeCategories;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param  amount  
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
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
	 * @param  isRedBack  
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param  createUserCode  
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
	 * @param  createUserName  
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param  createOrgCode  
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
	 * @param  createOrgName  
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param  createTime  
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
	 * @param  modifyTime  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param  modifyUserCode  
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
	 * @param  modifyUserName  
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return isDisable
	 */
	public String getIsDisable() {
		return isDisable;
	}

	/**
	 * @param  isDisable  
	 */
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	/**
	 * @return disableUserCode
	 */
	public String getDisableUserCode() {
		return disableUserCode;
	}

	/**
	 * @param  disableUserCode  
	 */
	public void setDisableUserCode(String disableUserCode) {
		this.disableUserCode = disableUserCode;
	}

	/**
	 * @return disableUserName
	 */
	public String getDisableUserName() {
		return disableUserName;
	}

	/**
	 * @param  disableUserName  
	 */
	public void setDisableUserName(String disableUserName) {
		this.disableUserName = disableUserName;
	}

	/**
	 * @return disableTime
	 */
	public Date getDisableTime() {
		return disableTime;
	}

	/**
	 * @param  disableTime  
	 */
	public void setDisableTime(Date disableTime) {
		this.disableTime = disableTime;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param  notes  
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param  currencyCode  
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @GET
	 * @return productCode
	 */
	public String getProductCode() {
		/*
		 *@get
		 *@ return productCode
		 */
		return productCode;
	}

	/**
	 * @SET
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		/*
		 *@set
		 *@this.productCode = productCode
		 */
		this.productCode = productCode;
	}
	/**
	 * @GET
	 * @return invoiceCategory
	 */
	public String getInvoiceCategory() {
		return invoiceCategory;
	}
	/**
	 * @SET
	 * @param invoiceCategory
	 */
	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
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

}
