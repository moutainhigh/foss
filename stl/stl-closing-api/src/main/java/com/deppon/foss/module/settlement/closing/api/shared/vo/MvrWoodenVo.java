package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;

/**
 * 代打木架VO
 * @author 045738
 *
 */
public class MvrWoodenVo implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5746602890723214588L;

	/** 注入dto. */
	private MvrWoodenDto mvrWoodenDto;
	

	public MvrWoodenDto getMvrWoodenDto() {
		return mvrWoodenDto;
	}

	public void setMvrWoodenDto(MvrWoodenDto mvrWoodenDto) {
		this.mvrWoodenDto = mvrWoodenDto;
	}
}
