package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountManagementDEntity {
    private String id;

    private String discuontNo;

    private String customerCode;

    private String customerName;

    private String dunningOrgCode;

    private String dunningOrgName;

    private String receivableId;

    private String receivableNo;

    private Date receivableBusinessDate;

    private String waybillNo;

    private String productCode;

    private BigDecimal preCodFee;

    private BigDecimal preInsuranceFee;

    private BigDecimal preTransportFee;

    private BigDecimal codDiscount;

    private BigDecimal insuranceDiscount;

    private BigDecimal transportDiscount;

    private String receiveBillType;

    private String active;

    private String notes;

    private String createUserCode;

    private String createUserName;

    private String createOrgCode;

    private String createOrgName;

    private Date createTime;

    private Date modifyTime;

    private String modifyUserCode;

    private String modifyUserName;
    
    private BigDecimal totalMoney;//总金额
    
    private String payAbleNo;//应付单编号
    
    private String contractOrgName;//合同部门
    
    private String contractOrgCode;//合同部门编码
    
    private String invoiceMark;//发票标记
   
    private String isUnifiedAccount;//是否统一结算
    
    private String discountType;//折扣单类型
    
    
    
    public String getPayAbleNo() {
		return payAbleNo;
	}

	public void setPayAbleNo(String payAbleNo) {
		this.payAbleNo = payAbleNo;
	}
    
    

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

    public String getDiscuontNo() {
        return discuontNo;
    }

    public void setDiscuontNo(String discuontNo) {
        this.discuontNo = discuontNo;
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

    public String getDunningOrgCode() {
        return dunningOrgCode;
    }

    public void setDunningOrgCode(String dunningOrgCode) {
        this.dunningOrgCode = dunningOrgCode;
    }

    public String getDunningOrgName() {
        return dunningOrgName;
    }

    public void setDunningOrgName(String dunningOrgName) {
        this.dunningOrgName = dunningOrgName;
    }

    public String getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(String receivableId) {
        this.receivableId = receivableId;
    }

    public String getReceivableNo() {
        return receivableNo;
    }

    public void setReceivableNo(String receivableNo) {
        this.receivableNo = receivableNo;
    }

    public Date getReceivableBusinessDate() {
        return receivableBusinessDate;
    }

    public void setReceivableBusinessDate(Date receivableBusinessDate) {
        this.receivableBusinessDate = receivableBusinessDate;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getPreCodFee() {
        return preCodFee;
    }

    public void setPreCodFee(BigDecimal preCodFee) {
        this.preCodFee = preCodFee;
    }

    public BigDecimal getPreInsuranceFee() {
        return preInsuranceFee;
    }

    public void setPreInsuranceFee(BigDecimal preInsuranceFee) {
        this.preInsuranceFee = preInsuranceFee;
    }

    public BigDecimal getPreTransportFee() {
        return preTransportFee;
    }

    public void setPreTransportFee(BigDecimal preTransportFee) {
        this.preTransportFee = preTransportFee;
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

    public String getReceiveBillType() {
        return receiveBillType;
    }

    public void setReceiveBillType(String receiveBillType) {
        this.receiveBillType = receiveBillType;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getIsUnifiedAccount() {
		return isUnifiedAccount;
	}

	public void setIsUnifiedAccount(String isUnifiedAccount) {
		this.isUnifiedAccount = isUnifiedAccount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
    
}