package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNpliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNpliDto;

/**
 * 始发偏线往来月报表VO.
 * 
 * @author guxinhua
 * @date 2013-3-6 上午11:16:09
 */
public class MvrNpliVo extends MvrNpliDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计专线到达. */
	private MvrNpliDto mvrNpliDto;

	/** 专线到达集合信息. */
	private List<MvrNpliEntity> mvrNpliEntityList;

	/**
	 * @return the mvrNpliDto
	 */
	public MvrNpliDto getMvrNpliDto() {
		return mvrNpliDto;
	}

	/**
	 * @param mvrNpliDto the mvrNpliDto to set
	 */
	public void setMvrNpliDto(MvrNpliDto mvrNpliDto) {
		this.mvrNpliDto = mvrNpliDto;
	}

	/**
	 * @return the mvrNpliEntityList
	 */
	public List<MvrNpliEntity> getMvrNpliEntityList() {
		return mvrNpliEntityList;
	}

	/**
	 * @param mvrNpliEntityList the mvrNpliEntityList to set
	 */
	public void setMvrNpliEntityList(List<MvrNpliEntity> mvrNpliEntityList) {
		this.mvrNpliEntityList = mvrNpliEntityList;
	}


}
