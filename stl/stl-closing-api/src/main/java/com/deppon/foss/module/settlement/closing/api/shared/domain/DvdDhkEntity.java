package com.deppon.foss.module.settlement.closing.api.shared.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代汇款明细报表
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public class DvdDhkEntity implements Serializable{

	private static final long serialVersionUID = 2552250356045369579L;

	private String id;

    private Date remitDate;

    private String period;

    private String remitOrgCode;

    private String remitOrgName;

    private String byremitOrgCode;

    private String byremitOrgName;

    private String billNo;

    private String sourceBillNo;

    private String billType;

    private BigDecimal amount;

    private String paymentType;

    private Date voucherCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(Date remitDate) {
        this.remitDate = remitDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRemitOrgCode() {
        return remitOrgCode;
    }

    public void setRemitOrgCode(String remitOrgCode) {
        this.remitOrgCode = remitOrgCode;
    }

    public String getRemitOrgName() {
        return remitOrgName;
    }

    public void setRemitOrgName(String remitOrgName) {
        this.remitOrgName = remitOrgName;
    }

    public String getByremitOrgCode() {
        return byremitOrgCode;
    }

    public void setByremitOrgCode(String byremitOrgCode) {
        this.byremitOrgCode = byremitOrgCode;
    }

    public String getByremitOrgName() {
        return byremitOrgName;
    }

    public void setByremitOrgName(String byremitOrgName) {
        this.byremitOrgName = byremitOrgName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getSourceBillNo() {
        return sourceBillNo;
    }

    public void setSourceBillNo(String sourceBillNo) {
        this.sourceBillNo = sourceBillNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getVoucherCreateTime() {
        return voucherCreateTime;
    }

    public void setVoucherCreateTime(Date voucherCreateTime) {
        this.voucherCreateTime = voucherCreateTime;
    }
}