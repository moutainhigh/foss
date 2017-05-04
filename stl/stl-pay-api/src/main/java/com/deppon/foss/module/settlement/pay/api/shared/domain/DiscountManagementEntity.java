package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountManagementEntity {
    
	private String id;//id

    private String discountNo;//折扣单号

    private String customerNo;//客户编码

    private String customerName;//客户名称

    private String discountOrgCode;//折扣部门

    private String discountOrgName;//折扣名称名称

    private BigDecimal codDiscount;//代收货款折扣

    private BigDecimal insuranceDiscount;//保价费折扣

    private BigDecimal transportDiscount;//运费折扣

    private BigDecimal codDiscountRate;//代收货款折扣率

    private BigDecimal insuranceRate;//保价费折扣率

    private BigDecimal transportRate;//运费折扣率

    private String notes;//备注

    private String createUserCode;//创建人工号

    private String createUserName;//创建人名称

    private String createOrgCode;//创建部门编码

    private String createOrgName;//创建部门名称

    private Date createTime;//创建时间

    private Date modifyTime;//最后修改时间

    private String modifyUserCode;//最后修改人工号

    private String modifyUserName;//最后修改人名称
    
    private String status;//单据状态
    
    private BigDecimal totalMoney;//总金额

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(String discountNo) {
        this.discountNo = discountNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDiscountOrgCode() {
        return discountOrgCode;
    }

    public void setDiscountOrgCode(String discountOrgCode) {
        this.discountOrgCode = discountOrgCode;
    }

    public String getDiscountOrgName() {
        return discountOrgName;
    }

    public void setDiscountOrgName(String discountOrgName) {
        this.discountOrgName = discountOrgName;
    }

    public BigDecimal getCodDiscount() {
        return codDiscount;
    }

    public void setCodDiscount(BigDecimal codDiscount) {
        this.codDiscount = codDiscount;
    }

    public BigDecimal getInsuranceDiscount() {
        return insuranceDiscount;
    }

    public void setInsuranceDiscount(BigDecimal insuranceDiscount) {
        this.insuranceDiscount = insuranceDiscount;
    }

    public BigDecimal getTransportDiscount() {
        return transportDiscount;
    }

    public void setTransportDiscount(BigDecimal transportDiscount) {
        this.transportDiscount = transportDiscount;
    }

    public BigDecimal getCodDiscountRate() {
        return codDiscountRate;
    }

    public void setCodDiscountRate(BigDecimal codDiscountRate) {
        this.codDiscountRate = codDiscountRate;
    }

    public BigDecimal getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(BigDecimal insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public BigDecimal getTransportRate() {
        return transportRate;
    }

    public void setTransportRate(BigDecimal transportRate) {
        this.transportRate = transportRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}