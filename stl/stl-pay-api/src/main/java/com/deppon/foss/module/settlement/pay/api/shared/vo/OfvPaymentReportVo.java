package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;

/**
 * 外请车付款报表vo
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-13 上午10:34:29
 */
public class OfvPaymentReportVo implements Serializable {

	/**
	 * 外请车付款报表vo序列号
	 */
	private static final long serialVersionUID = 4552780566836888472L;

	/**
	 * 外请车付款报表参数Dto
	 */
	private OfvPaymentReportQueryDto dto;

	/**
	 * 外请车付款统计信息
	 */
	private OfvPaymentReportResultDto ofvPaymentReportResultDto;
	
	/**
	 * 外请车付款报表结果dto集合
	 */
	private List<OfvPaymentReportResultDto> ofvPaymentReportResultDtoList;

	
	/**
	 * @get
	 * @return ofvPaymentReportResultDto
	 */
	public OfvPaymentReportResultDto getOfvPaymentReportResultDto() {
		/*
		 * @get
		 * @return ofvPaymentReportResultDto
		 */
		return ofvPaymentReportResultDto;
	}

	
	/**
	 * @set
	 * @param ofvPaymentReportResultDto
	 */
	public void setOfvPaymentReportResultDto(
			OfvPaymentReportResultDto ofvPaymentReportResultDto) {
		/*
		 *@set
		 *@this.ofvPaymentReportResultDto = ofvPaymentReportResultDto
		 */
		this.ofvPaymentReportResultDto = ofvPaymentReportResultDto;
	}

	/**
	 * @return dto
	 */
	public OfvPaymentReportQueryDto getDto() {
		return dto;
	}

	/**
	 * @param  dto  
	 */
	public void setDto(OfvPaymentReportQueryDto dto) {
		this.dto = dto;
	}

	/**
	 * @return ofvPaymentReportResultDtoList
	 */
	public List<OfvPaymentReportResultDto> getOfvPaymentReportResultDtoList() {
		return ofvPaymentReportResultDtoList;
	}

	/**
	 * @param  ofvPaymentReportResultDtoList  
	 */
	public void setOfvPaymentReportResultDtoList(
			List<OfvPaymentReportResultDto> ofvPaymentReportResultDtoList) {
		this.ofvPaymentReportResultDtoList = ofvPaymentReportResultDtoList;
	}

}
