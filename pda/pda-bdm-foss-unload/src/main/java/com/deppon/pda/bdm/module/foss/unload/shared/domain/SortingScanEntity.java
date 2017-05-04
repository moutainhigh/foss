package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;
/**
 * 分拣扫描实体
 * @author mt
 * 2013年7月17日17:40:43
 */
public class SortingScanEntity {
	/**
	 * 运单号
	 */
	private String wblCode;	
	/**
	 * 员工工号
	 */
	private String userCode;
	/**
	 * 部门编号
	 */
	private String deptCode;
	/**
	 * 操作时间
	 */
	private Date signTime;
	/**
	 * 扫描类型
	 */
	private String scanType;
	/**
	 * 标签号
	 */
	private String labelCode;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	
}
