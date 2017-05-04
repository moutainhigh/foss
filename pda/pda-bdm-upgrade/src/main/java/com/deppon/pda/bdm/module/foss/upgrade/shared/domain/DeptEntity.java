package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(部门实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:29:22,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:29:22
 * @since
 * @version
 */
public class DeptEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
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
	 * 是否自动分拣
	 */
	private String sortMachine;
	
	/**
	 * 是否可货到付款
	 */
	private String isCaseDelivery;
	
	/**
	 * 是否可代收货款
	 */
	private String isCanAgentCollected;
	
	/**
	 * 负责人
	 */
	private String principalName;
	
	/**
	 * 是否快递虚拟营业部
	 */
	private String salesDepartment;
	
	/**
	 * 是否快递大区
	 */
	private String bigRegion;
	
	/**
	 * 是否快递点部
	 */
	private String part;
	
	/**
	 * 可落地外发配载
	 */
	private String outAssemble;
	/**
	 * 是否驻地部门
	 */
	private String isstation;
	
	public String getIsstation() {
		return isstation;
	}

	public void setIsstation(String isstation) {
		this.isstation = isstation;
	}

	public String getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getOutAssemble() {
		return outAssemble;
	}

	public void setOutAssemble(String outAssemble) {
		this.outAssemble = outAssemble;
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

	public String getSortMachine() {
		return sortMachine;
	}

	public void setSortMachine(String sortMachine) {
		this.sortMachine = sortMachine;
	}

	public String getIsCaseDelivery() {
		return isCaseDelivery;
	}

	public void setIsCaseDelivery(String isCaseDelivery) {
		this.isCaseDelivery = isCaseDelivery;
	}

	public String getIsCanAgentCollected() {
		return isCanAgentCollected;
	}

	public void setIsCanAgentCollected(String isCanAgentCollected) {
		this.isCanAgentCollected = isCanAgentCollected;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	
	
}
