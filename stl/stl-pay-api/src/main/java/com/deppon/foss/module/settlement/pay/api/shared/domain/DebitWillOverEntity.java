package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.util.Date;

public class DebitWillOverEntity {
    private String id;

    private String bigRegionCode;

    private String bigRegionName;

    private String smallRegionCode;

    private String smallRegionName;

    private String deptCode;

    private String deptName;

    private String customerCode;

    private String customerName;

    private String customerType;

    private String paymentType;

    private Date minDebitTime;

    private Integer debitDays;

    private Integer remainDays;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigRegionCode() {
        return bigRegionCode;
    }

    public void setBigRegionCode(String bigRegionCode) {
        this.bigRegionCode = bigRegionCode;
    }

    public String getBigRegionName() {
        return bigRegionName;
    }

    public void setBigRegionName(String bigRegionName) {
        this.bigRegionName = bigRegionName;
    }

    public String getSmallRegionCode() {
        return smallRegionCode;
    }

    public void setSmallRegionCode(String smallRegionCode) {
        this.smallRegionCode = smallRegionCode;
    }

    public String getSmallRegionName() {
        return smallRegionName;
    }

    public void setSmallRegionName(String smallRegionName) {
        this.smallRegionName = smallRegionName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getMinDebitTime() {
        return minDebitTime;
    }

    public void setMinDebitTime(Date minDebitTime) {
        this.minDebitTime = minDebitTime;
    }

    public Integer getDebitDays() {
        return debitDays;
    }

    public void setDebitDays(Integer debitDays) {
        this.debitDays = debitDays;
    }

    public Integer getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}