package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

public class UnloadbindTrayDetailDto implements Serializable{

	/**
	 * @author 105795
	 * @date  2014-01-03
	 * 卸车任务绑定托盘运单明细
	 */
	private static final long serialVersionUID = 4035577588006216127L;

	
	private String taskNo;//托盘任务编号
	
	private String waybillNo;//运单号
	
	private String bindUserName;//绑定人姓名
	
	private String bindUserCode;//绑定人工号
	
	private String bindTraytaskTime;//绑定托盘任务时间
	
	private String forkliftDriverName;//叉车司机姓名
	
	private String forkliftDriverCode;//叉车司机工号
	
	private String trayscanTime;//叉车扫描时间
	
	private String packageNo;//包号
	
	private long count;//返回的数据总数
	
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getBindUserName() {
		return bindUserName;
	}
	public void setBindUserName(String bindUserName) {
		this.bindUserName = bindUserName;
	}
	public String getBindUserCode() {
		return bindUserCode;
	}
	public void setBindUserCode(String bindUserCode) {
		this.bindUserCode = bindUserCode;
	}
	public String getBindTraytaskTime() {
		return bindTraytaskTime;
	}
	public void setBindTraytaskTime(String bindTraytaskTime) {
		this.bindTraytaskTime = bindTraytaskTime;
	}
	public String getForkliftDriverName() {
		return forkliftDriverName;
	}
	public void setForkliftDriverName(String forkliftDriverName) {
		this.forkliftDriverName = forkliftDriverName;
	}
	public String getForkliftDriverCode() {
		return forkliftDriverCode;
	}
	public void setForkliftDriverCode(String forkliftDriverCode) {
		this.forkliftDriverCode = forkliftDriverCode;
	}
	public String getTrayscanTime() {
		return trayscanTime;
	}
	public void setTrayscanTime(String trayscanTime) {
		this.trayscanTime = trayscanTime;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
	
}
