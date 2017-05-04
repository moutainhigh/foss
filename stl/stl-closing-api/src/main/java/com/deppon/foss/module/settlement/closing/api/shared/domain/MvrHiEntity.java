package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 302307
 * @date 2015年12月15日11:08:15.
 */
public class MvrHiEntity implements Serializable {
	/** 序列号Id **/
	private static final long serialVersionUID = 1L;
    /** id **/
    private String id;
    /** 期间 **/
    private String period;
    /** 业务类型 **/
    private String businessType;
    /** 供应商编码 **/
    private String customerCode;
    /** 供应商名称 **/
    private  String customerName;
    /** 查询开始时间 **/
    private Date voucherBeginTime;
    /** 查询结束时间 **/
    private Date voucherEndTime;
    /** 原始部门编码 **/
    private String origOrgCode;
    /** 原始部门名称 **/
    private String origOrgName;
    /** 家装入成本 **/
    private BigDecimal hiCost;
    /** 家装应收录入 **/
    private BigDecimal hiRentry;
    /** 应收冲应付 **/
    private BigDecimal hiPwr;
    /** 家装付款申请 **/
    private BigDecimal hiPr;

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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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

    public BigDecimal getHiCost() {
        return hiCost;
    }

    public void setHiCost(BigDecimal hiCost) {
        this.hiCost = hiCost;
    }

    public BigDecimal getHiRentry() {
        return hiRentry;
    }

    public void setHiRentry(BigDecimal hiRentry) {
        this.hiRentry = hiRentry;
    }

    public BigDecimal getHiPwr() {
        return hiPwr;
    }

    public void setHiPwr(BigDecimal hiPwr) {
        this.hiPwr = hiPwr;
    }

    public BigDecimal getHiPr() {
        return hiPr;
    }

    public void setHiPr(BigDecimal hiPr) {
        this.hiPr = hiPr;
    }
}
