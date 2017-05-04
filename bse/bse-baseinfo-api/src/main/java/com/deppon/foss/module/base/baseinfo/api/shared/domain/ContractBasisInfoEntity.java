package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 合同基础信息
 * @author 308861 
 * @date 2016-8-12 下午3:29:29
 */
public class ContractBasisInfoEntity extends BaseEntity{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 合同编号(唯一标识)
	 */
	private String ptpId;
	/**
	 * 合同营业部门编码
	 */
	private String pointNumber;
	
	/**
	 * 合同营业部门名称
	 */
	private String pointName;
	
	/**
	 * 对接营业部门编码
	 */
	private String dockingDepNumber;
	
	/**
	 * 对接营业部门名称
	 */
	private String dockingDepName;
	
	/**
	 * 合同生效时间
	 */
	private Date contractStateTime;
	
	/**
	 * 合同终止时间
	 */
	private Date contractEndTime;
	
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 操作类型（传入整数，1、新增；2、作废）
	 */
	private String operatorSign;
	
	/**
	 * 版本号
	 */
	private String versionNo;
	
	public String getPtpId() {
		return ptpId;
	}

	public void setPtpId(String ptpId) {
		this.ptpId = ptpId;
	}

	public String getPointNumber() {
		return pointNumber;
	}

	public void setPointNumber(String pointNumber) {
		this.pointNumber = pointNumber;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getDockingDepNumber() {
		return dockingDepNumber;
	}

	public void setDockingDepNumber(String dockingDepNumber) {
		this.dockingDepNumber = dockingDepNumber;
	}

	public String getDockingDepName() {
		return dockingDepName;
	}

	public void setDockingDepName(String dockingDepName) {
		this.dockingDepName = dockingDepName;
	}

	public Date getContractStateTime() {
		return contractStateTime;
	}

	public void setContractStateTime(Date contractStateTime) {
		this.contractStateTime = contractStateTime;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getOperatorSign() {
		return operatorSign;
	}

	public void setOperatorSign(String operatorSign) {
		this.operatorSign = operatorSign;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
}
