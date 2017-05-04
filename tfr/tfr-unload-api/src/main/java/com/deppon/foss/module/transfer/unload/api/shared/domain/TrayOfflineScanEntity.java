package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TrayOfflineScanEntity extends BaseEntity{
	
	private static final long serialVersionUID = 6041437735642408920L;
	
	/**运单号*/
	private String waybillNo;
	/**流水号*/
	private String serialNo;
	/**包号**/
	private String packageNo;
	/**离线扫描时间*/
	private Date trayOfflineScanTime;
	/**叉车司机姓名*/
	private String forkLiftDriverName;
	/**叉车司机工号*/
	private String forkLiftDriverCode;
	/**叉车司机部门*/
	private String forkLiftDept;
	/**叉车离线扫描任务号*/
	private String offlineTaskNo;
	/**托盘任务号*/
	private String taskNo;
	/**创建人*/
	private String createUserName;
	/**创建人工号*/
	private String createUserCode;
	/**创建人部门*/
	private String createrDept;
	/**叉车票数*/
	private Integer forkliftCount;
	/**操作类型*/
	private String operatType;
	/**外场编码*/
	private String outFieldCode;
	/**卸车任务创建人工号*/
	private String loaderCode;
	/**卸车任务创建人姓名*/
	private String loaderName;
	/**卸车任务创建人部门*/
	private String loadOrgName;
	
	
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getLoaderCode() {
		return loaderCode;
	}
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	public String getLoaderName() {
		return loaderName;
	}
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	public String getLoadOrgName() {
		return loadOrgName;
	}
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getTrayOfflineScanTime() {
		return trayOfflineScanTime;
	}
	public void setTrayOfflineScanTime(Date trayOfflineScanTime) {
		this.trayOfflineScanTime = trayOfflineScanTime;
	}
	public String getForkLiftDriverName() {
		return forkLiftDriverName;
	}
	public void setForkLiftDriverName(String forkLiftDriverName) {
		this.forkLiftDriverName = forkLiftDriverName;
	}
	public String getForkLiftDriverCode() {
		return forkLiftDriverCode;
	}
	public void setForkLiftDriverCode(String forkLiftDriverCode) {
		this.forkLiftDriverCode = forkLiftDriverCode;
	}
	public String getForkLiftDept() {
		return forkLiftDept;
	}
	public void setForkLiftDept(String forkLiftDept) {
		this.forkLiftDept = forkLiftDept;
	}
	public String getOfflineTaskNo() {
		return offlineTaskNo;
	}
	public void setOfflineTaskNo(String offlineTaskNo) {
		this.offlineTaskNo = offlineTaskNo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreaterDept() {
		return createrDept;
	}
	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}
	public Integer getForkliftCount() {
		return forkliftCount;
	}
	public void setForkliftCount(Integer forkliftCount) {
		this.forkliftCount = forkliftCount;
	}
	public String getOperatType() {
		return operatType;
	}
	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}
	public String getOutFieldCode() {
		return outFieldCode;
	}
	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}
	
	
	
}
