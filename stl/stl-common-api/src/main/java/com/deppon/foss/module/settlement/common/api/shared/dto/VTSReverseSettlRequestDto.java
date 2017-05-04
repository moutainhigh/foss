package com.deppon.foss.module.settlement.common.api.shared.dto;

import com.deppon.foss.module.settlement.common.api.shared.domain.VTSReverseSettlRequest;
/**
 * 为了获取VTS的请求实体Dto
 * @author 218392 zhangyongxue 
 * @date 2016-06-18 12:16:00
 */
public class VTSReverseSettlRequestDto {
	
	 /**
	  * 反签收查询参数实体
	  */
	private VTSReverseSettlRequest vTSReverseSettlRequest;
	
	/**
	 * VTS统一格式的请求类型
	 */
	private String type;

	public VTSReverseSettlRequest getvTSReverseSettlRequest() {
		return vTSReverseSettlRequest;
	}

	public void setvTSReverseSettlRequest(
			VTSReverseSettlRequest vTSReverseSettlRequest) {
		this.vTSReverseSettlRequest = vTSReverseSettlRequest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
