package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityResultDto;

/**
 * 始发应收月报Vo
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityVo implements Serializable {

	/**
	 * 始发应收月报Vo序列号
	 */
	private static final long serialVersionUID = 4744728089448306272L;

	/**
	 * 始发应收月报参数Dto
	 */
	private MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto;

	/**
	 * 始发应收月报结果Dto
	 */
	private MvrNrfonoEntityResultDto mvrNrfonoEntityResultDto;

	public MvrNrfonoEntityQueryDto getMvrNrfonoEntityQueryDto() {
		return mvrNrfonoEntityQueryDto;
	}

	public void setMvrNrfonoEntityQueryDto(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto) {
		this.mvrNrfonoEntityQueryDto = mvrNrfonoEntityQueryDto;
	}

	public MvrNrfonoEntityResultDto getMvrNrfonoEntityResultDto() {
		return mvrNrfonoEntityResultDto;
	}

	public void setMvrNrfonoEntityResultDto(
			MvrNrfonoEntityResultDto mvrNrfonoEntityResultDto) {
		this.mvrNrfonoEntityResultDto = mvrNrfonoEntityResultDto;
	}

}
