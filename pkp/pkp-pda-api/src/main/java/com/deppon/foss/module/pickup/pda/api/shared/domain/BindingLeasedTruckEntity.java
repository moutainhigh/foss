package com.deppon.foss.module.pickup.pda.api.shared.domain;


/**
 * 外请车绑定车队
 * @author 310854
 * @date 2016-5-28
 */
public class BindingLeasedTruckEntity {

	//  车牌号
	private String vehicleNo;
	
	//  部门code/车队
    private String orgCode;
    
    //  操作人code
    private String operatorCode;
    
    private String deiverName;
    
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getDeiverName() {
		return deiverName;
	}

	public void setDeiverName(String deiverName) {
		this.deiverName = deiverName;
	}
}
