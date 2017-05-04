package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ToAddPartnerProgramEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ToAddPartnerProgramCondtionDto;

public class ToAddPartnerProgramVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto;
	private List<ToAddPartnerProgramEntity> toAddPartnerProgramEntityList;
	private ToAddPartnerProgramEntity toAddPartnerProgramEntity;
	
	private String toAddPartnerProgramid;

    private List<String> toAddPartnerProgramids;
	
	private String yesOrNo;
	
	//生效时间
	private Date effectiveTime;
	
	
	private String copyName;

	

	public String getToAddPartnerProgramid() {
		return toAddPartnerProgramid;
	}

	public void setToAddPartnerProgramid(String toAddPartnerProgramid) {
		this.toAddPartnerProgramid = toAddPartnerProgramid;
	}

	

	public List<String> getToAddPartnerProgramids() {
		return toAddPartnerProgramids;
	}

	public void setToAddPartnerProgramids(List<String> toAddPartnerProgramids) {
		this.toAddPartnerProgramids = toAddPartnerProgramids;
	}

	public ToAddPartnerProgramCondtionDto getToAddPartnerProgramCondtionDto() {
		return toAddPartnerProgramCondtionDto;
	}

	public void setToAddPartnerProgramCondtionDto(ToAddPartnerProgramCondtionDto toAddPartnerProgramCondtionDto) {
		this.toAddPartnerProgramCondtionDto = toAddPartnerProgramCondtionDto;
	}
	
	public ToAddPartnerProgramEntity getToAddPartnerProgramEntity() {
		return toAddPartnerProgramEntity;
	}

	public void setToAddPartnerProgramEntity(ToAddPartnerProgramEntity toAddPartnerProgramEntity) {
		this.toAddPartnerProgramEntity = toAddPartnerProgramEntity;
	}

	public String getYesOrNo() {
		return yesOrNo;
	}

	public void setYesOrNo(String yesOrNo) {
		this.yesOrNo = yesOrNo;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getCopyName() {
		return copyName;
	}

	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}

	public List<ToAddPartnerProgramEntity> getToAddPartnerProgramEntityList() {
		return toAddPartnerProgramEntityList;
	}

	public void setToAddPartnerProgramEntityList(List<ToAddPartnerProgramEntity> toAddPartnerProgramEntityList) {
		this.toAddPartnerProgramEntityList = toAddPartnerProgramEntityList;
	}



}
