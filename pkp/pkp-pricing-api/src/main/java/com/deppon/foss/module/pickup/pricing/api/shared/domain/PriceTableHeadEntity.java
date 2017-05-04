package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class PriceTableHeadEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6999023523104219039L;

	/**
	 * 有效时间
	 */
	Date currentDateTime;
	/**
	 * 价格表名称
	 */
	String name;
	/**
	 * 
	 */
	String cityName;
	/**
	 * 价格表类型
	 */
	String productType;
	public Date getCurrentDateTime() {
		return currentDateTime;
	}
	public void setCurrentDateTime(Date currentDateTime) {
		this.currentDateTime = currentDateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
