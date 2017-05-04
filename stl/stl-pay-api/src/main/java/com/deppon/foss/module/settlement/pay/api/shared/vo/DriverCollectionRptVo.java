package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;

/**
 * pda司机缴款dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午3:08:59
 */
public class DriverCollectionRptVo implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7729639337871786033L;

	/**
	 * dto
	 */
	private DriverCollectionRptDto dto;

	/**
	 * @return dto
	 */
	public DriverCollectionRptDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(DriverCollectionRptDto dto) {
		this.dto = dto;
	}

}
