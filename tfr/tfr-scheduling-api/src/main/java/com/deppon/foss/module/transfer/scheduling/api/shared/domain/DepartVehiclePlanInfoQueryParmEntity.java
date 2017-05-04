package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
* @description 获取发车计划信息查询参数实体
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:45:39
 */
public class DepartVehiclePlanInfoQueryParmEntity extends BaseEntity {
	
	private static final long serialVersionUID = 90799166272887728L;
		
	// 车牌号
	private String vehicleNo;
	
	// 交接时间
	private Date departDate;
	
	// 出发部门编码
	private String origOrgCode;
	
	// 到达部门编码
	private String destOrgCode;
	
	// 运输类型
	private String transProperty;
	
	// 发车计划类型
	private String planType;
	
	// 计划状态_新建
	private String planStatusNew;
	
	// 计划状态_下发
	private String planStatusRelease;
	
	// 班次类型_正常
	private String frequencyTypeNormal;
	
	// 班次类型_加发
	private String frequencyTypeAdd;
	
	//车辆已出发
	private String hasLeft;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
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

	public String getTransProperty() {
		return transProperty;
	}

	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getPlanStatusNew() {
		return planStatusNew;
	}

	public void setPlanStatusNew(String planStatusNew) {
		this.planStatusNew = planStatusNew;
	}

	public String getPlanStatusRelease() {
		return planStatusRelease;
	}

	public void setPlanStatusRelease(String planStatusRelease) {
		this.planStatusRelease = planStatusRelease;
	}

	public String getFrequencyTypeNormal() {
		return frequencyTypeNormal;
	}

	public void setFrequencyTypeNormal(String frequencyTypeNormal) {
		this.frequencyTypeNormal = frequencyTypeNormal;
	}

	public String getFrequencyTypeAdd() {
		return frequencyTypeAdd;
	}

	public void setFrequencyTypeAdd(String frequencyTypeAdd) {
		this.frequencyTypeAdd = frequencyTypeAdd;
	}

	public String getHasLeft() {
		return hasLeft;
	}

	public void setHasLeft(String hasLeft) {
		this.hasLeft = hasLeft;
	}
}