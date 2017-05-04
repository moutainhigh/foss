package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * FOSS申请反结清请求参数
 * 
 * @date 2016-11-02
 * @author 378375
 *
 */
public class FossQueryRequestDto implements Serializable {

	/**
	 * @Fields serialVersionUID : CUBC结清查询DTO
	 */
	private static final long serialVersionUID = 2676358192382732950L;
	/**
	 * 来源单号
	 */
	private String sourceNo;
	public String getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}
	public FossQueryRequestDto(String sourceNo) {
		super();
		this.sourceNo = sourceNo;
	}
	
	
}
