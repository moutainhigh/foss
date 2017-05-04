package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.Date;

/**
 * 
 * @ClassName QrySelfDeryInfo
 * @Description 到达联校验请求实体
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class QrySelfDeryInfoEntity {
	//到达联编号
	private String arrInfoCode;
	//扫描时间
	private Date scanTime;
	
	public String getArrInfoCode() {
		return arrInfoCode;
	}

	public void setArrInfoCode(String arrInfoCode) {
		this.arrInfoCode = arrInfoCode;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
}
