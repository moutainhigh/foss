package com.deppon.foss.module.settlement.consumer.api.shared.dto;/**
 * Created by 105762 on 2015/5/15.
 */


import java.math.BigDecimal;
import java.util.Date;

/**
 * QMS理赔付款超时需要的参数
 * @author 105762
 */
public class OverdueClaimPaymentDto {
    private BigDecimal amount; // 理赔金额
    private String waybillNo; // 运单号
    private String responsiblePeopleCode; // 责任人工号
    private String responsiblePeopleName; // 责任人姓名
    private String responsibleDeptCode; // 责任部门编码
    private String responsibleDeptUnfiedCode; //责任部门标杆编码
    private String responsibleDeptName; // 责任部门名称
    private Date claimConfirmTime; // CRM理赔工作流审批完成时间
    private String productCode; //产品类型
    private static final String changeItmes = "付款时间与CRM理赔工作流审批完成的时间差值超过24小时"; // 事情经过（“付款时间与CRM理赔工作流审批完成的时间差值超过24小时”）
    private String billingDeptCode; //开单部门
    private String billingDeptName; //开单部门

    /* setter & getter */

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getResponsiblePeopleCode() {
        return responsiblePeopleCode;
    }

    public void setResponsiblePeopleCode(String responsiblePeopleCode) {
        this.responsiblePeopleCode = responsiblePeopleCode;
    }



    public String getChangeItmes() {
        return changeItmes;
    }

    public String getResponsiblePeopleName() {
        return responsiblePeopleName;
    }

    public void setResponsiblePeopleName(String responsiblePeopleName) {
        this.responsiblePeopleName = responsiblePeopleName;
    }

    public String getResponsibleDeptCode() {
        return responsibleDeptCode;
    }

    public void setResponsibleDeptCode(String responsibleDeptCode) {
        this.responsibleDeptCode = responsibleDeptCode;
    }

    public String getResponsibleDeptName() {
        return responsibleDeptName;
    }

    public void setResponsibleDeptName(String responsibleDeptName) {
        this.responsibleDeptName = responsibleDeptName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResponsibleDeptUnfiedCode() {
        return responsibleDeptUnfiedCode;
    }

    public void setResponsibleDeptUnfiedCode(String responsibleDeptUnfiedCode) {
        this.responsibleDeptUnfiedCode = responsibleDeptUnfiedCode;
    }

    public Date getClaimConfirmTime() {
        return claimConfirmTime;
    }

    public void setClaimConfirmTime(Date claimConfirmTime) {
        this.claimConfirmTime = claimConfirmTime;
    }

    public String getBillingDeptCode() {
        return billingDeptCode;
    }

    public void setBillingDeptCode(String billingDeptCode) {
        this.billingDeptCode = billingDeptCode;
    }

    public String getBillingDeptName() {
        return billingDeptName;
    }

    public void setBillingDeptName(String billingDeptName) {
        this.billingDeptName = billingDeptName;
    }

}
