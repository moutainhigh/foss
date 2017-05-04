package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CUBCOtherRevenueRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//收入类别
	private String incomeCategories;
	
	//运单号
    private String waybillNo;
    
    //业务类型   零担和快递
    private String businessType;
    
	//收款方式  现金和银行卡
	private String paymentType;
	
    //金额
    private BigDecimal amount;
	
	//POS机流水号
	private String batchNo;
	
	//客户类型  客户/偏线代理/空运代理
	private String customerType;
	
	//客户编码
	private String customerCode;
	
	//客户名称
	private String customerName;
	
	//客户电话
	private String customerPhone;
	
    //发票标记
    private String invoiceMark;
    
    //是否统一结算
    private String isUnifySettlement;
    
    //合同部门编码
    private String contractOrgCode;
    
    //合同部门名称
    private String contractOrgName;
    
    //当前创建时间
    private Date createTime;
    
    //备注
    private String notes;
    
    //当前部门编码
    private String createOrgCode;
    
    //当前部门名称
    private String createOrgName;

	//当前用户编码
    private String createUserCode;
    
    //当前用户名称
    private String createUserName;

	public String getIncomeCategories() {
		return incomeCategories;
	}

	public void setIncomeCategories(String incomeCategories) {
		this.incomeCategories = incomeCategories;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getIsUnifySettlement() {
		return isUnifySettlement;
	}

	public void setIsUnifySettlement(String isUnifySettlement) {
		this.isUnifySettlement = isUnifySettlement;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
