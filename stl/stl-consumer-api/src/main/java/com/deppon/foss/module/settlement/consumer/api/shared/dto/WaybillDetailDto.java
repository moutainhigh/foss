package com.deppon.foss.module.settlement.consumer.api.shared.dto;

//import java.util.Date;

/**
 * Created by 322906 on 2016/6/20.
 * 合并运单新增查询的返回字段
 * 封装查询后返回给页面的字段
 */
public class WaybillDetailDto {
    /**
     * 运单号
     */
    private String waybillNo;

    //private Date billCreateDate;
    //private String transLine;

    /**
     * 业务类型（零担，快递，整车）
     */
    private String businessType;
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
     * 收货部门名称
     */
    private String receiveOrgName;

    /**
     * 到达部门编码（运单中的提货网点）
     */
    private String arriveOrgCode;
    /**
     * 到达部门名称
     */
    private String arriveOrgName;

    /**
     * 出发催款部门
     */
    private String originalDunningOrgName;
    /**
     * 出发合同部门
     */
    private String originalContractOrgName;
    /**
     * 到达催款部门
     */
    private String arriveDunningOrgName;
    /**
     * 到达合同部门
     */
    private String arriveContractOrgName;


    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }


    public String getArriveOrgCode() {
        return arriveOrgCode;
    }

    public void setArriveOrgCode(String arriveOrgCode) {
        this.arriveOrgCode = arriveOrgCode;
    }

    public String getArriveOrgName() {
        return arriveOrgName;
    }

    public void setArriveOrgName(String arriveOrgName) {
        this.arriveOrgName = arriveOrgName;
    }

    public String getOriginalDunningOrgName() {
        return originalDunningOrgName;
    }

    public void setOriginalDunningOrgName(String originalDunningOrgName) {
        this.originalDunningOrgName = originalDunningOrgName;
    }

    public String getOriginalContractOrgName() {
        return originalContractOrgName;
    }

    public void setOriginalContractOrgName(String originalContractOrgName) {
        this.originalContractOrgName = originalContractOrgName;
    }

    public String getArriveDunningOrgName() {
        return arriveDunningOrgName;
    }

    public void setArriveDunningOrgName(String arriveDunningOrgName) {
        this.arriveDunningOrgName = arriveDunningOrgName;
    }

    public String getArriveContractOrgName() {
        return arriveContractOrgName;
    }

    public void setArriveContractOrgName(String arriveContractOrgName) {
        this.arriveContractOrgName = arriveContractOrgName;
    }
}
