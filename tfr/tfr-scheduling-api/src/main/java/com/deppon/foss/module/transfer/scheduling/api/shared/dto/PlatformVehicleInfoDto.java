package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Ouyang
 */
public class PlatformVehicleInfoDto implements Serializable {

	private static final long serialVersionUID = 6929865843303791060L;

	/**
	 * 车辆任务id
	 */
	private String truckTaskId;

	/**
	 * 车辆任务明细id
	 */
	private String taskDetailId;

	/**
	 * 车牌
	 */
	private String vehicleNo;

	/**
	 * 长途、短途
	 */
	private String businessType;

	/**
	 * 预计到达时间(到达当前外场)
	 */
	private Date estimatedArriveTime;

	/**
	 * 可及时中转票数(只考虑卡航和城运)
	 */
	private int voteTransferInTime;

	/**
	 * 预分配月台
	 */
	private String prePlatformCode;

	/**
	 * 车里的卡航票数
	 */
	private int voteFlf;

	/**
	 * 车里的城运票数
	 */
	private int voteFsf;

	/**
	 * 车里的空运票数
	 */
	private int voteAf;

	/**
	 * 线路(如上海枢纽中心-陈村枢纽中心)
	 */
	private String line;

	/**
	 * 是否晚到(Y/N)
	 */
	private String late;

	/**
	 * 发车时间(到达当前外场线路的发车时间)
	 */
	private Date departTime;

	/**
	 * 出发部门(到达当前外场)
	 */
	private String origDeptName;

	/**
	 * 出发部门(到达当前外场)
	 */
	private String origDeptCode;

	/**
	 * 到达部门(即当前外场)
	 */
	private String destDeptName;

	/**
	 * 到达部门(即当前外场)
	 */
	private String destDeptCode;

	/**
	 * 车辆所属类型(公司车/外请车)
	 */
	private String vehicleOwnerType;

	/**
	 * 等待时长(xx小时xx分钟)
	 */
	private String timeWait;

	/**
	 * 状态(在途/到达)
	 */
	private String status;

	/**
	 * 实际到时间(到达当前外场)
	 */
	private Date actualArriveTime;

	/**
	 * 载重体积(xx千克xx立方)
	 */
	private String weightAndVolumn;

	/**
	 * 预计卸车耗时(xx小时xx分钟)
	 */
	private String time4UnloadTake;

	/**
	 * 车型code
	 */
	private String vehicleLengthCode;

	/**
	 * 车型名称
	 */
	private String vehicleLengthName;

	/**
	 * 预计出发时间(从当前外场出发；若为未到达，则是预计到达时间+预计卸车用时；若为到达，则是当前时间+预计卸车用时)
	 */
	private Date estimatedDepartTime;

	/**
	 * 封签校验时间
	 */
	private Date sealCheckTime;

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public String getVehicleLengthCode() {
		return vehicleLengthCode;
	}

	public void setVehicleLengthCode(String vehicleLengthCode) {
		this.vehicleLengthCode = vehicleLengthCode;
	}

	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	public String getTime4UnloadTake() {
		return time4UnloadTake;
	}

	public void setTime4UnloadTake(String time4UnloadTake) {
		this.time4UnloadTake = time4UnloadTake;
	}

	public String getWeightAndVolumn() {
		return weightAndVolumn;
	}

	public void setWeightAndVolumn(String weightAndVolumn) {
		this.weightAndVolumn = weightAndVolumn;
	}

	public String getTaskDetailId() {
		return taskDetailId;
	}

	public void setTaskDetailId(String taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getEstimatedArriveTime() {
		return estimatedArriveTime;
	}

	public void setEstimatedArriveTime(Date estimatedArriveTime) {
		this.estimatedArriveTime = estimatedArriveTime;
	}

	public int getVoteTransferInTime() {
		return voteTransferInTime;
	}

	public void setVoteTransferInTime(int voteTransferInTime) {
		this.voteTransferInTime = voteTransferInTime;
	}

	public int getVoteFlf() {
		return voteFlf;
	}

	public void setVoteFlf(int voteFlf) {
		this.voteFlf = voteFlf;
	}

	public int getVoteFsf() {
		return voteFsf;
	}

	public void setVoteFsf(int voteFsf) {
		this.voteFsf = voteFsf;
	}

	public int getVoteAf() {
		return voteAf;
	}

	public void setVoteAf(int voteAf) {
		this.voteAf = voteAf;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public String getOrigDeptName() {
		return origDeptName;
	}

	public void setOrigDeptName(String origDeptName) {
		this.origDeptName = origDeptName;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public String getTimeWait() {
		return timeWait;
	}

	public void setTimeWait(String timeWait) {
		this.timeWait = timeWait;
	}

	public String getOrigDeptCode() {
		return origDeptCode;
	}

	public void setOrigDeptCode(String origDeptCode) {
		this.origDeptCode = origDeptCode;
	}

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getEstimatedDepartTime() {
		return estimatedDepartTime;
	}

	public void setEstimatedDepartTime(Date estimatedDepartTime) {
		this.estimatedDepartTime = estimatedDepartTime;
	}

	public String getPrePlatformCode() {
		return prePlatformCode;
	}

	public void setPrePlatformCode(String prePlatformCode) {
		this.prePlatformCode = prePlatformCode;
	}

	public Date getSealCheckTime() {
		return sealCheckTime;
	}

	public void setSealCheckTime(Date sealCheckTime) {
		this.sealCheckTime = sealCheckTime;
	}

	@Override
	public String toString() {
		return "PlatformVehicleInfoDto [taskDetailId=" + taskDetailId
				+ ", vehicleNo=" + vehicleNo + ", businessType=" + businessType
				+ ", estimatedArriveTime=" + estimatedArriveTime
				+ ", voteTransferInTime=" + voteTransferInTime
				+ ", prePlatformCode=" + prePlatformCode + ", voteFlf="
				+ voteFlf + ", voteFsf=" + voteFsf + ", voteAf=" + voteAf
				+ ", line=" + line + ", late=" + late + ", departTime="
				+ departTime + ", origDeptName=" + origDeptName
				+ ", origDeptCode=" + origDeptCode + ", destDeptName="
				+ destDeptName + ", destDeptCode=" + destDeptCode
				+ ", vehicleOwnerType=" + vehicleOwnerType + ", timeWait="
				+ timeWait + ", status=" + status + ", actualArriveTime="
				+ actualArriveTime + ", weightAndVolumn=" + weightAndVolumn
				+ ", time4UnloadTake=" + time4UnloadTake
				+ ", vehicleLengthCode=" + vehicleLengthCode
				+ ", vehicleLengthName=" + vehicleLengthName
				+ ", estimatedDepartTime=" + estimatedDepartTime
				+ ", sealCheckTime=" + sealCheckTime + "]";
	}

}
