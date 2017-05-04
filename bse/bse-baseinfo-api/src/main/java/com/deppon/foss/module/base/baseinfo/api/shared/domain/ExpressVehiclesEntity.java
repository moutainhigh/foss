package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class ExpressVehiclesEntity  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6599813751939900120L;
	/**
	 * 开单营业部code(父级 事业部)
	 */
	private String prentOrgCode;
	
	public String getPrentOrgCode() {
		return prentOrgCode;
	}

	public void setPrentOrgCode(String prentOrgCode) {
		this.prentOrgCode = prentOrgCode;
	}

	//所属区域List
	private List<String> areaCodeList;
    //车牌号
    private String vehicleNo;
    //车型
    private String vehicleLengthCode;
    //车型
    private String vehicleLengthName;
    //所属快递员code
    private String empCode;
    //所属快递员name
    private String empName;
    //开单营业部code
    private String orgCode;
    //开单营业部name
    private String orgName;
    //区域明细
    private List<ExpressVehiclesDetailEntity> expressVehiclesDetailList;
    //手机号 
    private String mobilePhone;
    //是否启用
    private String active;
    //备注
    private String notes;
    
    //区域code
    private String areaCode;
    //区域name
    private String areaName;
    
    //市code
    private String CityCode;
    //市name
    private String CityName;
    
    //省code
    private String ProvCode;
    //省name
    private String ProvName;
    //明细ID
    private String empDistrictId;

    // 数据版本.
    private Long versionNo;
    //修改人
    private String modifyUserName;
	public List<String> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(List<String> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	

	/**
	 * 获取 修改人
	 * @return  the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置修改人
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getVehicleLengthCode() {
		return vehicleLengthCode;
	}

	public void setVehicleLengthCode(String vehicleLengthCode) {
		this.vehicleLengthCode = vehicleLengthCode;
	}

	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityCode() {
		return CityCode;
	}

	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getProvCode() {
		return ProvCode;
	}

	public void setProvCode(String provCode) {
		ProvCode = provCode;
	}

	public String getProvName() {
		return ProvName;
	}

	public void setProvName(String provName) {
		ProvName = provName;
	}

	public String getEmpDistrictId() {
		return empDistrictId;
	}

	public void setEmpDistrictId(String empDistrictId) {
		this.empDistrictId = empDistrictId;
	}

	public List<ExpressVehiclesDetailEntity> getExpressVehiclesDetailList() {
		return expressVehiclesDetailList;
	}

	public void setExpressVehiclesDetailList(
			List<ExpressVehiclesDetailEntity> expressVehiclesDetailList) {
		this.expressVehiclesDetailList = expressVehiclesDetailList;
	}
}
