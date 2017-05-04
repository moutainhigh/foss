package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.AgencySystemReportResultDto;

/**
 * 偏线全盘报表Vo
 * @author foss-zhangxiaohui
 * @date Dec 25, 2012 1:47:35 PM
 */
public class AgencySystemReportQueryVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6707781696138209749L;
	
	/**
	 * 查询条件Dto
	 */
	private AgencySystemReportQueryDto dto;
	
	/**
	 * 返回的查询结果List
	 */
	private List<AgencySystemReportResultDto> list;

	/**
	 * @return dto
	 */
	public AgencySystemReportQueryDto getDto() {
		return dto;
	}

	/**
	 * @param  dto  
	 */
	public void setDto(AgencySystemReportQueryDto dto) {
		this.dto = dto;
	}

	/**
	 * @return list
	 */
	public List<AgencySystemReportResultDto> getList() {
		return list;
	}

	/**
	 * @param  list  
	 */
	public void setList(List<AgencySystemReportResultDto> list) {
		this.list = list;
	}
}
