package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

public class DeliverbillVo_New {
	private String Deliverbill_No;//派送单号
	private String Vehicle_No;//车牌号
	private String Driver_Code;//司机编码
	private String Create_Type;//派车类型
	public String getDeliverbill_No() {
		return Deliverbill_No;
	}
	public void setDeliverbill_No(String deliverbill_No) {
		Deliverbill_No = deliverbill_No;
	}
	public String getVehicle_No() {
		return Vehicle_No;
	}
	public void setVehicle_No(String vehicle_No) {
		Vehicle_No = vehicle_No;
	}
	public String getDriver_Code() {
		return Driver_Code;
	}
	public void setDriver_Code(String driver_Code) {
		Driver_Code = driver_Code;
	}
	public String getCreate_Type() {
		return Create_Type;
	}
	public void setCreate_Type(String create_Type) {
		Create_Type = create_Type;
	}
}
