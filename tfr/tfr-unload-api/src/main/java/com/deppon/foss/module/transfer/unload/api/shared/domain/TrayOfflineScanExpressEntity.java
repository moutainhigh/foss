package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TrayOfflineScanExpressEntity extends BaseEntity{
	
	private static final long serialVersionUID = 6041437735642408920L;
	
	/**运单/包/笼号*/
	private String WaybillNo;
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
	
	//返回的数据总数
	private long count;
	
	
	
	public String getWaybillNo() {
		return WaybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		WaybillNo = waybillNo;
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
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
	
}
