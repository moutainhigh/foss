package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;

public class ComponentDto {
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
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 代收货款手续费
	 */
	private BigDecimal codFee;
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	/**
	 * 运单总票数
	 */
	private Integer countWaybill;
	/**
	 * 运单费用总和
	 */
	private BigDecimal total_amount;
	
	
	
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal totalAmount) {
		this.total_amount = totalAmount;
	}
	public Integer getCountWaybill() {
		return countWaybill;
	}
	public void setCountWaybill(Integer countWaybill) {
		this.countWaybill = countWaybill;
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
	public BigDecimal getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
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
	
}
