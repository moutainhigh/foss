package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class PriceTableEntity extends BaseEntity {

	private static final long serialVersionUID = 3641885880309734417L;

	/**
	 * 产品code
	 */
	private String productCode;
	/**
	 * 产品ID
	 */
	private String productID;	
	/**
	 * 产品name
	 */
	private String productName;
	/**
	 * 目的省份
	 */
	private String provinceName;
	/**
	 * 目的站
	 */
	private String destinationName;
	/**
	 * 预计运行时间
	 */
	private String runTime;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice;
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getRunTime() {
		return runTime;
	}
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	public BigDecimal getHeavyPrice() {
		return heavyPrice;
	}
	public void setHeavyPrice(BigDecimal heavyPrice) {
		this.heavyPrice = heavyPrice;
	}
	public BigDecimal getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(BigDecimal lightPrice) {
		this.lightPrice = lightPrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
