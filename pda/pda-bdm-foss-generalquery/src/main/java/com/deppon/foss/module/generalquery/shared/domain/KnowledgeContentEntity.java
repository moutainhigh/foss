package com.deppon.foss.module.generalquery.shared.domain;

/**
 * wfy:知识库实体类
 */
public class KnowledgeContentEntity {
	//内容
	private String content;
	//版本号
	private String version;
	
	//动作（0：增；1:修改；2：删）
    private String operType;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
}
