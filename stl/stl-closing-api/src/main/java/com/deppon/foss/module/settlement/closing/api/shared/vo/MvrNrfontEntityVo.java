package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityResultDto;

/**
 * 始发应收月报Vo
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:03:29
 */
public class MvrNrfontEntityVo implements Serializable {

	/**
	 * 始发应收月报Vo序列号
	 */
	private static final long serialVersionUID = 4744728089448306272L;

	/**
	 * 始发应收月报参数Dto
	 */
	private MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto;

	/**
	 * 始发应收月报结果Dto
	 */
	private MvrNrfontEntityResultDto mvrNrfontEntityResultDto;

	public MvrNrfontEntityQueryDto getMvrNrfontEntityQueryDto() {
		return mvrNrfontEntityQueryDto;
	}

	public void setMvrNrfontEntityQueryDto(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto) {
		this.mvrNrfontEntityQueryDto = mvrNrfontEntityQueryDto;
	}

	public MvrNrfontEntityResultDto getMvrNrfontEntityResultDto() {
		return mvrNrfontEntityResultDto;
	}

	public void setMvrNrfontEntityResultDto(
			MvrNrfontEntityResultDto mvrNrfontEntityResultDto) {
		this.mvrNrfontEntityResultDto = mvrNrfontEntityResultDto;
	}

}
