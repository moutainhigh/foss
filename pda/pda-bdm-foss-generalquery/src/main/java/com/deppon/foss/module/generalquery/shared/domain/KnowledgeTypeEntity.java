package com.deppon.foss.module.generalquery.shared.domain;

/**
 * 知识库类型相关信息实体（前台传过来的)
 */
public class KnowledgeTypeEntity {

	//类型名称
	private String typeName;
    
	//版本号(String)
	private String version;

	

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	

	
}
