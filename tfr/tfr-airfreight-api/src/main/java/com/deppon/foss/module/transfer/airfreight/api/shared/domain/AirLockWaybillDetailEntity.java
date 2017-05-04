package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 空运锁票明细
 * @author foss-105795-wqh
 * @date 2014-03-14 
 */
public class AirLockWaybillDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3530868031805817666L;

	//运单号
	private String waybillNo;
	//锁票备注
	private String lockRemark;
	//部门
	private String orgCode;
	//部门名称
	private String orgName;
	//锁票人工号
	private String createUserCode;
	//解锁人工号
	private String modifyUserCode;
	//锁票状态
	private String lockStatus;
	//get and set
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getLockRemark() {
		return lockRemark;
	}
	public void setLockRemark(String lockRemark) {
		this.lockRemark = lockRemark;
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
	
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	
	
}

