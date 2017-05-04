package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 走货实体类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-09-23
 */
public class TranSportEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 走货方式
	 */
	private String tranSport;
	/**
	 * 运输性质
	 */
	private String transMode;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private String updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 生效时间
	 */
	private String activeTime;
	public String getTranSport() {
		return tranSport;
	}
	public void setTranSport(String tranSport) {
		this.tranSport = tranSport;
	}
	public String getTransMode() {
		return transMode;
	}
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	
	
}
