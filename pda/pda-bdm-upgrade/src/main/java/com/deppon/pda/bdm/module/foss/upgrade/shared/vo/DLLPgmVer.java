package com.deppon.pda.bdm.module.foss.upgrade.shared.vo;

public class DLLPgmVer {
	/**
	 * 是否版本更新
	 * Y为需要更新。
	 * N为不需要更新
	 */
	private String reqUpgrade;
	/**
	 * 更新路径
	 */
	private String updUrl;
	
	public String getReqUpgrade() {
		return reqUpgrade;
	}
	public void setReqUpgrade(String reqUpgrade) {
		this.reqUpgrade = reqUpgrade;
	}
	public String getUpdUrl() {
		return updUrl;
	}
	public void setUpdUrl(String updUrl) {
		this.updUrl = updUrl;
	}
}
