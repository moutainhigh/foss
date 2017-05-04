package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdDhkDto;

/**
 * 代汇款报表VO.
 *
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public class DvdDhkVo extends DvdDhkDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 合计专线到达. */
	private DvdDhkDto dvdDhkDto;

	/** 代汇款报表集合信息. */
	private List<DvdDhkEntity> dvdDhkEntityList;

	/**
	 * @return the dvdDhkDto
	 */
	public DvdDhkDto getDvdDhkDto() {
		return dvdDhkDto;
	}

	/**
	 * @param dvdDhkDto the dvdDhkDto to set
	 */
	public void setDvdDhkDto(DvdDhkDto dvdDhkDto) {
		this.dvdDhkDto = dvdDhkDto;
	}

	/**
	 * @return the dvdDhkEntityList
	 */
	public List<DvdDhkEntity> getDvdDhkEntityList() {
		return dvdDhkEntityList;
	}

	/**
	 * @param dvdDhkEntityList the dvdDhkEntityList to set
	 */
	public void setDvdDhkEntityList(List<DvdDhkEntity> dvdDhkEntityList) {
		this.dvdDhkEntityList = dvdDhkEntityList;
	}


}
