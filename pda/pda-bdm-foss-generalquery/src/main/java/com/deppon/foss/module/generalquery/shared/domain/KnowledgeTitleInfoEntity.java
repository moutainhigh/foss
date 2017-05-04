package com.deppon.foss.module.generalquery.shared.domain;

import java.util.List;

/**
 * 知识库标题实体（传给前台）
 */
public class KnowledgeTitleInfoEntity {

	//版本
	private String version;
	
	private List<KnowledgeTitleEntity> knowledgeTitleEntityList;

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<KnowledgeTitleEntity> getKnowledgeTitleEntityList() {
		return knowledgeTitleEntityList;
	}

	public void setKnowledgeTitleEntityList(
			List<KnowledgeTitleEntity> knowledgeTitleEntityList) {
		this.knowledgeTitleEntityList = knowledgeTitleEntityList;
	}
	
}
