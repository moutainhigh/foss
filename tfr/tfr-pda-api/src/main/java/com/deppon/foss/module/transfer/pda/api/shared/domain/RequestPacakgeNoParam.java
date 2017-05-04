package com.deppon.foss.module.transfer.pda.api.shared.domain;

/*
 * 
* @description 智能分拣柜打印标签时请求实体
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年4月23日 下午6:02:59
 */
public class RequestPacakgeNoParam {
	//打印人姓名
	private String userName;
	//打印人工号
	private String userCode;
	//打印人部门名称
	private String deptName;
	//打印人部门编码
	private String deptCode;
	//打印次数
	private int printTimes;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public int getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}
	
	
}
