package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.PodDto;

/**
 * 签收记录vo
 * @author 198704 weitao
 * @date 2014-08-22
 */
public class PodInfoVo implements Serializable{

	/**
	 * vo序列化
	 */
	private static final long serialVersionUID = -8834486095876555853L;

	/**
	 * 定义一个podDto
	 */
	private PodDto dto;
	
	/**
	 * 定义一个podDto集合
	 */
	private List<PodDto> dtoList;

   /**
    * @return dto
    */
	public PodDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(PodDto dto) {
		this.dto = dto;
	}

	/**
	 * @return dtoList
	 */
	public List<PodDto> getDtoList() {
		return dtoList;
	}

	/**
	 * @param dtoList
	 */
	public void setDtoList(List<PodDto> dtoList) {
		this.dtoList = dtoList;
	} 
}
