/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.shared.vo
 * FILE    NAME: ExceptionProcessVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SerialNoDto;

/**
 * 异常处理VO.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-10-30 上午9:03:33
 */
public class ExceptionProcessVo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 异常处理查询条件. */
	private ExceptionProcessConditionDto exceptionProcessConditionDto;

	/** 异常处理返回Dto. */
	private List<ExceptionProcessDto> exceptionProcessDtoList;

	/** 异常处理历史信息Dto. */
	private ExceptionProcessDetailDto exceptionProcessDetailDto;
	
	private List<SerialNoDto> stockDtoList;
	
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<SerialNoDto> getStockDtoList() {
		return stockDtoList;
	}

	public void setStockDtoList(List<SerialNoDto> stockDtoList) {
		this.stockDtoList = stockDtoList;
	}

	/**
	 * Gets the exception process condition dto.
	 *
	 * @return the exceptionProcessConditionDto
	 */
	public ExceptionProcessConditionDto getExceptionProcessConditionDto() {
		return exceptionProcessConditionDto;
	}

	/**
	 * Sets the exception process condition dto.
	 *
	 * @param exceptionProcessConditionDto the exceptionProcessConditionDto to
	 * see
	 */
	public void setExceptionProcessConditionDto(ExceptionProcessConditionDto exceptionProcessConditionDto) {
		this.exceptionProcessConditionDto = exceptionProcessConditionDto;
	}

	/**
	 * Gets the exception process dto list.
	 *
	 * @return the exceptionProcessDtoList
	 */
	public List<ExceptionProcessDto> getExceptionProcessDtoList() {
		return exceptionProcessDtoList;
	}

	/**
	 * Sets the exception process dto list.
	 *
	 * @param exceptionProcessDtoList the exceptionProcessDtoList to see
	 */
	public void setExceptionProcessDtoList(List<ExceptionProcessDto> exceptionProcessDtoList) {
		this.exceptionProcessDtoList = exceptionProcessDtoList;
	}

	/**
	 * Gets the exception process detail dto.
	 *
	 * @return the exceptionProcessDetailDto
	 */
	public ExceptionProcessDetailDto getExceptionProcessDetailDto() {
		return exceptionProcessDetailDto;
	}

	/**
	 * Sets the exception process detail dto.
	 *
	 * @param exceptionProcessDetailDto the exceptionProcessDetailDto to see
	 */
	public void setExceptionProcessDetailDto(ExceptionProcessDetailDto exceptionProcessDetailDto) {
		this.exceptionProcessDetailDto = exceptionProcessDetailDto;
	}

}