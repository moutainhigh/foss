/**
 * @author foss 257200
 * 2015-9-1
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnDto;

/**
 * @author 257220
 *
 */
public class InputWeightVolumnVo {
	//交接单号
	private String handOverBillNos;
	//运单号
	private String waybillNos;
	//母件
	private String parentWaybillNo;
	
	private InputWeightVolumnDto dto;
	//录入重量体积dto list
	List<InputWeightVolumnDto> list;
	
	/*List<Map<String,InputWeightVolumnDto>>list;*/
	/**
	 * @return the handOverBillNos
	 */
	public String getHandOverBillNos() {
		return handOverBillNos;
	}

	/**
	 * @param handOverBillNos the handOverBillNos to set
	 */
	public void setHandOverBillNos(String handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}

	/**
	 * @return the waybillNos
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos the waybillNos to set
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return the dto
	 */
	public InputWeightVolumnDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(InputWeightVolumnDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the list
	 */
	public List<InputWeightVolumnDto> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<InputWeightVolumnDto> list) {
		this.list = list;
	}

	/**
	 * @return the parentWaybillNo
	 */
	public String getParentWaybillNo() {
		return parentWaybillNo;
	}

	/**
	 * @param parentWaybillNo the parentWaybillNo to set
	 */
	public void setParentWaybillNo(String parentWaybillNo) {
		this.parentWaybillNo = parentWaybillNo;
	}
	
}
