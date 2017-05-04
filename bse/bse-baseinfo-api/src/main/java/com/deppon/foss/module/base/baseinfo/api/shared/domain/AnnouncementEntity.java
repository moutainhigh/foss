package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 公告信息实体
 * 
 * @author Administrator
 * 
 */
public class AnnouncementEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 标题
	 */
	private String topic;
	/**
	 * 公告内容
	 */
	private String announcement;
	/**
	 * 状态 是否激活，激活为Y
	 */
	private String active;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getActive() {
		return active;
	}



	public void setActive(String active) {
		this.active = active;
	}

}
