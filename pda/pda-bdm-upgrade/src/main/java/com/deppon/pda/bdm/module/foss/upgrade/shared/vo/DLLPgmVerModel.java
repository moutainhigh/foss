package com.deppon.pda.bdm.module.foss.upgrade.shared.vo;

public class DLLPgmVerModel {
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
	/**
	 * 当前年月日
	 */
	private String nowDate;
	/**
	 * 文件名称
	 */
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
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
