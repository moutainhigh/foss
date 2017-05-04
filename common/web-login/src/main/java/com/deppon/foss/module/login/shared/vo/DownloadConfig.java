package com.deppon.foss.module.login.shared.vo;

import java.io.Serializable;

/**
 * 下载配置  MANA-2018
 * @author 157229-zxy
 * @date 2014-03-11
 */
public class DownloadConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 最大下载页数
	 */
	private int maxPageSize = 800;
	
	/**
	 * 请求令牌间隔
	 */
	private int requestInterval = 600000;
	
	/**
	 * 心跳间隔
	 */
	private int beatInterval = 300000;

	public int getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	public int getRequestInterval() {
		return requestInterval;
	}

	public void setRequestInterval(int requestInterval) {
		this.requestInterval = requestInterval;
	}

	public int getBeatInterval() {
		return beatInterval;
	}

	public void setBeatInterval(int beatInterval) {
		this.beatInterval = beatInterval;
	}

}
