package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExhibitForecastForWorldDto;

/**
 * 展会货Vo
 * @author 200978  xiaobingcheng
 * 2014-12-4
 */
public class ExhibitVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 部门，active，状态, 查询条件,返回数据*/
	private ExhibitForecastDto exhibitForecastDto;
	
	/**全国展会货list*/
	private List<ExhibitForecastForWorldDto> ExhibitForecastForWorldList;
	/**总票数*/
	private Integer totalWaybillQty;
	/**总重量*/
	private BigDecimal totalWeight;
	/**总体积*/
	private BigDecimal totalVolume;
	/**参数   驻地外场*/
	private String transferCenterCode;
	
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public List<ExhibitForecastForWorldDto> getExhibitForecastForWorldList() {
		return ExhibitForecastForWorldList;
	}

	public void setExhibitForecastForWorldList(
			List<ExhibitForecastForWorldDto> exhibitForecastForWorldList) {
		ExhibitForecastForWorldList = exhibitForecastForWorldList;
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

	public ExhibitForecastDto getExhibitForecastDto() {
		return exhibitForecastDto;
	}

	public void setExhibitForecastDto(ExhibitForecastDto exhibitForecastDto) {
		this.exhibitForecastDto = exhibitForecastDto;
	}

}
