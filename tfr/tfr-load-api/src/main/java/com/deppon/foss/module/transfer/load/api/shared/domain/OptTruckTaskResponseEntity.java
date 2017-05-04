package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * 类描述：	发车确认/到达确认/取消发车 监控状态接口查询结果返回实体
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-04-26 上午10:13:39
 * 
 */
public class OptTruckTaskResponseEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 任务ID
	 */
	private String truckTaskId;
	
	/**
	 * 任务编号
	 */
	private String truckNo;
	/**
	 * 车辆车牌号
	 */
	private String vehicleNo;
	/**
	 * 出发部门code
	 */
	private String origOrgCode;
	/**
	 * 出发部门名称
	 */
	private String origOrgName;
	/**
	 * 到达部门Code
	 */
	private String destOrgCode;
	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	/**
	 * 出发时间
	 */
	private String actualDepartTime;
	/**
	 * 到达时间
	 */
	private String actualArrivedTime;
	/**
	 * 出发操作人
	 */
	private String departOperName;
	
	/**
	 * 到达操作人
	 */
	private String arriveOperName;
	
	/**
	 * 到达情况
	 */
	private String status;
	
	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return "OptTruckTaskResponseEntity [truckTaskId=" + truckTaskId
				+ ", truckNo=" + truckNo + ", vehicleNo=" + vehicleNo
				+ ", origOrgCode=" + origOrgCode + ", origOrgName="
				+ origOrgName + ", destOrgCode=" + destOrgCode
				+ ", destOrgName=" + destOrgName + ", actualDepartTime="
				+ actualDepartTime + ", actualArrivedTime=" + actualArrivedTime
				+ ", departOperName=" + departOperName + ", arriveOperName="
				+ arriveOperName + ", status=" + status + "]";
	}
	/**
	 * set...get...
	 * @return
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getActualDepartTime() {
		return actualDepartTime;
	}
	public void setActualDepartTime(String actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}
	public String getActualArrivedTime() {
		return actualArrivedTime;
	}
	public void setActualArrivedTime(String actualArrivedTime) {
		this.actualArrivedTime = actualArrivedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTruckNo() {
		return truckNo;
	}
	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}
	public String getOrigOrgName() {
		return origOrgName;
	}
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}	
	
	public String getDepartOperName() {
		return departOperName;
	}
	public void setDepartOperName(String departOperName) {
		this.departOperName = departOperName;
	}
	public String getArriveOperName() {
		return arriveOperName;
	}
	public void setArriveOperName(String arriveOperName) {
		this.arriveOperName = arriveOperName;
	}
	
}
