package com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi;

import java.util.Date;

/**
 * 分拣扫描和查询补码 请求实体
 * @author 245955
 * @date 2015-08-14
 * @version 1.0
 * @since
 */
public class QrySortingReqModel {
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
	/**
	 * 设备编号
	 */
	private String pdaCode;
	//是否扫描
	private String ifPackage;
	/**
	 *同城或非同城
	 */
	private boolean queryType;
	
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
	public String getPdaCode() {
		return pdaCode;
	}
	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}
	public String getIfPackage() {
		return ifPackage;
	}
	public void setIfPackage(String ifPackage) {
		this.ifPackage = ifPackage;
	}
	public boolean isQueryType() {
		return queryType;
	}
	public void setQueryType(boolean queryType) {
		this.queryType = queryType;
	}
}
