package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 行业货源类别实体
 * @author 198771
 *
 */
public class SourceCategoriesEntity extends BaseEntity{
	
	private static final long serialVersionUID = 6364613164403610L;
	
	/**
	 * 类别
	 */
	private String category;
	
	/**
	 * 品名
	 */
	private String name;
	
	/**
	 * 是否启用
	 */
	private String active;

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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
