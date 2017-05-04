package com.deppon.foss.module.settlement.closing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人奖罚特殊月报.
 *
 * @author foss-youkun
 * @date 2016-3-18 下午5:57:28
 */
public class MvrPtpRpsEntity extends BaseEntity {

    private String id;

    private String period;

    private String productCode;

    private String customerCode;

    private String customerName;

    private String origOrgCode;

    private String origOrgName;

    private String destOrgCode;

    private String destOrgName;

    private BigDecimal ptpErrSspc;

    private BigDecimal ptpErrSsei;

    private BigDecimal ptpErrShwfr;

    private Date voucherBeginTime;

    private Date voucherEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode == null ? null : origOrgCode.trim();
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName == null ? null : origOrgName.trim();
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode == null ? null : destOrgCode.trim();
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName == null ? null : destOrgName.trim();
    }

    public BigDecimal getPtpErrSspc() {
        return ptpErrSspc;
    }

    public void setPtpErrSspc(BigDecimal ptpErrSspc) {
        this.ptpErrSspc = ptpErrSspc;
    }

    public BigDecimal getPtpErrSsei() {
        return ptpErrSsei;
    }

    public void setPtpErrSsei(BigDecimal ptpErrSsei) {
        this.ptpErrSsei = ptpErrSsei;
    }

    public BigDecimal getPtpErrShwfr() {
        return ptpErrShwfr;
    }

    public void setPtpErrShwfr(BigDecimal ptpErrShwfr) {
        this.ptpErrShwfr = ptpErrShwfr;
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
}