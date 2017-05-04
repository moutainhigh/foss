package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportResultDto;

/**
 * 偏线全盘报表Vo
 * @author guxinhua
 * @date 2013-08-09 1:47:35 PM
 */
public class LandSystemReportQueryVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6707781696138209749L;
	
	/**
	 * 区分查询类型
	 */
	private String queryType;
	
	/**
	 * 查询条件Dto
	 */
	private LdpAgencySystemReportQueryDto dto;
	
	/**
	 * 返回的查询结果List
	 */
	private List<LdpAgencySystemReportResultDto> list;

	/**
	 * @return dto
	 */
	public LdpAgencySystemReportQueryDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 */
	public void setDto(LdpAgencySystemReportQueryDto dto) {
		this.dto = dto;
	}

	/**
	 * @return list
	 */
	public List<LdpAgencySystemReportResultDto> getList() {
		return list;
	}

	/**
	 * @param list
	 */
	public void setList(List<LdpAgencySystemReportResultDto> list) {
		this.list = list;
	}

	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

}
