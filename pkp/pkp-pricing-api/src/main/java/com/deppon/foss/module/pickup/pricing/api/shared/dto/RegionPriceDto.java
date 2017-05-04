package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RegionPriceDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8941016417419638873L;
    /**
     * 费率（小数）或者单价（分）
     */
     
    private BigDecimal feeRate;
    
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
    /**
     * 费用类别
     */
    private String caculateType;
    /**
     * 计价条目
     */
    private String pricingValuationId;
	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
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

	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
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

	public String getPricingValuationId() {
		return pricingValuationId;
	}

	public void setPricingValuationId(String pricingValuationId) {
		this.pricingValuationId = pricingValuationId;
	}
    
}
