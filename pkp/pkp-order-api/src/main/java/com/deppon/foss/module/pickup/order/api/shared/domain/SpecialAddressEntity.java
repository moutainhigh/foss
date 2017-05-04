package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 特殊地址库
 * @author 043258-foss-zhaobin
 * @date 2014-4-18 下午4:05:59
 */
public class SpecialAddressEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String address;
	
	private String addressType;
	
	private String operator;
	
	private String operatorCode;
	
	private String operateOrgName;
	
	private String operateOrgCode;
	
	private Date operateTime;
	
	private String vehicleNo;
	
	private String vehicleDept;
	
	private String vehicleDeptCode;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getVehicleDept() {
		return vehicleDept;
	}

	public void setVehicleDept(String vehicleDept) {
		this.vehicleDept = vehicleDept;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleDeptCode() {
		return vehicleDeptCode;
	}

	public void setVehicleDeptCode(String vehicleDeptCode) {
		this.vehicleDeptCode = vehicleDeptCode;
	}
	
}
