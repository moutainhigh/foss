package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WaybillOneYearDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 开单时间
	 */
	private Date createTime;
	
    /**
     * 开单组织
     */
	private String createOrgCode;
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 开单付款方式
	 */
	private String paidMethod;

	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 接货费
	 */
	private BigDecimal pickupFee;
	
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;

	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	
	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;
	
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	
	
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;

	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	
	/**
	 * 发货市名称
	 */
	private String deliveryCustomerCityName;

	/**
	 * 收货市名称
	 */
	private String receiveCustomerCityName;
	
	/**
	 * 是否有效
	 */
	private String active;
	
    /**
     * 退款类型
     */
	private String refundType;

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}


	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
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

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getCodFee() {
		return codFee;
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	public BigDecimal getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeliveryCustomerCityName() {
		return deliveryCustomerCityName;
	}

	public void setDeliveryCustomerCityName(String deliveryCustomerCityName) {
		this.deliveryCustomerCityName = deliveryCustomerCityName;
	}

	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}

	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	

}
