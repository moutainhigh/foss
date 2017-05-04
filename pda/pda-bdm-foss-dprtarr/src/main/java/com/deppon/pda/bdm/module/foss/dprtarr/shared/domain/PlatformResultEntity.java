package com.deppon.pda.bdm.module.foss.dprtarr.shared.domain;

import java.io.Serializable;

public class PlatformResultEntity implements Serializable{

	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	
	//车牌号
	private String vehicleNo;
	//月台号
	private String platformNo;
	//出发部门code
	private String departOrgCode;
	//出发部门name
	private String departOrgName;
	//到达时间
	private String arriveTime;

	
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getDepartOrgCode() {
		return departOrgCode;
	}
	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}
	public String getDepartOrgName() {
		return departOrgName;
	}
	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	
	

}
