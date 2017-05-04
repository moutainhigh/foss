package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AnnouncementEntity;

/**
 * 系统公告的dto
 * @author zengjunfan
 * @date	2013-4-23上午8:14:52
 * 
 */
public class AnnouncementDto {
	//公告实体
	private AnnouncementEntity announcementEntity;
	//查询的开始时间
	private Date startTime;
	//查询的结束时间
	private Date endTime;
	
	
	public AnnouncementEntity getAnnouncementEntity() {
		return announcementEntity;
	}
	public void setAnnouncementEntity(AnnouncementEntity announcementEntity) {
		this.announcementEntity = announcementEntity;
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
