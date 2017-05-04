package com.deppon.foss.module.generalquery.shared.domain;

/**
 * wfy:知识库类型实体类（前台传过来的）
 */
public class KnowledgeInfoEntity {
	//标题
	private String title;
	//版本号
	private String version;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
