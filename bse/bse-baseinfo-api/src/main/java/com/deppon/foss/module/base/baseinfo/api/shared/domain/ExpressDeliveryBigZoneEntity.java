package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;


public class ExpressDeliveryBigZoneEntity extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5155870139268473746L;

	/**
     * 大区编码.
     */
    private String regionCode; 
    
    /**
     * 大区名称.
     */
    private String regionName;
    
    /**
     * 管理部门编码.
     */
    private String management;
    
    /**
     * 所属管理部门编码集合
     */
    private List<String> managementCodeList;
    
    /**
     * 管理部门名称(扩展).
     */
    private String managementName;
    
   
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 备注.
     */
    private String notes;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode; 

    /**
     * 所在省编码.
     */
    private String provCode;

    /**
     * 所在省名称（扩展）.
     */
    private String provName;

    /**
     * 所在市编码.
     */
    private String cityCode;

    /**
     * 所在市名称（扩展）.
     */
    private String cityName;

    /**
     * 所在区县.
     */
    private String countyCode;

    /**
     * 所在区县名称（扩展）.
     */
    private String countyName;
    
    private String empCode;
    

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public List<String> getManagementCodeList() {
		return managementCodeList;
	}

	public void setManagementCodeList(List<String> managementCodeList) {
		this.managementCodeList = managementCodeList;
	}

	public String getManagementName() {
		return managementName;
	}

	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getVirtualCode() {
		return virtualCode;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

    
  
    
}
