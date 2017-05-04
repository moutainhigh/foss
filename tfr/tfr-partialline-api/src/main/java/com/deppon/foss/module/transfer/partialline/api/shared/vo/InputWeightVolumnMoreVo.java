package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;

public class InputWeightVolumnMoreVo {
	//交接单号
	private String handOverBillNos;
	//运单号
	private String waybillNos;
	
	//查询dto
	private InputWeightVolumnMoreDto dto;
	//录入重量体积dto list
	List<InputWeightVolumnMoreDto> list;

	public String getHandOverBillNos() {
		return handOverBillNos;
	}

	public void setHandOverBillNos(String handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}

	public String getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	public InputWeightVolumnMoreDto getDto() {
		return dto;
	}

	public void setDto(InputWeightVolumnMoreDto dto) {
		this.dto = dto;
	}

	public List<InputWeightVolumnMoreDto> getList() {
		return list;
	}

	public void setList(List<InputWeightVolumnMoreDto> list) {
		this.list = list;
	}
}
