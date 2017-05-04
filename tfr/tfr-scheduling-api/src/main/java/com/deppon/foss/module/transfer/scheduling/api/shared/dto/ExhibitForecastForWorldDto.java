package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;



/**
 * 展会货查询 条件dto
 * @author 200978  xiaobingcheng
 * 2014-12-4
 */
public class ExhibitForecastForWorldDto {

	
	/**驻地外场编码*/
	private String transferCenterCode;
	/**驻地外场名称*/
	private String transferCenterName;
	/** 总票数*/
	private Integer totalWaybillQty;
	/**总重量*/
	private BigDecimal totalWeight;
	/**总体积*/
	private BigDecimal totalVolume;
	
	/**开单未交接信息*/
	private BigDecimal noBillWeight;
	private BigDecimal noBillVolume;
	private Integer noBillWaybillQty;
	
	/**库存信息*/
	private BigDecimal stockWeight;
	private BigDecimal stockVolume;
	private Integer stockWaybillQty;
	
	/**在途信息*/
	private BigDecimal runningWeight;
	private BigDecimal runningVolume;
	private Integer runningWaybillQty;
	
	/**库存占比*/
	private double stockRatio;

	
	
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

	public Integer getTotalWaybillQty() {
		return totalWaybillQty;
	}

	public void setTotalWaybillQty(Integer totalWaybillQty) {
		this.totalWaybillQty = totalWaybillQty;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	public BigDecimal getNoBillWeight() {
		return noBillWeight;
	}

	public void setNoBillWeight(BigDecimal noBillWeight) {
		this.noBillWeight = noBillWeight;
	}

	public BigDecimal getNoBillVolume() {
		return noBillVolume;
	}

	public void setNoBillVolume(BigDecimal noBillVolume) {
		this.noBillVolume = noBillVolume;
	}


	public BigDecimal getStockWeight() {
		return stockWeight;
	}

	public void setStockWeight(BigDecimal stockWeight) {
		this.stockWeight = stockWeight;
	}

	public BigDecimal getStockVolume() {
		return stockVolume;
	}

	public void setStockVolume(BigDecimal stockVolume) {
		this.stockVolume = stockVolume;
	}


	public BigDecimal getRunningWeight() {
		return runningWeight;
	}

	public void setRunningWeight(BigDecimal runningWeight) {
		this.runningWeight = runningWeight;
	}

	public BigDecimal getRunningVolume() {
		return runningVolume;
	}

	public void setRunningVolume(BigDecimal runningVolume) {
		this.runningVolume = runningVolume;
	}


	public double getStockRatio() {
		return stockRatio;
	}

	public void setStockRatio(double stockRatio) {
		this.stockRatio = stockRatio;
	}

	public Integer getNoBillWaybillQty() {
		return noBillWaybillQty;
	}

	public void setNoBillWaybillQty(Integer noBillWaybillQty) {
		this.noBillWaybillQty = noBillWaybillQty;
	}

	public Integer getStockWaybillQty() {
		return stockWaybillQty;
	}

	public void setStockWaybillQty(Integer stockWaybillQty) {
		this.stockWaybillQty = stockWaybillQty;
	}

	public Integer getRunningWaybillQty() {
		return runningWaybillQty;
	}

	public void setRunningWaybillQty(Integer runningWaybillQty) {
		this.runningWaybillQty = runningWaybillQty;
	}
	
	
	
	
	
	
	
	
}
