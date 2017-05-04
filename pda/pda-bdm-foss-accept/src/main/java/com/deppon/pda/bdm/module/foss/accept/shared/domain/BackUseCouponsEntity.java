package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file BackUseCouponsEntity.java
 * @description 退回优惠券使用
 * @author ChenLiang
 * @created 2012-12-31 下午3:01:26
 * @version 1.0
 */
public class BackUseCouponsEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券编号
	 */
	private String couponCode;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

}
