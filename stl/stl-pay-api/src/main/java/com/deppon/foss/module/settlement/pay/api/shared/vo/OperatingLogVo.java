package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogResultDto;

/**
 * 操作日志vo
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午2:27:06
 */
public class OperatingLogVo implements Serializable {

	/**
	 * 操作日志vo序列号
	 */
	private static final long serialVersionUID = -1767795685787097567L;

	/**
	 * 操作日志参数dto
	 */
	private OperatingLogQueryDto operatingLogQueryDto;

	/**
	 * 操作日志返回dto
	 */
	private OperatingLogResultDto operatingLogResultDto;

	/**
	 * 操作日志返回dto集合
	 */
	private List<OperatingLogResultDto> operatingLogResultDtoList;

	/**
	 * @return operatingLogQueryDto
	 */
	public OperatingLogQueryDto getOperatingLogQueryDto() {
		return operatingLogQueryDto;
	}

	/**
	 * @param operatingLogQueryDto
	 */
	public void setOperatingLogQueryDto(
			OperatingLogQueryDto operatingLogQueryDto) {
		this.operatingLogQueryDto = operatingLogQueryDto;
	}

	/**
	 * @return operatingLogResultDto
	 */
	public OperatingLogResultDto getOperatingLogResultDto() {
		return operatingLogResultDto;
	}

	/**
	 * @param operatingLogResultDto
	 */
	public void setOperatingLogResultDto(
			OperatingLogResultDto operatingLogResultDto) {
		this.operatingLogResultDto = operatingLogResultDto;
	}

	/**
	 * @return operatingLogResultDtoList
	 */
	public List<OperatingLogResultDto> getOperatingLogResultDtoList() {
		return operatingLogResultDtoList;
	}

	/**
	 * @param operatingLogResultDtoList
	 */
	public void setOperatingLogResultDtoList(
			List<OperatingLogResultDto> operatingLogResultDtoList) {
		this.operatingLogResultDtoList = operatingLogResultDtoList;
	}

}
