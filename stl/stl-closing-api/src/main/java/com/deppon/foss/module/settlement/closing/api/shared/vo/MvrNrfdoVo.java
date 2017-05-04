package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdoDto;

/**
 * 01到达月报表VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrNrfdoVo extends MvrNrfdoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计专线到达. */
	private MvrNrfdoDto mvrNrfdoDto;

	/** 专线到达集合信息. */
	private List<MvrNrfdoEntity> mvrNrfdoEntityList;

	/**
	 * @return the mvrNrfdoDto
	 */
	public MvrNrfdoDto getMvrNrfdoDto() {
		return mvrNrfdoDto;
	}

	/**
	 * @param mvrNrfdoDto the mvrNrfdoDto to set
	 */
	public void setMvrNrfdoDto(MvrNrfdoDto mvrNrfdoDto) {
		this.mvrNrfdoDto = mvrNrfdoDto;
	}

	/**
	 * @return the mvrNrfdoEntityList
	 */
	public List<MvrNrfdoEntity> getMvrNrfdoEntityList() {
		return mvrNrfdoEntityList;
	}

	/**
	 * @param mvrNrfdoEntityList the mvrNrfdoEntityList to set
	 */
	public void setMvrNrfdoEntityList(List<MvrNrfdoEntity> mvrNrfdoEntityList) {
		this.mvrNrfdoEntityList = mvrNrfdoEntityList;
	}


}
