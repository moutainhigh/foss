package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.OuterPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceCondtionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPriceDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.OuterPricePlanDto;

public class OuterPriceVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private OuterPriceCondtionDto outerPriceCondtionDto;
	
	private List<OuterPricePlanDto> outerPricePlanDtoList;
	
	private OuterPriceDetailDto outerPriceDetailDto;
	
	private OuterPriceEntity outerPriceEntity;
	
	private String outerPriceId;

    private List<String> outerPriceIds;
	
	private String yesOrNo;
	
	//生效时间
	private Date effectiveTime;
	
	private OuterPricePlanDto outerPricePlanDto;
	
	private String copyName;

	public String getOuterPriceId() {
		return outerPriceId;
	}

	public void setOuterPriceId(String outerPriceId) {
		this.outerPriceId = outerPriceId;
	}

	public List<String> getOuterPriceIds() {
		return outerPriceIds;
	}

	public void setOuterPriceIds(List<String> outerPriceIds) {
		this.outerPriceIds = outerPriceIds;
	}

	public OuterPriceCondtionDto getOuterPriceCondtionDto() {
		return outerPriceCondtionDto;
	}

	public void setOuterPriceCondtionDto(OuterPriceCondtionDto outerPriceCondtionDto) {
		this.outerPriceCondtionDto = outerPriceCondtionDto;
	}
	
	public List<OuterPricePlanDto> getOuterPricePlanDtoList() {
		return outerPricePlanDtoList;
	}

	public void setOuterPricePlanDtoList(
			List<OuterPricePlanDto> outerPricePlanDtoList) {
		this.outerPricePlanDtoList = outerPricePlanDtoList;
	}

	public OuterPriceDetailDto getOuterPriceDetailDto() {
		return outerPriceDetailDto;
	}

	public void setOuterPriceDetailDto(OuterPriceDetailDto outerPriceDetailDto) {
		this.outerPriceDetailDto = outerPriceDetailDto;
	}

	public OuterPriceEntity getOuterPriceEntity() {
		return outerPriceEntity;
	}

	public void setOuterPriceEntity(OuterPriceEntity outerPriceEntity) {
		this.outerPriceEntity = outerPriceEntity;
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

	public OuterPricePlanDto getOuterPricePlanDto() {
		return outerPricePlanDto;
	}

	public void setOuterPricePlanDto(OuterPricePlanDto outerPricePlanDto) {
		this.outerPricePlanDto = outerPricePlanDto;
	}

	public String getCopyName() {
		return copyName;
	}

	public void setCopyName(String copyName) {
		this.copyName = copyName;
	}

}
