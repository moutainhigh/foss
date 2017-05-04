package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
* @description 同步给快递系统的:车辆任务明细实体类
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月27日 上午10:06:21
*/
public class TruckTaskDetailWkEntity implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 上午10:08:09
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	private String id;// ID

	private String truckTaskDetailNo; // 车辆任务明细编号

	private String truckDestNo;// 车辆到达编号

	private String truckReleaseNo;// 车辆放行编号

	private String truckTaskId;// 车辆任务编号

	private String vehicleNo;// 车牌号

	private String lineNo;// 线路编号  

	private String lineName;// 线路名称

	private String businessType;// 车辆业务类型

	private String origOrgCode;// 出发部门编码

	private String origOrgName;// 出发部门名称

	private String destOrgCode;// 到达部门编码

	private String destOrgName;// 到达部门名称

	private Date departTime;// 出发时间

	private Date destTime;// 到达时间

	private String status;// 车辆任务明细状态

	private Date plannedDepartTime; // 计划出发时间

	private Date plannedDestTime;// 计划到达时间

	private Date realDepartTime;// 实际出发时间

	private Date realDestTime;// 实际到达时间

	private String realDepartType;// 实际出发类型

	private String realDestType;// 实际到达类型

	private String truckOwnDeptNo;// 车辆归属部门编码

	private String truckOwnDeptName;// 车辆归属部门名称

	private String truckOwnType;// 车辆归属类型

	private String shift;// 班次号

	private String wholeTruckType;// 整车类型

	private String truckType;// 车辆类型

	private String platformNo;// 月台编号

	private Date manualDepartTime;// 手工出发时间

	private Date manualConfirmDestTime;// 手工确认到达时间

	//private String manualReleaseEpmNo;// 手工放行人工号 106162 2016-06-30取消掉

	private String manualReleaseEpmName;// 手工放行人名称

	private String manualReleaseDeptCode;// 手工放行人部门编码

	private String manualReleaseDeptName;// 手工放行人部门名称

	private BigDecimal travelDuration;// 运行时长

	private String brandNo;// 挂牌号

	private Date createTime;// 创建时间

	private Date updateTime;// 更新时间

	private String createName;// 创建人名称

	//新增操作人 106162 2016-06-30
	private String departOperationCode; //出发
	
	private String destOperationCode; //到达
	
	

	public String getDepartOperationCode() {
		return departOperationCode;
	}

	public void setDepartOperationCode(String departOperationCode) {
		this.departOperationCode = departOperationCode;
	}

	public String getDestOperationCode() {
		return destOperationCode;
	}

	public void setDestOperationCode(String destOperationCode) {
		this.destOperationCode = destOperationCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTruckTaskDetailNo() {
		return truckTaskDetailNo;
	}

	public void setTruckTaskDetailNo(String truckTaskDetailNo) {
		this.truckTaskDetailNo = truckTaskDetailNo;
	}

	public String getTruckDestNo() {
		return truckDestNo;
	}

	public void setTruckDestNo(String truckDestNo) {
		this.truckDestNo = truckDestNo;
	}

	public String getTruckReleaseNo() {
		return truckReleaseNo;
	}

	public void setTruckReleaseNo(String truckReleaseNo) {
		this.truckReleaseNo = truckReleaseNo;
	}

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

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public Date getDestTime() {
		return destTime;
	}

	public void setDestTime(Date destTime) {
		this.destTime = destTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPlannedDepartTime() {
		return plannedDepartTime;
	}

	public void setPlannedDepartTime(Date plannedDepartTime) {
		this.plannedDepartTime = plannedDepartTime;
	}

	public Date getPlannedDestTime() {
		return plannedDestTime;
	}

	public void setPlannedDestTime(Date plannedDestTime) {
		this.plannedDestTime = plannedDestTime;
	}

	public Date getRealDepartTime() {
		return realDepartTime;
	}

	public void setRealDepartTime(Date realDepartTime) {
		this.realDepartTime = realDepartTime;
	}

	public Date getRealDestTime() {
		return realDestTime;
	}

	public void setRealDestTime(Date realDestTime) {
		this.realDestTime = realDestTime;
	}

	public String getRealDepartType() {
		return realDepartType;
	}

	public void setRealDepartType(String realDepartType) {
		this.realDepartType = realDepartType;
	}

	public String getRealDestType() {
		return realDestType;
	}

	public void setRealDestType(String realDestType) {
		this.realDestType = realDestType;
	}

	public String getTruckOwnDeptNo() {
		return truckOwnDeptNo;
	}

	public void setTruckOwnDeptNo(String truckOwnDeptNo) {
		this.truckOwnDeptNo = truckOwnDeptNo;
	}

	public String getTruckOwnDeptName() {
		return truckOwnDeptName;
	}

	public void setTruckOwnDeptName(String truckOwnDeptName) {
		this.truckOwnDeptName = truckOwnDeptName;
	}

	public String getTruckOwnType() {
		return truckOwnType;
	}

	public void setTruckOwnType(String truckOwnType) {
		this.truckOwnType = truckOwnType;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getWholeTruckType() {
		return wholeTruckType;
	}

	public void setWholeTruckType(String wholeTruckType) {
		this.wholeTruckType = wholeTruckType;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public Date getManualDepartTime() {
		return manualDepartTime;
	}

	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}

	public Date getManualConfirmDestTime() {
		return manualConfirmDestTime;
	}

	public void setManualConfirmDestTime(Date manualConfirmDestTime) {
		this.manualConfirmDestTime = manualConfirmDestTime;
	}

/*	public String getManualReleaseEpmNo() {
		return manualReleaseEpmNo;
	}

	public void setManualReleaseEpmNo(String manualReleaseEpmNo) {
		this.manualReleaseEpmNo = manualReleaseEpmNo;
	}*/

	public String getManualReleaseEpmName() {
		return manualReleaseEpmName;
	}

	public void setManualReleaseEpmName(String manualReleaseEpmName) {
		this.manualReleaseEpmName = manualReleaseEpmName;
	}

	public String getManualReleaseDeptCode() {
		return manualReleaseDeptCode;
	}

	public void setManualReleaseDeptCode(String manualReleaseDeptCode) {
		this.manualReleaseDeptCode = manualReleaseDeptCode;
	}

	public String getManualReleaseDeptName() {
		return manualReleaseDeptName;
	}

	public void setManualReleaseDeptName(String manualReleaseDeptName) {
		this.manualReleaseDeptName = manualReleaseDeptName;
	}

	public BigDecimal getTravelDuration() {
		return travelDuration;
	}

	public void setTravelDuration(BigDecimal travelDuration) {
		this.travelDuration = travelDuration;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	
}
