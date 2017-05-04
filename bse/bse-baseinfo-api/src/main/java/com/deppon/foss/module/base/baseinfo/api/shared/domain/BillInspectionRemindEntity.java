package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class BillInspectionRemindEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8831986737804540179L;
	
	//区域类型
	private String id;
	
	private String regionType;
	
	private String regionCode;
	
	private String regionName;
	
	private String regionLev;
	
	private String regionLevCode;
	
	private String provinceName;
	
	private String provinceCode;
	
	private String cityName;
	
	private String cityCode;
	
	private String countyName;
	
	private String countyCode;

	private String active;
	
	private Long versionNo;
	
	private String createUserName;

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

	public String getRegionLev() {
		return regionLev;
	}

	public void setRegionLev(String regionLev) {
		this.regionLev = regionLev;
	}

	public String getRegionLevCode() {
		return regionLevCode;
	}

	public void setRegionLevCode(String regionLevCode) {
		this.regionLevCode = regionLevCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

}
