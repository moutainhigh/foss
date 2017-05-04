package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class RegionPriceEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8941016417419638873L;
    /**
     * 轻货费率（小数）或者单价（分）
     */
    private BigDecimal lightFeeRate;
    /**
     * 重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRate;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 区县code
	 */
	private String countyCode;
	
	/**
	 * 产品code
	 */
	private String productCode;
	/**
	 * 产品ID
	 */
	private String productId;
	
	public BigDecimal getLightFeeRate() {
		return lightFeeRate;
	}
	public void setLightFeeRate(BigDecimal lightFeeRate) {
		this.lightFeeRate = lightFeeRate;
	}
	public BigDecimal getHeavyFeeRate() {
		return heavyFeeRate;
	}
	public void setHeavyFeeRate(BigDecimal heavyFeeRate) {
		this.heavyFeeRate = heavyFeeRate;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
    
}
