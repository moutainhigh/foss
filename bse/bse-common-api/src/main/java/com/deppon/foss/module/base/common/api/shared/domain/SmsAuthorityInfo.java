package com.deppon.foss.module.base.common.api.shared.domain;


/**
 * The Class SmsAuthorityInfo.
 */
public class SmsAuthorityInfo {

	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The postMethod. */
	private String wsUrl;
	
	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	 
	
	public String getWsUrl() {
		return wsUrl;
	}

	
	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
