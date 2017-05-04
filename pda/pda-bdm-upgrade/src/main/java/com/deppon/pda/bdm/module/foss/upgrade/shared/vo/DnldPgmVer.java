package com.deppon.pda.bdm.module.foss.upgrade.shared.vo;

import java.io.Serializable;

/**
 * 
 * TODO(PDA版本升级下载)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-1-4 下午1:47:40,content:TODO </p>
 * @author chengang
 * @date 2013-1-4 下午1:47:40
 * @since
 * @version
 */
public class DnldPgmVer implements Serializable{
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前版本
	 */
	private String currVer;
	/**
	 * 是否版本更新
	 */
	private String reqUpgrade;
	/**
	 * 更新路径
	 */
	private String updUrl;
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 当前时间
	 */
	private String nowTime;
	
	public String getCurrVer() {
		return currVer;
	}
	public void setCurrVer(String currVer) {
		this.currVer = currVer;
	}
	public String isReqUpgrade() {
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
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getReqUpgrade() {
		return reqUpgrade;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
	
	
	
}