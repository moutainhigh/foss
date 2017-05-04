package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;

/**
 * 代汇款报表VO.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public class MvrDhkVo extends MvrDhkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199519271775498788L;

	/** 合计代汇款报表. */
	private MvrDhkDto mvrDhkDto;

	/** 代汇款报表集合信息. */
	private List<MvrDhkEntity> mvrDhkEntityList;

	/**
	 * @return the mvrDhkDto
	 */
	public MvrDhkDto getMvrDhkDto() {
		return mvrDhkDto;
	}

	/**
	 * @param mvrDhkDto the mvrDhkDto to set
	 */
	public void setMvrDhkDto(MvrDhkDto mvrDhkDto) {
		this.mvrDhkDto = mvrDhkDto;
	}

	/**
	 * @return the mvrDhkEntityList
	 */
	public List<MvrDhkEntity> getMvrDhkEntityList() {
		return mvrDhkEntityList;
	}

	/**
	 * @param mvrDhkEntityList the mvrDhkEntityList to set
	 */
	public void setMvrDhkEntityList(List<MvrDhkEntity> mvrDhkEntityList) {
		this.mvrDhkEntityList = mvrDhkEntityList;
	}


}
