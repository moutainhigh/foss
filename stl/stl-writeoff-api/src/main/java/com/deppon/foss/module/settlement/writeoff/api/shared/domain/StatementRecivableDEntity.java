/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人收款对账单明细
 * @author foss-youkun
 * @date 2016/1/26
 */
public class StatementRecivableDEntity  extends BaseEntity {

    private static final Long serialVersionUID = 1L;
    //业务日期
    private Date businessDate;
    //运单号
    private String waybillNo;
    //目的站
    private String arrvRegionCode;
    //产品类型
    private String proType;
    //件数
    private BigDecimal qty;
    //计费体积
    private BigDecimal billingVolume;
    //计费重量
    private BigDecimal billWeight;
    //付款方式
    private String paymentType;
    //提货方式
    private String receiveMethod;
    //原始未核销金额
    private BigDecimal unverifyAmount;
    //总金额
    private BigDecimal amount;
    //单号
    private String sourceBillNo;
    //单据子类型
    private String billType;
    //客户代理编码
    private String customerCode;
    //客户代理名称
    private String customerName;
    //已核销金额
    private BigDecimal verifyAmount;
    //部门编码
    private String orgCode;
    //部门名称
    private String orgName;
    //记账日期
    private Date accountDate;
    //提货网点
    private String customerPickupOrgName;
    //货物名称
    private String goodsName;
    //始发网点编码
    private String origOrgCode;
    //始发网点名称
    private String origOrgName;
    //到达部门编码
    private String destOrgCode;
    //到达部门名称
    private String destOrgName;
    //发货客户编码
    private String deliveryCustomerCode;
    //签收日期
    private Date signDate;
    //审核状态
    private String auditStatus;
    //备注
    private String notes;
    //是否删除
    private String isDelete;
    //作废时间
    private Date cancelTime;
    //运单开单时间
    private Date billBeginTime;
    //单操作费
    private BigDecimal singleOperationFee;
    //包装费提成
    private BigDecimal packageFee;
    //保价费提成
    private BigDecimal insuranceFee;
    //代收货款手续费提成
    private BigDecimal disbursementFee;
    //送货费提成（不含上楼）
    private BigDecimal deliveryFee;
    //基础送货费提成
    private BigDecimal baseDeliveryFee;
    //床垫操作费提成
    private BigDecimal mattressOperationFee;
    //代理报关费提成
    private BigDecimal agentDeclarationFee;
    //拆包装费提成
    private BigDecimal removePackingFee;
    //登记费提成
    private BigDecimal registrationFee;
    //停车费提成
    private BigDecimal parkingFee;
    //其他费用提成
    private BigDecimal otherFee;
    //送货上楼费提成
    private BigDecimal upstairsFee;
    //送货进仓费提成
    private BigDecimal warehouseDeliveryFee;
    //大件上楼费提成
    private BigDecimal largeUpstairsFee;
    //超远派送费提成
    private BigDecimal superLongFee;
    //签收单返回提成
    private BigDecimal singleReturnFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 对账单单号
     */
    private String billStatementNo;


    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode == null ? null : arrvRegionCode.trim();
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getBillingVolume() {
        return billingVolume;
    }

    public void setBillingVolume(BigDecimal billingVolume) {
        this.billingVolume = billingVolume;
    }

    public BigDecimal getBillWeight() {
        return billWeight;
    }

