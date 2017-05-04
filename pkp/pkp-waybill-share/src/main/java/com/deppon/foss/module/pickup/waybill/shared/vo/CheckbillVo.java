package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;



/**
 * 图片开单Vo
 * 
 * @author 265041
 * @date 2015-6-18 14:53:43
 */
public class CheckbillVo {
	/**
	 * 图片开单查询条件
	 * */
	private WaybillPictureDto waybillPictureDto;

	/**
	 * 图片开单查询结果
	 * */
	private List<WaybillPictureDto> waybillPictureEntityList;

	public List<WaybillPictureDto> getWaybillPictureEntityList() {
		return waybillPictureEntityList;
	}

	public void setWaybillPictureEntityList(
			List<WaybillPictureDto> waybillPictureEntityList) {
		this.waybillPictureEntityList = waybillPictureEntityList;
	}
	public WaybillPictureDto getWaybillPictureDto() {
		return waybillPictureDto;
	}

	public void setWaybillPictureDto(WaybillPictureDto waybillPictureDto) {
		this.waybillPictureDto = waybillPictureDto;
	}
}
