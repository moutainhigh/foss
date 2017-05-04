package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;

public class ScUnloadStateEntity {

	/**
	 * 任务号
	 */
	private  String  taskCode;
	/**
	 * 交接单号
	 */
	private  String receptCode;
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getReceptCode() {
		return receptCode;
	}
	public void setReceptCode(String receptCode) {
		this.receptCode = receptCode;
	}
	
}