    public void setBillWeight(BigDecimal billWeight) {
        this.billWeight = billWeight;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getReceiveMethod() {
        return receiveMethod;
    }

    public void setReceiveMethod(String receiveMethod) {
        this.receiveMethod = receiveMethod == null ? null : receiveMethod.trim();
    }

    public BigDecimal getUnverifyAmount() {
        return unverifyAmount;
    }

    public void setUnverifyAmount(BigDecimal unverifyAmount) {
        this.unverifyAmount = unverifyAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceBillNo() {
        return sourceBillNo;
    }

    public void setSourceBillNo(String sourceBillNo) {
        this.sourceBillNo = sourceBillNo == null ? null : sourceBillNo.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public BigDecimal getVerifyAmount() {
        return verifyAmount;
    }

    public void setVerifyAmount(BigDecimal verifyAmount) {
        this.verifyAmount = verifyAmount;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public String getCustomerPickupOrgName() {
        return customerPickupOrgName;
    }

    public void setCustomerPickupOrgName(String customerPickupOrgName) {
        this.customerPickupOrgName = customerPickupOrgName == null ? null : customerPickupOrgName.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getOrigOrgCode() {
        return origOrgCode;
    }

    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode == null ? null : origOrgCode.trim();
    }

    public String getOrigOrgName() {
        return origOrgName;
    }

    public void setOrigOrgName(String origOrgName) {
        this.origOrgName = origOrgName == null ? null : origOrgName.trim();
    }

    public String getDestOrgCode() {
        return destOrgCode;
    }

    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode == null ? null : destOrgCode.trim();
    }

    public String getDestOrgName() {
        return destOrgName;
    }

    public void setDestOrgName(String destOrgName) {
        this.destOrgName = destOrgName == null ? null : destOrgName.trim();
    }

    public String getDeliveryCustomerCode() {
        return deliveryCustomerCode;
    }

    public void setDeliveryCustomerCode(String deliveryCustomerCode) {
        this.deliveryCustomerCode = deliveryCustomerCode == null ? null : deliveryCustomerCode.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getBillBeginTime() {
        return billBeginTime;
    }

    public void setBillBeginTime(Date billBeginTime) {
        this.billBeginTime = billBeginTime;
    }

    public BigDecimal getSingleOperationFee() {
        return singleOperationFee;
    }

    public void setSingleOperationFee(BigDecimal singleOperationFee) {
        this.singleOperationFee = singleOperationFee;
    }

    public BigDecimal getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(BigDecimal packageFee) {
        this.packageFee = packageFee;
    }

    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public BigDecimal getDisbursementFee() {
        return disbursementFee;
    }

    public void setDisbursementFee(BigDecimal disbursementFee) {
        this.disbursementFee = disbursementFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getBaseDeliveryFee() {
        return baseDeliveryFee;
    }

    public void setBaseDeliveryFee(BigDecimal baseDeliveryFee) {
        this.baseDeliveryFee = baseDeliveryFee;
    }

    public BigDecimal getMattressOperationFee() {
        return mattressOperationFee;
    }

    public void setMattressOperationFee(BigDecimal mattressOperationFee) {
        this.mattressOperationFee = mattressOperationFee;
    }

    public BigDecimal getAgentDeclarationFee() {
        return agentDeclarationFee;
    }

    public void setAgentDeclarationFee(BigDecimal agentDeclarationFee) {
        this.agentDeclarationFee = agentDeclarationFee;
    }

    public BigDecimal getRemovePackingFee() {
        return removePackingFee;
    }

    public void setRemovePackingFee(BigDecimal removePackingFee) {
        this.removePackingFee = removePackingFee;
    }

    public BigDecimal getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(BigDecimal registrationFee) {
        this.registrationFee = registrationFee;
    }

    public BigDecimal getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getUpstairsFee() {
        return upstairsFee;
    }

    public void setUpstairsFee(BigDecimal upstairsFee) {
        this.upstairsFee = upstairsFee;
    }

    public BigDecimal getWarehouseDeliveryFee() {
        return warehouseDeliveryFee;
    }

    public void setWarehouseDeliveryFee(BigDecimal warehouseDeliveryFee) {
        this.warehouseDeliveryFee = warehouseDeliveryFee;
    }

    public BigDecimal getLargeUpstairsFee() {
        return largeUpstairsFee;
    }

    public void setLargeUpstairsFee(BigDecimal largeUpstairsFee) {
        this.largeUpstairsFee = largeUpstairsFee;
    }

    public BigDecimal getSuperLongFee() {
        return superLongFee;
    }

    public void setSuperLongFee(BigDecimal superLongFee) {
        this.superLongFee = superLongFee;
    }

    public BigDecimal getSingleReturnFee() {
        return singleReturnFee;
    }

    public void setSingleReturnFee(BigDecimal singleReturnFee) {
        this.singleReturnFee = singleReturnFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBillStatementNo() {
        return billStatementNo;
    }

    public void setBillStatementNo(String billStatementNo) {
        this.billStatementNo = billStatementNo;
    }
}