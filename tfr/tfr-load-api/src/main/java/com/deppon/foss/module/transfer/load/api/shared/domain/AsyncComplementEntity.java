package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AsyncComplementEntity extends BaseEntity {

	private static final long serialVersionUID = 1790278157039214137L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 补码提货网点编码
	 */
	private String pkpOrgCode;

	/**
	 * 补码提货网点名称
	 */
	private String pkpOrgName;

	/**
	 * 补码提货网点是否落地配标识
	 */
	private String ldpFlag;

	/**
	 * 补码员工工号
	 */
	private String empCode;

	/**
	 * 补码员工姓名
	 */
	private String empName;

	/**
	 * 补码员工所在部门编码
	 */
	private String empDeptCode;

	/**
	 * 补码员工所在部门名称
	 */
	private String empDeptName;

	/**
	 * 补码外场编码，有可能在非外场补码
	 */
	private String tfrCtrCode;

	/**
	 * 补码外场名称，有可能在给外场补码
	 */
	private String tfrCtrName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 是否异步补码失败Y/N
	 */
	private String failed;

	/**
	 * 失败原因
	 */
	private String failInfo;

	/**
	 * 运单原提货网点编码
	 */
	private String beforePkpOrgCode;

	/**
	 * 运单原提货网点名称
	 */
	private String beforePkpOrgName;
	
	/**
	 * 匹配模式 326027
	 */
	private String matchType;

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPkpOrgCode() {
		return pkpOrgCode;
	}

	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}

	public String getPkpOrgName() {
		return pkpOrgName;
	}

	public void setPkpOrgName(String pkpOrgName) {
		this.pkpOrgName = pkpOrgName;
	}

	public String getLdpFlag() {
		return ldpFlag;
	}

	public void setLdpFlag(String ldpFlag) {
		this.ldpFlag = ldpFlag;
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

	public String getEmpDeptCode() {
		return empDeptCode;
	}

	public void setEmpDeptCode(String empDeptCode) {
		this.empDeptCode = empDeptCode;
	}

	public String getEmpDeptName() {
		return empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public String getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}

	public String getBeforePkpOrgCode() {
		return beforePkpOrgCode;
	}

	public void setBeforePkpOrgCode(String beforePkpOrgCode) {
		this.beforePkpOrgCode = beforePkpOrgCode;
	}

	public String getBeforePkpOrgName() {
		return beforePkpOrgName;
	}

	public void setBeforePkpOrgName(String beforePkpOrgName) {
		this.beforePkpOrgName = beforePkpOrgName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "AsyncComplementEntity [id=" + super.getId() + ", waybillNo=" + waybillNo + ", createTime=" + createTime
				+ ", failed=" + failed + ", failInfo=" + failInfo + "]";
	}

}
