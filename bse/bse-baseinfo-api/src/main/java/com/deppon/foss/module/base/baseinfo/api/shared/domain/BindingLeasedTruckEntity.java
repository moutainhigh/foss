package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 外请车绑定车队
 * @author 310854
 * @date 2016-5-28
 */
public class BindingLeasedTruckEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  createUser 也是操作人的code
	//  车牌号
	private String vehicleNo;
	
	//  部门code/车队
    private String orgCode;
    
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

	public String getDeiverName() {
		return deiverName;
	}

	public void setDeiverName(String deiverName) {
		this.deiverName = deiverName;
	}
}
