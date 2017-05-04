package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

public class DeryDeliveryTaskEntity {
	//快递员工号
	private String tallyerCode;
	//车牌号
	private String truckCode;
	//任务号
	private String taskCode;
	public String getTallyerCode() {
		return tallyerCode;
	}
	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

}
