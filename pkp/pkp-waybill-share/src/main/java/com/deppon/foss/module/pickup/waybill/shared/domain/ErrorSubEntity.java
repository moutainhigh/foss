package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 差错子报主信息
 * @author foss-273279-Liding
 * @date 2015-8-17 16:41:05
 */
public class ErrorSubEntity implements Serializable {

	private static final long serialVersionUID = 826759534405657894L;

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
	
	/**
	 * errorId <p>getter method</p>
	 * @author 273279
	 * @return  the errorId
	 */
	public String getErrorId() {
		return errorId;
	}
	/**
	 * errorId <p>setter method</p>
	 * @author 273279
	 * @param errorId the errorId to set
	 */
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	/**
	 * incident <p>getter method</p>
	 * @author 273279
	 * @return  the incident
	 */
	public String getIncident() {
		return incident;
	}
	/**
	 * incident <p>setter method</p>
	 * @author 273279
	 * @param incident the incident to set
	 */
	public void setIncident(String incident) {
		this.incident = incident;
	}
	/**
	 * shortMessageCodes <p>getter method</p>
	 * @author 273279
	 * @return  the shortMessageCodes
	 */
	public String getShortMessageCodes() {
		return shortMessageCodes;
	}
	/**
	 * shortMessageCodes <p>setter method</p>
	 * @author 273279
	 * @param shortMessageCodes the shortMessageCodes to set
	 */
	public void setShortMessageCodes(String shortMessageCodes) {
		this.shortMessageCodes = shortMessageCodes;
	}
	/**
	 * shortMessageNames <p>getter method</p>
	 * @author 273279
	 * @return  the shortMessageNames
	 */
	public String getShortMessageNames() {
		return shortMessageNames;
	}
	/**
	 * shortMessageNames <p>setter method</p>
	 * @author 273279
	 * @param shortMessageNames the shortMessageNames to set
	 */
	public void setShortMessageNames(String shortMessageNames) {
		this.shortMessageNames = shortMessageNames;
	}
	/**
	 * respEmpCode <p>getter method</p>
	 * @author 273279
	 * @return  the respEmpCode
	 */
	public String getRespEmpCode() {
		return respEmpCode;
	}
	/**
	 * respEmpCode <p>setter method</p>
	 * @author 273279
	 * @param respEmpCode the respEmpCode to set
	 */
	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}
	/**
	 * respEmpName <p>getter method</p>
	 * @author 273279
	 * @return  the respEmpName
	 */
	public String getRespEmpName() {
		return respEmpName;
	}
	/**
	 * respEmpName <p>setter method</p>
	 * @author 273279
	 * @param respEmpName the respEmpName to set
	 */
	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	/**
	 * respDeptCode <p>getter method</p>
	 * @author 273279
	 * @return  the respDeptCode
	 */
	public String getRespDeptCode() {
		return respDeptCode;
	}
	/**
	 * respDeptCode <p>setter method</p>
	 * @author 273279
	 * @param respDeptCode the respDeptCode to set
	 */
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}
	/**
	 * respDeptName <p>getter method</p>
	 * @author 273279
	 * @return  the respDeptName
	 */
	public String getRespDeptName() {
		return respDeptName;
	}
	/**
	 * respDeptName <p>setter method</p>
	 * @author 273279
	 * @param respDeptName the respDeptName to set
	 */
	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}
	/**
	 * divisionCode <p>getter method</p>
	 * @author 273279
	 * @return  the divisionCode
	 */
	public String getDivisionCode() {
		return divisionCode;
	}
	/**
	 * divisionCode <p>setter method</p>
	 * @author 273279
	 * @param divisionCode the divisionCode to set
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	/**
	 * divisionName <p>getter method</p>
	 * @author 273279
	 * @return  the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}
	/**
	 * divisionName <p>setter method</p>
	 * @author 273279
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

}

