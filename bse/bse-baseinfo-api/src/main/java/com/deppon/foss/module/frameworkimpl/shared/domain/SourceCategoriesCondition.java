package com.deppon.foss.module.frameworkimpl.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 行业货源基础资料查询条件
 * @author 198771
 *
 */
public class SourceCategoriesCondition implements Serializable{
	private static final long serialVersionUID = 3418913214439505029L;
	
	/**
	 * 类别
	 */
	private String category;
	
	/**
	 * 品名
	 */
	private String name;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 状态
	 */
	private String active;
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
