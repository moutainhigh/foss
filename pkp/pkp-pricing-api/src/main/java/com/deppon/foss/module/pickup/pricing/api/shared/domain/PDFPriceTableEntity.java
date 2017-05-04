package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class PDFPriceTableEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6890854503134181686L;
	/**
	 * 产品name
	 */
	private String productName;
	/**
	 * 目的省份
	 */
	private String provinceName1;
	/**
	 * 目的站
	 */
	private String destinationName1;
	/**
	 * 预计运行时间
	 */
	private String runTime1;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice1;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice1;
	
	/**
	 * 目的省份
	 */
	private String provinceName2;
	/**
	 * 目的站
	 */
	private String destinationName2;
	/**
	 * 预计运行时间
	 */
	private String runTime2;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice2;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice2;
	/**
	 * 目的省份
	 */
	private String provinceName3;
	/**
	 * 目的站
	 */
	private String destinationName3;
	/**
	 * 预计运行时间
	 */
	private String runTime3;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice3;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice3;
	/**
	 * 目的省份
	 */
	private String provinceName4;
	/**
	 * 目的站
	 */
	private String destinationName4;
	/**
	 * 预计运行时间
	 */
	private String runTime4;
	/**
	 * 重货价格
	 */
	private BigDecimal heavyPrice4;
	/**
	 * 轻货价格
	 */
	private BigDecimal lightPrice4;
	
	
	public PDFPriceTableEntity(String productName, String provinceName1,
			String destinationName1, String runTime1, BigDecimal heavyPrice1,
			BigDecimal lightPrice1, String provinceName2,
			String destinationName2, String runTime2, BigDecimal heavyPrice2,
			BigDecimal lightPrice2, String provinceName3,
			String destinationName3, String runTime3, BigDecimal heavyPrice3,
			BigDecimal lightPrice3, String provinceName4,
			String destinationName4, String runTime4, BigDecimal heavyPrice4,
			BigDecimal lightPrice4) {
		super();
		this.productName = productName;
		this.provinceName1 = provinceName1;
		this.destinationName1 = destinationName1;
		this.runTime1 = runTime1;
		this.heavyPrice1 = heavyPrice1;
		this.lightPrice1 = lightPrice1;
		this.provinceName2 = provinceName2;
		this.destinationName2 = destinationName2;
		this.runTime2 = runTime2;
		this.heavyPrice2 = heavyPrice2;
		this.lightPrice2 = lightPrice2;
		this.provinceName3 = provinceName3;
		this.destinationName3 = destinationName3;
		this.runTime3 = runTime3;
		this.heavyPrice3 = heavyPrice3;
		this.lightPrice3 = lightPrice3;
		this.provinceName4 = provinceName4;
		this.destinationName4 = destinationName4;
		this.runTime4 = runTime4;
		this.heavyPrice4 = heavyPrice4;
		this.lightPrice4 = lightPrice4;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProvinceName1() {
		return provinceName1;
	}
	public void setProvinceName1(String provinceName1) {
		this.provinceName1 = provinceName1;
	}
	public String getDestinationName1() {
		return destinationName1;
	}
	public void setDestinationName1(String destinationName1) {
		this.destinationName1 = destinationName1;
	}
	public String getRunTime1() {
		return runTime1;
	}
	public void setRunTime1(String runTime1) {
		this.runTime1 = runTime1;
	}
	public BigDecimal getHeavyPrice1() {
		return heavyPrice1;
	}
	public void setHeavyPrice1(BigDecimal heavyPrice1) {
		this.heavyPrice1 = heavyPrice1;
	}
	public BigDecimal getLightPrice1() {
		return lightPrice1;
	}
	public void setLightPrice1(BigDecimal lightPrice1) {
		this.lightPrice1 = lightPrice1;
	}
	public String getProvinceName2() {
		return provinceName2;
	}
	public void setProvinceName2(String provinceName2) {
		this.provinceName2 = provinceName2;
	}
	public String getDestinationName2() {
		return destinationName2;
	}
	public void setDestinationName2(String destinationName2) {
		this.destinationName2 = destinationName2;
	}
	public String getRunTime2() {
		return runTime2;
	}
	public void setRunTime2(String runTime2) {
		this.runTime2 = runTime2;
	}
	public BigDecimal getHeavyPrice2() {
		return heavyPrice2;
	}
	public void setHeavyPrice2(BigDecimal heavyPrice2) {
		this.heavyPrice2 = heavyPrice2;
	}
	public BigDecimal getLightPrice2() {
		return lightPrice2;
	}
	public void setLightPrice2(BigDecimal lightPrice2) {
		this.lightPrice2 = lightPrice2;
	}
	public String getProvinceName3() {
		return provinceName3;
	}
	public void setProvinceName3(String provinceName3) {
		this.provinceName3 = provinceName3;
	}
	public String getDestinationName3() {
		return destinationName3;
	}
	public void setDestinationName3(String destinationName3) {
		this.destinationName3 = destinationName3;
	}
	public String getRunTime3() {
		return runTime3;
	}
	public void setRunTime3(String runTime3) {
		this.runTime3 = runTime3;
	}
	public BigDecimal getHeavyPrice3() {
		return heavyPrice3;
	}
	public void setHeavyPrice3(BigDecimal heavyPrice3) {
		this.heavyPrice3 = heavyPrice3;
	}
	public BigDecimal getLightPrice3() {
		return lightPrice3;
	}
	public void setLightPrice3(BigDecimal lightPrice3) {
		this.lightPrice3 = lightPrice3;
	}
	public String getProvinceName4() {
		return provinceName4;
	}
	public void setProvinceName4(String provinceName4) {
		this.provinceName4 = provinceName4;
	}
	public String getDestinationName4() {
		return destinationName4;
	}
	public void setDestinationName4(String destinationName4) {
		this.destinationName4 = destinationName4;
	}
	public String getRunTime4() {
		return runTime4;
	}
	public void setRunTime4(String runTime4) {
		this.runTime4 = runTime4;
	}
	public BigDecimal getHeavyPrice4() {
		return heavyPrice4;
	}
	public void setHeavyPrice4(BigDecimal heavyPrice4) {
		this.heavyPrice4 = heavyPrice4;
	}
	public BigDecimal getLightPrice4() {
		return lightPrice4;
	}
	public void setLightPrice4(BigDecimal lightPrice4) {
		this.lightPrice4 = lightPrice4;
	}
	
}
