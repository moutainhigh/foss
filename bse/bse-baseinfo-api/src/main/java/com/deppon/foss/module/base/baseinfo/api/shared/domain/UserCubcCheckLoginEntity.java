package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;
/**
 * @date 2017年3月21日 14:04:14
 * @author 378375
 * 仅供CUBC校验当前登录人是否登录使用
 */
@SuppressWarnings("serial")
public class UserCubcCheckLoginEntity implements Serializable {
	
	/**
	 * 主页点击cubc按钮时  随机生成
	 */
	private String uuid;
	
	/**
	 * 标识  true:表示校验成功  false:表示校验失败
	 */
	private boolean resultMark;
	/**
	 * 当前登录人的用户名
	 */
	private String userName;
	/**
	 * 绑定uuid和用户名的session的id
	 */
	private String sessionId;
	/**
	 * cubc返回的登录ip
	 */
	private String ip;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isResultMark() {
		return resultMark;
	}
	public void setResultMark(boolean resultMark) {
		this.resultMark = resultMark;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
