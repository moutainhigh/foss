package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 
 * 代收货款线上汇款结果ENUM
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-7 下午5:18:42
 */
public enum BillCODOnlineResultEnum {

	// 汇款成功、汇款失败、汇款成功转失败
	SUCCESS("S"), FAILURE("F"), REMITTANCE_SUCCESS_TO_FAIL("RSTF");

	/**
	 * 汇款结果
	 */
	private String result;

	private BillCODOnlineResultEnum(String result) {
		this.result = result;
	}

	/**
	 * @return result
	 */
	public String getResult() {
		return result;
	}
}
