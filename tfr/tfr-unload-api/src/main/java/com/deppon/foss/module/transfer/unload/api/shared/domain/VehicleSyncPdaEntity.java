package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 已出发的车辆信息，对应表tfr.t_opt_vehicle_sync_pda
 */
public class VehicleSyncPdaEntity extends BaseEntity {

	private static final long serialVersionUID = 8561797563324373910L;

	private String truckTaskDetailId;

	private String type;

	private String failed;

	private String failedInfo;

	private Date createTime;

	private Date modifyTime;

	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}

	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public String getFailedInfo() {
		return failedInfo;
	}

	public void setFailedInfo(String failedInfo) {
		this.failedInfo = failedInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "VehicleSyncPdaEntity [truckTaskDetailId=" + truckTaskDetailId + ", type=" + type + ", failed=" + failed
				+ ", failedInfo=" + failedInfo + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}

}
