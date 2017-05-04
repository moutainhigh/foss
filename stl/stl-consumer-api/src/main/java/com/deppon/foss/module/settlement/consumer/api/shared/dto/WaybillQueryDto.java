package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 322906 on 2016/6/15.
 */
public class WaybillQueryDto implements Serializable{

    private static final long serialVersionUID = -151558799637969497L;
    /**
     * 登入员工号
     */
    private String empCode;

    /**
     * 运单号集合
     */
    private String[] waybillNos;

    /**
     * 发票标记(运输专票11%（01），非运输专票（02）)
     */
    private String invoiceMark;
    /**
     * 业务类型（快递01，零担02，整车03）
     */
    private String product;

//    /**
//     * 是否快递
//     * @return
//     */
//    private String isExpress;
//    /**
//     * 是否整车运单
//     */
//    private String isWholeVehicle;
    /**
     * 开单付款方式
     */
    private String[] paidMethods;
    /**
     * 是否统一结算
     */
    private String unifiedSettlement;
    /**
     * 客户类型（用来判断该运单的客户是发货客户还是收货客户）
     * OC--发货客户
     * DC--收货客户
     * ALL--发货客户收货客户
     */
    private String customerType;
    /**
     * 所属部门（收货部门必须和此部门一致，才可以查询）
     */
    private String orgCode;
    /**
     * 客户编码（页面输入的客户编码）
     */
    private String[] customerCodes;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 税务登记号
     */
    private String taxId;

    private Date startDate;
    private Date endDate;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String[] getPaidMethods() {
        return paidMethods;
    }

    public void setPaidMethods(String[] paidMethods) {
        this.paidMethods = paidMethods;
    }

    public String getUnifiedSettlement() {
        return unifiedSettlement;
    }

    public void setUnifiedSettlement(String unifiedSettlement) {
        this.unifiedSettlement = unifiedSettlement;
    }

    public String[] getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(String[] waybillNos) {
        this.waybillNos = waybillNos;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String[] getCustomerCodes() {
        return customerCodes;
    }

    public void setCustomerCodes(String[] customerCodes) {
        this.customerCodes = customerCodes;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
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
}
