package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportResultDto;


/**
 * 在线支付监控查询接口
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午8:41:34
 */
public class OnlionMonitorReporVo implements Serializable {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4786224237490470758L;
	/**
	 * 查询条件
	 */
	private OnlionMonitorReportDto dto;
	
	/**
	 * 返回结构集dto
	 */
	private OnlionMonitorReportResultDto resultDto;
	/**
	 * @return dto
	 */
	public OnlionMonitorReportDto getDto() {
		return dto;
	}
	/**
	 * @param dto
	 */
	public void setDto(OnlionMonitorReportDto dto) {
		this.dto = dto;
	}
	/**
	 * @return resultDto
	 */
	public OnlionMonitorReportResultDto getResultDto() {
		return resultDto;
	}
	/**
	 * @param resultDto
	 */
	public void setResultDto(OnlionMonitorReportResultDto resultDto) {
		this.resultDto = resultDto;
	}

}
