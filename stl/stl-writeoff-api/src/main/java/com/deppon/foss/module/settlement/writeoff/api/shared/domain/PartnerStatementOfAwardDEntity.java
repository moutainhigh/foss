package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class PartnerStatementOfAwardDEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	/**
	 * 原始来源单号
	 */
	private String origSourceBillNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 始发网点编码
	 */
	private String origOrgCode;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 始发网点名称
	 */
	private String origOrgName;

	/**
	 * 到达网点编码
	 */
	private String destOrgCode;

	/**
	 * 到达网点名称
	 */
	private String destOrgName;

	/**
	 * 客户\代理编码
	 */
	private String customerCode;

	/**
	 * 客户\代理名称
	 */
	private String customerName;

	/**
	 * 始发地
	 */
	private String deptRegionCode;

	/**
	 * 目的站
	 */
	private String arrvRegionCode;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgName;

	/**
	 * 件数
	 */
	private Integer qty;

	/**
	 * 计费体积
	 */
	private BigDecimal billingVolume;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;

	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 保价费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal insuranceFee;

	/**
	 * 公布价运费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal transportFee;

	/**
	 * 包装手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal packagingFee;

	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;

	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;

	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;

	/**
	 * 增值费用
	 */
	private BigDecimal valueAddFee;

	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFee;

	/**
	 * 是否删除
	 */
	private String isDelete;

	/**
	 * 删除时间
	 */
	private Date disableTime;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 运单签收日期
	 */
	private Date signDate;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 单据父类型
	 */
	private String billParentType;
	
	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 运费计费费率
	 */
	private BigDecimal unitPrice;
	
	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getOrigSourceBillNo() {
		return origSourceBillNo;
	}

	public void setOrigSourceBillNo(String origSourceBillNo) {
		this.origSourceBillNo = origSourceBillNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public BigDecimal getPackagingFee() {
		return packagingFee;
	}

	public void setPackagingFee(BigDecimal packagingFee) {
		this.packagingFee = packagingFee;
	}

	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
		this.notes = notes;
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
		this.auditStatus = auditStatus;
	}

	public String getBillParentType() {
		return billParentType;
	}

	public void setBillParentType(String billParentType) {
		this.billParentType = billParentType;
	}

	public Short getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	
	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact;
	


}
