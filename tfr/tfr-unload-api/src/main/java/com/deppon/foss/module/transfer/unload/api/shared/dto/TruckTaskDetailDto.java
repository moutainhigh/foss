/**
 * 
 */
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

/**
 * @author 105795
 *
 */
public class TruckTaskDetailDto implements Serializable {

	//任务车辆明细ID
	private String  truckTaskId;
	//车牌号
	private String  vehicleNo;
	//车辆业务类型
	private String  businesstype;
	//出发部门
	private String  origOrgcode;
	//到达部门
	private String  destOrgCode;
	//状态
	private String  status;
	//预计出发时间
	private String  planDepartTime;
	//实际出发时间
	private String  actualDepartTime;
	//预计到达时间
	private String  planArriveTime;
	//实际到达时间
	private String  actualArriveTime;
	//单据类型
	private String  billType;
	//任务编号
	private String  loadTaskNo;
	/**
	 * @return the truckTaskId
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}
	/**
	 * @param truckTaskId the truckTaskId to set
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the businesstype
	 */
	public String getBusinesstype() {
		return businesstype;
	}
	/**
	 * @param businesstype the businesstype to set
	 */
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	/**
	 * @return the origOrgcode
	 */
	public String getOrigOrgcode() {
		return origOrgcode;
	}
	/**
	 * @param origOrgcode the origOrgcode to set
	 */
	public void setOrigOrgcode(String origOrgcode) {
		this.origOrgcode = origOrgcode;
	}
	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the planDepartTime
	 */
	public String getPlanDepartTime() {
		return planDepartTime;
	}
	/**
	 * @param planDepartTime the planDepartTime to set
	 */
	public void setPlanDepartTime(String planDepartTime) {
		this.planDepartTime = planDepartTime;
	}
	/**
	 * @return the actualDepartTime
	 */
	public String getActualDepartTime() {
		return actualDepartTime;
	}
	/**
	 * @param actualDepartTime the actualDepartTime to set
	 */
	public void setActualDepartTime(String actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}
	/**
	 * @return the planArriveTime
	 */
	public String getPlanArriveTime() {
		return planArriveTime;
	}
	/**
	 * @param planArriveTime the planArriveTime to set
	 */
	public void setPlanArriveTime(String planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * @return the loadTaskNo
	 */
	public String getLoadTaskNo() {
		return loadTaskNo;
	}
	/**
	 * @param loadTaskNo the loadTaskNo to set
	 */
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	/**
	 * @return the actualArriveTime
	 */
	public String getActualArriveTime() {
		return actualArriveTime;
	}
	/**
	 * @param actualArriveTime the actualArriveTime to set
	 */
	public void setActualArriveTime(String actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}
	
}
