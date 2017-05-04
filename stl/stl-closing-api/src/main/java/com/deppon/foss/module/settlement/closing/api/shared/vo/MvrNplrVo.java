package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNplrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNplrDto;

/**
 * 偏线月报表VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrNplrVo extends MvrNplrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计专线到达. */
	private MvrNplrDto mvrNplrDto;

	/** 专线到达集合信息. */
	private List<MvrNplrEntity> mvrNplrEntityList;

	/**
	 * @return the mvrNplrDto
	 */
	public MvrNplrDto getMvrNplrDto() {
		return mvrNplrDto;
	}

	/**
	 * @param mvrNplrDto the mvrNplrDto to set
	 */
	public void setMvrNplrDto(MvrNplrDto mvrNplrDto) {
		this.mvrNplrDto = mvrNplrDto;
	}

	/**
	 * @return the mvrNplrEntityList
	 */
	public List<MvrNplrEntity> getMvrNplrEntityList() {
		return mvrNplrEntityList;
	}

	/**
	 * @param mvrNplrEntityList the mvrNplrEntityList to set
	 */
	public void setMvrNplrEntityList(List<MvrNplrEntity> mvrNplrEntityList) {
		this.mvrNplrEntityList = mvrNplrEntityList;
	}


}
