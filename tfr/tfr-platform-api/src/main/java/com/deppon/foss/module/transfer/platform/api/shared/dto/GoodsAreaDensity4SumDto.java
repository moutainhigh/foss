package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsAreaDensity4SumDto implements Serializable{

	private static final long serialVersionUID = -4294455515298108092L;

	/**
	 * 外场所属本部名称
	 */
	private String headquartersName;

	/**
	 * 外场所属事业部名称
	 */
	private String businessUnitName;
	
	/**
	 * 外场编码
	 */
	private String transferCenterCode;
	
	/**
	 * 外场名称
	 */
	private String transferCenterName;
	
	/**
	 * 外场日总货量
	 */
	private BigDecimal sumGoodsVolume4Daily;
	 
	/**
	 * 外场日货区总容量
	 */
	private BigDecimal sumGoodsAreaVolume4Daily;
	
	/**
	 * 日平均货区密度；外场各整点货区密度和的平均值
	 */
	private BigDecimal avgDensity4Daily;
	
	/**
	 * 外场月累计总货量；截至至查询日期
	 * 如查询条件中日期为2013-03-13，则此值为2013-03-01~2013-03-13外场各日货量和
	 */
	private BigDecimal sumGoodsVolume4Monthly;
	
	/**
	 * 外场月累计货区总容量；截至至查询日期
	 */
	private BigDecimal sumGoodsAreaVolume4Monthly;
	
	/**
	 * 外场月平均货区密度；截至至查询日期
	 */
	private BigDecimal avgDensity4Monthly;

	public String getHeadquartersName() {
		return headquartersName;
	}

	public void setHeadquartersName(String headquartersName) {
		this.headquartersName = headquartersName;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public BigDecimal getSumGoodsVolume4Daily() {
		return sumGoodsVolume4Daily;
	}

	public void setSumGoodsVolume4Daily(BigDecimal sumGoodsVolume4Daily) {
		this.sumGoodsVolume4Daily = sumGoodsVolume4Daily;
	}

	public BigDecimal getSumGoodsAreaVolume4Daily() {
		return sumGoodsAreaVolume4Daily;
	}

	public void setSumGoodsAreaVolume4Daily(BigDecimal sumGoodsAreaVolume4Daily) {
		this.sumGoodsAreaVolume4Daily = sumGoodsAreaVolume4Daily;
	}

	public BigDecimal getAvgDensity4Daily() {
		return avgDensity4Daily;
	}

	public void setAvgDensity4Daily(BigDecimal avgDensity4Daily) {
		this.avgDensity4Daily = avgDensity4Daily;
	}

	public BigDecimal getSumGoodsVolume4Monthly() {
		return sumGoodsVolume4Monthly;
	}

	public void setSumGoodsVolume4Monthly(BigDecimal sumGoodsVolume4Monthly) {
		this.sumGoodsVolume4Monthly = sumGoodsVolume4Monthly;
	}

	public BigDecimal getSumGoodsAreaVolume4Monthly() {
		return sumGoodsAreaVolume4Monthly;
	}

	public void setSumGoodsAreaVolume4Monthly(BigDecimal sumGoodsAreaVolume4Monthly) {
		this.sumGoodsAreaVolume4Monthly = sumGoodsAreaVolume4Monthly;
	}

	public BigDecimal getAvgDensity4Monthly() {
		return avgDensity4Monthly;
	}

	public void setAvgDensity4Monthly(BigDecimal avgDensity4Monthly) {
		this.avgDensity4Monthly = avgDensity4Monthly;
	}

	@Override
	public String toString() {
		return "GoodsAreaDensity4SumDto [headquartersName=" + headquartersName
				+ ", businessUnitName=" + businessUnitName
				+ ", transferCenterCode=" + transferCenterCode
				+ ", transferCenterName=" + transferCenterName
				+ ", sumGoodsVolume4Daily=" + sumGoodsVolume4Daily
				+ ", sumGoodsAreaVolume4Daily=" + sumGoodsAreaVolume4Daily
				+ ", avgDensity4Daily=" + avgDensity4Daily
				+ ", sumGoodsVolume4Monthly=" + sumGoodsVolume4Monthly
				+ ", sumGoodsAreaVolume4Monthly=" + sumGoodsAreaVolume4Monthly
				+ ", avgDensity4Monthly=" + avgDensity4Monthly + "]";
	}
}
