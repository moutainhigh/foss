package com.deppon.foss.module.generalquery.shared.domain;

/**
 * 返回最近两个热点知识库
 */
public class KnowledgeTimeEntity {
	//ID
	private String id;
	//标题
	private String title;
	//简介
	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
