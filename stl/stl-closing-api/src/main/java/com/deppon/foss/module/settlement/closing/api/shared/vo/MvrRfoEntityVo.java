package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityResultDto;

/**
 * 始发应收月报Vo
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午3:03:29
 */
public class MvrRfoEntityVo implements Serializable {

	/**
	 * 始发应收月报Vo序列号
	 */
	private static final long serialVersionUID = 4744728089448306272L;

	/**
	 * 始发应收月报参数Dto
	 */
	private MvrRfoEntityQueryDto mvrRfoEntityQueryDto;

	/**
	 * 始发应收月报结果Dto
	 */
	private MvrRfoEntityResultDto mvrRfoEntityResultDto;

	public MvrRfoEntityQueryDto getMvrRfoEntityQueryDto() {
		return mvrRfoEntityQueryDto;
	}

	public void setMvrRfoEntityQueryDto(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto) {
		this.mvrRfoEntityQueryDto = mvrRfoEntityQueryDto;
	}

	public MvrRfoEntityResultDto getMvrRfoEntityResultDto() {
		return mvrRfoEntityResultDto;
	}

	public void setMvrRfoEntityResultDto(
			MvrRfoEntityResultDto mvrRfoEntityResultDto) {
		this.mvrRfoEntityResultDto = mvrRfoEntityResultDto;
	}

}
