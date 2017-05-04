package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
/**合伙人付款对账单明细实体
 * @author 367638 
 *
 */
public class PartnerPayStatementDEntity {
    
    /**
    *ID
    */
    private String id;
    
    /**
    *对账单号
    */
    private String statementBillNo;
    
    /**
    *应付单号
    */
    private String payableNo;
    
    /**
    *运单号
    */
    private String waybillNo;
    
    /**
    *单据子类型
    */
    private String billType;
    
    /**
    *审核状态
    */
    private String auditStatus;
    
    /**
    *金额
    */
    private BigDecimal amount;
    
    /**
    *已核销金额
    */
    private BigDecimal verifyAmount;
    
    /**
    *未核销金额
    */
    private BigDecimal unverifyAmount;
    
    /**
    *部门编码
    */
    private String orgCode;
    
    /**
    *部门名称
    */
    private String orgName;
    
    /**
    *始发网点编码
    */
    private String origOrgCode;
    
    /**
    *始发网点名称
    */
    private String origOrgName;
    
    /**
    *到达网点编码
    */
    private String destOrgCode;
    
    /**
    *到达网点名称
    */
    private String destOrgName;
    
    /**
    *客户编码
    */
    private String customerCode;
    
    /**
    *客户名称
    */
    private String customerName;
    
    /**
    *始发地
    */
    private String deptRegionCode;
    
    /**
    *目的站
    */
    private String arrvRegionCode;
    
    /**
    *提货网点
    */
    private String customerPickupOrgName;
    
    /**
    *货物名称
    */
    private String goodsName;
    
    /**
    *发货客户编码
    */
    private String deliveryCustomerCode;
    
    /**
    *发货客户名称
    */
    private String deliveryCustomerName;
    
    /**
    *付款方式
    */
    private String paymentType;
    
    /**
    *提货方式
    */
    private String receiveMethod;
    
    /**
    *运输性质
    */
    private String productCode;
    
    /**
    *删除时间
    */
    private Date disableTime;
    
    /**
    *业务日期
    */
    private Date businessDate;
    
    /**
    *记账日期
    */
    private Date accountDate;
    
    /**
    *运单签收日期
    */
    private Date signDate;
    
    /**
    *创建时间
    */
    private Date createTime;
    
    /**
    *备注
    */
    private String notes;
    
    /**
    *运单开单时间
    */
    private Date waybillCreateDate;
    
    /**
    *支线转运提成
    */
    private BigDecimal regionalTransportFee;
    
    /**
    *送货费
    */
    private BigDecimal deliveryFee;
    
    /**
    *派送费
    */
    private BigDecimal dispatchFee;
    
    /**
    *送货进仓费
    */
    private BigDecimal deliveryWarehouseFee;
    
    /**
    *签收单返单费
    */
    private BigDecimal signReturnFee;
    
    /**
    *送货上楼费
    */
    private BigDecimal deliveryUpstairsFee;
    
    /**
    *大件上楼费
    */
    private BigDecimal bigUpstairsFee;
    
    /**
    *超远派送费
    */
    private BigDecimal farDeliveryFee;
    
    /**
    *删除标识
    */
    private String isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStatementBillNo() {
        return statementBillNo;
    }

    public void setStatementBillNo(String statementBillNo) {
        this.statementBillNo = statementBillNo == null ? null : statementBillNo.trim();
    }
    
    public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo == null ? null : waybillNo.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getVerifyAmount() {
        return verifyAmount;
    }

    public void setVerifyAmount(BigDecimal verifyAmount) {
        this.verifyAmount = verifyAmount;
    }

    public BigDecimal getUnverifyAmount() {
        return unverifyAmount;
    }

    public void setUnverifyAmount(BigDecimal unverifyAmount) {
        this.unverifyAmount = unverifyAmount;
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

    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode == null ? null : deptRegionCode.trim();
    }

    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode == null ? null : arrvRegionCode.trim();
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

    public String getDeliveryCustomerCode() {
        return deliveryCustomerCode;
    }

    public void setDeliveryCustomerCode(String deliveryCustomerCode) {
        this.deliveryCustomerCode = deliveryCustomerCode == null ? null : deliveryCustomerCode.trim();
    }

    public String getDeliveryCustomerName() {
        return deliveryCustomerName;
    }

    public void setDeliveryCustomerName(String deliveryCustomerName) {
        this.deliveryCustomerName = deliveryCustomerName == null ? null : deliveryCustomerName.trim();
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Date getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Date disableTime) {
        this.disableTime = disableTime;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Date getWaybillCreateDate() {
        return waybillCreateDate;
    }

    public void setWaybillCreateDate(Date waybillCreateDate) {
        this.waybillCreateDate = waybillCreateDate;
    }

    public BigDecimal getRegionalTransportFee() {
        return regionalTransportFee;
    }

    public void setRegionalTransportFee(BigDecimal regionalTransportFee) {
        this.regionalTransportFee = regionalTransportFee;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getDispatchFee() {
        return dispatchFee;
    }

    public void setDispatchFee(BigDecimal dispatchFee) {
        this.dispatchFee = dispatchFee;
    }

    public BigDecimal getDeliveryWarehouseFee() {
        return deliveryWarehouseFee;
    }

    public void setDeliveryWarehouseFee(BigDecimal deliveryWarehouseFee) {
        this.deliveryWarehouseFee = deliveryWarehouseFee;
    }

    public BigDecimal getSignReturnFee() {
        return signReturnFee;
    }

    public void setSignReturnFee(BigDecimal signReturnFee) {
        this.signReturnFee = signReturnFee;
    }

    public BigDecimal getDeliveryUpstairsFee() {
        return deliveryUpstairsFee;
    }

    public void setDeliveryUpstairsFee(BigDecimal deliveryUpstairsFee) {
        this.deliveryUpstairsFee = deliveryUpstairsFee;
    }

    public BigDecimal getBigUpstairsFee() {
        return bigUpstairsFee;
    }

    public void setBigUpstairsFee(BigDecimal bigUpstairsFee) {
        this.bigUpstairsFee = bigUpstairsFee;
    }

    public BigDecimal getFarDeliveryFee() {
        return farDeliveryFee;
    }

    public void setFarDeliveryFee(BigDecimal farDeliveryFee) {
        this.farDeliveryFee = farDeliveryFee;
    }

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}