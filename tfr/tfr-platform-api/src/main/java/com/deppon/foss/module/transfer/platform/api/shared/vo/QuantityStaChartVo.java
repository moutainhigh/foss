package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;

/**
 * 货量统计图表Vo
 * @author 045923
 *
 */
public class QuantityStaChartVo {
	
	/*
	 * dto
	 */
	private QuantitySta4ChartDto quantitySta4ChartDto;
	
	/**
	 * 查询条件DTO
	 */
	private QuantityStaQcDto quantityStaQcDto;
	
	/**
	 * 返回的结果集
	 */
	private List<QuantitySta4ChartDto> quantitySta4ChartDtoList;

	public QuantityStaQcDto getQuantityStaQcDto() {
		return quantityStaQcDto;
	}

	public void setQuantityStaQcDto(QuantityStaQcDto quantityStaQcDto) {
		this.quantityStaQcDto = quantityStaQcDto;
	}

	public List<QuantitySta4ChartDto> getQuantitySta4ChartDtoList() {
		return quantitySta4ChartDtoList;
	}

	public void setQuantitySta4ChartDtoList(
			List<QuantitySta4ChartDto> quantitySta4ChartDtoList) {
		this.quantitySta4ChartDtoList = quantitySta4ChartDtoList;
	}

	public QuantitySta4ChartDto getQuantitySta4ChartDto() {
		return quantitySta4ChartDto;
	}

	public void setQuantitySta4ChartDto(QuantitySta4ChartDto quantitySta4ChartDto) {
		this.quantitySta4ChartDto = quantitySta4ChartDto;
	}
	
}
