package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ExhibitForecastEntity;



/**
 * 展会货查询 条件dto
 * @author 200978  xiaobingcheng
 * 2014-12-4
 */
public class ExhibitForecastDto {

	
	/** 状态 */
	private String status;
	/** 到达外场编码 */
	private String transferCenterCode;
	/**是否有效    最新的一次统计为有效，其他无效*/
	private String active;
	
	/** 返回结果*/
	private List<ExhibitForecastEntity> exhibitList;
	/** 总票数*/
	private Integer totalWaybillQty;
	/**总重量*/
	private BigDecimal totalWeight;
	/**总体积*/
	private BigDecimal totalVolume;
	/**库存占比*/
	private double stockRatio;
	
	
	
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	public List<ExhibitForecastEntity> getExhibitList() {
		return exhibitList;
	}
	public void setExhibitList(List<ExhibitForecastEntity> exhibitList) {
		this.exhibitList = exhibitList;
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
	
	public double getStockRatio() {
		return stockRatio;
	}
	public void setStockRatio(double stockRatio) {
		this.stockRatio = stockRatio;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	
	
	
}
