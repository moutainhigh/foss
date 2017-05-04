package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.util.Date;

/**
 * Created by 073615 on 2015/2/6.
 */
public class DiscountRateDto {
    //客户编码
    private String customerCode;
    //截止日期
    private Date effectMonth;
    //是否有效
    private String active;
    //生成方式
    private String createType;
    //结束月份
    private Date endDate;
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
    /**
     * 单据子类型
     */
    private String[] billTypes;

    public String[] getBillTypes() {
        return billTypes;
    }

    public void setBillTypes(String[] billTypes) {
        this.billTypes = billTypes;
    }

    public String getIsDiscount() {
        return isDiscount;
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

    public String[] getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(String[] productCodes) {
        this.productCodes = productCodes;
    }

    //代收货款CRM 常量
    private static final String COD_CRM_CODE ="COLLAFTERDIS";
    //保价折常量
    private static final String INSURANCE_CRM_CODE ="ENSUREDPRICEAFTERDIS";
    //运费折扣常量
    private static final String TRANSPORT_CRM_CODE ="CARRIAGEAFTERDIS";

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Date getEffectMonth() {
        return effectMonth;
    }

    public void setEffectMonth(Date effectMonth) {
        this.effectMonth = effectMonth;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static String getCodCrmCode() {
        return COD_CRM_CODE;
    }

    public static String getInsuranceCrmCode() {
        return INSURANCE_CRM_CODE;
    }

    public static String getTransportCrmCode() {
        return TRANSPORT_CRM_CODE;
    }
}
