package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;

/**
 * 出发到达往来报表vo.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:19:27
 */
public class MvrLdiVo implements Serializable{

	/** 序列哈. */
	private static final long serialVersionUID = -4377951400654715462L;
	
	/** 注入dto. */
	private MvrLdiDto dto;

	/**
	 * @GET
	 * @return dto
	 */
	public MvrLdiDto getDto() {
		/*
		 *@get
		 *@ return dto
		 */
		return dto;
	}

	/**
	 * @SET
	 * @param dto
	 */
	public void setDto(MvrLdiDto dto) {
		/*
		 *@set
		 *@this.dto = dto
		 */
		this.dto = dto;
	}
		

}
