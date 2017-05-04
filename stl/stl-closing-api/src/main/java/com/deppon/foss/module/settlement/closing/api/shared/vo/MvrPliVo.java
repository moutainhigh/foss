package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;

/**
 * 始发偏线VO.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午11:09:30
 * @since
 * @version
 */
public class MvrPliVo  implements Serializable {

	/** 序列号. */
	private static final long serialVersionUID = 149873892158761927L;

	/** 始发偏线dto. */
	private MvrPliDto mvrPliDto;

	/** 始发、空运dtos. */
	private List<MvrPliDto> mvrPliDtos;

	
	/**
	 * Gets the mvr pli dto.
	 *
	 * @return  the mvrPliDto
	 */
	public MvrPliDto getMvrPliDto() {
		return mvrPliDto;
	}

	
	/**
	 * Sets the mvr pli dto.
	 *
	 * @param mvrPliDto the mvrPliDto to set
	 */
	public void setMvrPliDto(MvrPliDto mvrPliDto) {
		this.mvrPliDto = mvrPliDto;
	}

	
	/**
	 * Gets the mvr pli dtos.
	 *
	 * @return  the mvrPliDtos
	 */
	public List<MvrPliDto> getMvrPliDtos() {
		return mvrPliDtos;
	}

	
	/**
	 * Sets the mvr pli dtos.
	 *
	 * @param mvrPliDtos the mvrPliDtos to set
	 */
	public void setMvrPliDtos(List<MvrPliDto> mvrPliDtos) {
		this.mvrPliDtos = mvrPliDtos;
	}
	
	
}
