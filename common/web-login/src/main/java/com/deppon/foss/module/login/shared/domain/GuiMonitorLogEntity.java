/**   
 * @Title: GuiMonitorLogEntity.java 
 * @Package com.deppon.foss.module.login.shared.domain 
 * @Description: 用户登录系统监控日志，一个月以后被干掉
 * @author 270293   
 * @date 2015-8-31 上午9:17:46 
 * @version V1.0   
 */
package com.deppon.foss.module.login.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.entity.IEntity;

/**
 * @ClassName: GuiMonitorLogEntity
 * @Description: 用户登录系统监控日志
 * @author 270293-foss-zhangfeng
 * @date 2015-8-31 上午9:17:46
 * 
 */
public class GuiMonitorLogEntity extends BaseEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5388469157317984184L;
	private String Id;

	private String code;

	private String name;

	/*
	 * 本机mac
	 */
	private String macAddress;
	/*
	 * 本机ip
	 */
	private String ipAddress;
	/*
	 * 本机主机名
	 */
	private String hostName;

	private Date loginTime;

	private Date last_beat_time;
	/*
	 * pcinfo mac
	 */
	private String pc_mac;
	/*
	 * pcinfo ip
	 */

	private String pc_ip;

	/*
	 * pcinfo hostName
	 */
	private String pc_host;

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress
	 *            the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName
	 *            the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the last_beat_time
	 */
	public Date getLast_beat_time() {
		return last_beat_time;
	}

	/**
	 * @param last_beat_time
	 *            the last_beat_time to set
	 */
	public void setLast_beat_time(Date last_beat_time) {
		this.last_beat_time = last_beat_time;
	}

	/**
	 * @return the pc_mac
	 */
	public String getPc_mac() {
		return pc_mac;
	}

	/**
	 * @param pc_mac the pc_mac to set
	 */
	public void setPc_mac(String pc_mac) {
		this.pc_mac = pc_mac;
	}

	/**
	 * @return the pc_ip
	 */
	public String getPc_ip() {
		return pc_ip;
	}

	/**
	 * @param pc_ip the pc_ip to set
	 */
	public void setPc_ip(String pc_ip) {
		this.pc_ip = pc_ip;
	}

	/**
	 * @return the pc_host
	 */
	public String getPc_host() {
		return pc_host;
	}

	/**
	 * @param pc_host the pc_host to set
	 */
	public void setPc_host(String pc_host) {
		this.pc_host = pc_host;
	}


}
