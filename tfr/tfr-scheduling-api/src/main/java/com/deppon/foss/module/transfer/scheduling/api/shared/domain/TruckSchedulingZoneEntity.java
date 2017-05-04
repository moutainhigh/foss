package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TruckSchedulingZoneEntity extends BaseEntity{

	private static final long serialVersionUID = 8476737760249416210L;
	//接货排班车辆任务的id
	private String truckSchedulingTaskId;
	//车牌号
	private String vehicleNo;
	//小区编码
	private String zoneCode;
	//小区名称
	private String zoneName;
	//排班时间
	private Date schedulTime;
	//排班司机姓名
	private String driverName;
	//排班司机code
	private String driverCode;
	//车队名称code
	private String vehicleTeamCode;
	//车队名称
	private String vehicleTeamName;
	//创建人 
	private String createUserName;
	//创建人工号
	private String createUserCode;
	//创建时间
	private Date createTime;
	//定人定区code
	private String regionCode;
	//接送货类型（"PK":接货    "DE":送货）
	private String regionType;
	
	
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getTruckSchedulingTaskId() {
		return truckSchedulingTaskId;
	}
	public void setTruckSchedulingTaskId(String truckSchedulingTaskId) {
		this.truckSchedulingTaskId = truckSchedulingTaskId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public Date getSchedulTime() {
		return schedulTime;
	}
	public void setSchedulTime(Date schedulTime) {
		this.schedulTime = schedulTime;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getVehicleTeamCode() {
		return vehicleTeamCode;
	}
	public void setVehicleTeamCode(String vehicleTeamCode) {
		this.vehicleTeamCode = vehicleTeamCode;
	}
	public String getVehicleTeamName() {
		return vehicleTeamName;
	}
	public void setVehicleTeamName(String vehicleTeamName) {
		this.vehicleTeamName = vehicleTeamName;
	}
	
	
}
