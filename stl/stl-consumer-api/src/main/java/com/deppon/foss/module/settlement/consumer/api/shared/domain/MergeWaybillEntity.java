package com.deppon.foss.module.settlement.consumer.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 322906 on 2016/6/12.
 * 合并运单号表
 * 需求：我公司才在部门客户发货量大，单次申请发票设计运单超过150个，需要分批进行发票申请，并开具多张发票，
 * 现在foss系统根据一定的业务规则将查询到的的明细运单进行合并，然后发票系统中直接用“合并运单号”申请发票
 * 保存合并运单信息
 */
public class MergeWaybillEntity extends BaseEntity {

    private static final long serialVersionUID = -6529957818438524823L;
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
    private String receiveOrgName;
    /**
     * 到达部门
     */
    private String descOrgCode;
    private String descOrgName;
    /**
     * 合并运单创建时间
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
    private String receiveDunningDeptName;
    /**
     * 出发合同部门
     */
    private String receiveContractDeptCode;
    private String receiveContractDeptName;
    /**
     * 到达催款部门
     */
    private String descDunningDeptCode;
    private String descDunningDeptName;
    /**
     * 到达合同部门
     */
    private String descContractDeptCode;
    private String descContractDeptName;
    /**
     * 发票抬头
     */
    private String invoiceHeadCode;
    /**
     * 税务登记号
     */
    private String taxId;

    private String active;

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

    public String getDeliveryCustomerCode() {
        return deliveryCustomerCode;
    }

    public void setDeliveryCustomerCode(String deliveryCustomerCode) {
        this.deliveryCustomerCode = deliveryCustomerCode;
    }

    public String getDeliveryCustomerName() {
        return deliveryCustomerName;
    }

    public void setDeliveryCustomerName(String deliveryCustomerName) {
        this.deliveryCustomerName = deliveryCustomerName;
    }

    public String getReceiveCustomerCode() {
        return receiveCustomerCode;
    }

    public void setReceiveCustomerCode(String receiveCustomerCode) {
        this.receiveCustomerCode = receiveCustomerCode;
    }

    public String getReceiveCustomerName() {
        return receiveCustomerName;
    }

    public void setReceiveCustomerName(String receiveCustomerName) {
        this.receiveCustomerName = receiveCustomerName;
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

    public String getDescOrgCode() {
        return descOrgCode;
    }

    public void setDescOrgCode(String descOrgCode) {
        this.descOrgCode = descOrgCode;
    }

    public String getDescOrgName() {
        return descOrgName;
    }

    public void setDescOrgName(String descOrgName) {
        this.descOrgName = descOrgName;
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

    public String getReceiveDunningDeptName() {
        return receiveDunningDeptName;
    }

    public void setReceiveDunningDeptName(String receiveDunningDeptName) {
        this.receiveDunningDeptName = receiveDunningDeptName;
    }

    public String getReceiveContractDeptCode() {
        return receiveContractDeptCode;
    }

    public void setReceiveContractDeptCode(String receiveContractDeptCode) {
        this.receiveContractDeptCode = receiveContractDeptCode;
    }

    public String getReceiveContractDeptName() {
        return receiveContractDeptName;
    }

    public void setReceiveContractDeptName(String receiveContractDeptName) {
        this.receiveContractDeptName = receiveContractDeptName;
    }

    public String getDescDunningDeptCode() {
        return descDunningDeptCode;
    }

    public void setDescDunningDeptCode(String descDunningDeptCode) {
        this.descDunningDeptCode = descDunningDeptCode;
    }

    public String getDescDunningDeptName() {
        return descDunningDeptName;
    }

    public void setDescDunningDeptName(String descDunningDeptName) {
        this.descDunningDeptName = descDunningDeptName;
    }

    public String getDescContractDeptCode() {
        return descContractDeptCode;
    }

    public void setDescContractDeptCode(String descContractDeptCode) {
        this.descContractDeptCode = descContractDeptCode;
    }

    public String getDescContractDeptName() {
        return descContractDeptName;
    }

    public void setDescContractDeptName(String descContractDeptName) {
        this.descContractDeptName = descContractDeptName;
    }

    public String getInvoiceHeadCode() {
        return invoiceHeadCode;
    }

    public void setInvoiceHeadCode(String invoiceHeadCode) {
        this.invoiceHeadCode = invoiceHeadCode;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
