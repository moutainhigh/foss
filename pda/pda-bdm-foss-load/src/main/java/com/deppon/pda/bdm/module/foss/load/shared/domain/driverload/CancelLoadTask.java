package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

/**
 * 司机撤销装车任务
 * @author 245955
 *
 */
public class CancelLoadTask {
	
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
}