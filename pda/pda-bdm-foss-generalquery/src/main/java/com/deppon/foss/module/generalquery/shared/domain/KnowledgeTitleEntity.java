package com.deppon.foss.module.generalquery.shared.domain;

import java.util.Date;

/**
 * 知识库标题实体（传给前台）
 */
public class KnowledgeTitleEntity {

	//标题名称
	private String title;
    
	//动作（0：增；1:修改；2：删）
	private String operType;
	
	//是否热点
	private String isHot;
	
	//创建时间
	public Date createTime;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
