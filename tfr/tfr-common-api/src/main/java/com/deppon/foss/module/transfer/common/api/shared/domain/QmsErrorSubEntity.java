package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

public class QmsErrorSubEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	//差错编号
	protected String errorId;
	
	//事情经过
	protected String incident;
	//短信通知对象工号
	protected String shortMessageCodes;
	//短信通知对象名称
	protected String shortMessageNames;
	//责任人工号
	protected String respEmpCode;
	protected String respEmpName;
  	//责任部门标杆编码
	protected String respDeptCode;
	protected String respDeptName;
  	//责任事业部标杆编码
	protected String divisionCode;
	protected String divisionName;
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	public String getShortMessageCodes() {
		return shortMessageCodes;
	}
	public void setShortMessageCodes(String shortMessageCodes) {
		this.shortMessageCodes = shortMessageCodes;
	}
	public String getShortMessageNames() {
		return shortMessageNames;
	}
	public void setShortMessageNames(String shortMessageNames) {
		this.shortMessageNames = shortMessageNames;
	}
	public String getRespEmpCode() {
		return respEmpCode;
	}
	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}
	public String getRespEmpName() {
		return respEmpName;
	}
	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}
	public String getRespDeptName() {
		return respDeptName;
	}
	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
}

