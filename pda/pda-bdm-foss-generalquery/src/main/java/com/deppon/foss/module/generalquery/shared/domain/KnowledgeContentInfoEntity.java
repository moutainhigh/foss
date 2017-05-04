package com.deppon.foss.module.generalquery.shared.domain;

import java.util.Date;

/**
 * wfy:类型封装实体类
 */
public class KnowledgeContentInfoEntity {
	//标题
	private String title;
	//版本号
	private long versionLong;
	//版本号对应的时间
	private Date versionDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getVersionLong() {
		return versionLong;
	}
	public void setVersionLong(long versionLong) {
		this.versionLong = versionLong;
	}
	public Date getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	
}
