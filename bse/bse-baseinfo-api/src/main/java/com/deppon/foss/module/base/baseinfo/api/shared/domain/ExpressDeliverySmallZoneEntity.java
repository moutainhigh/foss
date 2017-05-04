package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

public class ExpressDeliverySmallZoneEntity extends BankEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6457800120365944303L;

	 
    /**
     * 小区编码.
     */
    private String regionCode;

    /**
     * 小区名称.
     */
    private String regionName;

    /**
     * 管理部门编码.
     */
    private String management;
    
    /**
     * 所属管理部门编码集合.
     */
    private List<String> managementCodeList;
    
    /**
     * 管理部门名称.
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
     * 区域类型：接货区：DictionaryValueConstants.REGION_TYPE_PK
     *        送货区：DictionaryValueConstants.REGION_TYPE_DE
     */
    private String regionType;

    /**
     * GIS系统小区范围ＩＤ.
     */
    private String gisid;
    
    /**
     * GIS系统小区范围 面积.
     */
    private String gisArea;

	/**
     * 所属大区虚拟编码.
     */
    private String bigzonecode;
    
    /**
     * 所属大区虚拟编码1(扩展字段).
     */
    private String bigzonecode1;
    
    /**
     * 所属大区名称(扩展).
     */
    private String bigzoneName;

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
    /**
     * 操作人编码
     */
    private String operatorCode;
    /**
     * 操作人名称(冗余字段)
     */
    private String operatorName;
    /**
     * 主责快递员编号
     */
    private String courierCode;
    /**
     * 主责快递员名称
     */
    private String courierName;
    /**
     * 地图状态
     * @return
     */
    private String mapState;
    /**
     * 营业部到小区的距离
     * @return
     */
    private String salesToSmallZone;
    /**
     *主责快递员在快递车辆中对应的开单营业部code
     * */
    private String depCoordinate;
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

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

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getGisid() {
		return gisid;
	}

	public void setGisid(String gisid) {
		this.gisid = gisid;
	}

	public String getGisArea() {
		return gisArea;
	}

	public void setGisArea(String gisArea) {
		this.gisArea = gisArea;
	}

	public String getBigzonecode() {
		return bigzonecode;
	}

	public void setBigzonecode(String bigzonecode) {
		this.bigzonecode = bigzonecode;
	}

	public String getBigzonecode1() {
		return bigzonecode1;
	}

	public void setBigzonecode1(String bigzonecode1) {
		this.bigzonecode1 = bigzonecode1;
	}

	public String getBigzoneName() {
		return bigzoneName;
	}

	public void setBigzoneName(String bigzoneName) {
		this.bigzoneName = bigzoneName;
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

	public String getCourierCode() {
		return courierCode;
	}

	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getMapState() {
		return mapState;
	}

	public void setMapState(String mapState) {
		this.mapState = mapState;
	}

	public String getSalesToSmallZone() {
		return salesToSmallZone;
	}

	public void setSalesToSmallZone(String salesToSmallZone) {
		this.salesToSmallZone = salesToSmallZone;
	}

	public String getDepCoordinate() {
		return depCoordinate;
	}

	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}

	
	
}
