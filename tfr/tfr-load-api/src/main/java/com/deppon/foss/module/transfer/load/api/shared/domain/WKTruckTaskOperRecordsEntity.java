package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * 类描述：	发车确认/到达确认/取消发车操作记录保存到：
 *          t_opt_truck_arrive、t_opt_truck_depart表中去
 * 创建人：	106162-FOSS-LIPING
 * 创建时间：	2016-07-204  上午10:13:39
 * 
 */
public class WKTruckTaskOperRecordsEntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 放行ID
	 */
	private String truckDepartId;
	/**
	 * 到达ID
	 */
	private String truckArriveId;
	/**
	 * 车牌号
	 */ 
	private String vehicleNo;
	/**
	 * 状态
	 */ 
	private String status;
	/**
	 * 创建人工号
	 */
	private String createUserCode;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 创建人部门CODE
	 */ 
	private String createOrgCode;
	/**
	 * 车辆任务ID
	 */
	private String truckTaskId;
	/**
	 * 实际出发操作人工号
	 */
	private String manualUserCode;
	/**
	 * PDA实际出发操作人工号
	 */  
	private String pdaUserCode;
	
	/**
	 * toString()方法
	 */
	@Override
	public String toString() {
		return "WKTruckTaskOperRecordsEntity [truckDepartId=" + truckDepartId
				+ ", truckArriveId=" + truckArriveId + ", vehicleNo="
				+ vehicleNo + ", status=" + status + ", createUserCode="
				+ createUserCode + ", createUserName=" + createUserName
				+ ", createOrgCode=" + createOrgCode + ", truckTaskId="
				+ truckTaskId + ", manualUserCode=" + manualUserCode
				+ ", pdaUserCode=" + pdaUserCode + "]";
	}

	/**
	 * set,get()...
	 * @return
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}

	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}

	public String getTruckArriveId() {
		return truckArriveId;
	}

	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public String getManualUserCode() {
		return manualUserCode;
	}

	public void setManualUserCode(String manualUserCode) {
		this.manualUserCode = manualUserCode;
	}

	public String getPdaUserCode() {
		return pdaUserCode;
	}

	public void setPdaUserCode(String pdaUserCode) {
		this.pdaUserCode = pdaUserCode;
	}

	
}
