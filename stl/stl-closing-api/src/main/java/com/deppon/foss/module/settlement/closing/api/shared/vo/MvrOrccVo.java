package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrccQueryDto;

public class MvrOrccVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询条件DTO
	 */
	private MvrOrccQueryDto dto ;
	/**
	 *查询结果
	 */
	private List<MvrOrccEntity> orccEntitys;
	
	public MvrOrccQueryDto getDto() {
		return dto;
	}
	public void setDto(MvrOrccQueryDto dto) {
		this.dto = dto;
	}
	public List<MvrOrccEntity> getOrccEntitys() {
		return orccEntitys;
	}
	public void setOrccEntitys(List<MvrOrccEntity> orccEntitys) {
		this.orccEntitys = orccEntitys;
	}

	
	
	

}
