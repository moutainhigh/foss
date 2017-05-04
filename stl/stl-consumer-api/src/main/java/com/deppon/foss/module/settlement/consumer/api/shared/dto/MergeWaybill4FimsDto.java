package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;

/**
 * 封装返回给发票系统的合并运单信息
 * Created by 322906 on 2016/7/4.
 */
public class MergeWaybill4FimsDto {

    private String customerName;
    /**
     * 合并运单号
     */
    private String mergeWaybillNo;
    /**
     * 业务类型（零担，快递，整车）
     */
    private String product;
    /**
     * 发票标记（）
     */
    private String invoiceMark;
    /**
     * 预付金额
     */
    private String prePayAmount;
    /**
     * 到付金额
     */
    private String toPayAmount;
    /**
     * 发货方客户编码
     */
    private String deliveryCustomerCode;

    /**
     * 收货方客户编码
     */
    private String receiveCustomerCode;

    /**
     * 收货部门编码
     */
    private String receiveOrgCode;

    /**
     * 到达部门编码
     */
    private String descOrgCode;

    /**
     * 开单日期
     */
    private Date billTime;
    /**
     * 运输路线
     */
    private String transferLine;
    /**
     * 出发催款部门
     */
    private String receiveDunningDeptCode;
    //private String receiveDunningDeptName;
    /**
     * 出发合同部门
     */
    private String receiveContractDeptCode;
    //private String receiveContractDeptName;
    /**
     * 到达催款部门
     */
    private String descDunningDeptCode;
   // private String descDunningDeptName;
    /**
     * 到达合同部门
     */
    private String descContractDeptCode;
    //private String descContractDeptName;



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMergeWaybillNo() {
        return mergeWaybillNo;
    }

    public void setMergeWaybillNo(String mergeWaybillNo) {
        this.mergeWaybillNo = mergeWaybillNo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public String getPrePayAmount() {
        return prePayAmount;
    }

    public void setPrePayAmount(String prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    public String getToPayAmount() {
        return toPayAmount;
    }

    public void setToPayAmount(String toPayAmount) {
        this.toPayAmount = toPayAmount;
    }

    public String getDeliveryCustomerCode() {
        return deliveryCustomerCode;
    }

    public void setDeliveryCustomerCode(String deliveryCustomerCode) {
        this.deliveryCustomerCode = deliveryCustomerCode;
    }

    public String getReceiveCustomerCode() {
        return receiveCustomerCode;
    }

    public void setReceiveCustomerCode(String receiveCustomerCode) {
        this.receiveCustomerCode = receiveCustomerCode;
    }

    public String getReceiveOrgCode() {
        return receiveOrgCode;
    }

    public void setReceiveOrgCode(String receiveOrgCode) {
        this.receiveOrgCode = receiveOrgCode;
    }

    public String getDescOrgCode() {
        return descOrgCode;
    }

    public void setDescOrgCode(String descOrgCode) {
        this.descOrgCode = descOrgCode;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getTransferLine() {
        return transferLine;
    }

    public void setTransferLine(String transferLine) {
        this.transferLine = transferLine;
    }

    public String getReceiveDunningDeptCode() {
        return receiveDunningDeptCode;
    }

    public void setReceiveDunningDeptCode(String receiveDunningDeptCode) {
        this.receiveDunningDeptCode = receiveDunningDeptCode;
    }

    public String getReceiveContractDeptCode() {
        return receiveContractDeptCode;
    }

    public void setReceiveContractDeptCode(String receiveContractDeptCode) {
        this.receiveContractDeptCode = receiveContractDeptCode;
    }

    public String getDescDunningDeptCode() {
        return descDunningDeptCode;
    }

    public void setDescDunningDeptCode(String descDunningDeptCode) {
        this.descDunningDeptCode = descDunningDeptCode;
    }

    public String getDescContractDeptCode() {
        return descContractDeptCode;
    }

    public void setDescContractDeptCode(String descContractDeptCode) {
        this.descContractDeptCode = descContractDeptCode;
    }
}
