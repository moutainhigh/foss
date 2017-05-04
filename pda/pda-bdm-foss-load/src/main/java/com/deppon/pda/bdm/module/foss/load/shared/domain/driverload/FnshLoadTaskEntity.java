package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.List;


/**
 * 完成装车任务
 * @ClassName FnshLoadTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-23
 */
public class FnshLoadTaskEntity{
	
	/**
	 * 任务号
	 */
	private List<String> taskCodes;
	
	/**
	 * 车牌号
	 */
    private String vehicleNo;
    

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public List<String> getTaskCodes() {
		return taskCodes;
	}

	public void setTaskCodes(List<String> taskCodes) {
		this.taskCodes = taskCodes;
	}
	
}
