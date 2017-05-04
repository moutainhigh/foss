package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdReturnCodDto;

/**
 * 退代收货款明细参数Vo.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午10:57:16
 * @since
 * @version
 */
public class DvdReturnCodVo implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -395297595782949874L;

	/** 退代收货款dto集合. */
	private List<DvdReturnCodDto> dvdReturnCodDtos;
	
	/** 退代收货款dto. */
	private DvdReturnCodDto dvdReturnCodDto;

	
	/**
	 * @return  the dvdReturnCodDtos
	 */
	public List<DvdReturnCodDto> getDvdReturnCodDtos() {
		return dvdReturnCodDtos;
	}

	
	/**
	 * @param dvdReturnCodDtos the dvdReturnCodDtos to set
	 */
	public void setDvdReturnCodDtos(List<DvdReturnCodDto> dvdReturnCodDtos) {
		this.dvdReturnCodDtos = dvdReturnCodDtos;
	}

	
	/**
	 * @return  the dvdReturnCodDto
	 */
	public DvdReturnCodDto getDvdReturnCodDto() {
		return dvdReturnCodDto;
	}

	
	/**
	 * @param dvdReturnCodDto the dvdReturnCodDto to set
	 */
	public void setDvdReturnCodDto(DvdReturnCodDto dvdReturnCodDto) {
		this.dvdReturnCodDto = dvdReturnCodDto;
	}
	
}
