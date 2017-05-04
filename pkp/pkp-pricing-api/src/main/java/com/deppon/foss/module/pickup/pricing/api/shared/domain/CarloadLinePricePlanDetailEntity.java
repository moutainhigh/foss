package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 整车线路价格方案明细
 * @author hehaisu
 * @date 2015-1-20 下午3:15:10
 */
public class CarloadLinePricePlanDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 整车线路价格方案id
	 */
	private String carloadLinePriceId;
	/**
	 * 类别:按重量(WEIGHT)、按体积(VOLUMN)
	 */
	private String type;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 发票标记
	 * INVOICE_TYPE_01—运输专票11%
	 * INVOICE_TYPE_02—非运输专票6%
	 */
	private String invoiceType;
	/**
	 * 上限
	 */
	private BigDecimal upLimit;
	/**
	 * 下限
	 */
	private BigDecimal downLimit;
	/**
	 * 收费标准
	 */
	private BigDecimal chargeStandard;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getCarloadLinePriceId() {
		return carloadLinePriceId;
	}
	public void setCarloadLinePriceId(String carloadLinePriceId) {
		this.carloadLinePriceId = carloadLinePriceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public BigDecimal getUpLimit() {
		return upLimit;
	}
	public void setUpLimit(BigDecimal upLimit) {
		this.upLimit = upLimit;
	}
	public BigDecimal getDownLimit() {
		return downLimit;
	}
	public void setDownLimit(BigDecimal downLimit) {
		this.downLimit = downLimit;
	}
	public BigDecimal getChargeStandard() {
		return chargeStandard;
	}
	public void setChargeStandard(BigDecimal chargeStandard) {
		this.chargeStandard = chargeStandard;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
