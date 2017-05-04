package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SpecialAddressConditionDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String address;
	
	private String operator;
	
	private String vehicleNo;
	
	private String addressType;
	
	private Date queryTimeBegin;
	
	private Date  queryTimeEnd;
	
	private List<String> orgList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Date getQueryTimeBegin() {
		return queryTimeBegin;
	}

	public void setQueryTimeBegin(Date queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}

	public Date getQueryTimeEnd() {
		return queryTimeEnd;
	}

	public void setQueryTimeEnd(Date queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}
	
}
