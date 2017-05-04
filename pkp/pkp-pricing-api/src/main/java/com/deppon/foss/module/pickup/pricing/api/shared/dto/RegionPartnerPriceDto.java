package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RegionPartnerPriceDto  implements Serializable{
	
	private static final long serialVersionUID = -8941016417419638873L;
   
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品code
	 */
	private String productCode;
	/**
     * 计价条目
     */
    private String pricingValuationId;
    /**
     * 到达区域ID
     */
    private String arrvRegionId;
    
    /**
     * 是否接货
     */
    private String centralizePickup;
    
    /**
     * 网点编码
     */
    private String orgCode;
    /**
     * 网点名称
     */
    private String orgName;
    /**
	 * 城市code
	 */
	private String cityCode;
	
	private String cityName;
	/**
	 * 区县code
	 */
	private String countyCode;
	
	private String countyName;
	
	
	private String provName;
	 /**
     * 费率（小数）或者单价（分）
     */
    private BigDecimal feeRate;
    
    /**
     * 费用类别
     */
    private String caculateType;
    
    private String arrType;
    
    private String rk;
    /**
     * 轻货费率（小数）或者单价（分）
     */
    private BigDecimal lightFeeRate;
    /**
     * 重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRate;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPricingValuationId() {
		return pricingValuationId;
	}

	public void setPricingValuationId(String pricingValuationId) {
		this.pricingValuationId = pricingValuationId;
	}

	public String getArrvRegionId() {
		return arrvRegionId;
	}

	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	public String getArrType() {
		return arrType;
	}

	public void setArrType(String arrType) {
		this.arrType = arrType;
	}

	public String getRk() {
		return rk;
	}

	public void setRk(String rk) {
		this.rk = rk;
	}

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}
    
    
	
}
