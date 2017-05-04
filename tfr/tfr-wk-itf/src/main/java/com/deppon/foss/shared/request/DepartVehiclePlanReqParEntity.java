package com.deppon.foss.shared.request;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
* @description 获取发车计划信息请求参数(悟空请求参数实体)
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:08:36
 */
@XmlRootElement(name="DepartVehiclePlanReqParEntity")
public class DepartVehiclePlanReqParEntity implements Serializable{
	
	// 序列化ID
	private static final long serialVersionUID = 7354429438336724231L;

	// 车牌号
	private String vehicleNo;
	
	// 交接时间
	private Date handoverTime;
	
	// 出发部门编码
	private String origOrgCode;
	
	// 到达部门编码
	private String arrivalDeptNo;
	
	// 运输类型
	private String transportType;
	
	// 发车计划类型
	private String planType;

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

	public Date getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	public String getArrivalDeptNo() {
		return arrivalDeptNo;
	}

	public void setArrivalDeptNo(String arrivalDeptNo) {
		this.arrivalDeptNo = arrivalDeptNo;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
}
