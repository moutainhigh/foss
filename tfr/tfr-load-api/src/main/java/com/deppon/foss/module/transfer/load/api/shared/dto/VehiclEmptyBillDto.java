package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

public class VehiclEmptyBillDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**线路虚拟编号*/
	private String lineVirtualCode;
	/**任务车辆id*/
	private String truckTaskId;
	/**任务车辆明细id*/
	private String truckTaskDettailId;
	/**任务车辆单据id*/
	private String truckTaskBillId;
	/**司机编号*/
	private String driverCode;
	/**线路*/
	private String lineName;
	/**员工电话号*/
	private String driverTel;
	/**空驶单号*/
	private String vehiclEmptyBillNo;
	/**状态*/
	private String state;
	/**车牌号*/
	private String vehicleNo;
	/**司机*/
	private String driverName;
	/**出发部门*/
	private String origOrgName;
	/**出发部门编码*/
	private String origOrgCode;
	/**到达部门编码*/    
	private String destOrgCode;
	/**到达部门*/
	private String destOrgName;
	/**制单时间开始*/
	private  Date startTime;
	/**制单时间结束*/
	private  Date endTime;
	/**制单达时间*/
	private  Date createTime;
	/**制单人*/
	private String createrUserName;
	/**业务类型*/
	private String businessType;
	/**出发时间*/
	private  Date origTime;
	/**到达时间*/
	private  Date destTime;
	
	public String getVehiclEmptyBillNo() {
		return vehiclEmptyBillNo;
	}
	public void setVehiclEmptyBillNo(String vehiclEmptyBillNo) {
		this.vehiclEmptyBillNo = vehiclEmptyBillNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
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
	
	@DateFormat
	public Date getCreateTime() {
		return createTime;
	}
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreaterUserName() {
		return createrUserName;
	}
	public void setCreaterUserName(String createrUserName) {
		this.createrUserName = createrUserName;
	}
	public String getTruckTaskId() {
		return truckTaskId;
	}
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	public String getTruckTaskDettailId() {
		return truckTaskDettailId;
	}
	public void setTruckTaskDettailId(String truckTaskDettailId) {
		this.truckTaskDettailId = truckTaskDettailId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	@DateFormat
	public Date getStartTime() {
		return startTime;
	}
	@DateFormat
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@DateFormat
	public Date getEndTime() {
		return endTime;
	}
	@DateFormat
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@DateFormat
	public Date getOrigTime() {
		return origTime;
	}
	@DateFormat
	public void setOrigTime(Date origTime) {
		this.origTime = origTime;
	}
	@DateFormat
	public Date getDestTime() {
		return destTime;
	}
	@DateFormat
	public void setDestTime(Date destTime) {
		this.destTime = destTime;
	}
	public String getTruckTaskBillId() {
		return truckTaskBillId;
	}
	public void setTruckTaskBillId(String truckTaskBillId) {
		this.truckTaskBillId = truckTaskBillId;
	}

}
	