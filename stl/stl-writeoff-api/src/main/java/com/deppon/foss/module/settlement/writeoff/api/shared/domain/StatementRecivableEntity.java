package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 合伙人收款对账单
 * @author foss-youkun
 * @date 2016/1/26
 */
public class StatementRecivableEntity  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //private String id;
    /**
     * 对账单号
     */
    private String statementBillNo;
    /**
     * 部门编码
     */
    private String createOrgCode;
    /**
     * 部门名称
     */
    private String createOrgName;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 部门标杆编码
     */
    private String unifiedCode;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 对账单子类型
     */
    private String billType;
    /**
     * 本期发生金额
     */
    private BigDecimal periodAmount;

    /**
     * 本期应收金额
     */
    private  BigDecimal periodRecAmount;
    /**
     * 本期剩余应收金额
     */
    private BigDecimal periodUnverifyRecAmount;

    /**
     * 账期开始日期
     */
    private Date periodBeginDate;
    /**
     * 账期结束日期
     */
    private Date periodEndDate;
    /**
     * 结账次数
     */
    private Short settleNum;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 子公司账号
     */
    private String companyAccountBankNo;
    /**
     * 开户名
     */
    private String accountUserName;
    /**
     * 支行名称
     */
    private String bankBranchName;
    /**
     * 对账单状态
     */
    private String statementStatus;
    /**
     * 本期已还金额
     */
    private BigDecimal periodRrpayAmount;
    /**
     * 本期未还金额
     */
    private BigDecimal periodNpayAmount;
    /**
     * 运单开单时间
     */
    private Date billBeginTime;
    /**
     * 对账单描述
     */
    private String statementDes;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getStatementBillNo() {
        return statementBillNo;
    }

    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUnifiedCode() {
        return unifiedCode;
    }

    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public BigDecimal getPeriodUnverifyRecAmount() {
        return periodUnverifyRecAmount;
    }

    public void setPeriodUnverifyRecAmount(BigDecimal periodUnverifyRecAmount) {
        this.periodUnverifyRecAmount = periodUnverifyRecAmount;
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
        this.companyAccountBankNo = companyAccountBankNo;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getStatementStatus() {
        return statementStatus;
    }

    public void setStatementStatus(String statementStatus) {
        this.statementStatus = statementStatus;
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
        this.statementDes = statementDes;
    }

    public BigDecimal getPeriodRecAmount() {
        return periodRecAmount;
    }

    public void setPeriodRecAmount(BigDecimal periodRecAmount) {
        this.periodRecAmount = periodRecAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}




