package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfiDto;

/**
 * 始发、空运VO.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:09:30
 * @since
 * @version
 */
public class MvrAfiVo  implements Serializable {

	/** 序列号. */
	private static final long serialVersionUID = 149873892158761927L;

	/** 始发、空运dto. */
	private MvrAfiDto mvrAfiDto;

	/** 始发、空运dtos. */
	private List<MvrAfiDto> mvrAfiDtos;
	
	/**
	 * Gets the mvr afi dto.
	 *
	 * @return  the mvrAfiDto
	 */
	public MvrAfiDto getMvrAfiDto() {
		return mvrAfiDto;
	}

	
	/**
	 * Sets the mvr afi dto.
	 *
	 * @param mvrAfiDto the mvrAfiDto to set
	 */
	public void setMvrAfiDto(MvrAfiDto mvrAfiDto) {
		this.mvrAfiDto = mvrAfiDto;
	}


	
	/**
	 * Gets the mvr afi dtos.
	 *
	 * @return  the mvrAfiDtos
	 */
	public List<MvrAfiDto> getMvrAfiDtos() {
		return mvrAfiDtos;
	}


	
	/**
	 * Sets the mvr afi dtos.
	 *
	 * @param mvrAfiDtos the mvrAfiDtos to set
	 */
	public void setMvrAfiDtos(List<MvrAfiDto> mvrAfiDtos) {
		this.mvrAfiDtos = mvrAfiDtos;
	}
	
	
}
