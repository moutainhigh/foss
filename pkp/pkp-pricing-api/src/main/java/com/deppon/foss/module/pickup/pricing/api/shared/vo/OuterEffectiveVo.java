package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterEffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterEffectivePlanDto;


public class OuterEffectiveVo implements Serializable {
	private static final long serialVersionUID = 5386445973289905537L;
	private OuterEffectivePlanConditionDto outerEffectivePlanConditionDto;
	
	private List<OuterEffectivePlanDto> outerEffectivePlanDtoList;
	
	private OuterEffectivePlanEntity outerEffectivePlanEntity;
	
	private String outerEffectivePlanId;

    private List<String> outerEffectivePlanIds;
	
	private String yesOrNo;
	
	//生效时间
	private Date effectiveTime;
	
	private OuterEffectivePlanDto outerEffectivePlanDto;
	
	private String copyName;
	
	public List<String> getOuterEffectivePlanIds() {
		return outerEffectivePlanIds;
	}
	public void setOuterEffectivePlanIds(List<String> outerEffectivePlanIds) {
		this.outerEffectivePlanIds = outerEffectivePlanIds;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public OuterEffectivePlanDto getOuterEffectivePlanDto() {
		return outerEffectivePlanDto;
	}
	public void setOuterEffectivePlanDto(OuterEffectivePlanDto outerEffectivePlanDto) {
		this.outerEffectivePlanDto = outerEffectivePlanDto;
	}
	public String getCopyName() {
		return copyName;
	}
	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}
	public String getYesOrNo() {
		return yesOrNo;
	}
	public void setYesOrNo(String yesOrNo) {
		this.yesOrNo = yesOrNo;
	}
	
	public String getOuterEffectivePlanId() {
		return outerEffectivePlanId;
	}
	public void setOuterEffectivePlanId(String outerEffectivePlanId) {
		this.outerEffectivePlanId = outerEffectivePlanId;
	}
	
	public OuterEffectivePlanConditionDto getOuterEffectivePlanConditionDto() {
		return outerEffectivePlanConditionDto;
	}

	public void setOuterEffectivePlanConditionDto(
			OuterEffectivePlanConditionDto outerEffectivePlanConditionDto) {
		this.outerEffectivePlanConditionDto = outerEffectivePlanConditionDto;
	}

	public List<OuterEffectivePlanDto> getOuterEffectivePlanDtoList() {
		return outerEffectivePlanDtoList;
	}

	public void setOuterEffectivePlanDtoList(
			List<OuterEffectivePlanDto> outerEffectivePlanDtoList) {
		this.outerEffectivePlanDtoList = outerEffectivePlanDtoList;
	}

	public OuterEffectivePlanEntity getOuterEffectivePlanEntity() {
		return outerEffectivePlanEntity;
	}

	public void setOuterEffectivePlanEntity(
			OuterEffectivePlanEntity outerEffectivePlanEntity) {
		this.outerEffectivePlanEntity = outerEffectivePlanEntity;
	}
	
}
