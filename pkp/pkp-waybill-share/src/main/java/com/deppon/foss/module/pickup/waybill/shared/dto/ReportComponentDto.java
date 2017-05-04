package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

public class ReportComponentDto {
	//用户手机号码
    private String mibilephone;
    //查询开始时间
    private Date startTime;
    //查询结束时间
    private Date endTime;
    //是否有效
    private String active;
    
    
    
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getMibilephone() {
		return mibilephone;
	}
	public void setMibilephone(String mibilephone) {
		this.mibilephone = mibilephone;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
    
    
}
