package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合伙人带货款上限返回实体
 * 2016年1月28日 16:30:58 葛亮亮
 */
public class SynPartenerCodAmountUpperLimitResponse implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3609987143168141010L;
	
	//上限金额
	private BigDecimal maxMoney;
	//访问状态
	private Boolean success;
	//异常信息
	private String errorMsg;

	public BigDecimal getMaxMoney() {
		return maxMoney;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
