package com.deppon.foss.module.generalquery.shared.domain;

/**
 * 查阅量
 * @ClassName KqueryCount.java 
 * @Description 
 * @author 245955
 * @date 2015-5-8
 */
public class KqueryCountEntity {
	
	private String userCode;//查询标题的员工
	private String isRead;//是否已读
	private String title;//新知识标题
	
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
