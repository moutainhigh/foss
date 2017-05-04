package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class CarloadPriceDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String carloadPriceId; // 整车参数方案id
	private String active; // 有效状态（Y,N）
	private long serialNo; // 版本号
	private String invoiceType; // 发票标记
	private BigDecimal floatUp; // 向上浮动值
	private BigDecimal floatDown; // 向下浮动值
	private String remark; // 备注

	private BigDecimal otherCostParameter;// 其他成本参数

	private BigDecimal packageFeeParameter;// 包装费参数

	private BigDecimal weightParameter;// 重量参数

	private BigDecimal carCostParameter;// 车价参数

	private BigDecimal humanCostParameter;// 人力成本参数

	public String getCarloadPriceId() {
		return carloadPriceId;
	}

	public void setCarloadPriceId(String carloadPriceId) {
		this.carloadPriceId = carloadPriceId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getFloatUp() {
		return floatUp;
	}

	public void setFloatUp(BigDecimal floatUp) {
		this.floatUp = floatUp;
	}

	public BigDecimal getFloatDown() {
		return floatDown;
	}

	public void setFloatDown(BigDecimal floatDown) {
		this.floatDown = floatDown;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getOtherCostParameter() {
		return otherCostParameter;
	}

	public void setOtherCostParameter(BigDecimal otherCostParameter) {
		this.otherCostParameter = otherCostParameter;
	}

	public BigDecimal getPackageFeeParameter() {
		return packageFeeParameter;
	}

	public void setPackageFeeParameter(BigDecimal packageFeeParameter) {
		this.packageFeeParameter = packageFeeParameter;
	}

	public BigDecimal getWeightParameter() {
		return weightParameter;
	}

	public void setWeightParameter(BigDecimal weightParameter) {
		this.weightParameter = weightParameter;
	}

	public BigDecimal getCarCostParameter() {
		return carCostParameter;
	}

	public void setCarCostParameter(BigDecimal carCostParameter) {
		this.carCostParameter = carCostParameter;
	}

	public BigDecimal getHumanCostParameter() {
		return humanCostParameter;
	}

	public void setHumanCostParameter(BigDecimal humanCostParameter) {
		this.humanCostParameter = humanCostParameter;
	}

}
