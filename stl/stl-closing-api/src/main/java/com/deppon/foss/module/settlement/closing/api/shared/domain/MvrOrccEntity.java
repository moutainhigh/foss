package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MvrOrccEntity {
    private String id;

    private String period;

    private String productCode;
    
    private Date voucherBeginTime;

    private Date voucherEndTime;

    private String customerCode;

    private String customerName;

    private String origOrgCode;

    private String origOrgName;

    private String destOrgCode;

    private String destOrgName;

    private BigDecimal origPay;

    private BigDecimal destCfmOcost;

    private BigDecimal destCfmDcost;

    private BigDecimal udestCfmOcost;

    private BigDecimal udestCrmDcost;

    private BigDecimal origPayTail;

    private BigDecimal destPayTail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getVoucherBeginTime() {
        return voucherBeginTime;
    }

    public void setVoucherBeginTime(Date voucherBeginTime) {
        this.voucherBeginTime = voucherBeginTime;
    }

    public Date getVoucherEndTime() {
        return voucherEndTime;
    }

    public void setVoucherEndTime(Date voucherEndTime) {
        this.voucherEndTime = voucherEndTime;
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

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName;
    }

    public BigDecimal getOrigPay() {
        return origPay;
    }

    public void setOrigPay(BigDecimal origPay) {
        this.origPay = origPay;
    }

    public BigDecimal getDestCfmOcost() {
        return destCfmOcost;
    }

    public void setDestCfmOcost(BigDecimal destCfmOcost) {
        this.destCfmOcost = destCfmOcost;
    }

    public BigDecimal getDestCfmDcost() {
        return destCfmDcost;
    }

    public void setDestCfmDcost(BigDecimal destCfmDcost) {
        this.destCfmDcost = destCfmDcost;
    }

    public BigDecimal getUdestCfmOcost() {
        return udestCfmOcost;
    }

    public void setUdestCfmOcost(BigDecimal udestCfmOcost) {
        this.udestCfmOcost = udestCfmOcost;
    }

    public BigDecimal getUdestCrmDcost() {
        return udestCrmDcost;
    }

    public void setUdestCrmDcost(BigDecimal udestCrmDcost) {
        this.udestCrmDcost = udestCrmDcost;
    }

    public BigDecimal getOrigPayTail() {
        return origPayTail;
    }

    public void setOrigPayTail(BigDecimal origPayTail) {
        this.origPayTail = origPayTail;
    }

    public BigDecimal getDestPayTail() {
        return destPayTail;
    }

    public void setDestPayTail(BigDecimal destPayTail) {
        this.destPayTail = destPayTail;
    }

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
    
    
}