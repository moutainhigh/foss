package com.deppon.foss.module.settlement.dubbo.api.define;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 应收单实体，由于客户在我司发货或在运输过程中代理代我司收取费用而需要支付给我司的费用款项 分区键记账日期 accountDate
 * 
 * @author dp-wujiangtao
 * @date 2012-10-10 上午9:05:27
 * @since
 * @version
 */
public class BillReceivableEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7973820969391677856L;

	/**
	 * 应收单号
	 */
	private String receivableNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单ID
	 */
	private String waybillId;

	/**
	 * 系统生成方式
	 */
	private String createType;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 应收部门编码
	 */
	private String receivableOrgCode;

	/**
	 * 应收部门名称
	 */
	private String receivableOrgName;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	/**
	 * 收入子公司编码
	 */
	private String generatingComCode;

	/**
	 * 收入子公司名称
	 */
	private String generatingComName;

	/**
	 * 催款部门编码
	 */
	private String dunningOrgCode;

	/**
	 * 催款部门名称
	 */
	private String dunningOrgName;

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
	 * 客户编码/应收代理编码
	 */
	private String customerCode;

	/**
	 * 客户名称/应收代理名称
	 */
	private String customerName;

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
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 确认收入日期
	 */
	private Date conrevenDate;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 运输名称
	 */
	private String productName;
	
	/**
	 * 产品ID
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
	 * 增值费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal valueAddFee;

	/**
	 * 优惠费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal promotionsFee;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 货物总件数
	 */
	private BigDecimal goodsQtyTotal;

	/**
	 * 目的站
	 */
	private String targetOrgCode;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 是否初始化
	 */
	private String isInit;
	
	/**
	 * 是否标记
	 */
	private String stamp;

	/**
	 * 制单人编码
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 制单部门编码
	 */
	private String createOrgCode;

	/**
	 * 制单部门名称
	 */
	private String createOrgName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 解锁时间
	 */
	private Date unlockDateTime;

	/**
	 * 锁定客户编码
	 */
	private String lockCustomerCode;

	/**
	 * 锁定客户名称
	 */
	private String lockCustomerName;

	/**
	 * 收款类别
	 */
	private String collectionType;
	
	
	/**
	 * 收款名称
	 */
	private String collectionName;


	/**
	 * 审核人编码
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 审批状态
	 */
	private String approveStatus;

	
	
	
	/**
	 * 作废人编码
	 */	
	private String disableUserCode; 
	
	
	/**
	 * 作废人名称
	 */	
	private String disableUserName;
	
	/**
	 * 审核日期
	 */
	private Date auditDate;

	/**
	 * 备注
	 */
	private String notes;

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
	 * 出发部门映射快递点部编码
	 */
	private String expressOrigOrgCode;
	
	/**
	 * 出发部门映射快递点部名称
	 */
	private String expressOrigOrgName;
	
	/**
	 * 到达部门映射快递点部编码
	 */
	private String expressDestOrgCode;
	
	/**
	 * 到达部门映射快递点部名称
	 */
	private String expressDestOrgName;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;

    /**
     *
     * 是否统一结算
     */
	private String unifiedSettlement;
    /**
     * 合同部门编码
     */
    private String contractOrgCode;
    /**
     * 合同部门名称
     */
    private String contractOrgName;
    
    /**
    *
    * 是否折扣
    */
	private String isDiscount;
	/**
    * 扣款状态
    */
	private String withholdStatus;

    public String getUnifiedSettlement() {
        return unifiedSettlement;
    }

    public void setUnifiedSettlement(String unifiedSettlement) {
        this.unifiedSettlement = unifiedSettlement;
    }

    public String getContractOrgCode() {
        return contractOrgCode;
    }

    public void setContractOrgCode(String contractOrgCode) {
        this.contractOrgCode = contractOrgCode;
    }

    public String getContractOrgName() {
        return contractOrgName;
    }

    public void setContractOrgName(String contractOrgName) {
        this.contractOrgName = contractOrgName;
    }

    public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
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

	/**
	 * @return receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}

	/**
	 * @param receivableNo
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
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
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
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
	 * @return receivableOrgCode
	 */
	public String getReceivableOrgCode() {
		return receivableOrgCode;
	}

	/**
	 * @param receivableOrgCode
	 */
	public void setReceivableOrgCode(String receivableOrgCode) {
		this.receivableOrgCode = receivableOrgCode;
	}

	/**
	 * @return receivableOrgName
	 */
	public String getReceivableOrgName() {
		return receivableOrgName;
	}

	/**
	 * @param receivableOrgName
	 */
	public void setReceivableOrgName(String receivableOrgName) {
		this.receivableOrgName = receivableOrgName;
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
	 * @return generatingComCode
	 */
	public String getGeneratingComCode() {
		return generatingComCode;
	}

	/**
	 * @param generatingComCode
	 */
	public void setGeneratingComCode(String generatingComCode) {
		this.generatingComCode = generatingComCode;
	}

	/**
	 * @return generatingComName
	 */
	public String getGeneratingComName() {
		return generatingComName;
	}

	/**
	 * @param generatingComName
	 */
	public void setGeneratingComName(String generatingComName) {
		this.generatingComName = generatingComName;
	}

	/**
	 * @return dunningOrgCode
	 */
	public String getDunningOrgCode() {
		return dunningOrgCode;
	}

	/**
	 * @param dunningOrgCode
	 */
	public void setDunningOrgCode(String dunningOrgCode) {
		this.dunningOrgCode = dunningOrgCode;
	}

	/**
	 * @return dunningOrgName
	 */
	public String getDunningOrgName() {
		return dunningOrgName;
	}

	/**
	 * @param dunningOrgName
	 */
	public void setDunningOrgName(String dunningOrgName) {
		this.dunningOrgName = dunningOrgName;
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
	 * @return goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
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
	 * @return customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return goodsQtyTotal
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
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
	 * @return unlockDateTime
	 */
	public Date getUnlockDateTime() {
		return unlockDateTime;
	}

	/**
	 * @param unlockDateTime
	 */
	public void setUnlockDateTime(Date unlockDateTime) {
		this.unlockDateTime = unlockDateTime;
	}

	/**
	 * @return lockCustomerCode
	 */
	public String getLockCustomerCode() {
		return lockCustomerCode;
	}

	/**
	 * @param lockCustomerCode
	 */
	public void setLockCustomerCode(String lockCustomerCode) {
		this.lockCustomerCode = lockCustomerCode;
	}

	/**
	 * @return lockCustomerName
	 */
	public String getLockCustomerName() {
		return lockCustomerName;
	}

	/**
	 * @param lockCustomerName
	 */
	public void setLockCustomerName(String lockCustomerName) {
		this.lockCustomerName = lockCustomerName;
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
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * @return auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @param auditDate
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getDisableUserCode() {
		return disableUserCode;
	}

	public void setDisableUserCode(String disableUserCode) {
		this.disableUserCode = disableUserCode;
	}

	public String getDisableUserName() {
		return disableUserName;
	}

	public void setDisableUserName(String disableUserName) {
		this.disableUserName = disableUserName;
	}

	/**
	 * @return  the stamp
	 */
	public String getStamp() {
		return stamp;
	}

	
	/**
	 * @param stamp the stamp to set
	 */
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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

	public String getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}

	public String getWithholdStatus() {
		return withholdStatus;
	}

	public void setWithholdStatus(String withholdStatus) {
		this.withholdStatus = withholdStatus;
	}
	
}
