package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * 类描述：	发车确认/到达确认/取消发车 监控状态接口接收参数实体
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-04-26 上午10:13:39
 * 
 */
public class OptTruckTaskEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 任务编号
	 */
	private String truckTaskId;
	/**
	 * 车辆车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 操作状态：1->发车确认;2->到达确认;0->取消
	 */
	private String departType;
	
	/**
	 * 业务类型：零担、快递【PDA说以后扩展用】
	 */
	private String sendType;

	/**
	 * 操作来源:人工放行-MANUAL，PDA放行-PDA，GPS放行-GPS
	 */
	private String actualDepartType;
	
	/**
	 * 出发到达确认操作人工号
	 */
	private String currentOprCode;
	/**
	 * 出发到达确认操作人名字
	 */
	private String currentOprName;
	/**
	 * 出发到达确认操作人当前部门（出发到达的行政组织部门）
	 */
	private String currentOprDeptCode;
	
	/**
	 * get...set...
	 * @return
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public String getDepartType() {
		return departType;
	}

	public void setDepartType(String departType) {
		this.departType = departType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getActualDepartType() {
		return actualDepartType;
	}

	public void setActualDepartType(String actualDepartType) {
		this.actualDepartType = actualDepartType;
	}

	public String getCurrentOprCode() {
		return currentOprCode;
	}

	public void setCurrentOprCode(String currentOprCode) {
		this.currentOprCode = currentOprCode;
	}

	public String getCurrentOprName() {
		return currentOprName;
	}

	public void setCurrentOprName(String currentOprName) {
		this.currentOprName = currentOprName;
	}

	public String getCurrentOprDeptCode() {
		return currentOprDeptCode;
	}

	public void setCurrentOprDeptCode(String currentOprDeptCode) {
		this.currentOprDeptCode = currentOprDeptCode;
	}

	/**
	 * toString()
	 */
	@Override
	public String toString() {
		return "OptTruckTaskEntity [truckTaskId=" + truckTaskId
				+ ", vehicleNo=" + vehicleNo + ", departType=" + departType
				+ ", sendType=" + sendType + ", actualDepartType="
				+ actualDepartType + ", currentOprCode=" + currentOprCode
				+ ", currentOprName=" + currentOprName
				+ ", currentOprDeptCode=" + currentOprDeptCode + "]";
	}
	
}
