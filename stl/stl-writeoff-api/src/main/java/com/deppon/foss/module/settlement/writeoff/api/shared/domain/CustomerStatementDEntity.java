/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class CustomerStatementDEntity extends BaseEntity {
	 
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
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	
	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact;
	
	/**
	 * 统一结算
	 */
	private String unifiedSettlement;
	
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return deptRegionCode
	 */
	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	/**
	 * @param deptRegionCode
	 */
	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	/**
	 * @return arrvRegionCode
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * @param arrvRegionCode
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	/**
	 * @return customerPickupOrgName
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * @param customerPickupOrgName
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * @return qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return billingVolume
	 */
	public BigDecimal getBillingVolume() {
		return billingVolume;
	}

	/**
	 * @param billingVolume
	 */
	public void setBillingVolume(BigDecimal billingVolume) {
		this.billingVolume = billingVolume;
	}

	/**
	 * @return billWeight
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * @param billWeight
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * @return goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return deliveryCustomerCode
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * @param deliveryCustomerCode
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * @return deliveryCustomerName
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return insuranceFee
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * @return transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * @param transportFee
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * @return packagingFee
	 */
	public BigDecimal getPackagingFee() {
		return packagingFee;
	}

	/**
	 * @param packagingFee
	 */
	public void setPackagingFee(BigDecimal packagingFee) {
		this.packagingFee = packagingFee;
	}

	/**
	 * @return deliverFee
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	/**
	 * @param deliverFee
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	/**
	 * @return pickupFee
	 */
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	/**
	 * @param pickupFee
	 */
	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	/**
	 * @return codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return otherFee
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * @param otherFee
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * @return valueAddFee
	 */
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * @param valueAddFee
	 */
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	/**
	 * @return promotionsFee
	 */
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * @param promotionsFee
	 */
	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	/**
	 * @return isDelete
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return disableTime
	 */
	public Date getDisableTime() {
		return disableTime;
	}

	/**
	 * @param disableTime
	 */
	public void setDisableTime(Date disableTime) {
		this.disableTime = disableTime;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return billParentType
	 */
	public String getBillParentType() {
		return billParentType;
	}

	/**
	 * @param billParentType
	 */
	public void setBillParentType(String billParentType) {
		this.billParentType = billParentType;
	}

	/**
	 * @return origSourceBillNo
	 */
	public String getOrigSourceBillNo() {
		return origSourceBillNo;
	}

	/**
	 * @param origSourceBillNo
	 */
	public void setOrigSourceBillNo(String origSourceBillNo) {
		this.origSourceBillNo = origSourceBillNo;
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

	public String getUnifiedSettlement() {
		return unifiedSettlement;
	}

	public void setUnifiedSettlement(String unifiedSettlement) {
		this.unifiedSettlement = unifiedSettlement;
	}
}
