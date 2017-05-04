package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNafiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNafiDto;

/**
 * 空运往来月报表VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrNafiVo extends MvrNafiDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计专线到达. */
	private MvrNafiDto mvrNafiDto;

	/** 专线到达集合信息. */
	private List<MvrNafiEntity> mvrNafiEntityList;

	/**
	 * @return the mvrNafiDto
	 */
	public MvrNafiDto getMvrNafiDto() {
		return mvrNafiDto;
	}

	/**
	 * @param mvrNafiDto the mvrNafiDto to set
	 */
	public void setMvrNafiDto(MvrNafiDto mvrNafiDto) {
		this.mvrNafiDto = mvrNafiDto;
	}

	/**
	 * @return the mvrNafiEntityList
	 */
	public List<MvrNafiEntity> getMvrNafiEntityList() {
		return mvrNafiEntityList;
	}

	/**
	 * @param mvrNafiEntityList the mvrNafiEntityList to set
	 */
	public void setMvrNafiEntityList(List<MvrNafiEntity> mvrNafiEntityList) {
		this.mvrNafiEntityList = mvrNafiEntityList;
	}


}
