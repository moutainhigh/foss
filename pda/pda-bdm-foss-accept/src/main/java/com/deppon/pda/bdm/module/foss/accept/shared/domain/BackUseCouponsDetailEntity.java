package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file BackUseCouponsDetailEntity.java
 * @description 使用优惠券
 * @author ChenLiang
 * @created 2012-12-31 下午3:01:07
 * @version 1.0
 */
public class BackUseCouponsDetailEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 能否反使用
	 */
	private String isCanAntiUse;

	/**
	 * 不能反使用的原因
	 */
	private String reason;

	public String getIsCanAntiUse() {
		return isCanAntiUse;
	}

	public void setIsCanAntiUse(String isCanAntiUse) {
		this.isCanAntiUse = isCanAntiUse;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
