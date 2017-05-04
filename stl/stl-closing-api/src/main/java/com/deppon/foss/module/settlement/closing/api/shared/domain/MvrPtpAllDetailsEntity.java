package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人日明细
 * @author 311396
 * @date 2016-3-22 下午2:06:17
 */
public class MvrPtpAllDetailsEntity{

	/**
	 * 业务场景
	 */
    private String businessCase;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户类型
     */
    private String customerType;

    /**
     * 始发部门编码
     */
    private String origOrgCode;

    /**
     * 始发部门名称
     */
    private String origOrgName;

    /**
     * 到达部门编码
     */
    private String destOrgCode;

    /**
     * 到达部门名称
     */
    private String destOrgName;

    /**
     * 应收部门编码
     */
    private String recOrgCode;

    /**
     * 应收部门名称
     */
    private String recOrgName;

    /**
     * 应付部门编码
     */
    private String payOrgCode;

    /**
     * 应付部门名称
     */
    private String payOrgName;

    /**
     * 预收部门编码
     */
    private String depOrgCode;

    /**
     * 预收部门名称
     */
    private String depOrgName;

    /**
     * 收入部门编码
     */
    private String generatingOrgCode;

    /**
     * 收入部门名称
     */
    private String generatingOrgName;

    /**
     * 费用承担部门编码
     */
    private String expenseBearCode;

    /**
     * 费用承担部门名称
     */
    private String expenseBearName;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 单号
     */
    private String billNo;

    /**
     * 会计日期
     */
    private Date accountDate;

    /**
     * 业务日期
     */
    private Date businessDate;

    /**
     * 主单据类型
     */
    private String billParentType;

    /**
     * 单据子类型
     */
    private String billType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 运费
     */
    private BigDecimal amountFrt;

    /**
     * 接货费
     */
    private BigDecimal amountPup;

    /**
     * 送货费
     */
    private BigDecimal amountDel;

    /**
     * 包装费
     */
    private BigDecimal amountPkg;

    /**
     * 保价费
     */
    private BigDecimal amountDv;

    /**
     * 代收货款手续费
     */
    private BigDecimal amountCod;

    /**
     * 开单操作费
     */
    private BigDecimal amountBof;

    /**
     * 送货费（不含上楼）
     */
    private BigDecimal amountDfni;

    /**
     * 送货上楼费
     */
    private BigDecimal amountDf;

    /**
     * 送货进仓费
     */
    private BigDecimal amountDc;

    /**
     *大件上楼费 
     */
    private BigDecimal amountLuf;

    /**
     * 超远派送费
     */
    private BigDecimal amountUldf;

    /**
     * 签收单返回
     */
    private BigDecimal amountSr;

    /**
     * 其他应收
     */
    private BigDecimal amountOt;

    /**
     * 产品类型
     */
    private String productCode;

    /**
     * id
     */
    private String id;
    
    /**
     * 期间
     */
    private String period;

    public String getBusinessCase() {
        return businessCase;
    }

    public void setBusinessCase(String businessCase) {
        this.businessCase = businessCase == null ? null : businessCase.trim();
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType == null ? null : customerType.trim();
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

    public String getRecOrgCode() {
        return recOrgCode;
    }

    public void setRecOrgCode(String recOrgCode) {
        this.recOrgCode = recOrgCode == null ? null : recOrgCode.trim();
    }

    public String getRecOrgName() {
        return recOrgName;
    }

    public void setRecOrgName(String recOrgName) {
        this.recOrgName = recOrgName == null ? null : recOrgName.trim();
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode == null ? null : payOrgCode.trim();
    }

    public String getPayOrgName() {
        return payOrgName;
    }

    public void setPayOrgName(String payOrgName) {
        this.payOrgName = payOrgName == null ? null : payOrgName.trim();
    }

    public String getDepOrgCode() {
        return depOrgCode;
    }

    public void setDepOrgCode(String depOrgCode) {
        this.depOrgCode = depOrgCode == null ? null : depOrgCode.trim();
    }

    public String getDepOrgName() {
        return depOrgName;
    }

    public void setDepOrgName(String depOrgName) {
        this.depOrgName = depOrgName == null ? null : depOrgName.trim();
    }

    public String getGeneratingOrgCode() {
        return generatingOrgCode;
    }

    public void setGeneratingOrgCode(String generatingOrgCode) {
        this.generatingOrgCode = generatingOrgCode == null ? null : generatingOrgCode.trim();
    }

    public String getGeneratingOrgName() {
        return generatingOrgName;
    }

    public void setGeneratingOrgName(String generatingOrgName) {
        this.generatingOrgName = generatingOrgName == null ? null : generatingOrgName.trim();
    }

    public String getExpenseBearCode() {
        return expenseBearCode;
    }

    public void setExpenseBearCode(String expenseBearCode) {
        this.expenseBearCode = expenseBearCode == null ? null : expenseBearCode.trim();
    }

    public String getExpenseBearName() {
        return expenseBearName;
    }

    public void setExpenseBearName(String expenseBearName) {
        this.expenseBearName = expenseBearName == null ? null : expenseBearName.trim();
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getBillParentType() {
        return billParentType;
    }

    public void setBillParentType(String billParentType) {
        this.billParentType = billParentType == null ? null : billParentType.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountFrt() {
        return amountFrt;
    }

    public void setAmountFrt(BigDecimal amountFrt) {
        this.amountFrt = amountFrt;
    }

    public BigDecimal getAmountPup() {
        return amountPup;
    }

    public void setAmountPup(BigDecimal amountPup) {
        this.amountPup = amountPup;
    }

    public BigDecimal getAmountDel() {
        return amountDel;
    }

    public void setAmountDel(BigDecimal amountDel) {
        this.amountDel = amountDel;
    }

    public BigDecimal getAmountPkg() {
        return amountPkg;
    }

    public void setAmountPkg(BigDecimal amountPkg) {
        this.amountPkg = amountPkg;
    }

    public BigDecimal getAmountDv() {
        return amountDv;
    }

    public void setAmountDv(BigDecimal amountDv) {
        this.amountDv = amountDv;
    }

    public BigDecimal getAmountCod() {
        return amountCod;
    }

    public void setAmountCod(BigDecimal amountCod) {
        this.amountCod = amountCod;
    }

    public BigDecimal getAmountBof() {
        return amountBof;
    }

    public void setAmountBof(BigDecimal amountBof) {
        this.amountBof = amountBof;
    }

    public BigDecimal getAmountDfni() {
        return amountDfni;
    }

    public void setAmountDfni(BigDecimal amountDfni) {
        this.amountDfni = amountDfni;
    }

    public BigDecimal getAmountDf() {
        return amountDf;
    }

    public void setAmountDf(BigDecimal amountDf) {
        this.amountDf = amountDf;
    }

    public BigDecimal getAmountDc() {
        return amountDc;
    }

    public void setAmountDc(BigDecimal amountDc) {
        this.amountDc = amountDc;
    }

    public BigDecimal getAmountLuf() {
        return amountLuf;
    }

    public void setAmountLuf(BigDecimal amountLuf) {
        this.amountLuf = amountLuf;
    }

    public BigDecimal getAmountUldf() {
        return amountUldf;
    }

    public void setAmountUldf(BigDecimal amountUldf) {
        this.amountUldf = amountUldf;
    }

    public BigDecimal getAmountSr() {
        return amountSr;
    }

    public void setAmountSr(BigDecimal amountSr) {
        this.amountSr = amountSr;
    }

    public BigDecimal getAmountOt() {
        return amountOt;
    }

    public void setAmountOt(BigDecimal amountOt) {
        this.amountOt = amountOt;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

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
}