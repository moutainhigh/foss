package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 系统帮助
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午3:32:35
 */
public class SystemHelpEntity extends BaseEntity {

	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5564860878458657662L;
	/**
	 * 标题
	 */
	private String topic;
	/**
	 * 系统内容
	 */
	private String systemHelp;
	/**
	 * 状态 是否激活，激活为Y
	 */
	private String active;
	
	/**
	 * @get
	 * @return topic
	 */
	public String getTopic() {
		/*
		 * @get
		 * @return topic
		 */
		return topic;
	}
	
	/**
	 * @set
	 * @param topic
	 */
	public void setTopic(String topic) {
		/*
		 *@set
		 *@this.topic = topic
		 */
		this.topic = topic;
	}
	
	/**
	 * @get
	 * @return systemHelp
	 */
	public String getSystemHelp() {
		/*
		 * @get
		 * @return systemHelp
		 */
		return systemHelp;
	}
	
	/**
	 * @set
	 * @param systemHelp
	 */
	public void setSystemHelp(String systemHelp) {
		/*
		 *@set
		 *@this.systemHelp = systemHelp
		 */
		this.systemHelp = systemHelp;
	}
	
	/**
	 * @get
	 * @return active
	 */
	public String getActive() {
		/*
		 * @get
		 * @return active
		 */
		return active;
	}
	
	/**
	 * @set
	 * @param active
	 */
	public void setActive(String active) {
		/*
		 *@set
		 *@this.active = active
		 */
		this.active = active;
	}
	
	
}
