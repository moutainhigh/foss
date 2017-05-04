package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
  * @ClassName ExpressVehicleEntity 
  * @Description TODO 快递车辆
  * @author mt 
  * @date 2013-8-16 上午9:36:59
 */
public class ExpressVehicleEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8986540543628617504L;
	/**
	 * 车牌
	 */
	private String vehicleNo;
	/**
	 * 车型
	 */
	private String vehiclelenghtCode;
	/**
	 * 所属快递员
	 */
	private String empCode;
	/**
	 * 开单营业部
	 */
	private String orgCode;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 数据版本
	 */
	private String versionNo;
	/**
	 * 操作标识
	 */
	private String operFlag;
	/**
	 * 备注
	 */
	private String description;
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehiclelenghtCode() {
		return vehiclelenghtCode;
	}
	public void setVehiclelenghtCode(String vehiclelenghtCode) {
		this.vehiclelenghtCode = vehiclelenghtCode;
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
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
