package com.deppon.foss.module.login.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.entity.IEntity;

/**
 * 
 * 公告信息实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-3-15 下午1:53:04,content:TODO </p>
 * @author ztjie
 * @date 2013-3-15 下午1:53:04
 * @since
 * @version
 */
public class AnnouncementEntity extends BaseEntity implements IEntity {

	private static final long serialVersionUID = -3208630716979760996L;

	private String topic;
	
	private String announcement;
	
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
