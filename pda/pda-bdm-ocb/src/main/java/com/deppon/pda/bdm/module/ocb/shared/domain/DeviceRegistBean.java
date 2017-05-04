package com.deppon.pda.bdm.module.ocb.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DeviceRegistBean extends BaseEntity{
	
	private static final long serialVersionUID = 2053187760194384128L;
	
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 工号
	 */
	private String userCode;
	/**
	 * 设备号
	 */
	private String dvcCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * SIM卡号
	 */
	private String simCardCode;
	/**
	 * 型号
	 */
	private String model;
	/**
	 * 所属机构编码
	 */
	private String companyCode;
	/**
	 * 购买日期
	 */
	private Date purDate;
	/**
	 * 最后更新日期
	 */
	private Date lastUpdDate;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 资产编号
	 */
	private String sernbr;
	/**
	 * 是否有笔
	 */
	private String hasPen;
	/**
	 * 是否有包
	 */
	private String hasPackage;
	
	/**
	 * 系统类型
	 */
	private String systemType;
	//车牌号  DN201605250010 需要加上外请车司机的车牌号
	private String truckCode;
	public String getDvcCode() {
		return dvcCode;
	}
	public void setDvcCode(String dvcCode) {
		this.dvcCode = dvcCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSimCardCode() {
		return simCardCode;
	}
	public void setSimCardCode(String simCardCode) {
		this.simCardCode = simCardCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Date getPurDate() {
		return purDate;
	}
	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSernbr() {
		return sernbr;
	}
	public void setSernbr(String sernbr) {
		this.sernbr = sernbr;
	}
	public String getHasPen() {
		return hasPen;
	}
	public void setHasPen(String hasPen) {
		this.hasPen = hasPen;
	}
	public String getHasPackage() {
		return hasPackage;
	}
	public void setHasPackage(String hasPackage) {
		this.hasPackage = hasPackage;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	

}
