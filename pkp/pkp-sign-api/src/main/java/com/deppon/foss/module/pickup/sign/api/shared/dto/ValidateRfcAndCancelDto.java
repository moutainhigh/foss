package com.deppon.foss.module.pickup.sign.api.shared.dto;
/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-18 下午4:19:30    
 */
public class ValidateRfcAndCancelDto {

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 员工工号
	 */
	private String empCode;
	
	/**
	 * 员工姓名
	 */
	private String empName;
	
	/**
	 * 当前登录部门编码
	 */
	private String currentDeptCode;
	
	/**
	 * 当前登录部门名称
	 */
	private String currentDeptName;
	
	/**
	 * 运单更改作废标识：1--更改；0--作废
	 */
	
	private String operate;
	
	/**
	 * 成功失败标识
	 */
	private boolean isSuccess;
	
	/**
	 * 消息
	 */
	private String msg;
	
	/**
	 * 整车首款支付状态
	 */
	private String firstStatus;
	
	/**
	 * 整车尾款支付状态
	 */
	private String lastStatus;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getFirstStatus() {
		return firstStatus;
	}

	public void setFirstStatus(String firstStatus) {
		this.firstStatus = firstStatus;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	
}
