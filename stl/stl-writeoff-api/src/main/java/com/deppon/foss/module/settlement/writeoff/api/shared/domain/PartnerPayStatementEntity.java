/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PartnerPayStatementEntity {
    
    /**
    *ID
    */
    private String id;
    
    /**
    *对账单号
    */
    private String statementBillNo;
    
    /**
    *部门编码
    */
    private String createOrgCode;
    
    /**
    *部门名称
    */
    private String createOrgName;
    
    /**
    *公司编码
    */
    private String companyCode;
    
    /**
    *公司名称
    */
    private String companyName;
    
    /**
    *部门标杆编码
    */
    private String unifiedCode;
    
    /**
    *客户编码
    */
    private String customerCode;
    
    /**
    *客户名称
    */
    private String customerName;
    
    /**
    *对账单子类型
    */
    private String billType;
    
    /**
    *本期发生金额
    */
    private BigDecimal periodAmount;
    
    /**
    *本期应付金额
    */
    private BigDecimal periodPayAmount;
    
    /**
    *本期剩余应付金额
    */
    private BigDecimal periodUnverifyPayAmount;
    
    /**
    *账期开始日期
    */
    private Date periodBeginDate;
    
    /**
    *账期结束日期
    */
    private Date periodEndDate;
    
    /**
    *结账次数
    */
    private Short settleNum;
    
    /**
    *确认时间
    */
    private Date confirmTime;
    
    /**
    *子公司账号
    */
    private String companyAccountBankNo;
    
    /**
    *开户名
    */
    private String accountUserName;
    
    /**
    *支行名称
    */
    private String bankBranchName;
    
    /**
    *对账单状态
    */
    private String statementStatus;
    
    /**
    *本期已还金额
    */
    private BigDecimal periodRrpayAmount;
    
    /**
    *本期未还金额
    */
    private BigDecimal periodNpayAmount;
    
    /**
    *运单开单时间
    */
    private Date billBeginTime;
    
    /**
    *对账单描述
    */
    private String statementDes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStatementBillNo() {
        return statementBillNo;
    }

    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo == null ? null : statementBillNo.trim();
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode == null ? null : createOrgCode.trim();
    }

    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName == null ? null : createOrgName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getUnifiedCode() {
        return unifiedCode;
    }

    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode == null ? null : unifiedCode.trim();
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public BigDecimal getPeriodPayAmount() {
        return periodPayAmount;
    }

    public void setPeriodPayAmount(BigDecimal periodPayAmount) {
        this.periodPayAmount = periodPayAmount;
    }

    public BigDecimal getPeriodUnverifyPayAmount() {
        return periodUnverifyPayAmount;
    }

    public void setPeriodUnverifyPayAmount(BigDecimal periodUnverifyPayAmount) {
        this.periodUnverifyPayAmount = periodUnverifyPayAmount;
    }

    public Date getPeriodBeginDate() {
        return periodBeginDate;
    }

    public void setPeriodBeginDate(Date periodBeginDate) {
        this.periodBeginDate = periodBeginDate;
    }

    public Date getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(Date periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public Short getSettleNum() {
        return settleNum;
    }

    public void setSettleNum(Short settleNum) {
        this.settleNum = settleNum;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCompanyAccountBankNo() {
        return companyAccountBankNo;
    }

    public void setCompanyAccountBankNo(String companyAccountBankNo) {
        this.companyAccountBankNo = companyAccountBankNo == null ? null : companyAccountBankNo.trim();
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName == null ? null : accountUserName.trim();
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName == null ? null : bankBranchName.trim();
    }

    public String getStatementStatus() {
        return statementStatus;
    }

    public void setStatementStatus(String statementStatus) {
        this.statementStatus = statementStatus == null ? null : statementStatus.trim();
    }

    public BigDecimal getPeriodRrpayAmount() {
        return periodRrpayAmount;
    }

    public void setPeriodRrpayAmount(BigDecimal periodRrpayAmount) {
        this.periodRrpayAmount = periodRrpayAmount;
    }

    public BigDecimal getPeriodNpayAmount() {
        return periodNpayAmount;
    }

    public void setPeriodNpayAmount(BigDecimal periodNpayAmount) {
        this.periodNpayAmount = periodNpayAmount;
    }

    public Date getBillBeginTime() {
        return billBeginTime;
    }

    public void setBillBeginTime(Date billBeginTime) {
        this.billBeginTime = billBeginTime;
    }

    public String getStatementDes() {
        return statementDes;
    }

    public void setStatementDes(String statementDes) {
        this.statementDes = statementDes == null ? null : statementDes.trim();
    }
}