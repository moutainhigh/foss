package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 现金收款单 分区键记账日期 accountDate
 * 
 * @author ibm-pengzhen
 * @date 2012-10-10 下午12:33:30
 * @since
 * @version
 */
public class BillCashCollectionEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 1875430044035041728L;

	/**
	 * 现金收款单单号
	 */
	private String cashCollectionNo;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 运单ID
	 */
	private String waybillId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 录入部门编码
	 */
	private String createOrgCode;

	/**
	 * 录入部门名称
	 */
	private String createOrgName;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;

	/**
	 * 收款部门所属子公司编码
	 */
	private String collectionCompanyCode;

	/**
	 * 收款部门所属子公司名称
	 */
	private String collectionCompanyName;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	/**
	 * 收入部门所属子公司编码
	 */
	private String generatingCompanyCode;

	/**
	 * 收入部门所属子公司名称
	 */
	private String generatingCompanyName;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 产品类型
	 */
	private String productCode;

	/**
	 * 产品Id
	 */
	private String productId;

	/**
	 * 公布价运费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal transportFee;

	/**
	 * 接货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal pickupFee;

	/**
	 * 送货费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 包装手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal packagingFee;

	/**
	 * 代收货款手续费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal codFee;

	/**
	 * 保价费 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal insuranceFee;

	/**
	 * 其他费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal otherFee;

	/**
	 * 增值服务费
	 */
	private BigDecimal valueAddFee;

	/**
	 * 优惠费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal promotionsFee;

	/**
	 * 小票
	 */
	private BigDecimal smallFee;
	
	/**
	 * 确定收入日期
	 */
	private Date conrevenDate;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 制单人工号
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 审核人工号
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 修改人工号
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

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
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 收款类别
	 */
	private String collectionType;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 出发部门名称
	 */
	private String origOrgName;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;
	
	/**
	 * 银联交易流水号
	 */
	private String batchNo;


	/**
	 * 出发部门映射快递代理点部编码
	 */
	private String expressOrigOrgCode;
	
	/**
	 * 出发部门映射快递代理点部名称
	 */
	private String expressOrigOrgName;
	
	/**
	 * 到达部门映射快递代理点部编码
	 */
	private String expressDestOrgCode;
	
	/**
	 * 到达部门映射快递代理点部名称
	 */
	private String expressDestOrgName;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;
	
	/**
	 * @return cashCollectionNo
	 */
	public String getCashCollectionNo() {
		return cashCollectionNo;
	}

	/**
	 * @param cashCollectionNo
	 */
	public void setCashCollectionNo(String cashCollectionNo) {
		this.cashCollectionNo = cashCollectionNo;
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
	 * @return waybillId
	 */
	public String getWaybillId() {
		return waybillId;
	}

	/**
	 * @param waybillId
	 */
	public void setWaybillId(String waybillId) {
		this.waybillId = waybillId;
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
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
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
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	/**
	 * @return collectionCompanyCode
	 */
	public String getCollectionCompanyCode() {
		return collectionCompanyCode;
	}

	/**
	 * @param collectionCompanyCode
	 */
	public void setCollectionCompanyCode(String collectionCompanyCode) {
		this.collectionCompanyCode = collectionCompanyCode;
	}

	/**
	 * @return collectionCompanyName
	 */
	public String getCollectionCompanyName() {
		return collectionCompanyName;
	}

	/**
	 * @param collectionCompanyName
	 */
	public void setCollectionCompanyName(String collectionCompanyName) {
		this.collectionCompanyName = collectionCompanyName;
	}

	/**
	 * @return generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return generatingCompanyCode
	 */
	public String getGeneratingCompanyCode() {
		return generatingCompanyCode;
	}

	/**
	 * @param generatingCompanyCode
	 */
	public void setGeneratingCompanyCode(String generatingCompanyCode) {
		this.generatingCompanyCode = generatingCompanyCode;
	}

	/**
	 * @return generatingCompanyName
	 */
	public String getGeneratingCompanyName() {
		return generatingCompanyName;
	}

	/**
	 * @param generatingCompanyName
	 */
	public void setGeneratingCompanyName(String generatingCompanyName) {
		this.generatingCompanyName = generatingCompanyName;
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
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	 * @return productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return deliveryGoodsFee
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * @param deliveryGoodsFee
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
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
	 * @return the smallFee
	 */
	public BigDecimal getSmallFee() {
		return smallFee;
	}

	/**
	 * @param smallFee the smallFee to set
	 */
	public void setSmallFee(BigDecimal smallFee) {
		this.smallFee = smallFee;
	}

	/**
	 * @return conrevenDate
	 */
	public Date getConrevenDate() {
		return conrevenDate;
	}

	/**
	 * @param conrevenDate
	 */
	public void setConrevenDate(Date conrevenDate) {
		this.conrevenDate = conrevenDate;
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
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
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
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	/**
	 * @return cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
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
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	/**
	 * @return cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
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
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return collectionType
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
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
	 * @GET
	 * @return posSerialNum
	 */
	public String getPosSerialNum() {
		/*
		 *@get
		 *@ return posSerialNum
		 */
		return posSerialNum;
	}

	/**
	 * @SET
	 * @param posSerialNum
	 */
	public void setPosSerialNum(String posSerialNum) {
		/*
		 *@set
		 *@this.posSerialNum = posSerialNum
		 */
		this.posSerialNum = posSerialNum;
	}

	/**
	 * @GET
	 * @return batchNo
	 */
	public String getBatchNo() {
		/*
		 *@get
		 *@ return batchNo
		 */
		return batchNo;
	}

	/**
	 * @SET
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		/*
		 *@set
		 *@this.batchNo = batchNo
		 */
		this.batchNo = batchNo;
	}

	/**
	 * @GET
	 * @return expressOrigOrgCode
	 */
	public String getExpressOrigOrgCode() {
		/*
		 *@get
		 *@ return expressOrigOrgCode
		 */
		return expressOrigOrgCode;
	}

	/**
	 * @SET
	 * @param expressOrigOrgCode
	 */
	public void setExpressOrigOrgCode(String expressOrigOrgCode) {
		/*
		 *@set
		 *@this.expressOrigOrgCode = expressOrigOrgCode
		 */
		this.expressOrigOrgCode = expressOrigOrgCode;
	}

	/**
	 * @GET
	 * @return expressOrigOrgName
	 */
	public String getExpressOrigOrgName() {
		/*
		 *@get
		 *@ return expressOrigOrgName
		 */
		return expressOrigOrgName;
	}

	/**
	 * @SET
	 * @param expressOrigOrgName
	 */
	public void setExpressOrigOrgName(String expressOrigOrgName) {
		/*
		 *@set
		 *@this.expressOrigOrgName = expressOrigOrgName
		 */
		this.expressOrigOrgName = expressOrigOrgName;
	}

	/**
	 * @GET
	 * @return expressDestOrgCode
	 */
	public String getExpressDestOrgCode() {
		/*
		 *@get
		 *@ return expressDestOrgCode
		 */
		return expressDestOrgCode;
	}

	/**
	 * @SET
	 * @param expressDestOrgCode
	 */
	public void setExpressDestOrgCode(String expressDestOrgCode) {
		/*
		 *@set
		 *@this.expressDestOrgCode = expressDestOrgCode
		 */
		this.expressDestOrgCode = expressDestOrgCode;
	}

	/**
	 * @GET
	 * @return expressDestOrgName
	 */
	public String getExpressDestOrgName() {
		/*
		 *@get
		 *@ return expressDestOrgName
		 */
		return expressDestOrgName;
	}

	/**
	 * @SET
	 * @param expressDestOrgName
	 */
	public void setExpressDestOrgName(String expressDestOrgName) {
		/*
		 *@set
		 *@this.expressDestOrgName = expressDestOrgName
		 */
		this.expressDestOrgName = expressDestOrgName;
	}
	
	/**
	 * @return the invoiceMark
	 */
	public String getInvoiceMark() {
		return invoiceMark;
	}

	/**
	 * @param invoiceMark the invoiceMark to set
	 */
	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

}
