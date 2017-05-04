package com.deppon.foss.module.transfer.common.api.shared.domain;

public class TfrNotifyConfig {
	private String className;
	private Integer sleepTime;
	private Integer nodataSleepTime;
	private Integer notifyCount;
	private Integer	notifyTimeLag;
	private Integer	notifyMaxNum;
	public String getClassName() {
		return className;
	}
	public Integer getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}
	public Integer getNodataSleepTime() {
		return nodataSleepTime;
	}
	public void setNodataSleepTime(Integer nodataSleepTime) {
		this.nodataSleepTime = nodataSleepTime;
	}
	public Integer getNotifyCount() {
		return notifyCount;
	}
	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}
	public Integer getNotifyTimeLag() {
		return notifyTimeLag;
	}
	public void setNotifyTimeLag(Integer notifyTimeLag) {
		this.notifyTimeLag = notifyTimeLag;
	}
	public Integer getNotifyMaxNum() {
		return notifyMaxNum;
	}
	public void setNotifyMaxNum(Integer notifyMaxNum) {
		this.notifyMaxNum = notifyMaxNum;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
