package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.SpecialAddressConditionDto;

public class SpecialAddressVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SpecialAddressConditionDto specialAddressConditionDto;
	
	private SpecialAddressEntity specialAddressEntity;
	
	private List<SpecialAddressEntity> specialAddressEntityList;

	
	public SpecialAddressEntity getSpecialAddressEntity() {
		return specialAddressEntity;
	}

	public void setSpecialAddressEntity(SpecialAddressEntity specialAddressEntity) {
		this.specialAddressEntity = specialAddressEntity;
	}

	public SpecialAddressConditionDto getSpecialAddressConditionDto() {
		return specialAddressConditionDto;
	}

	public void setSpecialAddressConditionDto(
			SpecialAddressConditionDto specialAddressConditionDto) {
		this.specialAddressConditionDto = specialAddressConditionDto;
	}

	public List<SpecialAddressEntity> getSpecialAddressEntityList() {
		return specialAddressEntityList;
	}

	public void setSpecialAddressEntityList(
			List<SpecialAddressEntity> specialAddressEntityList) {
		this.specialAddressEntityList = specialAddressEntityList;
	}
}
