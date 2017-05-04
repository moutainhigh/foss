package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class Dates4StockDurationDto implements Serializable {

	private static final long serialVersionUID = -5018273965991422846L;

	/**
	 * 卸车任务开始时间
	 */
	private Date unloadTaskBeginTime;

	/**
	 * 卸车扫描时间
	 */
	private Date unloadScanTime;

	/**
	 * 托盘绑定时间
	 */
	private Date trayBindingTime;

	/**
	 * 叉车扫描时间
	 */
	private Date forkliftScanTime;

	public Date getUnloadTaskBeginTime() {
		return unloadTaskBeginTime;
	}

	public void setUnloadTaskBeginTime(Date unloadTaskBeginTime) {
		this.unloadTaskBeginTime = unloadTaskBeginTime;
	}

	public Date getUnloadScanTime() {
		return unloadScanTime;
	}

	public void setUnloadScanTime(Date unloadScanTime) {
		this.unloadScanTime = unloadScanTime;
	}

	public Date getTrayBindingTime() {
		return trayBindingTime;
	}

	public void setTrayBindingTime(Date trayBindingTime) {
		this.trayBindingTime = trayBindingTime;
	}

	public Date getForkliftScanTime() {
		return forkliftScanTime;
	}

	public void setForkliftScanTime(Date forkliftScanTime) {
		this.forkliftScanTime = forkliftScanTime;
	}

}
