package com.deppon.foss.module.pickup.pricing.api.shared.domain;

public class CarloadPriceOrgEntity {
	 private String id;               //id
	 private String carloadPriceId;   //方案名称
	 private String organizationID; //组织id
	 private String organizationCode; //组织编码
	 private String organizationName; //组织名称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarloadPriceId() {
		return carloadPriceId;
	}
	public void setCarloadPriceId(String carloadPriceId) {
		this.carloadPriceId = carloadPriceId;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	 
	 
}
