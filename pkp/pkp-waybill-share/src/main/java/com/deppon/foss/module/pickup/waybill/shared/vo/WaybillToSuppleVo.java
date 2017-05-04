package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleCondtionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
/**
 * 待补录查询Vo
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-11 09:53:43
 */
public class WaybillToSuppleVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 待补录查询条件Dto
	 */
	private WaybillToSuppleCondtionDto waybillToSuppleCondtionDto;
	
	/**
	 * 查询结果Dto
	 */
	private List<WaybillToSuppleResultDto> waybillSuppleResultDtoList;
	
	/**
	 * 待补录记录日志表实体
	 */
	private WaybillSupplementLogEntity waybillSupplementLogEntity;

	public WaybillToSuppleCondtionDto getWaybillToSuppleCondtionDto() {
		return waybillToSuppleCondtionDto;
	}

	public void setWaybillToSuppleCondtionDto(
			WaybillToSuppleCondtionDto waybillToSuppleCondtionDto) {
		this.waybillToSuppleCondtionDto = waybillToSuppleCondtionDto;
	}

	public List<WaybillToSuppleResultDto> getWaybillSuppleResultDtoList() {
		return waybillSuppleResultDtoList;
	}
	
	public void setWaybillSuppleResultDtoList(
			List<WaybillToSuppleResultDto> waybillSuppleResultDtoList) {
		this.waybillSuppleResultDtoList = waybillSuppleResultDtoList;
	}

	public WaybillSupplementLogEntity getWaybillSupplementLogEntity() {
		return waybillSupplementLogEntity;
	}

	public void setWaybillSupplementLogEntity(
			WaybillSupplementLogEntity waybillSupplementLogEntity) {
		this.waybillSupplementLogEntity = waybillSupplementLogEntity;
	}
}