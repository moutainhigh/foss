package com.deppon.foss.module.settlement.closing.api.shared.domain;

/**
 * 大类小类实体
 * 
 * @author nanjusong
 * 
 */

public class BigSubTypeEntity {
	/**
	 * 明细视图名
	 */
	private String tableName;

	private String bigTypeCode;

	private String bigTypeName;

	private String subTypeCode;

	private String subTypeName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBigTypeCode() {
		return bigTypeCode;
	}

	public void setBigTypeCode(String bigTypeCode) {
		this.bigTypeCode = bigTypeCode;
	}

	public String getBigTypeName() {
		return bigTypeName;
	}

	public void setBigTypeName(String bigTypeName) {
		this.bigTypeName = bigTypeName;
	}

	public String getSubTypeCode() {
		return subTypeCode;
	}

	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

}
