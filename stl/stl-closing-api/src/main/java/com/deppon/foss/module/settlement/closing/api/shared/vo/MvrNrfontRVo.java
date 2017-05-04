package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontRDto;

/**
 * 02普通业务重分类报表Vo
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:41:44
 */
public class MvrNrfontRVo extends MvrNrfontRDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * dto
	 */
	private MvrNrfontRDto mvrNrfontRDto;
	/**
	 * 实体集合
	 */
	private List<MvrNrfontREntity> mvrNrfontRList;


	public List<MvrNrfontREntity> getMvrNrfontRList() {
		return mvrNrfontRList;
	}

	public void setMvrNrfontRList(List<MvrNrfontREntity> mvrNrfontRList) {
		this.mvrNrfontRList = mvrNrfontRList;
	}

	public MvrNrfontRDto getMvrNrfontRDto() {
		return mvrNrfontRDto;
	}

	public void setMvrNrfontRDto(MvrNrfontRDto mvrNrfontRDto) {
		this.mvrNrfontRDto = mvrNrfontRDto;
	}
}
