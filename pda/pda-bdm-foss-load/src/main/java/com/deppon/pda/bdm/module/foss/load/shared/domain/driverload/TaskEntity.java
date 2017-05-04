package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

public class TaskEntity {

	/**
	 * 标识
	 */
	private String flag;
	/**
	 * 任务号
	 */
	private String TaskCodes;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTaskCodes() {
		return TaskCodes;
	}
	public void setTaskCodes(String taskCodes) {
		TaskCodes = taskCodes;
	}
	
}
