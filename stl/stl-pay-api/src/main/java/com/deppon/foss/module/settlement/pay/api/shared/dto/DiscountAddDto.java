package com.deppon.foss.module.settlement.pay.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 073615 on 2014/12/23.
 */
public class DiscountAddDto implements Serializable{
    /**
     * 开始时间
     */
    private Date startDate ;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 客户编码
     */
    private String customerCode;

    /**
     *折扣单单号
     */
    private String discountNo;

    /**
     *是否有效
     */
    private String active;
    /**
     * 单据子类型
     */
    private String[] billTypes;

    /**
     *代收货款手续费折扣率
     */
    private BigDecimal codDiscountRate;
    /**
     * 运费折扣率
     */
    private BigDecimal transportDiscountRate;
    /**
     * 保价费折扣率
     */
    private BigDecimal insuranceDiscountRate;
    /**
     * 当前登录人
     */
    CurrentInfo currentUser;
    /**
     * 当前登录人所在部门
     */
    OrgAdministrativeInfoEntity dept;

    /**
     *是否折扣
     */
    private String isDiscount;
    /**
     * 付款方式
     */
    private String[] paymentTypes;
    /**
     * 产品类型
     */
    private String[] productCodes;

    public String[] getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(String[] productCodes) {
        this.productCodes = productCodes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(String discountNo) {
        this.discountNo = discountNo;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String[] getBillTypes() {
        return billTypes;
    }

    public void setBillTypes(String[] billTypes) {
        this.billTypes = billTypes;
    }


    public void setCodDiscountRate(BigDecimal codDiscountRate) {
        this.codDiscountRate = codDiscountRate;
    }


    public void setTransportDiscountRate(BigDecimal transportDiscountRate) {
        this.transportDiscountRate = transportDiscountRate;
    }

    public void setInsuranceDiscountRate(BigDecimal insuranceDiscountRate) {
        this.insuranceDiscountRate = insuranceDiscountRate;
    }

    public CurrentInfo getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentInfo currentUser) {
        this.currentUser = currentUser;
    }

    public OrgAdministrativeInfoEntity getDept() {
        return dept;
    }

    public void setDept(OrgAdministrativeInfoEntity dept) {
        this.dept = dept;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String[] getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(String[] paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public BigDecimal getCodDiscountRate() {
        return codDiscountRate;
    }

    public BigDecimal getTransportDiscountRate() {
        return transportDiscountRate;
    }

    public BigDecimal getInsuranceDiscountRate() {
        return insuranceDiscountRate;
    }

    public String getIsDiscount() {
        return isDiscount;
    }
}
