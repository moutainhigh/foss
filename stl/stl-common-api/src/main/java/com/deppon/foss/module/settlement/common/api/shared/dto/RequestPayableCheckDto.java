package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.RequestPayableCheckEntity;

/**
 * @218392 张永雪
 * @author 218392
 * @date 2016-06-21 23:08:10
 * 应付单和付款单付款时候，校验VTS运单号和来源单号：
 * 1.合同编码是否存在；2.单号+合同编码一一对应关系；3.运单状态：作废，不允许发起付款。
 */
public class RequestPayableCheckDto {
	
	/**
	 * VTS请求参数固定写法：默认值是contractOfCarriageService
	 */
	private String type;
	
	/**
	 * 请求参数List
	 */
	private List<RequestPayableCheckEntity> requestEntity;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RequestPayableCheckEntity> getRequestEntity() {
		return requestEntity;
	}

	public void setRequestEntity(List<RequestPayableCheckEntity> requestEntity) {
		this.requestEntity = requestEntity;
	}
	
	
	
}
