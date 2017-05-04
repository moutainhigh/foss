package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class PositionEntity extends BaseEntity  {

	/**
	 * 人员
	 * 
	 * @author 130346-foss-lifanghong
	 * @date 2014-04-29 上午11:57:45
	 */
	private static final long serialVersionUID = 6390934461131903610L;
	private String id;
	/**
	 * 职位
	 */
	private String title;
	
	/**
	 * 职位code
	 */
	private String titleCode;
	
	/**
	 * 
	 * @return
	 */
	private String active;
	
	 /**
     * 查询参数
     */
    private String queryParam;
    
	
	public String getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	
}
