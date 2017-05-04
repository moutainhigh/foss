package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityResultDto;

/**
 * 始发应收月报Vo
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoEntityVo implements Serializable {

	/**
	 * 始发应收月报Vo序列号
	 */
	private static final long serialVersionUID = 4744728089448306272L;

	/**
	 * 始发应收月报参数Dto
	 */
	private MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto;

	/**
	 * 始发应收月报结果Dto
	 */
	private MvrNrfosoEntityResultDto mvrNrfosoEntityResultDto;

	public MvrNrfosoEntityQueryDto getMvrNrfosoEntityQueryDto() {
		return mvrNrfosoEntityQueryDto;
	}

	public void setMvrNrfosoEntityQueryDto(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto) {
		this.mvrNrfosoEntityQueryDto = mvrNrfosoEntityQueryDto;
	}

	public MvrNrfosoEntityResultDto getMvrNrfosoEntityResultDto() {
		return mvrNrfosoEntityResultDto;
	}

	public void setMvrNrfosoEntityResultDto(
			MvrNrfosoEntityResultDto mvrNrfosoEntityResultDto) {
		this.mvrNrfosoEntityResultDto = mvrNrfosoEntityResultDto;
	}

}
