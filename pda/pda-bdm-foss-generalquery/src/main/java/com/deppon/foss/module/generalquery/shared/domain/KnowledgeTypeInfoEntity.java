package com.deppon.foss.module.generalquery.shared.domain;

import java.util.Date;

/**
 * wfy:知识库类型相关信息实体（查询数据库）
 */
public class KnowledgeTypeInfoEntity {

	private String deptName;
	private String typeName;
	private long versionLong;
	private Date versionDate;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public long getVersionLong() {
		return versionLong;
	}
	public void setVersionLong(long versionLong) {
		this.versionLong = versionLong;
	}
	public Date getVersionDate() {
		return versionDate;
	}
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
