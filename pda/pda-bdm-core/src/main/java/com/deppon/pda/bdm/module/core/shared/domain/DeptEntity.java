package com.deppon.pda.bdm.module.core.shared.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
  * @ClassName DeptEntity 
  * @Description 部门实体 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class DeptEntity extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 组织编号
	 */
	private String deptCode;
	
	/**
	 * 外场编号
	 */
	private String deptNo;
	
	/**
	 * 组织名称
	 */
	private String deptName;
	
	/**
	 * 组织联系方式
	 */
	private String phoneAndFax;
	
	/**
	 * 组织标准编码
	 */
	private String deptStandCode;
	
	
	/**
	 * 组织性质
	 */
	private String deptAttribute;
	
	/**
	 * 是否财务实体
	 */
	private String isBizUnit;
	
	/**
	 * 组织省份
	 */
	private String deptProvince;
	
	/**
	 * 组织城市
	 */
	private String deptCity;
	
	/**
	 * 组织区县
	 */
	private String deptCounty;
	
	/**
	 * 组织地址
	 */
	private String deptAddress;

	/**
	 * 是否启用
	 */
	private String status;
	
	/**
	 * 是否空运配载
	 */
	private String isAirAssembly;
	
	/**
	 * 是否汽运配载
	 */
	private String isCarAssembly;
	
	/**
	 * 是否外发配载
	 */
	private String isOutAssembly;
	
	/**
	 * 是否外场
	 */
	private String isOutStorage;
	
	/**
	 * 是否中转
	 */
	private String isTransfer;
	
	/**
	 * 是否到达
	 */
	private String isArrived;
	
	/**
	 * 操作类型
	 */
	private String operFlag;
	
	/**
	 * 变化时间
	 */
	private String updTime;
	
	/**
	 * 是否激活
	 */
	private String isActive;
	
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 上级组织
	 */
	private String parentOrgCode;
	
	/**
	 * 是否驻地部门
	 */
	private String isstation;
	/**
	 * 是否顶级车队
	 */
	private String isTopFleet;
	
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPhoneAndFax() {
		return phoneAndFax;
	}

	public void setPhoneAndFax(String phoneAndFax) {
		this.phoneAndFax = phoneAndFax;
	}

	public String getDeptStandCode() {
		return deptStandCode;
	}

	public void setDeptStandCode(String deptStandCode) {
		this.deptStandCode = deptStandCode;
	}

	public String getDeptAttribute() {
		return deptAttribute;
	}

	public void setDeptAttribute(String deptAttribute) {
		this.deptAttribute = deptAttribute;
	}

	public String getIsBizUnit() {
		return isBizUnit;
	}

	public void setIsBizUnit(String isBizUnit) {
		this.isBizUnit = isBizUnit;
	}

	public String getDeptProvince() {
		return deptProvince;
	}

	public void setDeptProvince(String deptProvince) {
		this.deptProvince = deptProvince;
	}

	public String getDeptCity() {
		return deptCity;
	}

	public void setDeptCity(String deptCity) {
		this.deptCity = deptCity;
	}

	public String getDeptCounty() {
		return deptCounty;
	}

	public void setDeptCounty(String deptCounty) {
		this.deptCounty = deptCounty;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsAirAssembly() {
		return isAirAssembly;
	}

	public void setIsAirAssembly(String isAirAssembly) {
		this.isAirAssembly = isAirAssembly;
	}

	public String getIsCarAssembly() {
		return isCarAssembly;
	}

	public void setIsCarAssembly(String isCarAssembly) {
		this.isCarAssembly = isCarAssembly;
	}

	public String getIsOutAssembly() {
		return isOutAssembly;
	}

	public void setIsOutAssembly(String isOutAssembly) {
		this.isOutAssembly = isOutAssembly;
	}

	public String getIsOutStorage() {
		return isOutStorage;
	}

	public void setIsOutStorage(String isOutStorage) {
		this.isOutStorage = isOutStorage;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getIsArrived() {
		return isArrived;
	}

	public void setIsArrived(String isArrived) {
		this.isArrived = isArrived;
	}

	

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	@JsonIgnore
	public String getIsstation() {
		return isstation;
	}

	public void setIsstation(String isstation) {
		this.isstation = isstation;
	}

	public String getIsTopFleet() {
		return isTopFleet;
	}

	public void setIsTopFleet(String isTopFleet) {
		this.isTopFleet = isTopFleet;
	}
	
	
}
