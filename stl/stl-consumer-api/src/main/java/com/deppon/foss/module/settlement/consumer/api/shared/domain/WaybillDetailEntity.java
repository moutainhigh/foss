package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 322906 on 2016/6/15.
 *
 */
public class WaybillDetailEntity extends BaseEntity{

    private String mergeWaybillNo;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 业务类型
     * 01--快递
     * 02--零担
     * 03--整车
     */
    private String product;

    /**
     * 开单日期
     */
    private Date billTime;
    /**
     * 运输路线
     */
    private String transferLine;
    /**
     * 发票标记（）
     */
    private String invoiceMark;
    /**
     * 预付金额
     */
    private BigDecimal prePayAmount;
    /**
     * 到付金额
     */
    private BigDecimal toPayAmount;
    /**
     * 发货方客户编码
     */
    private String deliveryCustomerCode;
    private String deliveryCustomerName;
    /**
     * 收货方客户编码
     */
    private String receiveCustomerCode;
    private String receiveCustomerName;
    /**
     * 收货部门（出发部门）
     */
    private String receiveOrgCode;

    /**
     * 收货部门名称（出发部门）
     */
    private String receiveOrgName;

    /**
     * 到达部门
     */
    private String destOrgCode;
    private String destOrgName;


    /**
     * 出发催款部门编码
     */
    private String origDunningOrgCode;
    private String origDunningOrgName;
    /**
     * 出发合同部门
     */
    private String origContractOrgCode;
    private String origContractOrgName;
    /**
     * 到达催款部门
     */
    private String destDunningOrgCode;
    private String destDunningOrgName;
    /**
     * 到达合同部门
     */
    private String destContractOrgCode;
    private String destContractOrgName;


    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public BigDecimal getPrePayAmount() {
        return prePayAmount;
    }

    public void setPrePayAmount(BigDecimal prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    public BigDecimal getToPayAmount() {
        return toPayAmount;
    }

    public void setToPayAmount(BigDecimal toPayAmount) {
        this.toPayAmount = toPayAmount;
    }

    public String getDeliveryCustomerName() {
        return deliveryCustomerName;
    }

    public void setDeliveryCustomerName(String deliveryCustomerName) {
        this.deliveryCustomerName = deliveryCustomerName;
    }

    public String getReceiveCustomerName() {
        return receiveCustomerName;
    }

    public void setReceiveCustomerName(String receiveCustomerName) {
        this.receiveCustomerName = receiveCustomerName;
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

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName;
    }

    public String getOrigDunningOrgCode() {
        return origDunningOrgCode;
    }

    public void setOrigDunningOrgCode(String origDunningOrgCode) {
        this.origDunningOrgCode = origDunningOrgCode;
    }

    public String getOrigDunningOrgName() {
        return origDunningOrgName;
    }

    public void setOrigDunningOrgName(String origDunningOrgName) {
        this.origDunningOrgName = origDunningOrgName;
    }

    public String getOrigContractOrgCode() {
        return origContractOrgCode;
    }

    public void setOrigContractOrgCode(String origContractOrgCode) {
        this.origContractOrgCode = origContractOrgCode;
    }

    public String getOrigContractOrgName() {
        return origContractOrgName;
    }

    public void setOrigContractOrgName(String origContractOrgName) {
        this.origContractOrgName = origContractOrgName;
    }

    public String getDestDunningOrgCode() {
        return destDunningOrgCode;
    }

    public void setDestDunningOrgCode(String destDunningOrgCode) {
        this.destDunningOrgCode = destDunningOrgCode;
    }

    public String getDestDunningOrgName() {
        return destDunningOrgName;
    }

    public void setDestDunningOrgName(String destDunningOrgName) {
        this.destDunningOrgName = destDunningOrgName;
    }

    public String getDestContractOrgCode() {
        return destContractOrgCode;
    }

    public void setDestContractOrgCode(String destContractOrgCode) {
        this.destContractOrgCode = destContractOrgCode;
    }

    public String getDestContractOrgName() {
        return destContractOrgName;
    }

    public void setDestContractOrgName(String destContractOrgName) {
        this.destContractOrgName = destContractOrgName;
    }

    public String getMergeWaybillNo() {
        return mergeWaybillNo;
    }

    public void setMergeWaybillNo(String mergeWaybillNo) {
        this.mergeWaybillNo = mergeWaybillNo;
    }


}
