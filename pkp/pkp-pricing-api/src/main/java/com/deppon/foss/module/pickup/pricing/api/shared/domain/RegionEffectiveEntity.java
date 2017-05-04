package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class RegionEffectiveEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1927203409508823490L;
	/**
	 * 产品类型id
	 */
	private String productId;
	/**
	 * 产品类型id
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
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 区县code
	 */
	private String countyCode;
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