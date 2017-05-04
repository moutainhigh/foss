package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdtEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdtDto;

/**
 * 02到达月报表VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrNrfdtVo extends MvrNrfdtDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计专线到达. */
	private MvrNrfdtDto mvrNrfdtDto;

	/** 专线到达集合信息. */
	private List<MvrNrfdtEntity> mvrNrfdtEntityList;

	/**
	 * @return the mvrNrfdtDto
	 */
	public MvrNrfdtDto getMvrNrfdtDto() {
		return mvrNrfdtDto;
	}

	/**
	 * @param mvrNrfdtDto the mvrNrfdtDto to set
	 */
	public void setMvrNrfdtDto(MvrNrfdtDto mvrNrfdtDto) {
		this.mvrNrfdtDto = mvrNrfdtDto;
	}

	/**
	 * @return the mvrNrfdtEntityList
	 */
	public List<MvrNrfdtEntity> getMvrNrfdtEntityList() {
		return mvrNrfdtEntityList;
	}

	/**
	 * @param mvrNrfdtEntityList the mvrNrfdtEntityList to set
	 */
	public void setMvrNrfdtEntityList(List<MvrNrfdtEntity> mvrNrfdtEntityList) {
		this.mvrNrfdtEntityList = mvrNrfdtEntityList;
	}


}
