package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;

/**
 * 合伙人日明细vo
 * @author 311396
 * @date 2016-3-22 下午2:39:47
 */
public class MvrPtpAllDetailsVo implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3962128745698294045L;

	/**
	 * dto
	 */
	private MvrPtpAllDetailsDto dto;
	
	private List<MvrPtpAllDetailResultDto> resultList;

	public MvrPtpAllDetailsDto getDto() {
		return dto;
	}

	public void setDto(MvrPtpAllDetailsDto dto) {
		this.dto = dto;
	}

	public List<MvrPtpAllDetailResultDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<MvrPtpAllDetailResultDto> resultList) {
		this.resultList = resultList;
	}
}
