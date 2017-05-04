package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityResultDto;

/**
 * 始发应收月报Vo
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:03:29
 */
public class MvrNrfostEntityVo implements Serializable {

	/**
	 * 始发应收月报Vo序列号
	 */
	private static final long serialVersionUID = 4744728089448306272L;

	/**
	 * 始发应收月报参数Dto
	 */
	private MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto;

	/**
	 * 始发应收月报结果Dto
	 */
	private MvrNrfostEntityResultDto mvrNrfostEntityResultDto;

	public MvrNrfostEntityQueryDto getMvrNrfostEntityQueryDto() {
		return mvrNrfostEntityQueryDto;
	}

	public void setMvrNrfostEntityQueryDto(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto) {
		this.mvrNrfostEntityQueryDto = mvrNrfostEntityQueryDto;
	}

	public MvrNrfostEntityResultDto getMvrNrfostEntityResultDto() {
		return mvrNrfostEntityResultDto;
	}

	public void setMvrNrfostEntityResultDto(
			MvrNrfostEntityResultDto mvrNrfostEntityResultDto) {
		this.mvrNrfostEntityResultDto = mvrNrfostEntityResultDto;
	}

}
