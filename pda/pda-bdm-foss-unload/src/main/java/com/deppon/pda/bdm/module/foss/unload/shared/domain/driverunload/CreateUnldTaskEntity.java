package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;


public class CreateUnldTaskEntity {

	/**
	 * 单据编号
	 */
	private String receptCode;
	//private List<String> receiptCodes;
	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 任务号
	 */
	private String taskCode;
	
	public String getReceptCode() {
		return receptCode;
	}

	public void setReceptCode(String receptCode) {
		this.receptCode = receptCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
    	
}
