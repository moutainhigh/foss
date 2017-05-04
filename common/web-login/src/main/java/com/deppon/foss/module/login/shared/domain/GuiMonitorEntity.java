package com.deppon.foss.module.login.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.entity.IEntity;

/**
 * 
 * 公告信息实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-15 下午1:53:04,content:TODO </p>
 * @author ztjie
 * @date 2013-3-15 下午1:53:04
 * @since
 * @version
 */
public class GuiMonitorEntity extends BaseEntity implements IEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8395600871592384493L;

	private String id;
	
	private String code;
	
	private String name;
	
	private String macAddress;
	
	private String ipAddress;
	
	private String hostName;
	
	private Date loginTime;
	
	/**
	 * 下载开始时间 MANA-2018
	 */
	private Date downloadStartTime;
	
	/**
	 * 下载结束时间 MANA-2018
	 */
	private Date downloadEndTime;
	
	/**
	 * 下载状态: MANA-2018
	 * 0未开始,
	 * 1下载中,
	 * 2下载结束,
	 * -1下载异常中断
	 */
	private String downloadStatus;
	
	/**
	 * 最近心跳时间MANA-2018
	 */
	private Date lastBeatTime;

	/**
	 * 客户端版本号
	 */
	private String pgmVersion;
	
	/**
	 * @return the clientVersion
	 */
	public String getPgmVersion() {
		return pgmVersion;
	}

	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setPgmVersion(String clientVersion) {
		this.pgmVersion = clientVersion;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getDownloadStartTime() {
		return downloadStartTime;
	}

	public void setDownloadStartTime(Date downloadStartTime) {
		this.downloadStartTime = downloadStartTime;
	}

	public Date getDownloadEndTime() {
		return downloadEndTime;
	}

	public void setDownloadEndTime(Date downloadEndTime) {
		this.downloadEndTime = downloadEndTime;
	}

	public String getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public Date getLastBeatTime() {
		return lastBeatTime;
	}

	public void setLastBeatTime(Date lastBeatTime) {
		this.lastBeatTime = lastBeatTime;
	}
}
