package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

public class InviteFossVehicleCostQueryDto implements Serializable{

	private static final long serialVersionUID = 3300713068429543868L;

	//ID
	private String Id;
	//事业部名称
	private String businessName;
	//事业部code
	private String businessCode;
	//大区名称
	private String regionalName;
	//大区code
	private String regionalCode;
	
	/**
	 *getter
	 */
	public String getId() {
		return Id;
	}
	/**
	 *setter
	 */
	public void setId(String id) {
		Id = id;
	}
	/**
	 *getter
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 *setter
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 *getter
	 */
	public String getBusinessCode() {
		return businessCode;
	}
	/**
	 *setter
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	/**
	 *getter
	 */
	public String getRegionalName() {
		return regionalName;
	}
	/**
	 *setter
	 */
	public void setRegionalName(String regionalName) {
		this.regionalName = regionalName;
	}
	/**
	 *getter
	 */
	public String getRegionalCode() {
		return regionalCode;
	}
	/**
	 *setter
	 */
	public void setRegionalCode(String regionalCode) {
		this.regionalCode = regionalCode;
	}
}
