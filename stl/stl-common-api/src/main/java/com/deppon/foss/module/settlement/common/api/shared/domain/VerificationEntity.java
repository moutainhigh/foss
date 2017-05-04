package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.List;

public class VerificationEntity {

    // 需要添加的参数 员工编号 员工名称 当前部门名称 当前部门编码
    /**
     * 员工工号
     */
    private String empCode;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 登录部门
     */
    private String orgCode;

    /**
     * 登陆部门名称
     */
    private String orgName;

    /*
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 交易金额
     */
    private BigDecimal totalAmount;

    /**
     * 运单号/对账单号
     */
    private String outTradeNo;

    /**
     * 单号:此处单号为对账单模块所需参数，客户编码或者对账单号
     */
    private List<String> numbers;

    /**
     * 买家在支付宝的用户id
     */
    private String buyerUserId;

    /**
     * 部门编码(汇款部门=产生该支付编码的登录部门)
     */

    /**
     * 支付类型(条码支付)
     */
    private String payType;

    /**
     * 收款账户 对应第三方支付接口传递的查询应收单dto中的收款账号和推送财务自助的收款账号
     */
    private String collectionAccount;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCollectionAccount() {
        return collectionAccount;
    }

    public void setCollectionAccount(String collectionAccount) {
        this.collectionAccount = collectionAccount;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    private String storeName;

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
