package com.deppon.foss.module.login.shared.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;

/**
 * GUI登陆参数传递
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-8 上午11:06:43,content:TODO </p>
 * @author ningyu
 * @date 2013-3-8 上午11:06:43
 * @since
 * @version
 */
public class LoginForGUIInfo implements Serializable{

	/**
	  * serialVersionUID:类唯一标识
	  *
	*/
	private static final long serialVersionUID = 6586635903305099436L;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * host名称
	 */
	private String hostName;
	
	/**
	 * mac地址
	 */
	private String macAddress;
	
	/**
	 * IP 地址
	 */
	private String ipAddress;
	
	/**
	 * 客户端版本号
	 */
	private String clientVersion;
	
	/**
	 * @return the clientVersion
	 */
	
	private String pc_mac;
	
	private String pc_ip;
	
	private String pc_hostName;
	
	public String getClientVersion() {
		return clientVersion;
	}

	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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
	 * @return the pc_hostName
	 */
	public String getPc_hostName() {
		return pc_hostName;
	}

	/**
	 * @param pc_hostName the pc_hostName to set
	 */
	public void setPc_hostName(String pc_hostName) {
		this.pc_hostName = pc_hostName;
	}

	
}
