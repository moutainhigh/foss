package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

public class GetDetail {
	
	//快递员工号
	private String courierCode;
	//任务号
	private String taskCode;

	public String getCourierCode() {
		return courierCode;
	}

	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
