package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
* @description 修改外请约车信息参数实体
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:46:32
 */
public class InviteVehicleEditParmEntity extends BaseEntity {
	
	private static final long serialVersionUID = 90799166272887728L;
		
	/** 车牌号 */
	private String vehicleNo;
	
	/** 约车编号 */
	private String inviteNo;
	
	/** 使用状态 UNUSED : 未使用, USING :已使用 */
	private String useStatus;
	
	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
}