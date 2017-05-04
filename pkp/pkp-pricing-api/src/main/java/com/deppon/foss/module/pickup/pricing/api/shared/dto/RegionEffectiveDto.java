package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

public class RegionEffectiveDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3951301662140301144L;
	/**
	 * 产品类型id
	 */
	private String productId;
	/**
	 * 产品类型code
	 */
	private String productCode;
	/**
	 * 长短途
	 */
	private String longOrShort;
	/**
	 * 省份name
	 */
	private String provinceName;
	/**
	 * 城市name
	 */
	private String cityName;
	/**
	 * 区县name
	 */
	private String countyName;
	/**
	 * 省份code
	 */
	private String provinceCode;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 区县code
	 */
	private String countyCode;
	
	/**
     * 承诺最长时间
     */
    private Integer maxTime;

    /**
     * 承诺最长时间单位
     */
    private String maxTimeUnit;

    /**
     * 承诺最短时间
     */
    private Integer minTime;

    /**
     *  承诺最短时间单位
     */
    private String minTimeUnit;
    /**
     * 承诺到达营业部时间
     */
    private String arriveTime;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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
	public Integer getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}
	public String getMaxTimeUnit() {
		return maxTimeUnit;
	}
	public void setMaxTimeUnit(String maxTimeUnit) {
		this.maxTimeUnit = maxTimeUnit;
	}
	public Integer getMinTime() {
		return minTime;
	}
	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}
	public String getMinTimeUnit() {
		return minTimeUnit;
	}
	public void setMinTimeUnit(String minTimeUnit) {
		this.minTimeUnit = minTimeUnit;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLongOrShort() {
		return longOrShort;
	}
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
