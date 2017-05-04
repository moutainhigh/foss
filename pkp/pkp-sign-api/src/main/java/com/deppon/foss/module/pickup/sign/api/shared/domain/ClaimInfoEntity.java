package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.io.Serializable;

public class ClaimInfoEntity implements Serializable{

	/**
	 * 序列号 
	 */
	private static final long serialVersionUID = -4276144540602805175L;

	/**
	 * 理赔状态
	 */
	private boolean claimIsExist;
	/**
	 * 结果
	 */
	private String reason;
	
	public boolean isClaimIsExist() {
		return claimIsExist;
	}
	public void setClaimIsExist(boolean claimIsExist) {
		this.claimIsExist = claimIsExist;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
