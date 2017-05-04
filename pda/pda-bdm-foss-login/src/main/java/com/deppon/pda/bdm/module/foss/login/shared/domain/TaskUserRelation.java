package com.deppon.pda.bdm.module.foss.login.shared.domain;

public class TaskUserRelation {
	private String id;
	/**
	 * pda编号
	 */
	private String pdaCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	/**
	 * 任务编号
	 */
	private String taskCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 是否主PDA
	 */
	private String isAdmin;
	/**
	 * 是否完成
	 */
	private String status;
	/**
	 * 是否提交
	 */
	private String submit;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 单号
	 */
	private String listCode;
	public String getPdaCode() {
		return pdaCode;
	}
	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getListCode() {
		return listCode;
	}
	public void setListCode(String listCode) {
		this.listCode = listCode;
	}
	
	
}
