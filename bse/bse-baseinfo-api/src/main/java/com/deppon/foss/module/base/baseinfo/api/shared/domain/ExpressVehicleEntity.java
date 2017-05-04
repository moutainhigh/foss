package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递车实体
 * 
 * @author WangPeng
 * @date   2013-07-29 10:48 PM
 *
 */
public class ExpressVehicleEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8898805266258502472L;

	/**
	 * 车牌
	 */
	private String vehicleNo;
	
	/**
	 * 车型 
	 */
	private String vehicleLengthCode;
	
	/**
	 * 所属快递员工号
	 */
	private String empCode;
	
	/**
	 * 开单营业部
	 */
	private String orgCode;
	
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/**
	 * 备注
	 */
	private String description;
	
	
	/**
	 * 是否启用
	 */
	private String active;
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleLengthCode() {
		return vehicleLengthCode;
	}
	public void setVehicleLengthCode(String vehicleLengthCode) {
		this.vehicleLengthCode = vehicleLengthCode;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

}
