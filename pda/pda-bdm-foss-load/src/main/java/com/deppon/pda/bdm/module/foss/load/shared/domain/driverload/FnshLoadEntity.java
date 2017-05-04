package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.List;

public class FnshLoadEntity {

	private List<String> taskCode;
	private String vehicleNo;
	
	public List<String> getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(List<String> taskCode) {
		this.taskCode = taskCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
}
