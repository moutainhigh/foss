package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * 
 * 代收货款支付结果
 * 
 * @author dp-zengbinwen
 * @date 2012-10-26 上午9:36:57
 */
public enum PaymentResultEnum {

	// 成功、失败、汇款成功转失败
	SUCCESS("success"), FAILURE("failure"), SUCCESS2FAIL("success2fail");

	// 汇款结果
	private String result;

	private PaymentResultEnum(String result) {
		this.result = result;
	}

	/**
	 * @return result
	 */
	public String getResult() {
		return result;
	}
}
