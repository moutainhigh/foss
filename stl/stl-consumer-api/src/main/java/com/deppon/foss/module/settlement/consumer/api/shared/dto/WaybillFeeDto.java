package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运单费用DTO
 * 
 * @author ibm-zhuwei
 * @date 2012-11-2 下午4:59:05
 */
public class WaybillFeeDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1619225900874254215L;

	/**
	 * 总费用BigDecimal存储，单位（元）
	 */
	private BigDecimal totalFee = BigDecimal.ZERO;

	/**
	 * 公布价运费 BigDecimal存储，单位（元）
	 */
	private BigDecimal transportFee = BigDecimal.ZERO;

	/**
	 * 接货费 BigDecimal存储，单位（元）
	 */
	private BigDecimal pickupFee = BigDecimal.ZERO;

	/**
	 * 送货费 BigDecimal存储，单位（元）
	 */
	private BigDecimal deliveryGoodsFee = BigDecimal.ZERO;

	/**
	 * 包装手续费 BigDecimal存储，单位（元）
	 */
	private BigDecimal packagingFee = BigDecimal.ZERO;

	/**
	 * 代收货款手续费 BigDecimal存储，单位（元）
	 */
	private BigDecimal codFee = BigDecimal.ZERO;

	/**
	 * 保价费 BigDecimal存储，单位（元）
	 */
	private BigDecimal insuranceFee = BigDecimal.ZERO;

	/**
	 * 其他费用 BigDecimal存储，单位（元）
	 */
	private BigDecimal otherFee = BigDecimal.ZERO;

	/**
	 * 增值费用 BigDecimal存储，单位（元）
	 */
	private BigDecimal valueAddFee = BigDecimal.ZERO;

	/**
	 * 优惠费用 BigDecimal存储，单位（元）
	 */
	private BigDecimal promotionsFee = BigDecimal.ZERO;

	/**
	 * @return totalFee
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
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

}
