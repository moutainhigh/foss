package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file UseCoponDetailEntity.java 
 * @description 使用优惠券返回实体
 * @author ChenLiang
 * @created 2012-12-31 下午3:04:17    
 * @version 1.0
 */
public class UseCoponDetailEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否能使用
	 */
	private String isUse;

	/**
	 * 优惠金额
	 */
	private double couponMoney;

	/**
	 * 不能使用的原因
	 */
	private String reason;

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public double getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(double couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
