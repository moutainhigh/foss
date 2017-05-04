package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.util.Date;

import com.deppon.foss.util.DateUtils;

/**
 * 用来查询卸车流水信息
 * 后续可以继续添加字段
 * @author 311396
 *
 */
public class UnloadSerialDetaiDto {
	private String serialNo;
	private Date unloadTime;
	private String unloadTimeStr;
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getUnloadTime() {
		return unloadTime;
	}
	public void setUnloadTime(Date unloadTime) {
		this.unloadTime = unloadTime;
	}
	public String getUnloadTimeStr() {
		return unloadTimeStr;
	}
	public void setUnloadTimeStr() {
		this.unloadTimeStr = DateUtils.convert(this.unloadTime, DateUtils.DATE_TIME_FORMAT);
	}
	
}
