package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.crm.module.client.sync.domain.dto.BaseEntity;


/**
 * @ClassName: TrunkDiscountManEntity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:51:27
 */
public class TrunkDiscountManEntity extends BaseEntity {

	/**
	 * 收入部门
	 */
	private String generatingOrgName;
	/**
	 * 合同部门
	 */
	private String contractOrgName;
	/**
	 * 合同部门编码
	 */
	private String contractOrgCode;
	/**
	 * 发票标记
	 */
	private String invoiceMark;
	/**
	 * 是否统一结算
	 */
	private String unifiedSettlement;
	/**
	 * 折扣单类型
	 */
	private String discountType;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 应付单号
	 */
	private String payableNo;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户编码
	 */
	private String customerNo;
	/**
	 * 总金额
	 */
	private BigDecimal totalMoney;
	/**
	 * 折扣后总金额
	 */
	private BigDecimal discountMoney;
	/**
	 * 折扣率
	 */
	private BigDecimal transportRate;
	/**
	 * 原纯运费
	 */
	private BigDecimal pureFee;
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codAgencyFee;
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	/**
	 * 纯运费折扣金额
	 */
	private BigDecimal discountFee;
	/**
	 * 折扣后运费
	 */
	private BigDecimal pureDiscountFee;
	/**
	 * 已核销折扣金额
	 */
	private BigDecimal verifyDiscountFee;

	// ---------getter setter ------------
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public String getUnifiedSettlement() {
		return unifiedSettlement;
	}

	public void setUnifiedSettlement(String unifiedSettlement) {
		this.unifiedSettlement = unifiedSettlement;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

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

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getTransportRate() {
		return transportRate;
	}

	public void setTransportRate(BigDecimal transportRate) {
		this.transportRate = transportRate;
	}

	public BigDecimal getPureFee() {
		return pureFee;
	}

	public void setPureFee(BigDecimal pureFee) {
		this.pureFee = pureFee;
	}

	public BigDecimal getCodAgencyFee() {
		return codAgencyFee;
	}

	public void setCodAgencyFee(BigDecimal codAgencyFee) {
		this.codAgencyFee = codAgencyFee;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public BigDecimal getPureDiscountFee() {
		return pureDiscountFee;
	}

	public void setPureDiscountFee(BigDecimal pureDiscountFee) {
		this.pureDiscountFee = pureDiscountFee;
	}

	public BigDecimal getVerifyDiscountFee() {
		return verifyDiscountFee;
	}

	public void setVerifyDiscountFee(BigDecimal verifyDiscountFee) {
		this.verifyDiscountFee = verifyDiscountFee;
	}

	@Override
	public String toString() {
		return "TrunkDiscountManEntity [generatingOrgName=" + generatingOrgName
				+ ", contractOrgName=" + contractOrgName + ", contractOrgCode="
				+ contractOrgCode + ", invoiceMark=" + invoiceMark
				+ ", unifiedSettlement=" + unifiedSettlement
				+ ", discountType=" + discountType + ", productCode="
				+ productCode + ", waybillNo=" + waybillNo + ", payableNo="
				+ payableNo + ", customerName=" + customerName
				+ ", customerNo=" + customerNo + ", totalMoney=" + totalMoney
				+ ", discountMoney=" + discountMoney + ", transportRate="
				+ transportRate + ", pureFee=" + pureFee + ", codAgencyFee="
				+ codAgencyFee + ", insuranceFee=" + insuranceFee
				+ ", deliveryGoodsFee=" + deliveryGoodsFee + ", pickupFee="
				+ pickupFee + ", otherFee=" + otherFee + ", discountFee="
				+ discountFee + ", pureDiscountFee=" + pureDiscountFee
				+ ", verifyDiscountFee=" + verifyDiscountFee + "]";
	}

}
