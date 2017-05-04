package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;

/**
* @description 智能分拣柜拉取走货路由请求参数实体
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月23日 下午6:09:12
 */
public class RequestPathDetailParam {
	//拉取路由的部门
	private String depetCode;
	//来取起始时间
	private Date startTime;
	//拉取的截止时间
	private Date endTime;
	
	public String getDepetCode() {
		return depetCode;
	}
	public void setDepetCode(String depetCode) {
		this.depetCode = depetCode;
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
