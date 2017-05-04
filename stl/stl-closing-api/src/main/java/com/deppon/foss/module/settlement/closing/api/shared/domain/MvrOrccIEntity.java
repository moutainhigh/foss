package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 始发外请车往来月报表 entity
 * @author 073615
 *
 */
public class MvrOrccIEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String orgCode;

    private String orgName;

    private String orgType;

    private String orgUnifiedCode;

    private Date voucherBeginTime;

    private Date voucherEndTime;

    private String customerType;

    private String invoiceMark;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgUnifiedCode() {
        return orgUnifiedCode;
    }

    public void setOrgUnifiedCode(String orgUnifiedCode) {
        this.orgUnifiedCode = orgUnifiedCode;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

	public BigDecimal getDestPayTail() {
		return destPayTail;
	}

	public void setDestPayTail(BigDecimal destPayTail) {
		this.destPayTail = destPayTail;
	}

    
}