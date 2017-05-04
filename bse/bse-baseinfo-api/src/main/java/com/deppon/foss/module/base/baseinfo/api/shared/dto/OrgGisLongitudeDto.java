package com.deppon.foss.module.base.baseinfo.api.shared.dto;
/**
 * 
 *<p>Title: OrgGisLongitudeDto</p>
 * <p>Description:部门识别GIS经纬度DTO </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-17
 */
public class OrgGisLongitudeDto {
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 经度坐标
	 */
	private String longitude;
	/**
	 * 纬度坐标
	 */
	private String Latitude;
	
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	
	
}
