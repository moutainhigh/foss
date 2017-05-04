package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 
 * 审核结果
 * 
 * @author dp-zengbinwen
 * @date 2012-10-24 下午2:22:25
 */
public enum AuditResultEnum {

	// 通过，退回
	PASSED("passed"), RETURNED("returned");

	private String result;

	private AuditResultEnum(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
